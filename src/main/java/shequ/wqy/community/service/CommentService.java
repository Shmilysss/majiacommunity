package shequ.wqy.community.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
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
            return ;
        }
        if(comment.getType() == 1000){
            //回复评论
            Comment dbComment = commentMapper.selectByPrimaryKey(comment.getParentId());
            if(dbComment == null){
                //抛异常，未找到记录
                System.out.println("dbComment抛异常，未找到记录!");
            }
            commentMapper.insert(comment);
        } else {
            //回复问题
            Question question = questionMapper.selectByPrimaryKey(comment.getParentId());
            if(question == null){
                //抛异常
                System.out.println("question抛异常，未找到记录!");
            }
            commentMapper.insert(comment);
            question.setCommentCount(1);
            questionExMapper.incCommentCount(question);
        }
    }
}
