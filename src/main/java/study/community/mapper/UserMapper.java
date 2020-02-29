package study.community.mapper;

import org.apache.ibatis.annotations.*;
import org.springframework.stereotype.Service;
import study.community.model.User;

@Mapper
public interface UserMapper {

    @Select("select * from user where id = #{id}")
    User findById(@Param("id") int id);

    @Insert("insert into user (account_id,name,token,gmt_create,gmt_modified,avatar_url) VALUES (#{accountId},#{name},#{token},#{gmtCreate},#{gmtModified},#{avatarUrl})")
    void insert(User user);

    @Select("select * from user where token = #{token}")
    User findToken(@Param("token") String token);

    @Select("select * from user where account_id = #{accountId}")
    User findByGithubId(@Param("accountId")long accountId);

    @Update("update user set name = #{name},token = #{token},gmt_modified = #{gmtModified},avatar_url = #{avatarUrl}")
    void update(User user);
}
