package com.douye.myblog.model;

import lombok.Data;

@Data
public class Comment {
    private Long id;
    private Long parentId;   // 所回复的问题/评论的id
    private Integer type;    // 回复类型（1-评论，2-问题）
    private Long commentator; // 评论人的id
    private Long gmtCreate;
    private Long gmtModified;
    private Long likeCount;    // 点赞数
    private Integer commentCount; // 评论数
    private String content;    // 评论内容
}
