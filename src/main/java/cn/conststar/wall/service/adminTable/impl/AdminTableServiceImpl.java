package cn.conststar.wall.service.adminTable.impl;

import cn.conststar.wall.config.GlobalConfig;
import cn.conststar.wall.dao.AdminTableDao;
import cn.conststar.wall.exception.BusinessException;
import cn.conststar.wall.pojo.model.TableDomain;
import cn.conststar.wall.pojo.model.UserDomain;
import cn.conststar.wall.service.adminLog.AdminLogService;
import cn.conststar.wall.service.adminTable.AdminTableService;
import cn.conststar.wall.service.user.UserService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Arrays;
import java.util.List;

@Service
public class AdminTableServiceImpl implements AdminTableService {
    @Autowired
    private AdminTableDao tableDao;


    @Autowired
    private UserService userService;

    @Autowired
    private AdminLogService adminLogService;

    // 获取帖子列表
    public List<TableDomain> getTables(int pageIndex, int pageSize, String rankName, boolean rankType) throws Exception {
        if (pageIndex < 1 || pageSize < 0)
            throw new BusinessException("页数有误");

        int startIndex = (pageIndex - 1) * pageSize;
        return tableDao.getTables(startIndex, pageSize, rankName, rankType);
    }

    // 获取帖子数量
    public int getTableCount() throws Exception {
        return tableDao.getTableCount();
    }

    @Override
    public List<TableDomain> getSearchTables(String keyword, int pageIndex, int pageSize, String rankName, boolean rankType) throws Exception {
        if (pageIndex < 1 || pageSize < 0)
            throw new BusinessException("页数有误");

        int startIndex = (pageIndex - 1) * pageSize;
        return tableDao.getSearchTables(keyword, startIndex, pageSize, rankName, rankType);
    }

    @Override
    public int getSearchTableCount(String keyword) throws Exception {
        return tableDao.getSearchTableCount(keyword);
    }

    // 获取帖子
    public TableDomain getTableById(int tableId) throws Exception {
        return tableDao.getTableById(tableId);
    }

    // 更新帖子
    public void updateTable(TableDomain table) throws Exception {
        ObjectMapper mapper = new ObjectMapper();
        List<String> imageList = null;
        if (table.getImages() != null && !table.getImages().isEmpty())
            imageList = Arrays.asList(mapper.readValue(table.getImages(), String[].class));
        if (imageList == null || imageList.isEmpty())
            table.setImages(null);
        else if (imageList.size() > GlobalConfig.tableImageMaxNumber)
            throw new BusinessException("帖子最多上传" + GlobalConfig.tableImageMaxNumber + "张图片");
        else
            table.setImages(mapper.writeValueAsString(imageList));

        UserDomain admin = userService.getUserBySubject();
        adminLogService.addLog("更新帖子", table.toString(), admin.getId());

        tableDao.updateTable(table);
    }

    // 删除帖子
    public void deleteTable(int tableId) throws Exception {

        TableDomain table = tableDao.getTableById(tableId);
        UserDomain admin = userService.getUserBySubject();
        adminLogService.addLog("删除帖子", table.toString(), admin.getId());

        tableDao.deleteTable(tableId);
    }
}
