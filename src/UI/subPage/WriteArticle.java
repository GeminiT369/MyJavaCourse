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

        send=new JButton("发表");
        checkBox=new JCheckBox();
        name=new JTextField();
        name.setBorder(new TitledBorder("标题"));
        JPanel pref=new JPanel(new GridLayout(1,2));
        pref.setBorder(new TitledBorder("标签"));
        pref.add(new JLabel("选择标签:"));
        pref.add(label);
        JSplitPane top=new JSplitPane(JSplitPane.VERTICAL_SPLIT,name,pref);

        JTextArea content=new JTextArea();
        content.setBorder(new TitledBorder("正文"));

        JPanel bottom=new JPanel(new GridLayout(1,7));
        bottom.add(new JLabel());
        bottom.add(new JLabel("公开发表"));
        bottom.add(checkBox);
        bottom.add(new JLabel());
        bottom.add(new JLabel());
        bottom.add(send);
        bottom.add(new JLabel());

        frame.add(top,"North");
        frame.add(content,"Center");
        frame.add(bottom,"South");

        frame.setTitle("发表文章");
        frame.setDefaultCloseOperation(2);
        frame.setBounds(500, 160, 600, 450);
        frame.setVisible(true);

        send.addActionListener((event)->{
                String title=name.getText();
                String context=content.getText();
                if (title.equals("")||context.equals("")){
                    UserHandle.showMsg("请输入标题和正文！");
                }else {
                    Article article=new Article(title,UserHandle.user.getUid(),context);
                    if (UserHandle.deliverArticle(article)){
                        UserHandle.showMsg("发布成功！");
                        frame.dispose();
                    }else
                        UserHandle.showMsg("发布失败！");
                }
        });
    }
}
