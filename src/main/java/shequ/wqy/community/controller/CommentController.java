package shequ.wqy.community.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import shequ.wqy.community.dto.CommentDTO;
import shequ.wqy.community.mapper.CommentMapper;
import shequ.wqy.community.model.Comment;

import java.util.HashMap;
import java.util.Map;

/**
 * Author: wanqiangying
 * Date: 2020/1/1 22:24
 * Content:
 */
@Controller
public class CommentController {

    @Autowired
    private CommentMapper commentMapper;

    @ResponseBody
    @RequestMapping(value = "/comment",method = RequestMethod.POST)
    public Object post(@RequestBody CommentDTO commentDTO){
        Comment comment = new Comment();
        comment.setParentId(commentDTO.getParentId());
        comment.setContent(commentDTO.getContent());
        comment.setType(commentDTO.getType());
        comment.setGmtCreate(System.currentTimeMillis());
        comment.setGmtModified(System.currentTimeMillis());
        comment.setCommentator(1);
        comment.setLikeCount(0L);
        commentMapper.insert(comment);
        Map<Object,Object> objectObjectMap =  new HashMap<>();
        objectObjectMap.put("message","成功");
        return null;
    }
}
