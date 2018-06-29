package UI.Pages;

import Client.UserHandle;
import UI.Component.MyListCellRenderer;
import UI.subPage.ReadArticle;

import javax.swing.*;
import javax.swing.border.TitledBorder;
import java.awt.*;
import java.awt.event.*;

public class Page1 extends JPanel {

    private JList list;
    private JFrame frame;

    public Page1(JFrame frame){
        this.frame=frame;
        setLayout(new BorderLayout());
        setBorder(new TitledBorder("ÎÄÕÂ"));

        list=new JList(UserHandle.atcList);
        list.setCellRenderer(new MyListCellRenderer());
        list.setFixedCellHeight(100);
        JScrollPane main=new JScrollPane(list);

        JLabel rcmd=new JLabel("ÍÆ¼ö");
        JLabel sbmt=new JLabel("¹Ø×¢");
        rcmd.setForeground(Color.blue);
        sbmt.setForeground(Color.black);
        JPanel northPane=new JPanel(new GridLayout(1,7));
        northPane.add(new JLabel());
        northPane.add(new JLabel());
        northPane.add(rcmd);
        northPane.add(new JLabel());
        northPane.add(sbmt);
        northPane.add(new JLabel());

        this.add(main,"Center");
        this.add(northPane,"North");


        list.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                int index=list.getSelectedIndex();
                if (index!=-1){
                    if (e.getClickCount()==2)
                        new ReadArticle(UserHandle.articles.get(index));
                }
            }
        });

        rcmd.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                rcmd.setForeground(Color.blue);
                sbmt.setForeground(Color.black);
                UserHandle.refreshArticle(true);
            }
        });

        sbmt.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                rcmd.setForeground(Color.black);
                sbmt.setForeground(Color.blue);
                UserHandle.refreshArticle(false);
            }
        });

        UserHandle.refreshArticle(true);
    }

}
