package UI;

import Bean.Msg.CtrlMsg;
import Bean.Msg.Message;
import Bean.Msg.TextMsg;
import Server.ServerHandle;
import UI.BasePanel.ServerPanel;

import javax.swing.*;
import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;

import static Server.MyDataBase.*;

public class Server extends ServerPanel {

    private int max;
    private int port;
    private int count;

    private ServerThread serverThread;


    @Override
    public void onStart(int port,int max) {
        isStart=true;
        this.max=max;
        this.port=port;
        this.count=0;
        serverThread.start();
        contentArea.append("服务器成功启动!\n");
    }

    @Override
    public void onClose() {
        isStart=false;
        serverThread.close();
        contentArea.append("服务器成功停止!\n");
    }

    @Override
    public void onUserSelection(int index) {

    }

    @Override
    public void onGroupSelection(int index) {

    }

    @Override
    public void sendMsg() {
        Message msg=new TextMsg("SERVER","ALL",txt_message.getText().trim());
        sendToAll(msg);
    }

    public void sendToAll(Message msg){
        var users=userMap.entrySet();
        try{
            for (var user:users){
                user.getValue().getObjectOutputStream().writeObject(msg);
            }
        }catch (IOException ie){
            ie.printStackTrace();
        }
    }

    public Server(){

        setTitle("Server");
        ServerHandle.textArea=contentArea;
        serverThread=new ServerThread();
    }

    class ServerThread extends Thread{
        @Override
        public void run() {
            try {
                ServerSocket serverSocket=new ServerSocket(port);
                while (isStart){
                    Socket socket=serverSocket.accept();
                    if (isStart){
                        if (count==max)
                            System.out.println("连接数达到上限!");
                        else {
                            new ServerHandle(socket).start();
                            count++;
                        }
                    }
                }
            }catch (IOException ie){
                JOptionPane.showMessageDialog(frame, "端口已被占用!");
            }
        }

        void close(){
            isStart=false;
            try {
                new Socket("127.0.0.1",3456);
            }catch (IOException ie){
                ie.printStackTrace();
            }
            CtrlMsg msg=new CtrlMsg("SERVER","ALL",CtrlMsg.DISCONNECT);
            sendToAll(msg);
            System.out.println("Server stop!");
        }
    }

    public static void main(String[] args) {
        new Server();
    }

}
