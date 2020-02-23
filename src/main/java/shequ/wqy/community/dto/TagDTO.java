package shequ.wqy.community.dto;

import lombok.Data;

import java.util.List;

/**
 * Author: wanqiangying
 * Date: 2020/2/23 17:41
 * Content:
 */
@Data
public class TagDTO {
    private String categoryName;
    private List<String> tags;
}
