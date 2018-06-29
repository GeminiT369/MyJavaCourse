package Client;


import Bean.Article;
import Bean.Msg.CmtMsg;
import Bean.Msg.CtrlMsg;
import Bean.StateCode;
import Bean.User;

import javax.swing.*;
import java.io.IOException;
import java.net.Socket;
import java.util.ArrayList;
import java.util.List;

public class UserHandle {

    //用户数据
    public static User user;
    public static Socket socket;
    public static MsgHandle msgHandle;

    public static DefaultListModel cmtList;
    public static DefaultListModel atcList;
    public static DefaultListModel userList;
    public static DefaultListModel groupList;

    public static List<Article> articles;
    public static List<DefaultListModel> userChat;
    public static List<DefaultListModel> groupChat;

    public static JFrame frame;
    public static StateCode stateCode;
    //配置信息
    private static final int port=3456;
    private static final String host="127.0.0.1";
    private static final String server="SERVER";

    static {
        try {
            socket=new Socket(host,port);
            user=new User(socket,false);
            msgHandle =new MsgHandle(user);
            articles=new ArrayList<>();
            cmtList=new DefaultListModel();
            atcList=new DefaultListModel();
            userList=new DefaultListModel();
            groupList=new DefaultListModel();
            userChat=new ArrayList<>();
            groupChat=new ArrayList<>();
            stateCode=new StateCode();
            msgHandle.start();
        }catch (IOException ie){
            System.out.println("服务器未启动！");
        }
    }

    public static boolean login(String acnt,String pswd){
        if (msgHandle==null){
            showMsg("服务器未启动!");
            return false;
        }
        msgHandle.setUid(acnt);
        int code=msgHandle.sendCtrlMsg(acnt,CtrlMsg.LOGIN,pswd);
        if(code!=CtrlMsg.OPERATE_SUCCESS){
            switch (code){
                case CtrlMsg.NO_SUCH_USER:
                    showError("用户不存在!");
                    break;
                case CtrlMsg.WRONG_PASSWORD:
                    showError("密码错误!");
            }
            return false;
        }else {
            msgHandle.setUid(acnt);
            return true;
        }
    }

    public static boolean register(String acnt,String pswd){
        if (msgHandle==null){
            showMsg("服务器未启动!");
            return false;
        }
        msgHandle.setUid(acnt);
        int code=msgHandle.sendCtrlMsg(acnt,CtrlMsg.REGISTER,pswd);
        if(code==CtrlMsg.OPERATE_SUCCESS){
            msgHandle.setUid(acnt);
            return true;
        }else {
            showMsg("用户名已被使用！");
            return false;
        }
    }

    public static boolean addFriend(String uid){
        if (msgHandle.sendCtrlMsg(uid, CtrlMsg.CHECK_USER) == CtrlMsg.NO_SUCH_USER) {
            showMsg("用户不存在!");
            return false;
        } else {
            UserHandle.userList.addElement(uid);
            UserHandle.userChat.add(new DefaultListModel());
            return true;
        }
    }

    public static boolean joinGroup(String gid){
        if (msgHandle.sendCtrlMsg(gid, CtrlMsg.JOIN_GROUP) == CtrlMsg.NO_SUCH_GROUP) {
            showMsg("社团不存在!");
            return false;
        } else {
            UserHandle.groupList.addElement(gid);
            UserHandle.groupChat.add(new DefaultListModel());
            return true;
        }
    }

    public static boolean createGroup(String gid){
        if (msgHandle.sendCtrlMsg(gid, CtrlMsg.ADD_GROUP) == CtrlMsg.GID_ILLEGAL) {
            showMsg("社团名已被使用！");
            return false;
        }else{
            UserHandle.groupList.addElement(gid);
            UserHandle.groupChat.add(new DefaultListModel());
            return true;
        }
    }
    public static boolean removeFriend(String uid){
        if (msgHandle.sendCtrlMsg(uid, CtrlMsg.DELETE_USER) != CtrlMsg.OPERATE_SUCCESS) {
            System.out.println("删除好友失败!");
            return false;
        }else{
            return true;
        }
    }

    public static boolean quitGroup(String gid){
        if (msgHandle.sendCtrlMsg(gid, CtrlMsg.QUIT_GROUP) != CtrlMsg.OPERATE_SUCCESS) {
            System.out.println("退出社团失败!");
            return false;
        }else{
            return true;
        }
    }

    public static void getCommit(int aid){
        msgHandle.sendCtrlMsg(Integer.toString(aid),CtrlMsg.GET_COMMIT);
    }

    public static void deliverCommit(int aid,String content){
        msgHandle.sendMsg(new CmtMsg(user.getUid(),aid,content));
    }

    public static boolean deliverArticle(Article article){
        msgHandle.sendAtcMsg(article);
        return true;
    }

    public static boolean refreshArticle(boolean isRcmd){

        UserHandle.atcList.removeAllElements();
        UserHandle.articles.clear();
        if (isRcmd){
            if (msgHandle.sendCtrlMsg(server,CtrlMsg.GET_RECOMMEND)!=CtrlMsg.OPERATE_SUCCESS){
                System.out.println("获取推荐文章成功");
                return false;
            }
        }else {
            if (msgHandle.sendCtrlMsg(server,CtrlMsg.GET_SUBMIT)!=CtrlMsg.OPERATE_SUCCESS){
                System.out.println("获取关注文章失败");
                return false;
            }
        }
        return true;
    }






    public static void showMsg(String msg){
        JOptionPane.showMessageDialog(frame, msg,
                "提示", JOptionPane.INFORMATION_MESSAGE);
    }

    public static void showError(String msg){
        JOptionPane.showMessageDialog(frame, msg,
                "错误", JOptionPane.ERROR_MESSAGE);
    }

}
