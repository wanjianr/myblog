package com.douye.myblog.controller;

import com.douye.myblog.dto.CommentDTO;
import com.douye.myblog.dto.QuestionDTO;
import com.douye.myblog.enums.CommentTypeEnum;
import com.douye.myblog.service.CommentService;
import com.douye.myblog.service.QuestionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import java.util.List;

@Controller
public class QuestionController {
    @Autowired
    QuestionService questionService;

    @Autowired
    CommentService commentService;

    @GetMapping("/question/{id}")
    public String questionDetail(@PathVariable(name = "id") Long id, Model model) {
        QuestionDTO question = questionService.findById(id);
        List<QuestionDTO> relatedQuestions =  questionService.searchRelated(question);
        questionService.viewCountInc(id);
        List<CommentDTO> commentDTOS = commentService.findByQuestionId(question.getId(), CommentTypeEnum.QUESTION);
        model.addAttribute("question",question);
        model.addAttribute("relatedQuestions", relatedQuestions);
        model.addAttribute("comments",commentDTOS);
        return "question";
    }
}
