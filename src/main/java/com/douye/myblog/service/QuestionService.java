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

        PaginationDTO paginationDTO = new PaginationDTO();
        // 查询问题总数
        Integer totalCount = questionMapper.findCount();


        Integer totalPage;
        if (totalCount % size == 0) {
            totalPage = totalCount / size;
        } else {
            totalPage = totalCount / size + 1;
        }
        if (page < 1) page = 1;
        if (page > totalPage) page = totalPage;
        if (page < 1) page = 1;

        paginationDTO.setPagination(totalPage,page);

        List<QuestionDTO> questionDTOList = new ArrayList<>();
        Integer offset = (page-1) * size;
        List<Question> questions = questionMapper.findAll(offset,size);

        for (Question question : questions) {
            User user = userMapper.findByCreator(question.getCreator());
            QuestionDTO questionDTO = new QuestionDTO();
            BeanUtils.copyProperties(question,questionDTO);
            questionDTO.setUser(user);
            questionDTOList.add(questionDTO);
        }
        paginationDTO.setQuestions(questionDTOList);

        return paginationDTO;
    }

    public PaginationDTO findMyQuestion(Long id, Integer page, Integer size) {
        PaginationDTO paginationDTO = new PaginationDTO();
        Integer totalPage;
        Integer totalCount = questionMapper.userQuestionCount(id);

        List<QuestionDTO> questionDTOList = new ArrayList<>();


        if (totalCount % size == 0) {
            totalPage = totalCount / size;
        } else {
            totalPage = totalCount / size + 1;
        }

        if (page < 1) page = 1;
        if (page > totalPage) page = totalPage;
        if (page < 1) page = 1; // 防止总页数为0的 情况

        paginationDTO.setPagination(totalPage,page);

        Integer offset = (page-1) * size;
        List<Question> questions = questionMapper.findByCreator(id,offset,size);

        for (Question question : questions) {
            User user = userMapper.findByCreator(question.getCreator());
            QuestionDTO questionDTO = new QuestionDTO();
            BeanUtils.copyProperties(question,questionDTO);
            questionDTO.setUser(user);
            questionDTOList.add(questionDTO);
        }
        paginationDTO.setQuestions(questionDTOList);
        return paginationDTO;
    }

    public QuestionDTO findById(Long id) {
        QuestionDTO questionDTO = questionMapper.findById(id);
        User byCreator = userMapper.findByCreator(questionDTO.getCreator());
        questionDTO.setUser(byCreator);
        return questionDTO;
    }

    public void updateOrCreate(Question question) {
        if (question.getId() == null) {
            // 插入
            question.setGmtCreate(System.currentTimeMillis());
            question.setGmtModified(question.getGmtCreate());
            question.setViewCount(0);
            question.setLikeCount(0);
            question.setCommentCount(0);
            questionMapper.create(question);
        } else {
            // 更新
            question.setGmtModified(System.currentTimeMillis());
            questionMapper.update(question);
        }
    }

    public void viewCountInc(Long id) {
        questionMapper.updateViewCount(id);
    }
}
