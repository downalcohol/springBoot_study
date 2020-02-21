package study.community.dio;

public class GithubUser {
    private String name;
    private Long id;
    private String dio;

    public GithubUser(){

    }

    public GithubUser(String name, Long id, String dio) {
        this.name = name;
        this.id = id;
        this.dio = dio;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getDio() {
        return dio;
    }

    public void setDio(String dio) {
        this.dio = dio;
    }
}
