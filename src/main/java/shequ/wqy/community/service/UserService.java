package shequ.wqy.community.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import shequ.wqy.community.mapper.UserMapper;
import shequ.wqy.community.model.User;

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
        User dbUser = null;
        if(user!=null && user.getAccountId()!=null){
            dbUser = userMapper.findByAccountId(user.getAccountId());
        }
        if(dbUser==null){
            //插入一条数据
            user.setGmtCreate(System.currentTimeMillis());
            user.setGmtModified(user.getGmtCreate());
            userMapper.insert(user);
        }else{
            //更新,从github中取的user，更新到本地数据库中
            dbUser.setAvatarUrl(user.getAvatarUrl());
            dbUser.setGmtModified(System.currentTimeMillis());
            dbUser.setToken(user.getToken());
            dbUser.setName(user.getName());
            userMapper.update(dbUser);
        }
    }
}
