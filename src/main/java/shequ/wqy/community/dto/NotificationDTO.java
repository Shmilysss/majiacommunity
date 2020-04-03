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
    private Long notifier;
    private String notifierName;
    private Long outerid;
    private String outerTitle;
    private Integer type;
    private String typeName;
}
