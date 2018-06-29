package Bean.Msg;

import java.io.Serializable;

public abstract class Message implements Serializable {

    public String src;
    public String dst;
    private boolean isGMsg;

    public Message(String src,String dst){
        this(src,dst,false);
    }

    public Message(String src,String dst,boolean isGMsg){
        this.src=src;
        this.dst=dst;
        this.isGMsg=isGMsg;
    }

    public boolean isGMsg() {
        return isGMsg;
    }

    public String getDst() {
        return dst;
    }

    public String getSrc() {
        return src;
    }

    public void setSrc(String src) {
        this.src = src;
    }

    public void setDst(String dst) {
        this.dst = dst;
    }

    protected void setGMsg(boolean GMsg) {
        isGMsg = GMsg;
    }

    @Override
    abstract public String toString();

    abstract public String getMsg();
}
