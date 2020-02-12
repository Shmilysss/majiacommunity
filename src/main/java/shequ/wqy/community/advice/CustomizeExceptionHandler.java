package shequ.wqy.community.advice;

import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.servlet.ModelAndView;
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
    ModelAndView handle(HttpServletRequest request, Throwable e, Model model) {
        if(e instanceof CustomizeException){
            model.addAttribute("message",e);
        }else{
            model.addAttribute("message","服务连接失败，请稍后再试！");
        }
        return new ModelAndView("error");
    }
}
