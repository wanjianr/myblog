package com.douye.myblog.service;

import com.douye.myblog.dto.PaginationDTO;
import com.douye.myblog.dto.QuestionDTO;
import com.douye.myblog.mapper.QuestionMapper;
import com.douye.myblog.mapper.UserMapper;
import com.douye.myblog.model.Question;
import com.douye.myblog.model.User;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class QuestionService {

    @Autowired
    QuestionMapper questionMapper;

    @Autowired
    UserMapper userMapper;

    public PaginationDTO findAll(Integer page, Integer size) {
        List<QuestionDTO> questionDTOList = new ArrayList<>();
        Integer offset = (page-1) * size;
        List<Question> questions = questionMapper.findAll(offset,size);
        PaginationDTO paginationDTO = new PaginationDTO();

        for (Question question : questions) {
            User user = userMapper.findById(question.getCreator());
            QuestionDTO questionDTO = new QuestionDTO();
            BeanUtils.copyProperties(question,questionDTO);
            questionDTO.setUser(user);
            questionDTOList.add(questionDTO);
        }
        paginationDTO.setQuestions(questionDTOList);

        // 查询问题总数
        Integer totalCount = questionMapper.findCount();
        paginationDTO.setPagination(totalCount,page,size);
        return paginationDTO;
    }
}
