package com.douye.myblog.mapper;

import com.douye.myblog.dto.NotificationDTO;
import com.douye.myblog.dto.PaginationDTO;
import com.douye.myblog.model.Notification;
import org.apache.ibatis.annotations.*;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
@Mapper
public interface NotificationMapper {

    @Insert("insert into notification (id, notifier, receiver, \n" +
            "      outer_id, type, gmt_create, \n" +
            "      status, notifier_name, outer_title\n" +
            "      )\n" +
            "    values (#{id}, #{notifier}, #{receiver}, \n" +
            "      #{outerId}, #{type}, #{gmtCreate,jdbcType=BIGINT}, \n" +
            "      #{status}, #{notifierName}, #{outerTitle})")
    void insert(Notification notification);

    @Select("select count(1) from notification where receiver = #{id}")
    Integer userCommentCount(@Param("id") Long id);

    @Select("select * from notification where receiver = #{id} limit #{offset},#{size}")
    List<Notification> findByReceiver(@Param("id") Long id, @Param("offset") Integer offset, @Param("size") Integer size);

    @Select("select * from notification where id = #{id}")
    Notification findByPrimaryKey(@Param("id") Long id);

    @Update("update notification set status = #{status} where id = #{id}")
    void updateByPrimaryKey(Notification notification);

    @Select("select count(1) from notification where receiver=#{id} and status=#{status}")
    int getUnreadCount(@Param("id") Long id, @Param("status") int status);
}
