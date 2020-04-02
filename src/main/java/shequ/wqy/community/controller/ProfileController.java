package shequ.wqy.community.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;
import shequ.wqy.community.dto.PaginationDTO;
import shequ.wqy.community.model.User;
import shequ.wqy.community.service.NotificationService;
import shequ.wqy.community.service.QuestionService;

import javax.servlet.http.HttpServletRequest;

@Controller
public class ProfileController {
    @Autowired
    private QuestionService questionService;

    @Autowired
    private NotificationService notificationService;

    @GetMapping("/profile/{action}")
    public String profile(@PathVariable(name="action") String action,
                          HttpServletRequest request,
                          Model model,
                          @RequestParam(name = "page", defaultValue = "1") Integer page,
                          @RequestParam(name = "size", defaultValue = "5") Integer size){

        User user = (User)request.getSession().getAttribute("user");
        if(user == null){
            return "redirect:/";
        }
        if("questions".equals(action)){
            model.addAttribute("section","questions");
            model.addAttribute("sectionName","我的提问");
            PaginationDTO paginationDTO = questionService.listByUserId(user.getId(), page, size);
            model.addAttribute("paginations", paginationDTO);
        }else if("replies".equals(action)){
            model.addAttribute("section","replies");
            model.addAttribute("sectionName","最新回复");
            PaginationDTO paginationDTO = notificationService.list(user.getId(), page, size);
            Long unreadCount =notificationService.unreadCount(user.getId());
            model.addAttribute("unreadCount", unreadCount);
            model.addAttribute("paginations", paginationDTO);
        }
        return "profile";
    }
}
