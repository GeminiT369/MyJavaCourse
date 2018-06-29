package Bean;

import java.util.ArrayList;
import java.util.List;

public class Group {
    private String gid;
    private List<User> list;

    public Group(String gid){
        this.gid=gid;
        this.list=new ArrayList<>();
    }

    public String getGid() {
        return gid;
    }

    public void setGid(String gid) {
        this.gid = gid;
    }

    public void addUser(User user){
        list.add(user);
    }

    public void removeUser(User user){
        list.remove(user);
    }

    public List<User> getList() {
        return list;
    }
}
