package com.douye.myblog.service;

import com.douye.myblog.dto.NotificationDTO;
import com.douye.myblog.dto.PaginationDTO;
import com.douye.myblog.dto.QuestionDTO;
import com.douye.myblog.enums.NotificationStatusEnum;
import com.douye.myblog.enums.NotificationTypeEnum;
import com.douye.myblog.exception.CustomizeErrorCode;
import com.douye.myblog.exception.CustomizeException;
import com.douye.myblog.mapper.NotificationMapper;
import com.douye.myblog.mapper.UserMapper;
import com.douye.myblog.model.Notification;
import com.douye.myblog.model.Question;
import com.douye.myblog.model.User;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class NotificationService {


    @Autowired
    NotificationMapper notificationMapper;

    public PaginationDTO findMyComment(Long id, Integer page, Integer size) {
        PaginationDTO<NotificationDTO> paginationDTO = new PaginationDTO<>();
        Integer totalPage;
        Integer totalCount = notificationMapper.userCommentCount(id);

        List<NotificationDTO> notificationDTOList = new ArrayList<>();

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
        List<Notification> notifications = notificationMapper.findByReceiver(id,offset,size);

        for (Notification notification : notifications) {
            NotificationDTO notificationDTO = new NotificationDTO();
            BeanUtils.copyProperties(notification,notificationDTO);
            notificationDTO.setTypeName(NotificationTypeEnum.nameOfType(notification.getType()));
            notificationDTOList.add(notificationDTO);
        }
        paginationDTO.setData(notificationDTOList);
        return paginationDTO;
    }

    public NotificationDTO read(Long id, User user) {
        Notification notification = notificationMapper.findByPrimaryKey(id);
        if (notification == null) {
            throw new CustomizeException(CustomizeErrorCode.NOTIFICATION_NOT_FOUND);
        }
        // 将该回复设置为已读状态
        notification.setStatus(NotificationStatusEnum.READ.getStatus());
        notificationMapper.updateByPrimaryKey(notification);

        NotificationDTO notificationDTO = new NotificationDTO();
        BeanUtils.copyProperties(notification, notificationDTO);
        notificationDTO.setTypeName(NotificationTypeEnum.nameOfType(notification.getType()));
        return notificationDTO;
    }
}
