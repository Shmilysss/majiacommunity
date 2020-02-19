package shequ.wqy.community.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import shequ.wqy.community.dto.CommentDTO;
import shequ.wqy.community.dto.QuestionDTO;
import shequ.wqy.community.enums.CommentTypeEnum;
import shequ.wqy.community.service.CommentService;
import shequ.wqy.community.service.QuestionService;

import java.util.List;

/**
 * Author: wanqiangying
 * Date: 2019/11/24 20:01
 * Content：问题具体内容
 */
@Controller
public class QuestionController {

    @Autowired
    private QuestionService questionService;

    @Autowired
    private CommentService commentService;

    @GetMapping("/question/{id}")
    public String question(@PathVariable(name="id") Long id,
                           Model model){
        QuestionDTO questionDTO = questionService.getById(id);
        List<QuestionDTO> relatedQuestions = questionService.selectRelated(questionDTO);
        List<CommentDTO> comments = commentService.ListByTargetId(id, CommentTypeEnum.QUESTION);
        //增加一次浏览数
        questionService.updView(id);
        model.addAttribute("question",questionDTO);
        model.addAttribute("comments",comments);
        model.addAttribute("relatedQuestions",relatedQuestions);
        return "question";

    }


}
