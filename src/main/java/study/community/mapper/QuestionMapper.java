package study.community.mapper;


import com.github.pagehelper.PageInfo;
import org.apache.ibatis.annotations.*;
import study.community.dto.QuestionDTO;
import study.community.model.Question;

import java.util.List;

@Mapper
public interface QuestionMapper {


    @Insert("insert into question (title,description,gmt_create,gmt_modified,creator,comment_count,view_count,like_count,tag) values (#{title},#{description},#{gmtCreate},#{gmtModified},#{creator},#{commentCount},#{viewCount},#{likeCount},#{tag})")
    void create(Question question);

    @Select("select * from question")
    List<Question> all();

    @Select("select * from question where creator = #{id}")
    List<Question> getByCreatorId(@Param("id")Integer id);

    @Select("select * from question where id = #{id}")
    Question getById(@Param("id") Integer id);

    @Update("update question set title = #{title},description = #{description},gmt_modified = #{gmtModified},tag = #{tag} where id = #{id}")
    void update(Question question);
}
