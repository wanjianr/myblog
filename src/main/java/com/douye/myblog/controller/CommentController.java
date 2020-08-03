package com.douye.myblog.controller;

import com.douye.myblog.dto.CommentDTO;
import com.douye.myblog.dto.ResultDTO;
import com.douye.myblog.enums.CommentTypeEnum;
import com.douye.myblog.exception.CustomizeErrorCode;
import com.douye.myblog.model.Comment;
import com.douye.myblog.model.User;
import com.douye.myblog.service.CommentService;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Controller
public class CommentController {

    @Autowired
    private CommentService commentService;

    @ResponseBody
    @RequestMapping(value = "/comment", method = RequestMethod.POST)
    public Object post(@RequestBody CommentDTO commentDTO,
                       HttpServletRequest request) { // @RequestBody 自动将json封装为对象------(反序列化)
        User user = (User) request.getSession().getAttribute("user");
        if (user == null) {
            return ResultDTO.errorOf(CustomizeErrorCode.NO_LOGIN);
        }
        if (commentDTO == null || StringUtils.isBlank(commentDTO.getContent())) {  // StringUtils.isBlank(obj) obj == null || obj == ""
            return ResultDTO.errorOf(CustomizeErrorCode.CONTENT_IS_EMPTY);
        }

        Comment comment = new Comment();
        comment.setParentId(commentDTO.getParentId());
        comment.setType(commentDTO.getType());
        comment.setCommentator(user.getId());
        comment.setGmtCreate(System.currentTimeMillis());
        comment.setGmtModified(commentDTO.getGmtCreate());
        comment.setLikeCount(commentDTO.getLikeCount());
        comment.setContent(commentDTO.getContent());
        commentService.insert(comment);
        return ResultDTO.okOf();   // @ResponseBody 可以讲对象序列化为json传给前端
    }

    @ResponseBody
    @RequestMapping(value = "/comment/{id}", method = RequestMethod.GET)
    public ResultDTO<List<CommentDTO>> comments(@PathVariable(name = "id") Long id) {
        List<CommentDTO> commentDTOS = commentService.findByQuestionId(id, CommentTypeEnum.COMMENT);
        return ResultDTO.okOf(commentDTOS);
    }
}
