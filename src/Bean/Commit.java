package Bean;

import java.io.Serializable;

public class Commit implements Serializable {

    public int aid;
    public String uid;
    public String content;

    public Commit(int aid,String uid,String content){
        this.aid=aid;
        this.uid=uid;
        this.content=content;
    }

    public String getMsg(){
        return uid+": "+content;
    }
}
