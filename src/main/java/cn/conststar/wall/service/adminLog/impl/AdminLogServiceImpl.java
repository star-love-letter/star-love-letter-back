package cn.conststar.wall.service.adminLog.impl;

import cn.conststar.wall.config.GlobalConfig;
import cn.conststar.wall.dao.AdminLogDao;
import cn.conststar.wall.exception.BusinessException;
import cn.conststar.wall.pojo.model.LogDomain;
import cn.conststar.wall.service.adminLog.AdminLogService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class AdminLogServiceImpl implements AdminLogService {

    @Autowired
    private AdminLogDao adminLogDao;

    @Override
    public List<LogDomain> all() throws Exception {
        return adminLogDao.all();
    }

    public void addLog(String action, String data, int userId) {
        LogDomain log = new LogDomain();
        log.setAction(action);
        log.setData(data);
        log.setUserId(userId);

        this.addLog(log);
    }

    public void addLog(LogDomain log) {
        adminLogDao.addLog(log);
    }

    public List<LogDomain> getLogs(int pageIndex, int pageSize, String rankName, boolean rankType) throws Exception {
        if (pageIndex < 1 || pageSize < 0)
            throw new BusinessException("页数有误");

        int startIndex = (pageIndex - 1) * pageSize;
        return adminLogDao.getLogs(startIndex, pageSize, rankName, rankType);
    }

    public int getLogCount() throws Exception {
        return adminLogDao.getLogCount();
    }

    public List<LogDomain> getSearchLogs(String keyword, int pageIndex, int pageSize, String rankName, boolean rankType) throws Exception {
        if (pageIndex < 1 || pageSize < 0)
            throw new BusinessException("页数有误");

        int startIndex = (pageIndex - 1) * pageSize;
        return adminLogDao.getSearchLogs(keyword, startIndex, pageSize, rankName, rankType);
    }

    public int getSearchLogCount(String keyword) throws Exception {
        return adminLogDao.getSearchLogCount(keyword);
    }

    // 清空过期的日志
    public void clearLogExpire() throws Exception {
        adminLogDao.clearLog(GlobalConfig.logMaxNumber);
    }

}
