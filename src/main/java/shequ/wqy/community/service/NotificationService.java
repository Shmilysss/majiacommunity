package shequ.wqy.community.service;

import org.apache.ibatis.session.RowBounds;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import shequ.wqy.community.dto.NotificationDTO;
import shequ.wqy.community.dto.PaginationDTO;
import shequ.wqy.community.mapper.NotificationMapper;
import shequ.wqy.community.mapper.UserMapper;
import shequ.wqy.community.model.Notification;
import shequ.wqy.community.model.NotificationExample;
import shequ.wqy.community.model.User;
import shequ.wqy.community.model.UserExample;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

/**
 * Author: wanqiangying
 * Date: 2020/4/2 20:33
 * Content:
 */
@Service
public class NotificationService {

    @Autowired
    private NotificationMapper notificationMapper;

    @Autowired
    private UserMapper userMapper;

    public PaginationDTO list(Long userId, Integer page, Integer size) {

        NotificationExample notificationExample = new NotificationExample();
        notificationExample.createCriteria()
                .andReceiverEqualTo(userId);
        Integer totalCount = (int) notificationMapper.countByExample(notificationExample);
        Integer totalPage = new Integer(0);
        ;
        if (totalCount % size == 0) {
            totalPage = totalCount / size;
        } else {
            totalPage = totalCount / size + 1;
        }
        if (page < 1) {
            page = 1;
        }
        if (page > totalPage) {
            page = totalPage;
        }
        Integer offset = size * (page - 1);
        NotificationExample example = new NotificationExample();
        example.createCriteria()
                .andReceiverEqualTo(userId);
        List<Notification> notifications = notificationMapper.selectByExampleWithRowbounds(example, new RowBounds(offset, size));

        PaginationDTO<NotificationDTO> paginationDTO = new PaginationDTO<>();
        if(notifications.size() == 0){
            return paginationDTO;
        }

        Set<Long> disUserIds = notifications.stream().map(notify -> notify.getNotifier()).collect(Collectors.toSet());
        List<Long> userIds = new ArrayList<>(disUserIds);
        UserExample userExample = new UserExample();
        userExample.createCriteria()
                .andIdIn(userIds);
        List<User> users = userMapper.selectByExample(userExample);
        Map<Long, User> userMap = users.stream().collect(Collectors.toMap(u -> u.getId(), u -> u));

       /* for (Notification notification : questions) {
            User user = userMapper.selectByPrimaryKey(notification.getCreator());
            QuestionDTO questionDTO = new QuestionDTO();
            BeanUtils.copyProperties(notification, questionDTO);
            questionDTO.setUser(user);
            notificationDTOS.add(questionDTO);
        }*/
        List<NotificationDTO> notificationDTOS = new ArrayList<NotificationDTO>();
        paginationDTO.setData(notificationDTOS);
        paginationDTO.setPagination(totalPage, page, size);

        return paginationDTO;
    }
}
