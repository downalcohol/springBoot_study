package study.community.model;

import lombok.Data;

@Data
public class User {
    private Integer id;
    private String accountId;
    private String name;
    private String token;
    private Long gmtCreate;
    private Long gmtModified;
    private String tag;
    private String avatarUrl;

    public User() {
    }

    public User(String accountId, String name, String token, Long gmtCreate, Long gmtModified,String avatarUrl) {
        this(-1,accountId,name,token,gmtCreate,gmtModified,avatarUrl);
    }

    public User(int id, String accoutId, String name, String token, Long gmtCreate, Long gmtModified,String avatarUrl) {
        this.id = id;
        this.accountId = accoutId;
        this.name = name;
        this.token = token;
        this.gmtCreate = gmtCreate;
        this.gmtModified = gmtModified;
        this.avatarUrl = avatarUrl;
    }

}
