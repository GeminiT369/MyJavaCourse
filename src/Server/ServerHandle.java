package Server;

import Bean.Article;
import Bean.Commit;
import Bean.Group;
import Bean.Msg.*;
import Bean.User;

import javax.swing.*;
import java.io.*;
import java.net.*;
import java.util.ArrayList;
import java.util.List;

import static Server.MyDataBase.*;

public class ServerHandle extends Thread {

    private User user;

    private boolean stop;
    private ObjectInputStream ois;
    private ObjectOutputStream oos;

    public static JTextArea textArea;

    public ServerHandle(Socket socket){
        this.stop=false;
        this.user=new User(socket,true);
        this.ois=user.getObjectInputStream();
        this.oos=user.getObjectOutputStream();
    }

    @Override
    public void run() {
        try {
            while (!stop) {
                Message msg = (Message) ois.readObject();
                System.out.println(msg.toString());
                if (msg instanceof CtrlMsg)
                    handleCommend((CtrlMsg)msg);
                else if(msg instanceof TextMsg)
                    forward(msg);
                else if (msg instanceof FileMsg)
                    forward(msg);
                else if (msg instanceof AtcMsg){
                    Article article=((AtcMsg) msg).getArticle();
                    article.setAid(articles.size());
                    articles.add(article);
                    commits.put(article.getAid(),new ArrayList());
                }else if (msg instanceof CmtMsg){
                    commits.get(Integer.parseInt(msg.dst)).add(((CmtMsg) msg).getCommit());
                } else
                    System.out.println("Unknown Msg Kind!");
            }
        }catch (SocketException se){
            close();
        }catch (IOException ie){
            ie.printStackTrace();
        }catch (ClassNotFoundException ce){
            ce.printStackTrace();
        }
    }


    public void forward(Message msg,User dst) throws IOException{
        if (dst!=null&&dst!=user){
            ObjectOutputStream oos=dst.getObjectOutputStream();
            textArea.append(msg.toString()+'\n');
            oos.writeObject(msg);
            oos.flush();
        }
    }

    public void forward(Message msg) throws IOException{
        if (msg.isGMsg()){
            List<User> group =groupMap.get(msg.dst).getList();
            for (User dst:group){
                System.out.println(dst.getUid());
                forward(msg,dst);
            }
        }else {
            forward(msg,userMap.get(msg.dst));
            textArea.append(msg.toString()+'\n');
        }
    }

    public void handleCommend(CtrlMsg msg) throws IOException{
        int flag=0;
        CtrlMsg ctrlMsg=new CtrlMsg("SERVER",msg.getSrc(),-1);
        CtrlMsg notify=new CtrlMsg("SERVER",msg.getDst(),-1);
        switch (msg.getCmd()){
            case CtrlMsg.LOGIN:
                user.setUid(msg.getSrc());
                if (userMsg.get(msg.src)==null){
                    ctrlMsg.setCmd(CtrlMsg.NO_SUCH_USER);
                }else if (!userMsg.get(msg.src).equals(msg.getExtMsg())){
                    ctrlMsg.setCmd(CtrlMsg.WRONG_PASSWORD);
                }else {
                    System.out.println("User uid:"+msg.src+" Login.");
                    userMap.put(user.getUid(),user);
                    userModel.addElement(user.getUid());
                    ctrlMsg.setCmd(CtrlMsg.OPERATE_SUCCESS);
                    System.out.println("User uid:"+user.getUid()+" is Connected!");
                }
                break;
            case CtrlMsg.REGISTER:
                user.setUid(msg.getSrc());
                if (userMsg.get(msg.src)==null){
                    System.out.println("User uid:"+msg.src+" Register.");
                    userMsg.put(msg.src,ctrlMsg.getExtMsg());
                    userMap.put(user.getUid(),user);
                    userModel.addElement(user.getUid());

                    ctrlMsg.setCmd(CtrlMsg.OPERATE_SUCCESS);
                    System.out.println("User uid:"+user.getUid()+" is Connected!");
                } else {
                    ctrlMsg.setCmd(CtrlMsg.UID_ILLEGAL);
                    System.out.println("UID is illegal. uid:"+msg.getDst());
                }
                break;
            case CtrlMsg.GET_RECOMMEND:
                ctrlMsg.setCmd(CtrlMsg.OPERATE_SUCCESS);
                flag=1;
                break;
            case CtrlMsg.GET_SUBMIT:
                ctrlMsg.setCmd(CtrlMsg.OPERATE_SUCCESS);
                flag=2;
                break;
            case CtrlMsg.GET_COMMIT:
                ctrlMsg.setCmd(CtrlMsg.OPERATE_SUCCESS);
                flag=3;
                break;
            case CtrlMsg.CHECK_USER:
                if (userMap.get(msg.getDst())==null){
                    ctrlMsg.setCmd(CtrlMsg.NO_SUCH_USER);
                }else {
                    ctrlMsg.setCmd(CtrlMsg.OPERATE_SUCCESS);
                }
                break;
            case CtrlMsg.JOIN_GROUP:
                Group g=groupMap.get(msg.getDst());
                if(g!=null){
                    groupModel.addElement(g.getGid());
                    g.addUser(user);
                    //反馈
                    ctrlMsg.setCmd(CtrlMsg.OPERATE_SUCCESS);
                    System.out.println("User uid:"+user.getUid()+" join group:"+msg.getDst());
                }else {
                    ctrlMsg.setCmd(CtrlMsg.NO_SUCH_GROUP);
                    System.out.println("NO such Gid:"+msg.getDst());
                }
                break;
            case CtrlMsg.ADD_GROUP:
                if (groupMap.get(msg.getDst())==null){
                    groupMap.put(msg.getDst(),new Group(msg.getDst()));
                    ctrlMsg.setCmd(CtrlMsg.OPERATE_SUCCESS);
                    groupMap.get(msg.getDst()).addUser(user);
                    System.out.println("User uid:"+user.getUid()+" create group:"+msg.getDst());
                } else {
                    ctrlMsg.setCmd(CtrlMsg.GID_ILLEGAL);
                    System.out.println("Illegal Gid:"+msg.getDst());
                }
                break;
            case CtrlMsg.QUIT_GROUP:
                if (groupMap.get(msg.getDst())!=null){
                    groupMap.get(msg.getDst()).removeUser(user);
                }
                ctrlMsg.setCmd(CtrlMsg.OPERATE_SUCCESS);
                break;
            case CtrlMsg.ADD_USER:
                if (userMap.get(msg.getDst())==null){
                    ctrlMsg.setCmd(CtrlMsg.NO_SUCH_USER);
                }else {
                    ctrlMsg.setCmd(CtrlMsg.OPERATE_SUCCESS);
                }
                break;
            case CtrlMsg.DELETE_USER:
                ctrlMsg.setCmd(CtrlMsg.OPERATE_SUCCESS);
                break;
            case CtrlMsg.DISCONNECT:
                ctrlMsg.setCmd(CtrlMsg.OPERATE_SUCCESS);
                notify.setCmd(CtrlMsg.DELETE_USER);
                flag=-1;
                break;
        }
        sendMsg(ctrlMsg);
        if (notify.getCmd()!=-1){
            sendToAll(notify);
        }

        //后续处理
        switch (flag){
            case 0:
                break;
            case -1:
                close();
                break;
            case 1:
                sendArticle(1);
                break;
            case 2:
                sendArticle(2);
                break;
            case 3:
                sendCommit(msg.dst);
                break;
        }
    }

    public void sendArticle(int flag) throws IOException{
        int index;
        Article article;
        for (int i=0;i<5;i++){
            index=(int)(Math.random()*articles.size());
            article=articles.get(index);
            sendMsg(new AtcMsg(article.getAuthor(),article));
        }
    }

    public void sendCommit(String aid) throws IOException{
        List<Commit> cmts=commits.get(Integer.parseInt(aid));
        for (var cmt:cmts){
            sendMsg(new CmtMsg(cmt));
        }
    }

    public void sendMsg(Message msg) throws IOException {
        oos.writeObject(msg);
        oos.flush();
    }

    public void sendToAll(Message msg) throws IOException{
        var users=userMap.entrySet();
        for (var user:users){
            user.getValue().getObjectOutputStream().writeObject(msg);
        }
    }


    public void close(){
        stop=true;
        userMap.remove(user.getUid());
        user.destroy();
        userMap.remove(user.getUid());
        userModel.removeElement(user.getUid());
        textArea.append("\nDisconnected with user uid:"+user.getUid());
    }
}
