package UI.Pages;

import javax.swing.*;
import javax.swing.border.TitledBorder;
import java.awt.*;

public class Page3 extends JPanel {

    private JFrame frame;
    private JList list;

    private DefaultListModel data;

    public Page3(JFrame frame){
        this.frame=frame;
        this.setLayout(new BorderLayout());
        this.setBorder(new TitledBorder("����"));

        data=new DefaultListModel();
        list=new JList(data);
        list.setCellRenderer(new DefaultListCellRenderer() {
            public void paintComponent(Graphics g) {
                super.paintComponent(g);
                g.setColor(Color.blue);
                g.drawLine(0, getHeight() - 1, getWidth(), getHeight() - 1);
            }
        });
        list.setFixedCellHeight(50);
        JScrollPane pane=new JScrollPane(list);
        this.add(pane);

        initial();
    }

    private void initial(){
        data.addElement("�˻�");
        data.addElement("����");
        data.addElement("��ע");
        data.addElement("����");
        data.addElement("����");
        data.addElement("�˳�");
    }
}
