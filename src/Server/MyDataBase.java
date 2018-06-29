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

        articles.add(new Article(0,"念奴娇","苏轼",null,
                "        大江东去，浪淘尽，千古风流人物。\n" +
                "        故垒西边，人道是，三国周郎赤壁。\n" +
                "        乱石穿空，惊涛拍岸，卷起千堆雪。\n" +
                "        江山如画，一时多少豪杰。\n" +
                "\n" +
                "        遥想公瑾当年，小乔初嫁了，雄姿英发。\n" +
                "        羽扇纶巾，谈笑间，樯橹灰飞烟灭。\n" +
                "        故国神游，多情应笑我，早生华发。\n" +
                "        人生如梦，一尊还酹江月。\n"));
        commits.put(0,new ArrayList<>());
        commits.get(0).add(new Commit(0,"admin","Very Good"));

        articles.add(new Article(1,"江城子·乙卯正月二十日夜记梦","苏轼",null,
                "十年生死两茫茫，不思量，自难忘。\n" +
                        "千里孤坟，无处话凄凉。\n" +
                        "纵使相逢应不识，尘满面，鬓如霜。\n" +
                        "\n"+
                        "夜来幽梦忽还乡，小轩窗，正梳妆。\n" +
                        "相顾无言，惟有泪千行。\n" +
                        "料得年年肠断处，明月夜，短松冈。\n"));
        commits.put(1,new ArrayList<>());
        commits.get(1).add(new Commit(0,"admin","Just so so"));

        articles.add(new Article(2,"雨霖铃","柳永",null,
                "        寒蝉凄切，对长亭晚，骤雨初歇。都门帐饮无绪，留恋处，兰舟催发。执手相看泪眼，竟无语凝噎。念去去，千里烟波，暮霭沉沉楚天阔。\n" +
                "        多情自古伤离别，更那堪，冷落清秋节！今宵酒醒何处？杨柳岸，晓风残月。此去经年，应是良辰好景虚设。便纵有千种风情，更与何人说？"));
        commits.put(2,new ArrayList<>());
        commits.get(2).add(new Commit(0,"admin","emm..."));

        articles.add(new Article(3,"望月怀远","唐诗",null,
                "        海上生明月，天涯共此时。\n" +
                "        情人怨遥夜，竟夕起相思。\n" +
                "        灭烛怜光满，披衣觉露滋。\n" +
                "        不堪盈手赠，还寝梦佳期。\n"));
        commits.put(3,new ArrayList<>());
        commits.get(3).add(new Commit(0,"admin","haha"));

        articles.add(new Article(4,"永遇乐·京口北固亭怀古","唐诗",null,
                "古江山，英雄无觅，孙仲谋处。\n舞榭歌台，风流总被雨打风吹去。\n斜阳草树，寻常巷陌，人道寄奴曾住。\n想当年，金戈铁马，气吞万里如虎。\n" +
                "元嘉草草，封狼居胥，赢得仓皇北顾。\n四十三年，望中犹记，烽火扬州路。\n可堪回首，佛狸祠下，一片神鸦社鼓。\n凭谁问，廉颇老矣，尚能饭否？"));
        commits.put(4,new ArrayList<>());
        commits.get(4).add(new Commit(0,"admin","perfect!"));

        articles.add(new Article(5,"蝶恋花","宋词",null,
                "槛菊愁烟兰泣露，罗幕轻寒，燕子双飞去。\n明月不谙离恨苦，斜光到晓穿朱户。\n" +
                "昨夜西风凋碧树，独上高楼，望尽天涯路。\n欲寄彩笺兼尺素，山长水阔知何处？"));
        commits.put(5,new ArrayList<>());
        commits.get(5).add(new Commit(0,"admin","dsd"));

        articles.add(new Article(6,"临江仙","宋词",null,
                "梦后楼台高锁，酒醒帘幕低垂。\n去年春恨却来时。\n落花人独立，微雨燕双飞。 \n\n" +
                "记得小蘋初见，两重心字罗衣。\n琵琶弦上说相思。\n当时明月在，曾照彩云归。"));
        commits.put(6,new ArrayList<>());
        commits.get(6).add(new Commit(0,"admin","go to hell"));

        articles.add(new Article(7,"临江仙","佚名",null,
                "　　滚滚长江东逝水，浪花淘尽英雄。\n" +
                "　　是非成败转头空。\n" +
                "　　青山依旧在，几度夕阳红。\n" +
                "\n" +
                "　　白发渔樵江渚上，惯看秋月春风。\n" +
                "　   一壶浊酒喜相逢。\n" +
                "　　古今多少事，都付笑谈中。"));
        commits.put(7,new ArrayList<>());
        commits.get(7).add(new Commit(0,"admin","666"));

        articles.add(new Article(8,"上邪","佚名",null,"上邪！\n" +
                "我欲与君相知，\n" +
                "长命无绝衰。\n" +
                "山无陵，\n" +
                "江水为竭，\n" +
                "冬雷震震，\n" +
                "夏雨雪，\n" +
                "天地合，\n" +
                "乃敢与君绝！"));
        commits.put(8,new ArrayList<>());
        commits.get(8).add(new Commit(0,"admin","999"));

        articles.add(new Article(9,"无名","王勃",null,
                "　　滕王高阁临江渚，佩玉鸣鸾罢歌舞。\n" +
                "　　画栋朝飞南浦云，珠帘暮卷西山雨。\n" +
                "　　闲云潭影日悠悠，物换星移几度秋。\n" +
                "　　阁中帝子今何在？槛外长江空自流。\n"));
        commits.put(9,new ArrayList<>());
        commits.get(9).add(new Commit(0,"admin","我是谁"));
    }
}
