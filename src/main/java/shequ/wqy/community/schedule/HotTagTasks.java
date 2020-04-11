package shequ.wqy.community.schedule;

import lombok.extern.slf4j.Slf4j;
import org.apache.ibatis.session.RowBounds;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import shequ.wqy.community.mapper.QuestionMapper;
import shequ.wqy.community.model.Question;
import shequ.wqy.community.model.QuestionExample;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * Author: wanqiangying
 * Date: 2020/4/11 16:06
 * Content:
 */
@Component
@Slf4j
public class HotTagTasks {

    @Autowired
    QuestionMapper questionMapper;

    @Scheduled(fixedRate = 10000)
//    @Scheduled(cron = "0 0 1 * * *") //每天凌晨1点
    public void hotTagSchedule() {
        //定时器任务，新的知识点
        log.info("task begin {}", new Date());
        int offset = 0;
        int limit = 20;
        List<Question> list = new ArrayList<>();
        while (offset == 0 || list.size() == limit) {
            list = questionMapper.selectByExampleWithRowbounds(new QuestionExample(), new RowBounds(offset, limit));
            for (Question question : list) {
                log.info("question ++ {} ++ {}",question.getId(),question.getTitle());
            }
            offset += limit;
        }
        log.info("task end {}", new Date());
    }
}
