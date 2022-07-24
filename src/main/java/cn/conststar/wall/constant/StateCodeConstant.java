package cn.conststar.wall.constant;

public class StateCodeConstant {

    /* 账号 */
    public static final int USER_NONE = 0;        //用户正常
    public static final int USER_WAIT = 1;        //账号待审核
    public static final int USER_LOCK = -1;       //账号被锁定

    /* 帖子 */
    public static final int TABLE_NONE = 0;       //帖子正常
    public static final int TABLE_WAIT = 1;       //帖子带审核
    public static final int TABLE_LOCK = -1;      //帖子被锁定

    /* 评论 */
    public static final int COMMENT_NONE = 0;     //评论正常
    public static final int COMMENT_WAIT = 1;     //评论带审核
    public static final int COMMENT_LOCK = -1;    //评论被锁定

    /* 举报 */
    public static final int REPORT_WAIT = 0;      //举报未处理
    public static final int REPORT_SUCCESS = 1;   //举报成功
    public static final int REPORT_FAILED = -1;   //举报失败
}
