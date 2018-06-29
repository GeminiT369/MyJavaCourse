package UI.BasePanel;

import Client.UserHandle;
import UI.Component.CircleButton;
import UI.Pages.Page1;
import UI.Pages.Page2;
import UI.Pages.Page3;
import UI.subPage.WriteArticle;

import java.awt.*;

import javax.swing.*;

public class CardPanel {

    private JPanel main;
    private JPanel bottom;
    private JPanel current;
    private JButton bt1,bt2,bt3;

    protected JFrame frame;
    protected JButton add,top;
    protected JPanel page1,page2,page3;

    public CardPanel(){

        frame = new JFrame("树屋");


        //初始化底部面板
        bottom=new JPanel();
        bottom.setLayout(new GridLayout(1,3));
        bt1=new JButton("bt1");
        bt1.setText("文章");
        bt2=new JButton("bt2");
        bt2.setText("社交");
        bt3=new JButton("bt3");
        bt3.setText("个人");
        add=new CircleButton("+");
        top=new CircleButton("\u21BB");
        bottom.add(bt1);
        bottom.add(bt2);
        bottom.add(bt3);


        //初始化各个界面
        page1=new Page1(frame);
        page2=new Page2(frame);
        page3=new Page3(frame);

        //初始按钮
        add.setBounds(700,450,50,50);
        top.setBounds(700,350,50,50);
        JLayeredPane pane=frame.getLayeredPane();
        pane.add(add,10,1);
        pane.add(top,10,0);

        //初始化主面板
        current=page1;
        main = new JPanel();
        main.setLayout(new BorderLayout());
        main.add(current,"Center");
        main.add(bottom,"South");

        frame.add(main);
        frame.setBounds(400, 100, 800, 600);
        frame.setDefaultCloseOperation(3);
        frame.setVisible(true);


        bt1.addActionListener((e)->{
            if (current!=page1){
                main.remove(current);
                current=page1;
                main.add(current,"Center");
                showFlowComp(true);
                main.updateUI();
                main.repaint();
            }
        });
        bt2.addActionListener((e)->{
            if (current!=page2){
                main.remove(current);
                current=page2;
                main.add(current,"Center");
                showFlowComp(false);
                main.updateUI();
                main.repaint();
            }
        });
        bt3.addActionListener((e)->{
            if (current!=page3){
                main.remove(current);
                current=page3;
                main.add(current,"Center");
                showFlowComp(false);
                main.updateUI();
                main.repaint();
            }
        });

        add.addActionListener((event)->new WriteArticle());
        top.addActionListener((event)-> UserHandle.refreshArticle(true));

    }

    private void showFlowComp(boolean viewable){
        add.setVisible(viewable);
        top.setVisible(viewable);
    }

}
