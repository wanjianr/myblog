package com.douye.myblog.schedule;

import com.douye.myblog.cache.HotTagCache;
import com.douye.myblog.dto.QuestionQueryDTO;
import com.douye.myblog.mapper.QuestionMapper;
import com.douye.myblog.model.Question;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.util.*;

@Component
@Slf4j
public class HotTagTasks {

    @Autowired
    QuestionMapper questionMapper;

    @Autowired
    HotTagCache hotTagCache;

    @Scheduled(fixedRate = 1000 * 60)
    public void hotTagSchedule() {
        int offset = 0, limit = 5;
        log.info("hotTagSchedule start {}", new Date());
        QuestionQueryDTO questionQueryDTO = new QuestionQueryDTO();
        Map<String,Integer> priorities = new HashMap<>();
        List<Question> list = new ArrayList<>();
        while (offset == 0 || limit==list.size()) {
            questionQueryDTO.setOffset(offset);
            questionQueryDTO.setSize(limit);
            list = questionMapper.findAll(questionQueryDTO);
            for (Question question : list) {
                String[] tags = StringUtils.split(question.getTag(), ",");
                for (String tag : tags) {
                    Integer priority = priorities.get(tag);
                    if (priority == null) {
                        priorities.put(tag,1 + question.getCommentCount());
                    } else {
                        priorities.put(tag,priority + 1 + question.getCommentCount());
                    }
                }
            }
            offset += limit;
        }
//        priorities.forEach((k,v) -> {
//            System.out.print(k + ": ");
//            System.out.print(v);
//            System.out.println();
//        });
        hotTagCache.updateTags(priorities);
        log.info("hotTagSchedule stop {}", new Date());
    }
}
