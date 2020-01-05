package shequ.wqy.community.service;

import org.springframework.stereotype.Service;
import shequ.wqy.community.model.Comment;

/**
 * Author: wanqiangying
 * Date: 2020/1/5 22:19
 * Content:
 */
@Service
public class CommentService {
    public void insert(Comment comment) {
        if (comment.getParentId() == 0 || comment.getParentId() == null) {
//            throw  Exception;
        }
    }
}
