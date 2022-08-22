package cn.conststar.wall.service.reportUser;

import cn.conststar.wall.pojo.model.ReportUserDomain;

public interface ReportUserService {
    /**
     * 举报用户
     * @param reportUser 举报用户信息
     */
    void report(ReportUserDomain reportUser) throws Exception;
}
