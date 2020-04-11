package shequ.wqy.community.dto;

import lombok.Data;

/**
 * Author: wanqiangying
 * Date: 2020/4/11 16:56
 * Content:
 */
@Data
public class HotTagDTO implements Comparable {
    private String name;
    private Integer priority;

    @Override
    public int compareTo(Object o) {
        return this.getPriority() - ((HotTagDTO) o).getPriority();
    }
}
