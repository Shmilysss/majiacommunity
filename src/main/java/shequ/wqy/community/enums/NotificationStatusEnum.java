package shequ.wqy.community.enums;

/**
 * Author: wanqiangying
 * Date: 2020/2/25 20:34
 * Content:
 */
public enum NotificationStatusEnum {
    UNREAD(0), READ(1);
    private int status;

    public int getStatus() {
        return status;
    }

    NotificationStatusEnum(int status) {
        this.status = status;
    }
}