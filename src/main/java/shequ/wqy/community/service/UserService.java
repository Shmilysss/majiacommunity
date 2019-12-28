package shequ.wqy.community.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import shequ.wqy.community.mapper.UserMapper;
import shequ.wqy.community.model.User;
import shequ.wqy.community.model.UserExample;

import java.util.List;

/**
 * Author: wanqiangying
 * Date: 2019/11/26 22:01
 * Content:
 */
@Service
public class UserService {

    @Autowired
    private UserMapper userMapper;

    public void createOrUpdate(User user) {
        if (user != null && user.getAccountId() != null) {
            UserExample userExample = new UserExample();
            userExample.createCriteria()
                    .andAccountIdEqualTo(user.getAccountId());
            List<User> userList = userMapper.selectByExample(userExample);
//            dbUser = userMapper.findByAccountId(user.getAccountId());
            if (userList.size() == 0) {
                //插入一条数据
                user.setGmtCreate(System.currentTimeMillis());
                user.setGmtModified(user.getGmtCreate());
                userMapper.insert(user);
            } else {
                //更新,从github中取的user，更新到本地数据库中
                User dbUser = userList.get(0);
                User updUser = new User();
                updUser.setAvatarUrl(user.getAvatarUrl());
                updUser.setGmtModified(System.currentTimeMillis());
                updUser.setToken(user.getToken());
                updUser.setName(user.getName());
                UserExample example = new UserExample();
                example.createCriteria()
                        .andIdEqualTo(dbUser.getId());
                userMapper.updateByExampleSelective(updUser,example);
//                userMapper.update(dbUser);
            }
        }

    }
}
