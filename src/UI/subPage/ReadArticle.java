package UI.subPage;

import Bean.Article;
import Client.UserHandle;
import UI.Component.MyListCellRenderer;

import javax.swing.*;
import javax.swing.border.TitledBorder;
import java.awt.*;

public class ReadArticle {
    private JFrame frame;
    private JTextField cmt;
    private JButton send;
    private DefaultListModel clist;

    public ReadArticle(Article article){
        frame=new JFrame();
        frame.setLayout(new BorderLayout());
        String[] lList={"A","B","C","D","E"};
        clist=new DefaultListModel();
        UserHandle.cmtList=clist;

        cmt=new JTextField();
        send=new JButton("����");

        //���ر���
        JTextField title=new JTextField();
        title.setBorder(new TitledBorder("����"));
        title.setEditable(false);
        title.setText(article.getTitle());

        //��������
        JPanel author=new JPanel(new GridLayout(1,3));
        author.setBorder(new TitledBorder("����"));
        JLabel name=new JLabel(article.getAuthor());
        JButton submit=new JButton("+��ע");
        JButton add_friend=new JButton("+����");
        author.add(name);
        author.add(submit);
        author.add(add_friend);

        //���ر�ǩ
        JPanel pref=new JPanel(new GridLayout(1,5));
        pref.setBorder(new TitledBorder("��ǩ"));
        pref.add(new JLabel(lList[0]));
        pref.add(new JLabel(lList[1]));
        pref.add(new JLabel(lList[2]));
        pref.add(new JLabel(lList[3]));
        pref.add(new JLabel(lList[4]));
        JPanel top=new JPanel(new GridLayout(3,1));
        top.add(title);
        top.add(author);
        top.add(pref);


        Font font=new Font("����",Font.PLAIN,16);
        //�������ݼ�����
        JTextArea content=new JTextArea(article.getContent());
        content.setFont(font);
        content.setLineWrap(true);
        content.setWrapStyleWord(true);
        content.setBorder(new TitledBorder("����"));

        JList cmts=new JList(clist);
        cmts.setCellRenderer(new MyListCellRenderer());
        cmts.setFixedCellHeight(30);
        cmts.setBorder(new TitledBorder("����"));

        JPanel panel=new JPanel(new BorderLayout());
        panel.add(content,"Center");
        panel.add(cmts,"South");
        JScrollPane main=new JScrollPane(panel);
        main.getVerticalScrollBar().setUnitIncrement(15);

        //�������
        JPanel bottom=new JPanel(new BorderLayout());
        bottom.add(new JLabel("�ң�"),"West");
        bottom.add(cmt,"Center");
        bottom.add(send,"East");

        //���岼��
        frame.add(top,"North");
        frame.add(main,"Center");
        frame.add(bottom,"South");

        frame.setTitle("��������");
        frame.setDefaultCloseOperation(2);
        frame.setBounds(500, 160, 600, 450);
        frame.setVisible(true);


        send.addActionListener((event)->{
            if (!cmt.getText().equals("")&&cmt.getText()!=null){
                UserHandle.deliverCommit(article.getAid(),cmt.getText());
                cmt.setText(null);
            }
        });

        cmt.addActionListener((event)->{
            if (!cmt.getText().equals("")&&cmt.getText()!=null){
                UserHandle.deliverCommit(article.getAid(),cmt.getText());
                cmt.setText(null);
            }
        });

        add_friend.addActionListener((event)->UserHandle.addFriend(article.getAuthor()));

        UserHandle.getCommit(article.getAid());
    }
}
