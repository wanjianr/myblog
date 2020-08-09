package com.douye.myblog.controller;

import com.douye.myblog.cache.HotTagCache;
import com.douye.myblog.dto.PaginationDTO;
import com.douye.myblog.dto.QuestionDTO;
import com.douye.myblog.mapper.UserMapper;
import com.douye.myblog.service.QuestionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

@Controller
public class IndexController {

    @Autowired
    UserMapper userMapper;

    @Autowired
    QuestionService questionService;

    @Autowired
    HotTagCache hotTagCache;

    @GetMapping("/")
    public String index(Model model ,
                        @RequestParam(name = "page", defaultValue = "1") Integer page,
                        @RequestParam(name = "size", defaultValue = "8") Integer size,
                        @RequestParam(name = "search",required = false) String search,
                        @RequestParam(name = "tag",required = false) String tag) {
        PaginationDTO<QuestionDTO> pagination = questionService.findAll(page, size, search, tag);
        List<String> hots = hotTagCache.getHots();
        model.addAttribute("tag",tag);
        model.addAttribute("tags", hots);
        model.addAttribute("pagination",pagination);
        return "index";
    }
}