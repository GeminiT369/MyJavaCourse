package UI.subPage;

import Bean.Article;
import Client.UserHandle;
import UI.Component.MultiComboBox;

import javax.swing.*;
import javax.swing.border.TitledBorder;
import java.awt.*;

public class WriteArticle {
    JFrame frame;
    MultiComboBox label;
    JTextField name;
    JCheckBox checkBox;
    JButton send;

    public WriteArticle(){
        frame=new JFrame();
        frame.setLayout(new BorderLayout());
        String[] list={"ALL","A","B","C","D","E"};
        String[] dft={"ALL"};
        label=new MultiComboBox(list,dft);

        send=new JButton("����");
        checkBox=new JCheckBox();
        name=new JTextField();
        name.setBorder(new TitledBorder("����"));
        JPanel pref=new JPanel(new GridLayout(1,2));
        pref.setBorder(new TitledBorder("��ǩ"));
        pref.add(new JLabel("ѡ���ǩ:"));
        pref.add(label);
        JSplitPane top=new JSplitPane(JSplitPane.VERTICAL_SPLIT,name,pref);

        JTextArea content=new JTextArea();
        content.setBorder(new TitledBorder("����"));

        JPanel bottom=new JPanel(new GridLayout(1,7));
        bottom.add(new JLabel());
        bottom.add(new JLabel("��������"));
        bottom.add(checkBox);
        bottom.add(new JLabel());
        bottom.add(new JLabel());
        bottom.add(send);
        bottom.add(new JLabel());

        frame.add(top,"North");
        frame.add(content,"Center");
        frame.add(bottom,"South");

        frame.setTitle("��������");
        frame.setDefaultCloseOperation(2);
        frame.setBounds(500, 160, 600, 450);
        frame.setVisible(true);

        send.addActionListener((event)->{
                String title=name.getText();
                String context=content.getText();
                if (title.equals("")||context.equals("")){
                    UserHandle.showMsg("�������������ģ�");
                }else {
                    Article article=new Article(title,UserHandle.user.getUid(),context);
                    if (UserHandle.deliverArticle(article)){
                        UserHandle.showMsg("�����ɹ���");
                        frame.dispose();
                    }else
                        UserHandle.showMsg("����ʧ�ܣ�");
                }
        });
    }
}
