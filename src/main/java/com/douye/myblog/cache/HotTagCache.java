package com.douye.myblog.cache;

import com.douye.myblog.dto.HotTagDTO;
import lombok.Data;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.PriorityQueue;

@Component
@Data
public class HotTagCache {
    private List<String> hots = new ArrayList<>();

    public void updateTags(Map<String, Integer> tags) {
        int max = 10; // 设置前max个标签作为热门标签
        PriorityQueue<HotTagDTO> priorityQueue = new PriorityQueue<>(max);
        tags.forEach((tag,priority) -> {
            HotTagDTO hotTagDTO = new HotTagDTO();
            hotTagDTO.setTag(tag);
            hotTagDTO.setPriority(priority);
            if (priorityQueue.size() < max) {
                // 增加热门标签
                priorityQueue.add(hotTagDTO);
            } else {
                // 更新热门标签
                HotTagDTO peek = priorityQueue.peek();
                if (hotTagDTO.compareTo(peek) > 0) {
                    priorityQueue.poll();
                    priorityQueue.add(hotTagDTO);
                }
            }
        });
        List<String> sortedTags = new ArrayList<>();
        while (!priorityQueue.isEmpty()) {
            sortedTags.add(0,priorityQueue.poll().getTag());
        }
        hots = sortedTags;
    }
}
