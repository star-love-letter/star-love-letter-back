package cn.conststar.wall.service.reportComment;

import cn.conststar.wall.pojo.model.ReportCommentDomain;

public interface ReportCommentService {
    /**
     * 举报评论
     * @param reportComment 举报评论信息
     */
    void report(ReportCommentDomain reportComment) throws Exception;
}
