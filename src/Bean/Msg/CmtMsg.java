package Bean.Msg;

import Bean.Commit;

public class CmtMsg extends Message{

    private Commit commit;
    public CmtMsg(String uid,int aid,String text){
        super(uid,Integer.toString(aid));
        commit=new Commit(aid,uid,text);
    }

    public CmtMsg(Commit commit){
        super(commit.uid,Integer.toString(commit.aid));
        this.commit=commit;
    }

    public Commit getCommit() {
        return commit;
    }

    @Override
    public String getMsg() {
        return commit.uid+": "+commit.content;
    }

    @Override
    public String toString() {
        return "From "+commit.uid+" to "+commit.aid+" Content:"+commit.content;
    }
}
