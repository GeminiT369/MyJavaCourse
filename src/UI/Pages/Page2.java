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
        this.setBorder(new TitledBorder("�罻"));


        msgList=new JList();

        userList=new JList(UserHandle.userList);
        groupList=new JList(UserHandle.groupList);

        send=new JButton("����");
        message=new JTextField();

        //��Ϣ��Դ
        JLabel sysMsg=new JLabel("ϵͳ��Ϣ");

        JScrollPane userPane=new JScrollPane(userList);
        userPane.setBorder(new TitledBorder("�û�"));
        JScrollPane groupPane=new JScrollPane(groupList);
        groupPane.setBorder(new TitledBorder("����"));

        JSplitPane userMsg=new JSplitPane(JSplitPane.VERTICAL_SPLIT,userPane,groupPane);
        userMsg.setDividerLocation(220);

        JSplitPane leftPane=new JSplitPane(JSplitPane.VERTICAL_SPLIT,sysMsg,userMsg);
        leftPane.setDividerLocation(30);

        //��Ϣ���
        msgList.setCellRenderer(new MyListCellRenderer());
        msgList.setFixedCellHeight(30);
        msgPane=new JScrollPane(msgList);
        msgPane.setBorder(new TitledBorder("��Ϣ"));

        //������Ϣ���
        JPanel sendPane = new JPanel(new BorderLayout());
        sendPane.setBorder(new TitledBorder("�ظ�"));
        sendPane.add(message, "Center");
        sendPane.add(send, "East");

        //�ܲ���
        JSplitPane rightPane=new JSplitPane(JSplitPane.VERTICAL_SPLIT,msgPane,sendPane);
        rightPane.setDividerLocation(440);
        JSplitPane pane=new JSplitPane(JSplitPane.HORIZONTAL_SPLIT,leftPane,rightPane);
        pane.setDividerLocation(100);

        this.add(pane);

        //�����Ҽ��˵�
        JPopupMenu userMenu=new JPopupMenu();
        JPopupMenu groupMenu=new JPopupMenu();


        //�����˵������
        JMenuItem add_user=new JMenuItem("��Ӻ���");
        JMenuItem remove_user=new JMenuItem("ɾ������");
        JMenuItem join_group=new JMenuItem("��������");
        JMenuItem create_group=new JMenuItem("��������");
        JMenuItem quit_group=new JMenuItem("�˳�����");
        userMenu.add(add_user);
        userMenu.add(remove_user);
        groupMenu.add(join_group);
        groupMenu.add(create_group);
        groupMenu.add(quit_group);


        //�¼�����
        message.addActionListener((event)->{
            sendMsg();
        });

        // �������Ͱ�ťʱ�¼�
        send.addActionListener((event)->{
            sendMsg();
        });

        frame.add(userMenu);
        frame.add(groupMenu);

        //�����б��¼�
        userList.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseReleased(MouseEvent e) {
                if (e.getButton() == e.BUTTON1) {
                    int index=userList.getSelectedIndex();
                    if (index!=-1&e.getClickCount()==2){
                        inGroup=false;
                        list_index=index;
                        msgList.setModel(UserHandle.userChat.get(index));
                        msgPane.setBorder(new TitledBorder("��ǰ���ѣ�"+UserHandle.userList.get(index)));
                    }
                } else if (e.getButton() == e.BUTTON3) {
                    userMenu.show(e.getComponent(),e.getX(),e.getY());
                }
            }
        });

        //�����б��¼�
        groupList.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseReleased(MouseEvent e) {
                if (e.getButton() == e.BUTTON1) {
                    int index=groupList.getSelectedIndex();
                    if (index!=-1&e.getClickCount()==2){
                        inGroup=true;
                        list_index=index;
                        msgList.setModel(UserHandle.groupChat.get(index));
                        msgPane.setBorder(new TitledBorder("��ǰ���ţ�"+UserHandle.groupList.get(index)));
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
                new AddObject("��Ӻ���",0);
            }
        });
        remove_user.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                int index=userList.getSelectedIndex();
                if (index!=-1){
                    if (UserHandle.removeFriend((String)UserHandle.userList.get(index))){
                        UserHandle.userList.remove(index);
                        UserHandle.showMsg("ɾ�����ѳɹ�!");
                    }
                }
            }
        });
        join_group.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                //System.out.println("JOIN_GROUP");
                new AddObject("��������",1);
            }
        });
        create_group.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                new AddObject( "��������",2);
            }
        });
        quit_group.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                int index=groupList.getSelectedIndex();
                if (index!=-1){
                    if (UserHandle.quitGroup((String)UserHandle.groupList.get(index))){
                        UserHandle.groupList.remove(index);
                        UserHandle.showMsg("�˳����ųɹ�!");
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
                UserHandle.groupChat.get(list_index).addElement("�ң�"+message.getText());
            } else {
                Message msg=new TextMsg(UserHandle.user.getUid(),(String)UserHandle.userList.get(list_index),message.getText(),false);
                UserHandle.msgHandle.sendMsg(msg);
                UserHandle.userChat.get(list_index).addElement("�ң�"+message.getText());
            }
        }
        message.setText(null);
    }
}
