package shequ.wqy.community.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import shequ.wqy.community.dto.QuestionDTO;
import shequ.wqy.community.service.QuestionService;

/**
 * Author: wanqiangying
 * Date: 2019/11/24 20:01
 * Content：问题具体内容
 */
@Controller
public class QuestionController {

    @Autowired
    private QuestionService questionService;

    @GetMapping("/question/{id}")
    public String question(@PathVariable(name="id") Integer id,
                           Model model){
        QuestionDTO questionDTO = questionService.getById(id);
        //增加一次浏览数
        questionService.updView(id);
        model.addAttribute("question",questionDTO);
        return "question";

    }


}
