package com.douye.myblog.mapper;

import com.douye.myblog.dto.QuestionDTO;
import com.douye.myblog.dto.QuestionQueryDTO;
import com.douye.myblog.model.Question;
import org.apache.ibatis.annotations.*;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
@Mapper
public interface QuestionMapper {

    @Insert("insert into question (title,description,gmt_create,gmt_modified,creator,comment_count,view_count,like_count,tag) values (#{title},#{description},#{gmtCreate},#{gmtModified},#{creator},#{commentCount},#{viewCount},#{likeCount},#{tag})")
    void create(Question question);

    @Select("<script> \n" +
            "select * from question \n" +
                "<if test=\"search!=null and search!=''\"> \n" +
                    "where title regexp #{search} \n" +
                "</if> \n" +
                "<if test=\"sort==null or sort==''\"> \n" +
                    "order by gmt_create desc \n" +
                "</if> \n" +
            " limit #{offset},#{size} \n" +
            "</script> \n")
    List<Question> findAll(QuestionQueryDTO questionQueryDTO);

    @Select("<script> \n" +
            "select count(*) from question \n" +
                "<if test=\"search != null and search != ''\"> \n" +
                    "where title regexp #{search} \n" +
                "</if> \n" +
            "</script> \n")
    Integer findCount(QuestionQueryDTO questionQueryDTO);

    @Select("select * from question where creator=#{id} order by gmt_create desc limit #{offset},#{size}")
    List<Question> findByCreator(@Param("id") Long id, @Param("offset") Integer offset, @Param("size") Integer size);

    @Select("select count(1) from question where creator=#{id} order by gmt_create desc")
    Integer userQuestionCount(@Param("id") Long id);

    @Select("select * from question where id = #{id}")
    QuestionDTO findById(@Param("id") Long id);

    @Update("update question set title=#{title}, description=#{description}, gmt_modified=#{gmtModified}, tag=#{tag} where id=#{id}")
    void update(Question question);

    @Update("update question set view_count = view_count+1 where id=#{id}")
    void updateViewCount(@Param("id") Long id);

    @Select("select * from question where id = #{id}")
    Question selectByPrimaryKey(@Param("id") Long id);

    @Update("update question set comment_count = comment_count + #{commentCount} where id = #{id}")
    int incCommentCount(Question record);

    @Select("select * from question where tag regexp #{tag}")
    List<Question> searchRelated(Question question);
}
