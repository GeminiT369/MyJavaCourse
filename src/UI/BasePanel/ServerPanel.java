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
        btn_start = new JButton("启动");
        btn_stop = new JButton("停止");
        btn_send = new JButton("发送");
        btn_stop.setEnabled(false);
        usersList = new JList(userModel);
        groupList = new JList(groupModel);

        //下方布局
        southPanel = new JPanel(new BorderLayout());
        southPanel.setBorder(new TitledBorder("写消息"));
        southPanel.add(txt_message, "Center");
        southPanel.add(btn_send, "East");

        //中央布局
        usersPane=new JScrollPane(usersList);
        usersPane.setBorder(new TitledBorder("在线用户"));
        groupPane=new JScrollPane(groupList);
        groupPane.setBorder(new TitledBorder("在线群组"));

        leftPanel=new JSplitPane(JSplitPane.VERTICAL_SPLIT,usersPane,groupPane);
        leftPanel.setDividerLocation(120);

        rightPanel = new JScrollPane(contentArea);
        rightPanel.setBorder(new TitledBorder("消息显示区"));

        centerSplit = new JSplitPane(JSplitPane.HORIZONTAL_SPLIT, leftPanel,
                rightPanel);
        centerSplit.setDividerLocation(100);

        //上部信息布局
        northPanel = new JPanel();
        northPanel.setLayout(new GridLayout(1, 6));
        northPanel.add(new JLabel("人数上限"));
        northPanel.add(txt_max);
        northPanel.add(new JLabel("端口"));
        northPanel.add(txt_port);
        northPanel.add(btn_start);
        northPanel.add(btn_stop);
        northPanel.setBorder(new TitledBorder("配置信息"));

        //整体布局
        frame.setLayout(new BorderLayout());
        frame.add(northPanel, "North");
        frame.add(centerSplit, "Center");
        frame.add(southPanel, "South");
        frame.setSize(600, 400);
        //frame.setSize(Toolkit.getDefaultToolkit().getScreenSize());//设置全屏
        int screen_width = Toolkit.getDefaultToolkit().getScreenSize().width;
        int screen_height = Toolkit.getDefaultToolkit().getScreenSize().height;
        frame.setLocation((screen_width - frame.getWidth()) / 2,
                (screen_height - frame.getHeight()) / 2);
        frame.setVisible(true);

        usersList.addListSelectionListener((e)->onUserSelection(usersList.getSelectedIndex()));

        groupList.addListSelectionListener((e)->onGroupSelection(groupList.getSelectedIndex()));

        // 关闭窗口时事件
        frame.addWindowListener(new WindowAdapter() {
            public void windowClosing(WindowEvent e) {
                if (isStart) {
                    onClose();// 关闭服务器
                }
                System.exit(0);// 退出程序
            }
        });

        // 文本框按回车键时事件
        txt_message.addActionListener((event)->{
            sendMsg();
            txt_message.setText(null);
        });

        // 单击发送按钮时事件
        btn_send.addActionListener((event)->{
            sendMsg();
            txt_message.setText(null);
        });

        // 单击启动服务器按钮时事件
        btn_start.addActionListener((event)->{
            if (isStart) {
                JOptionPane.showMessageDialog(frame, title+"已处于启动状态，不要重复启动！",
                        "错误", JOptionPane.ERROR_MESSAGE);
                return;
            }
            int max;
            int port;
            try {
                try {
                    max = Integer.parseInt(txt_max.getText());
                } catch (Exception e1) {
                    throw new Exception("人数上限为正整数！");
                }
                if (max <= 0) {
                    throw new Exception("人数上限为正整数！");
                }
                try {
                    port = Integer.parseInt(txt_port.getText());
                } catch (Exception e1) {
                    throw new Exception("端口号为正整数！");
                }
                if (port <= 0) {
                    throw new Exception("端口号为正整数！");
                }
                onStart(port,max);
                btn_start.setEnabled(false);
                txt_max.setEnabled(false);
                txt_port.setEnabled(false);
                btn_stop.setEnabled(true);
            } catch (Exception exc) {
                JOptionPane.showMessageDialog(frame, exc.getMessage(),
                        "错误", JOptionPane.ERROR_MESSAGE);
            }
        });

        // 单击停止服务器按钮时事件
        btn_stop.addActionListener((event)->{
            if (!isStart) {
                JOptionPane.showMessageDialog(frame, "还未启动，无需停止！", "错误",
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
                JOptionPane.showMessageDialog(frame, "停止时发生异常！", "错误",
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