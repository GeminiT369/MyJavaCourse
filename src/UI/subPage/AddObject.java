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
        JButton send=new JButton("���");
        JPanel panel=new JPanel(new BorderLayout());
        panel.setBorder(new TitledBorder("����"));
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
                    UserHandle.showMsg("��Ӻ��ѳɹ�!");
                    frame.dispose();
                }
                break;
            case 1:
                if (UserHandle.joinGroup(text)) {
                    UserHandle.showMsg("�������ųɹ�!");
                    frame.dispose();
                }
                break;
            case 2:
                if (UserHandle.createGroup(text)){
                    UserHandle.showMsg("�������ųɹ�!");
                    frame.dispose();
                }
                break;
        }
    }
}
