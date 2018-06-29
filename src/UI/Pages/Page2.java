package UI.Pages;

import Bean.Msg.Message;
import Bean.Msg.TextMsg;
import Client.UserHandle;
import UI.Component.MyListCellRenderer;
import UI.subPage.AddObject;

import javax.swing.*;
import javax.swing.border.TitledBorder;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

public class Page2 extends JPanel {

    private JFrame frame;
    private JList msgList;
    private JList userList;
    private JList groupList;

    private JButton send;
    private JTextField message;
    private JScrollPane msgPane;


    private boolean inGroup;
    private int list_index=-1;

    public Page2(JFrame frame){
        this.frame=frame;
        this.setLayout(new BorderLayout());
        this.setBorder(new TitledBorder("社交"));


        msgList=new JList();

        userList=new JList(UserHandle.userList);
        groupList=new JList(UserHandle.groupList);

        send=new JButton("发送");
        message=new JTextField();

        //消息来源
        JLabel sysMsg=new JLabel("系统消息");

        JScrollPane userPane=new JScrollPane(userList);
        userPane.setBorder(new TitledBorder("用户"));
        JScrollPane groupPane=new JScrollPane(groupList);
        groupPane.setBorder(new TitledBorder("社团"));

        JSplitPane userMsg=new JSplitPane(JSplitPane.VERTICAL_SPLIT,userPane,groupPane);
        userMsg.setDividerLocation(220);

        JSplitPane leftPane=new JSplitPane(JSplitPane.VERTICAL_SPLIT,sysMsg,userMsg);
        leftPane.setDividerLocation(30);

        //消息面板
        msgList.setCellRenderer(new MyListCellRenderer());
        msgList.setFixedCellHeight(30);
        msgPane=new JScrollPane(msgList);
        msgPane.setBorder(new TitledBorder("消息"));

        //发送消息面板
        JPanel sendPane = new JPanel(new BorderLayout());
        sendPane.setBorder(new TitledBorder("回复"));
        sendPane.add(message, "Center");
        sendPane.add(send, "East");

        //总布局
        JSplitPane rightPane=new JSplitPane(JSplitPane.VERTICAL_SPLIT,msgPane,sendPane);
        rightPane.setDividerLocation(440);
        JSplitPane pane=new JSplitPane(JSplitPane.HORIZONTAL_SPLIT,leftPane,rightPane);
        pane.setDividerLocation(100);

        this.add(pane);

        //创建右键菜单
        JPopupMenu userMenu=new JPopupMenu();
        JPopupMenu groupMenu=new JPopupMenu();


        //创建菜单项组件
        JMenuItem add_user=new JMenuItem("添加好友");
        JMenuItem remove_user=new JMenuItem("删除好友");
        JMenuItem join_group=new JMenuItem("加入社团");
        JMenuItem create_group=new JMenuItem("创建社团");
        JMenuItem quit_group=new JMenuItem("退出社团");
        userMenu.add(add_user);
        userMenu.add(remove_user);
        groupMenu.add(join_group);
        groupMenu.add(create_group);
        groupMenu.add(quit_group);


        //事件监听
        message.addActionListener((event)->{
            sendMsg();
        });

        // 单击发送按钮时事件
        send.addActionListener((event)->{
            sendMsg();
        });

        frame.add(userMenu);
        frame.add(groupMenu);

        //好友列表事件
        userList.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseReleased(MouseEvent e) {
                if (e.getButton() == e.BUTTON1) {
                    int index=userList.getSelectedIndex();
                    if (index!=-1&e.getClickCount()==2){
                        inGroup=false;
                        list_index=index;
                        msgList.setModel(UserHandle.userChat.get(index));
                        msgPane.setBorder(new TitledBorder("当前好友："+UserHandle.userList.get(index)));
                    }
                } else if (e.getButton() == e.BUTTON3) {
                    userMenu.show(e.getComponent(),e.getX(),e.getY());
                }
            }
        });

        //社团列表事件
        groupList.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseReleased(MouseEvent e) {
                if (e.getButton() == e.BUTTON1) {
                    int index=groupList.getSelectedIndex();
                    if (index!=-1&e.getClickCount()==2){
                        inGroup=true;
                        list_index=index;
                        msgList.setModel(UserHandle.groupChat.get(index));
                        msgPane.setBorder(new TitledBorder("当前社团："+UserHandle.groupList.get(index)));
                    }
                } else if (e.getButton() == e.BUTTON3) {
                    groupMenu.show(e.getComponent(),e.getX(),e.getY());
                }
            }
        });

        add_user.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                //System.out.println("ADD_USER");
                new AddObject("添加好友",0);
            }
        });
        remove_user.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                int index=userList.getSelectedIndex();
                if (index!=-1){
                    if (UserHandle.removeFriend((String)UserHandle.userList.get(index))){
                        UserHandle.userList.remove(index);
                        UserHandle.showMsg("删除好友成功!");
                    }
                }
            }
        });
        join_group.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                //System.out.println("JOIN_GROUP");
                new AddObject("加入社团",1);
            }
        });
        create_group.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                new AddObject( "创建社团",2);
            }
        });
        quit_group.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                int index=groupList.getSelectedIndex();
                if (index!=-1){
                    if (UserHandle.quitGroup((String)UserHandle.groupList.get(index))){
                        UserHandle.groupList.remove(index);
                        UserHandle.showMsg("退出社团成功!");
                    }
                }
            }
        });
    }


    public void sendMsg(){
        //TODO write your logic here
        if (list_index!=-1){
            if (inGroup){
                Message msg=new TextMsg(UserHandle.user.getUid(),(String)UserHandle.groupList.get(list_index),message.getText(),true);
                UserHandle.msgHandle.sendMsg(msg);
                UserHandle.groupChat.get(list_index).addElement("我："+message.getText());
            } else {
                Message msg=new TextMsg(UserHandle.user.getUid(),(String)UserHandle.userList.get(list_index),message.getText(),false);
                UserHandle.msgHandle.sendMsg(msg);
                UserHandle.userChat.get(list_index).addElement("我："+message.getText());
            }
        }
        message.setText(null);
    }
}
