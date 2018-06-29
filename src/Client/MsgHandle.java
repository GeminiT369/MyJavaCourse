package Client;

import Bean.Article;
import Bean.Msg.*;
import Bean.User;


import javax.swing.*;
import java.io.*;

public class MsgHandle extends Thread{

    private User user;
    private String uid;
    private boolean stop;
    private ObjectInputStream is;
    private ObjectOutputStream os;

    public MsgHandle(User user) {
        this.user=user;
        this.stop=false;
        this.os = user.getObjectOutputStream();
        this.is = user.getObjectInputStream();
    }

    public void setUid(String uid) {
        this.uid = uid;
        user.setUid(uid);
    }


    @Override
    public void run() {
        while (!stop) {
            try {
                Message msg = (Message) is.readObject();
                System.out.println(msg.toString());
                if (msg instanceof CtrlMsg) {
                    handleCtrlMsg((CtrlMsg) msg);
                    UserHandle.stateCode.putStateCode(((CtrlMsg) msg).getCmd());
                } else if (msg instanceof FileMsg) {
                    System.out.println(msg.getMsg());
                    System.out.println("Receiving File...");
                    ((FileMsg) msg).receiveFile(new File("E:\\Codes\\"));
                    addMsgToList(msg);
                } else if (msg instanceof TextMsg) {
                    System.out.println(msg.getMsg());
                    addMsgToList(msg);
                }else if (msg instanceof AtcMsg){
                    Article article=((AtcMsg) msg).getArticle();
                    UserHandle.atcList.addElement(article.formatDisplay());
                    UserHandle.articles.add(article);
                }else if (msg instanceof CmtMsg){
                    UserHandle.cmtList.addElement(msg.getMsg());
                }

            } catch (IOException ie) {
                ie.printStackTrace();
            } catch (ClassNotFoundException ce) {
                ce.printStackTrace();
            }
        }
    }

    private void addMsgToList(Message msg){
        if (msg.isGMsg()){
            for (int i=0;i<UserHandle.groupList.size();i++){
                if (((String)(UserHandle.groupList.get(i))).equals(msg.dst)){
                    UserHandle.groupChat.get(i).addElement(msg.getMsg());
                    break;
                }
            }
        }else {
            for (int i=0;i<UserHandle.userList.size();i++){
                if (((String)(UserHandle.userList.get(i))).equals(msg.src)){
                    UserHandle.userChat.get(i).addElement(msg.getMsg());
                    break;
                }
            }
        }
    }


    private void handleCtrlMsg(CtrlMsg msg){
        int index;
        switch (msg.getCmd()){
            case CtrlMsg.OPERATE_SUCCESS:
                break;
            case CtrlMsg.ADD_USER:
                if (!msg.getDst().equals(uid)) {
                    UserHandle.userList.addElement(msg.getDst());
                    UserHandle.userChat.add(new DefaultListModel());
                }
                break;
            case CtrlMsg.ADD_GROUP:
                UserHandle.groupList.addElement(msg.getDst());
                UserHandle.groupChat.add(new DefaultListModel());
                break;
            case CtrlMsg.DELETE_USER:
                index=UserHandle.userList.indexOf(msg.getDst());
                UserHandle.userChat.remove(index);
                UserHandle.userList.remove(index);
                break;
            case CtrlMsg.DELETE_GROUP:
                index=UserHandle.groupList.indexOf(msg.getDst());
                UserHandle.groupChat.remove(index);
                UserHandle.groupList.remove(index);
                break;
            case CtrlMsg.DISCONNECT:
                close();
                break;
        }
    }

    public void sendMsg(Message msg){
        try {
            os.writeObject(msg);
            os.flush();
        } catch (IOException ie) {
            ie.printStackTrace();
        }
    }

    public int sendCtrlMsg(String dst,int cmd,String msg){
        sendMsg(new CtrlMsg(uid,dst,cmd,msg));
        return UserHandle.stateCode.getStateCode();
    }

    public int sendCtrlMsg(String dst,int cmd){
        return sendCtrlMsg(dst,cmd,null);
    }


    public boolean sendTextMsg(String dst,String text,boolean isGMsg){
        sendMsg(new TextMsg(uid,dst,text,isGMsg));
        return false;
    }

    public boolean sendFileMsg(String dst,File file,boolean isGMsg){
        sendMsg(new FileMsg(uid,dst,file,isGMsg));
        return false;
    }

    public boolean sendAtcMsg(Article article){
        sendMsg(new AtcMsg(uid,article));
        return false;
    }



    public void close(){
        stop=true;
        UserHandle.showMsg("服务器已下线!");
    }

}
