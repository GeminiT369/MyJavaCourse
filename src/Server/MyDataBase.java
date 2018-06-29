package Server;

import Bean.Article;
import Bean.Commit;
import Bean.Group;
import Bean.User;

import javax.swing.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class MyDataBase {

    public static DefaultListModel userModel;
    public static DefaultListModel groupModel;
    public static HashMap<String,User> userMap;
    public static HashMap<String,Group> groupMap;
    public static HashMap<String,String> userMsg;

    public static List<Article> articles;
    public static HashMap<Integer,List<Commit>> commits;

    static {

        userModel=new DefaultListModel();
        groupModel=new DefaultListModel();

        userMap = new HashMap<>();
        groupMap = new HashMap<>();

        userMsg=new HashMap<>();

        commits = new HashMap<>();
        articles = new ArrayList<>();

        userMsg.put("admin","666666");
        userMsg.put("user1","pswd1");
        userMsg.put("user2","pswd2");
        userMsg.put("user3","pswd3");

        articles.add(new Article(0,"��ū��","����",null,
                "        �󽭶�ȥ�����Ծ���ǧ�ŷ������\n" +
                "        �������ߣ��˵��ǣ��������ɳ�ڡ�\n" +
                "        ��ʯ���գ������İ�������ǧ��ѩ��\n" +
                "        ��ɽ�续��һʱ���ٺ��ܡ�\n" +
                "\n" +
                "        ң�빫誵��꣬С�ǳ����ˣ�����Ӣ����\n" +
                "        �����ڽ�̸Ц�䣬���ֻҷ�����\n" +
                "        �ʹ����Σ�����ӦЦ�ң�����������\n" +
                "        �������Σ�һ�������¡�\n"));
        commits.put(0,new ArrayList<>());
        commits.get(0).add(new Commit(0,"admin","Very Good"));

        articles.add(new Article(1,"�����ӡ���î���¶�ʮ��ҹ����","����",null,
                "ʮ��������ãã����˼������������\n" +
                        "ǧ��·أ��޴���������\n" +
                        "��ʹ���Ӧ��ʶ�������棬����˪��\n" +
                        "\n"+
                        "ҹ�����κ����磬С����������ױ��\n" +
                        "������ԣ�Ω����ǧ�С�\n" +
                        "�ϵ����곦�ϴ�������ҹ�����ɸԡ�\n"));
        commits.put(1,new ArrayList<>());
        commits.get(1).add(new Commit(0,"admin","Just so so"));

        articles.add(new Article(2,"������","����",null,
                "        �������У��Գ�ͤ�������Ъ���������������������������۴߷���ִ���࿴���ۣ���������ҭ����ȥȥ��ǧ���̲���ĺ��������������\n" +
                "        �����Թ�����𣬸��ǿ�����������ڣ��������Ѻδ�����������������¡���ȥ���꣬Ӧ�������þ����衣������ǧ�ַ��飬�������˵��"));
        commits.put(2,new ArrayList<>());
        commits.get(2).add(new Commit(0,"admin","emm..."));

        articles.add(new Article(3,"���»�Զ","��ʫ",null,
                "        ���������£����Ĺ���ʱ��\n" +
                "        ����Թңҹ����Ϧ����˼��\n" +
                "        ���������������¾�¶�̡�\n" +
                "        ����ӯ�����������μ��ڡ�\n"));
        commits.put(3,new ArrayList<>());
        commits.get(3).add(new Commit(0,"admin","haha"));

        articles.add(new Article(4,"�����֡����ڱ���ͤ����","��ʫ",null,
                "�Ž�ɽ��Ӣ�����٣�����ı����\n��鿸�̨�������ܱ����紵ȥ��\nб��������Ѱ����İ���˵���ū��ס��\n�뵱�꣬����������������绢��\n" +
                "Ԫ�βݲݣ����Ǿ��㣬Ӯ�òֻʱ��ˡ�\n��ʮ���꣬�����̼ǣ��������·��\n�ɿ����ף��������£�һƬ��ѻ��ġ�\nƾ˭�ʣ��������ӣ����ܷ���"));
        commits.put(4,new ArrayList<>());
        commits.get(4).add(new Commit(0,"admin","perfect!"));

        articles.add(new Article(5,"������","�δ�",null,
                "���ճ�������¶����Ļ�Ắ������˫��ȥ��\n���²�����޿࣬б�⵽�����컧��\n" +
                "��ҹ�������������ϸ�¥����������·��\n���Ĳʼ����أ�ɽ��ˮ��֪�δ���"));
        commits.put(5,new ArrayList<>());
        commits.get(5).add(new Commit(0,"admin","dsd"));

        articles.add(new Article(6,"�ٽ���","�δ�",null,
                "�κ�¥̨������������Ļ�ʹ���\nȥ�괺��ȴ��ʱ��\n�仨�˶�����΢����˫�ɡ� \n\n" +
                "�ǵ�С�O�����������������¡�\n��������˵��˼��\n��ʱ�����ڣ����ղ��ƹ顣"));
        commits.put(6,new ArrayList<>());
        commits.get(6).add(new Commit(0,"admin","go to hell"));

        articles.add(new Article(7,"�ٽ���","����",null,
                "����������������ˮ���˻��Ծ�Ӣ�ۡ�\n" +
                "�����Ƿǳɰ�תͷ�ա�\n" +
                "������ɽ�����ڣ�����Ϧ���졣\n" +
                "\n" +
                "�����׷����Խ���ϣ��߿����´��硣\n" +
                "��   һ���Ǿ�ϲ��ꡣ\n" +
                "�����Ž�����£�����Ц̸�С�"));
        commits.put(7,new ArrayList<>());
        commits.get(7).add(new Commit(0,"admin","666"));

        articles.add(new Article(8,"��а","����",null,"��а��\n" +
                "���������֪��\n" +
                "�����޾�˥��\n" +
                "ɽ���꣬\n" +
                "��ˮΪ�ߣ�\n" +
                "��������\n" +
                "����ѩ��\n" +
                "��غϣ�\n" +
                "�˸��������"));
        commits.put(8,new ArrayList<>());
        commits.get(8).add(new Commit(0,"admin","999"));

        articles.add(new Article(9,"����","����",null,
                "���������߸��ٽ�侣��������ո��衣\n" +
                "�����������������ƣ�����ĺ����ɽ�ꡣ\n" +
                "��������̶Ӱ�����ƣ��ﻻ���Ƽ����\n" +
                "�������е��ӽ���ڣ����ⳤ����������\n"));
        commits.put(9,new ArrayList<>());
        commits.get(9).add(new Commit(0,"admin","����˭"));
    }
}
