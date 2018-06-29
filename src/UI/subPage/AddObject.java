package UI.subPage;

import Client.UserHandle;

import javax.swing.*;
import javax.swing.border.TitledBorder;
import java.awt.*;

public class AddObject {

    private int flag;
    private JFrame frame;
    private JTextField obj;

    public AddObject(String title,int flag){
        frame=new JFrame();
        frame.setTitle(title);
        this.flag=flag;

        obj=new JTextField();
        JButton send=new JButton("添加");
        JPanel panel=new JPanel(new BorderLayout());
        panel.setBorder(new TitledBorder("查找"));
        panel.add(obj,"Center");
        panel.add(send,"East");

        frame.add(panel);

        frame.setBounds(600, 350, 500, 90);
        frame.setVisible(true);

        obj.addActionListener((event)->onClick());
        send.addActionListener((event)->onClick());
    }

    private void onClick(){
        String text=obj.getText();
        switch (flag){
            case 0:
                if(UserHandle.addFriend(text)){
                    UserHandle.showMsg("添加好友成功!");
                    frame.dispose();
                }
                break;
            case 1:
                if (UserHandle.joinGroup(text)) {
                    UserHandle.showMsg("加入社团成功!");
                    frame.dispose();
                }
                break;
            case 2:
                if (UserHandle.createGroup(text)){
                    UserHandle.showMsg("创建社团成功!");
                    frame.dispose();
                }
                break;
        }
    }
}
