package com.douye.myblog.mapper;

import com.douye.myblog.model.Question;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
@Mapper
public interface QuestionMapper {

    @Insert("insert into question (title,description,gmt_create,gmt_modified,creator,comment_count,view_count,like_count,tag) values (#{title},#{description},#{gmtCreate},#{gmtModified},#{creator},#{commentCount},#{viewCount},#{likeCount},#{tag})")
    void create(Question question);

    @Select("select * from question limit #{offset},#{size}")
    List<Question> findAll(@Param("offset") Integer offset, @Param("size") Integer size);

    @Select("select count(1) from question")
    Integer findCount();

    @Select("select * from question where creator=#{id} limit #{offset},#{size}")
    List<Question> findByCreator(@Param("id") Long id, @Param("offset") Integer offset, @Param("size") Integer size);

    @Select("select count(1) from question where creator=#{id}")
    Integer userQuestionCount(@Param("id") Long id);
}
