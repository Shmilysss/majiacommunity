package shequ.wqy.community.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import shequ.wqy.community.mapper.UserMapper;
import shequ.wqy.community.model.User;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;

@Controller
public class IndexController {

    @Autowired
    private UserMapper userMapper;

    @GetMapping("/")
    public String Index(HttpServletRequest request){

        Cookie[] cookies = request.getCookies();
        for (Cookie cookie : cookies) {
            if("token".equals(cookie.getName())){
                String token = cookie.getValue();
                User user = userMapper.findByToken(token);
                if(user != null){
                    //表中存在当前token，返回登录界面，将user写进session和cookie
                    request.getSession().setAttribute("user",user);
                }
                break;
            }
        }

        return "index";
    }

}
