package shequ.wqy.community.exception;

/**
 * Author: wanqiangying
 * Date: 2020/2/12 20:55
 * Content:
 */
public enum CustomizeErrorCode implements ICustomizeErrorCode {
    QUESTION_NOT_FOUND(2001,"当前问题不存在"),
    TARGET_PARAM_NOT_FOUND(2002,"未选中任何问题或评论"),
    NO_LOGIN(2003,"未登录，不能进行评论，请先登录"),
    ;

    @Override
    public String getMessage() {
        return message;
    }

    @Override
    public Integer getCode() {
        return code;
    }

    private Integer code;
    private String message;

    CustomizeErrorCode(Integer code, String message) {
        this.code = code;
        this.message = message;
    }
}
