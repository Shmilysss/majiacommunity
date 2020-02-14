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
    SYS_ERROR(2004,"服务连接失败，请稍后再试！"),
    TYPE_WRONG(2004,"类型不正确"),
    COMMENT_NOT_FOUND(2005,"评论内容不存在"),
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
