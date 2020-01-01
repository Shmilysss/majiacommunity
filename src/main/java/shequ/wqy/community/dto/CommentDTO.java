package shequ.wqy.community.dto;

import lombok.Data;

/**
 * Author: wanqiangying
 * Date: 2020/1/1 22:31
 * Content:
 */
@Data
public class CommentDTO {
    private Long parentId;
    private String content;
    private Integer type;
}
