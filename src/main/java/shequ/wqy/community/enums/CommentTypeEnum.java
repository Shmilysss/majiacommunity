package shequ.wqy.community.enums;

/**
 * Author: wanqiangying
 * Date: 2020/2/12 19:40
 * Content:
 */
public enum CommentTypeEnum {
    QUESTION(1),
    COMMENT(2);
    private Integer type;

    public static boolean isExist(Integer type) {
        for (CommentTypeEnum commentTypeEnum : CommentTypeEnum.values()) {
            if(commentTypeEnum.getType() == type){
                return true;
            }
        }
        return false;
    }

    public Integer getType() {
        return type;
    }
    CommentTypeEnum(Integer type) {
        this.type = type;
    }
}
