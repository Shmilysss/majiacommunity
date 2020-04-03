package shequ.wqy.community.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;
import shequ.wqy.community.dto.NotificationDTO;
import shequ.wqy.community.dto.PaginationDTO;
import shequ.wqy.community.enums.NotificationTypeEnum;
import shequ.wqy.community.model.Notification;
import shequ.wqy.community.model.User;
import shequ.wqy.community.service.NotificationService;

import javax.servlet.http.HttpServletRequest;

/**
 * Author: wanqiangying
 * Date: 2020/4/3 20:07
 * Content:
 */
@Controller
public class NotificationController {

    @Autowired
    private NotificationService notificationService;

    @GetMapping("/notification/{id}")
    public String profile(@PathVariable(name = "id") Long id,
                          HttpServletRequest request) {

        User user = (User) request.getSession().getAttribute("user");
        if (user == null) {
            return "redirect:/";
        }

        NotificationDTO notificationDTO = notificationService.read(id, user);
        if(NotificationTypeEnum.REPLY_COMMENT.getType() == notificationDTO.getType()
        || NotificationTypeEnum.REPLY_QUESTION.getType() == notificationDTO.getType()){
            return "redirect:/question/"+notificationDTO.getOuterid();
        }
        return "profile";
    }
}
