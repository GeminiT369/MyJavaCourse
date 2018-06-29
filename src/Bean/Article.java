package Bean;

import java.io.Serializable;

public class Article implements Serializable {

    private int aid;
    private String title;
    private String author;
    private int[] label;
    private String content;

    public Article(String title,String author,String content){
        this(-1,title,author,null,content);
    }

    public Article(String title,String author,int[] label,String content){
        this(-1,title,author,label,content);
    }

    public Article(int aid,String title,String author,int[] label,String content){
        this.aid=aid;
        this.title=title;
        this.author=author;
        this.label=label;
        this.content=content;
    }

    public int getAid() {
        return aid;
    }

    public String getTitle() {
        return title;
    }

    public String getAuthor() {
        return author;
    }

    public int[] getLabel() {
        return label;
    }

    public String getContent() {
        return content;
    }

    public String formatDisplay(){
        String summary;
        if (content.length()>20)
            summary=content.substring(0,20);
        else
            summary=content;

        return "<html><body><div style='color:#36648B;font-size:15px;font-family:ºÚÌå;'>"
                +title+"</div>"
                +author+"<br>"
                +summary+"</body></html>";
    }

    public void setAid(int aid) {
        this.aid = aid;
    }
}
