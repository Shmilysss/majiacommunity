package shequ.wqy.community.advice;

import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.servlet.ModelAndView;
import shequ.wqy.community.dto.ResultDTO;
import shequ.wqy.community.exception.CustomizeException;

import javax.servlet.http.HttpServletRequest;

/**
 * Author: wanqiangying
 * Date: 2020/2/12 20:22
 * Content:
 */
@ControllerAdvice
public class CustomizeExceptionHandler {
    @ExceptionHandler(Exception.class)
    Object handle(HttpServletRequest request, Throwable e, Model model) {

        String contentType = request.getContentType();
        if ("application/json".equals(contentType)) {
            //返回JSON
            if (e instanceof CustomizeException) {
                return ResultDTO.errorOf((CustomizeException) e);
            } else {
                model.addAttribute("message", "服务连接失败，请稍后再试！");
            }
            return null;
        } else {
            //错误页面跳转
            if (e instanceof CustomizeException) {
                model.addAttribute("message", e);
            } else {
                model.addAttribute("message", "服务连接失败，请稍后再试！");
            }
            return new ModelAndView("error");
        }


    }
}
