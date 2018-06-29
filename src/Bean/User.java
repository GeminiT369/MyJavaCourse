package Bean;

import java.io.*;
import java.net.Socket;

public class User {

    private String uid;
    private Socket socket;
    private InputStream is;
    private OutputStream os;
    private ObjectInputStream ois;
    private ObjectOutputStream oos;


    public User(Socket socket){
        this(socket,false);
    }

    public User(Socket socket,boolean flag){
        this.socket = socket;
        try {
            this.is=socket.getInputStream();
            this.os=socket.getOutputStream();
            if(flag){
                this.ois=new ObjectInputStream(socket.getInputStream());
                this.oos=new ObjectOutputStream(socket.getOutputStream());
            }else {
                this.oos=new ObjectOutputStream(socket.getOutputStream());
                this.ois=new ObjectInputStream(socket.getInputStream());
            }
        }catch (IOException ie){
            ie.printStackTrace();
        }
    }



    public void setUid(String uid) {
        this.uid = uid;
    }

    public String getUid() {
        return uid;
    }
    public InputStream getInputStream(){
        return is;
    }
    public OutputStream getOutputStream(){
        return os;
    }
    public ObjectInputStream getObjectInputStream() {
        return ois;
    }
    public ObjectOutputStream getObjectOutputStream() {
        return oos;
    }


    public void destroy(){
        try{
            ois.close();
            oos.close();
            is.close();
            os.close();
            socket.close();
        }catch (IOException ie){
            ie.printStackTrace();
        }
    }
}
