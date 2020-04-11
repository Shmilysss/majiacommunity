package shequ.wqy.community.schedule;

import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.apache.ibatis.session.RowBounds;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import shequ.wqy.community.cache.HotTagCache;
import shequ.wqy.community.mapper.QuestionMapper;
import shequ.wqy.community.model.Question;
import shequ.wqy.community.model.QuestionExample;

import java.util.*;

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

    @Autowired
    private HotTagCache hotTagCache;

    @Scheduled(fixedRate = 60000)
//    @Scheduled(cron = "0 0 1 * * *") //每天凌晨1点
    public void hotTagSchedule() {
        //定时器任务，新的知识点
        log.info("task begin {}", new Date());
        int offset = 0;
        int limit = 20;
        List<Question> list = new ArrayList<>();
        Map<String, Integer> tmpMap = new HashMap<>();
        while (offset == 0 || list.size() == limit) {
            list = questionMapper.selectByExampleWithRowbounds(new QuestionExample(), new RowBounds(offset, limit));
            for (Question question : list) {
                String tags = question.getTag();
                String[] tagArr = StringUtils.split(tags, ",");
                for (String tag : tagArr) {
                    Integer cha = tmpMap.get(tag);
                    if (cha != null) {
                        tmpMap.put(tag, cha + 5 + question.getCommentCount());
                    } else {
                        tmpMap.put(tag, 5 + question.getCommentCount());
                    }
                }

            }
            offset += limit;
        }
        hotTagCache.updateTags(tmpMap);
        log.info("task end {}", new Date());
    }
}
