package com.douye.myblog.service;

import com.douye.myblog.mapper.UserMapper;
import com.douye.myblog.model.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class UserService {
    @Autowired
    UserMapper userMapper;

    public void createOrUpdate(User user) {
        // 根据accountId查询user表
        User userMapperByAccountId = userMapper.findByAccountId(user.getAccountId());
        if (userMapperByAccountId == null) {
            // 用户不存在，执行插入操作
            user.setGmtCreate(System.currentTimeMillis());
            user.setGmtModified(user.getGmtCreate());
            userMapper.insert(user);
        } else {
            // 用户存在，执行更新操作
            user.setGmtCreate(System.currentTimeMillis());
            user.setGmtModified(user.getGmtCreate());
            userMapper.updata(user);
        }

    }
}
