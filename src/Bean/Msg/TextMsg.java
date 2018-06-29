package Bean.Msg;

public class TextMsg extends Message {

    private String text;

    public TextMsg(String src,String dst,String text){
        this(src,dst,text,false);
    }

    public TextMsg(String src,String dst,String text,boolean isGMsg){
        super(src,dst,isGMsg);
        this.text=text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public String getText() {
        return text;
    }

    @Override
    public String toString() {
        return "From "+src+" To "+dst+" Msg: "+text;
    }

    @Override
    public String getMsg() {
        return src+"\t: "+text;
    }
}
