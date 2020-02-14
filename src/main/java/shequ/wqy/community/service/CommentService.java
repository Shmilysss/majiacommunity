package shequ.wqy.community.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import shequ.wqy.community.enums.CommentTypeEnum;
import shequ.wqy.community.exception.CustomizeErrorCode;
import shequ.wqy.community.exception.CustomizeException;
import shequ.wqy.community.mapper.CommentMapper;
import shequ.wqy.community.mapper.QuestionExMapper;
import shequ.wqy.community.mapper.QuestionMapper;
import shequ.wqy.community.model.Comment;
import shequ.wqy.community.model.Question;

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

    @Transactional
    public void insert(Comment comment) {
        if (comment.getParentId() == 0 || comment.getParentId() == null) {
            throw new CustomizeException(CustomizeErrorCode.TARGET_PARAM_NOT_FOUND);
        }
        if(comment.getType() == null || !CommentTypeEnum.isExist(comment.getType())){
            throw new CustomizeException(CustomizeErrorCode.TYPE_WRONG);
        }
        if(comment.getType() == CommentTypeEnum.COMMENT.getType()){
            //回复评论
            Comment dbComment = commentMapper.selectByPrimaryKey(comment.getParentId());
            if(dbComment == null){
                throw new CustomizeException(CustomizeErrorCode.COMMENT_NOT_FOUND);
            }
            commentMapper.insert(dbComment);
        }else{
            //回复问题
            questionMapper.selectByPrimaryKey(comment.getParentId());
        }
    }
}
