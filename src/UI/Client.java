package UI;

import Client.UserHandle;
import UI.BasePanel.CardPanel;

import javax.swing.*;
import javax.swing.border.TitledBorder;
import java.awt.*;

public class Client {

    private JFrame frame;
    private JPanel main;
    private JTextField account;
    private JPasswordField password;
    private JPasswordField ensure_pswd;

    public Client(){
        frame=new JFrame();
        frame.setTitle("����");

        TitledBorder regTitle=new TitledBorder("ע��");
        TitledBorder logTitle=new TitledBorder("��½");
        main=new JPanel(new GridLayout(5,1));
        main.setBorder(logTitle);

        account=new JTextField();
        password=new JPasswordField();
        ensure_pswd=new JPasswordField();

        JButton log=new JButton("��½");
        JButton reg=new JButton("ע��");
        JButton toReg=new JButton("ȥע��");
        JButton toLog=new JButton("ȥ��½");

        //����
        JLabel top=new JLabel("����");
        top.setHorizontalAlignment(SwingConstants.CENTER);

        //������
        JPanel p1=new JPanel(new BorderLayout());
        p1.add(new JLabel("�˺ţ�"),"West");
        p1.add(account,"Center");

        JPanel p2=new JPanel(new BorderLayout());
        p2.add(new JLabel("���룺"),"West");
        p2.add(password,"Center");

        JPanel p3=new JPanel(new BorderLayout());
        p3.add(new JLabel("ȷ�ϣ�"),"West");
        p3.add(ensure_pswd,"Center");

        JPanel p4=new JPanel(new BorderLayout());
        p4.add(new JLabel("            "),"West");
        p4.add(log,"Center");
        p4.add(toReg,"East");

        JPanel p5=new JPanel(new BorderLayout());
        p5.add(new JLabel("            "),"West");
        p5.add(reg,"Center");
        p5.add(toLog,"East");

        JLabel forget=new JLabel("��������?");
        forget.setHorizontalAlignment(SwingConstants.RIGHT);

        main.add(top);
        main.add(p1);
        main.add(p2);
        main.add(p4);
        main.add(forget);

        frame.add(main);
        frame.setDefaultCloseOperation(3);
        frame.setBounds(600, 200, 450, 250);
        frame.setVisible(true);

        //������
        toReg.addActionListener((event)->{
                main.remove(p4);
                main.remove(forget);
                main.add(p3);
                main.add(p5);
                main.setBorder(regTitle);
                main.updateUI();
                main.repaint();
        });

        toLog.addActionListener((event)->{
                main.remove(p3);
                main.remove(p5);
                main.add(p4);
                main.add(forget);
                main.setBorder(logTitle);
                main.updateUI();
                main.repaint();
        });

        log.addActionListener((event)->{
                String ac=account.getText();
                String ps=new String(password.getPassword());
                if (ac.equals("")||ps.equals("")){
                    JOptionPane.showMessageDialog(frame, "�������˺ż�����","����", JOptionPane.ERROR_MESSAGE);
                }else {
                    tryLogin(ac,ps);
                }
        });

        reg.addActionListener((event)->{
                String ac=account.getText();
                String ps=new String(password.getPassword());
                String ep=new String(ensure_pswd.getPassword());
                if (ac.equals("")||ps.equals("")){
                    JOptionPane.showMessageDialog(frame, "�������˺ż�����","����", JOptionPane.ERROR_MESSAGE);
                }else if(!ps.equals(ep)){
                    JOptionPane.showMessageDialog(frame, "�����������벻һ��","����", JOptionPane.ERROR_MESSAGE);
                }else {
                    tryRegister(ac,ps);
                }
        });
    }

    private void tryLogin(String acnt,String pswd){
        if (UserHandle.login(acnt,pswd)){
            new CardPanel();
            frame.dispose();
        }
    }

    private void tryRegister(String acnt,String pswd){
        if (UserHandle.register(acnt,pswd)){
            new CardPanel();
            frame.dispose();
        }
    }

    public static void main(String[] args) {
        new Client();
    }
}
