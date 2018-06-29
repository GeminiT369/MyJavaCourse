package Bean.Msg;

public class CtrlMsg extends Message {

    //Client
    public final static int ADD_USER=2;
    public final static int ADD_GROUP=4;
    public final static int JOIN_GROUP=5;
    public final static int DISCONNECT=6;
    public final static int CHECK_USER=7;
    public final static int DELETE_USER=8;
    public final static int DELETE_GROUP=9;

    public final static int LOGIN=10;
    public final static int REGISTER=11;
    public final static int QUIT_GROUP=12;

    public final static int GET_RECOMMEND=20;
    public final static int GET_SUBMIT=21;
    public final static int GET_COMMIT=22;

    //Server
    public final static int OPERATE_SUCCESS=100;
    public final static int UID_ILLEGAL=101;
    public final static int GID_ILLEGAL=102;
    public final static int NO_SUCH_USER=103;
    public final static int NO_SUCH_GROUP=104;
    public final static int WRONG_PASSWORD=105;

    //public final static int AD

    private int cmd;
    private String msg;

    public CtrlMsg(String src,String dst,int cmd){
        this(src,dst,cmd,null);
    }

    public CtrlMsg(String src,String dst,int cmd,String msg){
        super(src,dst);
        this.cmd=cmd;
        this.msg=msg;
    }

    public String getExtMsg(){
        return msg;
    }

    public void setCmd(int cmd) {
        this.cmd = cmd;
    }

    public int getCmd() {
        return cmd;
    }

    @Override
    public String toString() {
        return "From "+src+" obj:"+dst+" Cmd: "+cmd+" Msg:"+msg;
    }

    @Override
    public String getMsg() {
        return toString();
    }
}
