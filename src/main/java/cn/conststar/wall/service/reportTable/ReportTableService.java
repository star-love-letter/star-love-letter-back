package cn.conststar.wall.service.reportTable;

import cn.conststar.wall.pojo.model.ReportTableDomain;

public interface ReportTableService {
    /**
     * 举报帖子
     * @param reportTable 举报帖子信息
     */
    void report(ReportTableDomain reportTable) throws Exception;
}
