package com.douye.myblog.controller;

import com.douye.myblog.dto.PaginationDTO;
import com.douye.myblog.dto.QuestionDTO;
import com.douye.myblog.mapper.QuestionMapper;
import com.douye.myblog.mapper.UserMapper;
import com.douye.myblog.model.Question;
import com.douye.myblog.model.User;
import com.douye.myblog.service.QuestionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import java.util.List;

@Controller
public class IndexController {

    @Autowired
    UserMapper userMapper;

    @Autowired
    QuestionService questionService;

    @GetMapping("/")
    public String index(Model model ,
                        @RequestParam(name = "page", defaultValue = "1") Integer page,
                        @RequestParam(name = "size", defaultValue = "3") Integer size) {
        PaginationDTO pagination = questionService.findAll(page,size);
        model.addAttribute("pagination",pagination);
        return "index";
    }
}