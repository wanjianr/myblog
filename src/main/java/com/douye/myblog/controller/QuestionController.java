package com.douye.myblog.controller;

import com.douye.myblog.dto.QuestionDTO;
import com.douye.myblog.service.QuestionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@Controller
public class QuestionController {
    @Autowired
    QuestionService questionService;

    @GetMapping("/question/{id}")
    public String questionDetail(@PathVariable(name = "id") Long id, Model model) {
        QuestionDTO question = questionService.findById(id);
        model.addAttribute("question",question);
        return "question";
    }
}
