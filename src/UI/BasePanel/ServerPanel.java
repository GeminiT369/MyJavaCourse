package UI.BasePanel;

import javax.swing.*;
import javax.swing.border.TitledBorder;
import java.awt.*;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

import static Server.MyDataBase.*;

abstract public class ServerPanel {

    private JPanel northPanel;
    private JPanel southPanel;
    private JScrollPane usersPane;
    private JScrollPane groupPane;
    private JScrollPane rightPanel;
    private JSplitPane leftPanel;
    private JSplitPane centerSplit;

    private JTextField txt_max;
    private JTextField txt_port;
    private JButton btn_start;
    private JButton btn_stop;
    private JButton btn_send;
    private JList usersList;
    private JList groupList;

    protected JFrame frame;
    protected JTextArea contentArea;
    protected JTextField txt_message;


    protected boolean isStart = false;
    protected String title="NONE";


    public ServerPanel() {
        frame = new JFrame(title);
        contentArea = new JTextArea();
        contentArea.setEditable(false);
        contentArea.setForeground(Color.blue);
        txt_message = new JTextField();
        txt_max = new JTextField("300");
        txt_port = new JTextField("3456");
        btn_start = new JButton("����");
        btn_stop = new JButton("ֹͣ");
        btn_send = new JButton("����");
        btn_stop.setEnabled(false);
        usersList = new JList(userModel);
        groupList = new JList(groupModel);

        //�·�����
        southPanel = new JPanel(new BorderLayout());
        southPanel.setBorder(new TitledBorder("д��Ϣ"));
        southPanel.add(txt_message, "Center");
        southPanel.add(btn_send, "East");

        //���벼��
        usersPane=new JScrollPane(usersList);
        usersPane.setBorder(new TitledBorder("�����û�"));
        groupPane=new JScrollPane(groupList);
        groupPane.setBorder(new TitledBorder("����Ⱥ��"));

        leftPanel=new JSplitPane(JSplitPane.VERTICAL_SPLIT,usersPane,groupPane);
        leftPanel.setDividerLocation(120);

        rightPanel = new JScrollPane(contentArea);
        rightPanel.setBorder(new TitledBorder("��Ϣ��ʾ��"));

        centerSplit = new JSplitPane(JSplitPane.HORIZONTAL_SPLIT, leftPanel,
                rightPanel);
        centerSplit.setDividerLocation(100);

        //�ϲ���Ϣ����
        northPanel = new JPanel();
        northPanel.setLayout(new GridLayout(1, 6));
        northPanel.add(new JLabel("��������"));
        northPanel.add(txt_max);
        northPanel.add(new JLabel("�˿�"));
        northPanel.add(txt_port);
        northPanel.add(btn_start);
        northPanel.add(btn_stop);
        northPanel.setBorder(new TitledBorder("������Ϣ"));

        //���岼��
        frame.setLayout(new BorderLayout());
        frame.add(northPanel, "North");
        frame.add(centerSplit, "Center");
        frame.add(southPanel, "South");
        frame.setSize(600, 400);
        //frame.setSize(Toolkit.getDefaultToolkit().getScreenSize());//����ȫ��
        int screen_width = Toolkit.getDefaultToolkit().getScreenSize().width;
        int screen_height = Toolkit.getDefaultToolkit().getScreenSize().height;
        frame.setLocation((screen_width - frame.getWidth()) / 2,
                (screen_height - frame.getHeight()) / 2);
        frame.setVisible(true);

        usersList.addListSelectionListener((e)->onUserSelection(usersList.getSelectedIndex()));

        groupList.addListSelectionListener((e)->onGroupSelection(groupList.getSelectedIndex()));

        // �رմ���ʱ�¼�
        frame.addWindowListener(new WindowAdapter() {
            public void windowClosing(WindowEvent e) {
                if (isStart) {
                    onClose();// �رշ�����
                }
                System.exit(0);// �˳�����
            }
        });

        // �ı��򰴻س���ʱ�¼�
        txt_message.addActionListener((event)->{
            sendMsg();
            txt_message.setText(null);
        });

        // �������Ͱ�ťʱ�¼�
        btn_send.addActionListener((event)->{
            sendMsg();
            txt_message.setText(null);
        });

        // ����������������ťʱ�¼�
        btn_start.addActionListener((event)->{
            if (isStart) {
                JOptionPane.showMessageDialog(frame, title+"�Ѵ�������״̬����Ҫ�ظ�������",
                        "����", JOptionPane.ERROR_MESSAGE);
                return;
            }
            int max;
            int port;
            try {
                try {
                    max = Integer.parseInt(txt_max.getText());
                } catch (Exception e1) {
                    throw new Exception("��������Ϊ��������");
                }
                if (max <= 0) {
                    throw new Exception("��������Ϊ��������");
                }
                try {
                    port = Integer.parseInt(txt_port.getText());
                } catch (Exception e1) {
                    throw new Exception("�˿ں�Ϊ��������");
                }
                if (port <= 0) {
                    throw new Exception("�˿ں�Ϊ��������");
                }
                onStart(port,max);
                btn_start.setEnabled(false);
                txt_max.setEnabled(false);
                txt_port.setEnabled(false);
                btn_stop.setEnabled(true);
            } catch (Exception exc) {
                JOptionPane.showMessageDialog(frame, exc.getMessage(),
                        "����", JOptionPane.ERROR_MESSAGE);
            }
        });

        // ����ֹͣ��������ťʱ�¼�
        btn_stop.addActionListener((event)->{
            if (!isStart) {
                JOptionPane.showMessageDialog(frame, "��δ����������ֹͣ��", "����",
                        JOptionPane.ERROR_MESSAGE);
                return;
            }
            try {
                onClose();
                btn_start.setEnabled(true);
                txt_max.setEnabled(true);
                txt_port.setEnabled(true);
                btn_stop.setEnabled(false);

            } catch (Exception exc) {
                JOptionPane.showMessageDialog(frame, "ֹͣʱ�����쳣��", "����",
                        JOptionPane.ERROR_MESSAGE);
            }
        });
    }

    protected void setTitle(String title){
        frame.setTitle(title);
    }

    abstract public void onStart(int port,int max);

    abstract public void onUserSelection(int index);

    abstract public void onGroupSelection(int index);

    abstract public void onClose();

    abstract public void sendMsg();
}