package shequ.wqy.community.service;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import shequ.wqy.community.dto.CommentDTO;
import shequ.wqy.community.enums.CommentTypeEnum;
import shequ.wqy.community.enums.NotificationStatusEnum;
import shequ.wqy.community.enums.NotificationTypeEnum;
import shequ.wqy.community.exception.CustomizeErrorCode;
import shequ.wqy.community.exception.CustomizeException;
import shequ.wqy.community.mapper.*;
import shequ.wqy.community.model.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

/**
 * Author: wanqiangying
 * Date: 2020/1/5 22:19
 * Content:
 */
@Service
public class CommentService {

    @Autowired
    private CommentMapper commentMapper;

    @Autowired
    private QuestionMapper questionMapper;

    @Autowired
    private QuestionExMapper questionExMapper;

    @Autowired
    private UserMapper userMapper;

    @Autowired
    private NotificationMapper notificationMapper;

    @Transactional
    public void insert(Comment comment,User user) {
        if (comment.getParentId() == 0 || comment.getParentId() == null) {
            throw new CustomizeException(CustomizeErrorCode.TARGET_PARAM_NOT_FOUND);
        }
        if (comment.getType() == null || !CommentTypeEnum.isExist(comment.getType())) {
            throw new CustomizeException(CustomizeErrorCode.TYPE_WRONG);
        }
        if (comment.getType() == CommentTypeEnum.COMMENT.getType()) {
            //回复评论
            Comment dbComment = commentMapper.selectByPrimaryKey(comment.getParentId());
            if (dbComment == null) {
                throw new CustomizeException(CustomizeErrorCode.COMMENT_NOT_FOUND);
            }
            //回复问题
            Question question = questionMapper.selectByPrimaryKey(dbComment.getParentId());
            if (question == null) {
                throw new CustomizeException(CustomizeErrorCode.QUESTION_NOT_FOUND);
            }
            commentMapper.insert(comment);
            // 创建通知
            createNotify(comment, dbComment.getCommentator(), user.getName(), question.getTitle(), NotificationTypeEnum.REPLY_COMMENT);
        } else {
            //回复问题
            Question question = questionMapper.selectByPrimaryKey(comment.getParentId());
            if (question == null) {
                throw new CustomizeException(CustomizeErrorCode.QUESTION_NOT_FOUND);
            }
            commentMapper.insert(comment);
            question.setCommentCount(1);
            questionExMapper.incCommentCount(question);

            // 创建通知
            createNotify(comment, question.getCreator(), user.getName(),question.getTitle(), NotificationTypeEnum.REPLY_COMMENT);
        }
    }

    public List<CommentDTO> ListByTargetId(Long id, CommentTypeEnum type) {
        CommentExample commentExample = new CommentExample();
        commentExample.createCriteria()
                .andParentIdEqualTo(id)
                .andTypeEqualTo(type.getType());
        commentExample.setOrderByClause("gmt_create desc");
        List<Comment> comments = commentMapper.selectByExample(commentExample);

        //lamda获取去重的评论人
        Set<Long> commentors = comments.stream().map(comment -> comment.getCommentator()).collect(Collectors.toSet());
        if(comments.size() == 0){
            return new ArrayList<>();
        }
        List<Long> userIds = new ArrayList<>();
        userIds.addAll(commentors);

        //获取评论人并转换为map
        UserExample userExample = new UserExample();
        userExample.createCriteria()
                .andIdIn(userIds);
        List<User> users = userMapper.selectByExample(userExample);
        Map<Long, User> userMap = users.stream().collect(Collectors.toMap(user -> user.getId(), user -> user));

        //转换comment 为 commentDTO
        List<CommentDTO> commentDTOS = comments.stream().map(comment -> {
            CommentDTO commentDTO = new CommentDTO();
            BeanUtils.copyProperties(comment,commentDTO);
            commentDTO.setUser(userMap.get(comment.getCommentator()));
            return commentDTO;
        }).collect(Collectors.toList());
        return commentDTOS;
    }

    //创建通知方法
    private void createNotify(Comment comment, Long receiver, String notifierName, String outerTitle, NotificationTypeEnum notificationType) {
        if (receiver == comment.getCommentator()) {
            return;
        }
        Notification notification = new Notification();
        notification.setGmtCreate(System.currentTimeMillis());
        notification.setType(notificationType.getType());
        notification.setOuterid(comment.getParentId());
        notification.setNotifier(comment.getCommentator());
        notification.setStatus(NotificationStatusEnum.UNREAD.getStatus());
        notification.setReceiver(receiver);
        notification.setNotifierName(notifierName);
        notification.setOuterTitle(outerTitle);
        notificationMapper.insert(notification);
    }
}
