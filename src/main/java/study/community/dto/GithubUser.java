package study.community.dto;

import lombok.Data;

@Data
public class GithubUser {
    private String name;
    private Long id;
    private String dio;
    private String avatarUrl;

    public GithubUser(){

    }

    public GithubUser(String name, Long id, String dio,String avatarUrl) {
        this.name = name;
        this.id = id;
        this.dio = dio;
        this.avatarUrl = avatarUrl;
    }
}
