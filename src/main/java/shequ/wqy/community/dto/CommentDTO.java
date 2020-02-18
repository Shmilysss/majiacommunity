package shequ.wqy.community.dto;

import lombok.Data;
import shequ.wqy.community.model.User;

/**
 * Author: wanqiangying
 * Date: 2020/2/18 18:42
 * Content:
 */
@Data
public class CommentDTO {
    private Long id;
    private Long parentId;
    private Integer type;
    private Long commentator;
    private Long gmtCreate;
    private Long gmtModified;
    private Long likeCount;
    private String content;
    private User user;
}
