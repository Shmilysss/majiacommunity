package shequ.wqy.community.dto;

import lombok.Data;

/**
 * Author: wanqiangying
 * Date: 2020/4/2 20:28
 * Content:
 */
@Data
public class NotificationDTO {
    private Long id;
    private Long gmtCreate;
    private Integer status;
    private String notifier;
    private String outerTitle;
    private String type;
}
