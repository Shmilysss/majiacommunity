package shequ.wqy.community.exception;

/**
 * Author: wanqiangying
 * Date: 2020/2/12 20:55
 * Content:
 */
public enum CustomizeErrorCode implements ICustomizeErrorCode {
    QUESTION_NOT_FOUND("当前问题不存在");
    private String message;

    @Override
    public String getMessage() {
        return message;
    }

    CustomizeErrorCode(String message) {
        this.message = message;
    }
}
