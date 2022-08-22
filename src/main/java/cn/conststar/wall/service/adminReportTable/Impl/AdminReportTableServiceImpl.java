package cn.conststar.wall.service.adminReportTable.Impl;

import cn.conststar.wall.constant.StateCodeConstant;
import cn.conststar.wall.dao.AdminReportTableDao;
import cn.conststar.wall.exception.BusinessException;
import cn.conststar.wall.pojo.model.ReportTableDomain;
import cn.conststar.wall.pojo.model.TableDomain;
import cn.conststar.wall.service.adminReportTable.AdminReportTableService;
import cn.conststar.wall.service.adminTable.AdminTableService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class AdminReportTableServiceImpl implements AdminReportTableService {

    @Autowired
    private AdminReportTableDao adminReportTableDao;

    @Autowired
    private AdminTableService tableService;

    @Override
    public List<TableDomain> getReportTables(int pageIndex, int pageSize, int state) throws Exception {
        if (pageIndex < 1 || pageSize < 0)
            throw new BusinessException("页数有误");

        int startIndex = (pageIndex - 1) * pageSize;
        return adminReportTableDao.getReportTables(startIndex, pageSize, state);
    }

    @Override
    public int getReportTableCount(int state) throws Exception {
        return adminReportTableDao.getReportTableCount(state);
    }

    @Override
    public List<TableDomain> getSearchReportTables(String keyword, int pageIndex, int pageSize, int state) throws Exception {
        if (pageIndex < 1 || pageSize < 0)
            throw new BusinessException("页数有误");

        int startIndex = (pageIndex - 1) * pageSize;
        return adminReportTableDao.getSearchReportTables(keyword, startIndex, pageSize, state);
    }

    @Override
    public int getSearchReportTableCount(String keyword, int state) throws Exception {
        return adminReportTableDao.getSearchReportTableCount(keyword, state);
    }

    @Override
    public List<ReportTableDomain> getReportByTable(int tableId) throws Exception {
        return adminReportTableDao.getReportByTable(tableId);
    }

    @Override
    public List<ReportTableDomain> getReportByReportUser(int reportUserId) throws Exception {
        return adminReportTableDao.getReportByReportUser(reportUserId);
    }

    @Override
    public void handleReport(int tableId, boolean agree) throws Exception {
        // 同意举报
        if (agree) {
            // 封禁帖子
            TableDomain table = tableService.getTableById(tableId);
            table.setState(StateCodeConstant.TABLE_LOCK);
            tableService.updateTable(table);

            // 处理举报
            adminReportTableDao.updateReportState(tableId, StateCodeConstant.REPORT_SUCCESS);
        }
        // 拒绝举报
        else {
            adminReportTableDao.updateReportState(tableId,  StateCodeConstant.REPORT_FAILED);
        }
    }
}
