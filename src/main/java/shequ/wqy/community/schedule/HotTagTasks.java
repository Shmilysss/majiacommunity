package shequ.wqy.community.schedule;

import lombok.extern.slf4j.Slf4j;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.util.Date;

/**
 * Author: wanqiangying
 * Date: 2020/4/11 16:06
 * Content:
 */
@Component
@Slf4j
public class HotTagTasks {
    @Scheduled(fixedRate = 60000)//1min
//    @Scheduled(cron = "0 0 1 * * *") //每天凌晨1点
    public void reportCurrentTime() {
        //定时器任务，新的知识点
        log.info("The time is now {}", new Date());
    }
}
