package study.community.model;

import lombok.Data;

@Data
public class Question {
    private int id;
    private String title;
    private String description;
    private long gmtCreate;
    private long gmtModified;
    private int creator;
    private int commentCount;
    private int viewCount;
    private int likeCount;
    private String tag;

    public Question() {
    }

    public Question( String title, String description, long gmtCreate, long gmtModified, int creator,String tag) {
        this(title,description,gmtCreate,gmtModified,creator,0,0,0,tag);
    }

    public Question( String title, String description, long gmtCreate, long gmtModified, int creator, int commentCount, int viewCount, int likeCount,String tag) {
        this.title = title;
        this.description = description;
        this.gmtCreate = gmtCreate;
        this.gmtModified = gmtModified;
        this.creator = creator;
        this.commentCount = commentCount;
        this.viewCount = viewCount;
        this.likeCount = likeCount;
        this.tag = tag;
    }

}
