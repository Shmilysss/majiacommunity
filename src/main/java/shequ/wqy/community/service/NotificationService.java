package shequ.wqy.community.service;

import org.apache.ibatis.session.RowBounds;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import shequ.wqy.community.dto.NotificationDTO;
import shequ.wqy.community.dto.PaginationDTO;
import shequ.wqy.community.dto.QuestionDTO;
import shequ.wqy.community.mapper.NotificationMapper;
import shequ.wqy.community.mapper.UserMapper;
import shequ.wqy.community.model.*;

import java.util.ArrayList;
import java.util.List;

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
        List<Notification> questions = notificationMapper.selectByExampleWithRowbounds(example, new RowBounds(offset, size));
        List<NotificationDTO> notificationDTOS = new ArrayList<NotificationDTO>();

        PaginationDTO<NotificationDTO> paginationDTO = new PaginationDTO<>();
        /*for (Notification notification : questions) {
            User user = userMapper.selectByPrimaryKey(notification.getCreator());
            QuestionDTO questionDTO = new QuestionDTO();
            BeanUtils.copyProperties(notification, questionDTO);
            questionDTO.setUser(user);
            notificationDTOS.add(questionDTO);
        }*/
        paginationDTO.setData(notificationDTOS);
        paginationDTO.setPagination(totalPage, page, size);

        return paginationDTO;
    }
}
