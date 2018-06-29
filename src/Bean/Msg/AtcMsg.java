package Bean.Msg;

import Bean.Article;

public class AtcMsg extends Message{

    private Article article;

    public AtcMsg(String uid,Article article){
        super(uid,"SERVER");
        this.article=article;
    }

    public Article getArticle() {
        return article;
    }

    @Override
    public String getMsg() {
        return "Article [title:"+article.getTitle()+",author:"+article.getAuthor()+"]";
    }

    @Override
    public String toString() {
        return getMsg();
    }
}
