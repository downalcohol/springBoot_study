package study.community.mapper;

import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import study.community.model.User;

@Mapper
public interface UserMapper {

    @Select("select * from user where id = #{creatorId}")
    User findById(@Param("creatorId") int creatorId);

    @Insert("insert into user (account_id,name,token,gmt_create,gmt_modified,avatar_url) VALUES (#{accountId},#{name},#{token},#{gmtCreate},#{gmtModified},#{avatarUrl})")
    void insert(User user);

    @Select("select * from user where token = #{token}")
    User findToken(@Param("token") String token);
}
