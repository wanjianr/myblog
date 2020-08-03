package com.douye.myblog.mapper;

import com.douye.myblog.model.Comment;
import org.apache.ibatis.annotations.*;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
@Mapper
public interface CommentMapper {
    @Insert("insert into comment (parent_id,type,commentator,gmt_create,gmt_modified,like_count,content) values (#{parentId},#{type},#{commentator},#{gmtCreate},#{gmtModified},#{likeCount},#{content})")
    void insert(Comment comment);

    @Select("select * from comment where id = #{id}")
    Comment selectByPrimaryKey(@Param("id") Long id);

    @Select("select * from comment where parent_id=#{id} and type = #{type} order by gmt_create desc")
    List<Comment> findByParentId(@Param("id") Long id, Integer type);

    @Update("update comment set comment_count = comment_count + #{commentCount} where id=#{id}")
    void incCommentCount(Comment comment);
}
