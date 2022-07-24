package cn.conststar.wall.service.table.impl;

import cn.conststar.wall.config.GlobalConfig;
import cn.conststar.wall.constant.StateCodeConstant;
import cn.conststar.wall.exception.BusinessException;
import cn.conststar.wall.dao.TableDao;
import cn.conststar.wall.pojo.dto.TableDto;
import cn.conststar.wall.constant.ResponseEnumConstant;
import cn.conststar.wall.pojo.model.TableDomain;
import cn.conststar.wall.pojo.model.UserDomain;
import cn.conststar.wall.service.table.TableService;
import cn.conststar.wall.service.user.impl.UserServiceImpl;
import cn.conststar.wall.utils.Tools;
import cn.conststar.wall.utils.TextUtils;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Arrays;
import java.util.HashSet;
import java.util.List;

@Service
public class TableServiceImpl implements TableService {

    @Autowired
    private TableDao tableDao;

    @Autowired
    private UserServiceImpl userService;

    public List<TableDto> getTables(int pageIndex, int pageSize, String rankName, boolean rankType) throws Exception {
        if (pageIndex < 1 || pageSize < 0)
            throw new BusinessException("页数有误");

        int userId = -1;
        try {
            userId = userService.getUserBySubject().getId();
        } catch (Exception ex) {
            //允许未登录
        }

        int startIndex = (pageIndex - 1) * pageSize;
        return tableDao.getTables(startIndex, pageSize, userId, rankName, rankType);
    }

    //获取帖子的数量
    public int getCount() throws Exception {
        int userId = -1;
        try {
            userId = userService.getUserBySubject().getId();
        } catch (Exception ex) {
            //允许未登录
        }

        return tableDao.getCount(userId);
    }

    public List<TableDto> getSearchTables(String keyword, int pageIndex, int pageSize, String rankName, boolean rankType) throws Exception {
        if (keyword.isEmpty())
            throw new BusinessException("搜索内容为空");

        if (pageIndex < 1 || pageSize < 0)
            throw new BusinessException("页数有误");

        int userId = -1;
        try {
            userId = userService.getUserBySubject().getId();
        } catch (Exception ex) {
            //允许未登录
        }

        int startIndex = (pageIndex - 1) * pageSize;
        return tableDao.getSearchTables(keyword, startIndex, pageSize, userId, rankName, rankType);
    }

    //获取搜索帖子的数量
    public int getSearchCount(String keyword) throws Exception {
        int userId = -1;
        try {
            userId = userService.getUserBySubject().getId();
        } catch (Exception ex) {
            //允许未登录
        }

        return tableDao.getSearchCount(keyword, userId);
    }

    @Override
    public List<TableDto> getMyTables(int pageIndex, int pageSize, String rankName, boolean rankType) throws Exception {
        if (pageIndex < 1 || pageSize < 0)
            throw new BusinessException("页数有误");

        UserDomain user = userService.getUserBySubject();
        int startIndex = (pageIndex - 1) * pageSize;
        return tableDao.getMyTables(startIndex, pageSize, user.getId(), rankName, rankType);
    }

    @Override
    public int getMyCount() throws Exception {
        UserDomain user = userService.getUserBySubject();
        return tableDao.getMyCount(user.getId());
    }

    @Override
    public List<TableDto> getMySearchTables(String keyword, int pageIndex, int pageSize, String rankName, boolean rankType) throws Exception {
        if (keyword.isEmpty())
            throw new BusinessException("搜索内容为空");

        if (pageIndex < 1 || pageSize < 0)
            throw new BusinessException("页数有误");


        UserDomain user = userService.getUserBySubject();
        int startIndex = (pageIndex - 1) * pageSize;
        return tableDao.getMySearchTables(keyword, startIndex, pageSize, user.getId(), rankName, rankType);
    }

    @Override
    public int getMySearchCount(String keyword) throws Exception {
        UserDomain user = userService.getUserBySubject();

        return tableDao.getMySearchCount(keyword, user.getId());
    }

    public TableDto getTable(int id) throws Exception {

        int userId = -1;
        try {
            userId = userService.getUserBySubject().getId();
        } catch (Exception ex) {
            //允许未登录
        }

        TableDto table = tableDao.getTable(id, userId);
        if (table == null)
            throw new BusinessException("帖子不存在");

        return table;
    }

    public void addTable(TableDomain table) throws Exception {

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


        //获取用户
        UserDomain user = userService.getUserBySubject();
        table.setUserId(user.getId());

        //是否不需要审核
        int state = StateCodeConstant.TABLE_NONE;
        if ((imageList != null && !imageList.isEmpty())
                || TextUtils.checkText(table.getSender()) || TextUtils.checkText(table.getRecipient())
                || TextUtils.checkText(table.getContent())) {
            state = StateCodeConstant.TABLE_WAIT;
        }
        table.setState(state);


        //发布
        int line = tableDao.addTable(table);
        if (line != 1) {
            throw new BusinessException("数据库操作失败，数据库添加行数为" + line, ResponseEnumConstant.CODE_50002);
        }

        //发布成功后移动图片
        if (imageList != null)
            Tools.addDataImages(new HashSet<>(imageList));
    }

    public void addSupport(int tableId) throws Exception {
        //获取用户
        UserDomain user = userService.getUserBySubject();

        if (isSupport(tableId, user.getId()))
            throw new BusinessException("已经点过赞了");

        tableDao.addSupport(tableId, user.getId());
    }

    public TableDomain getTableDomain(int id) throws Exception {
        return tableDao.getTableDomain(id);
    }

    //取消点赞
    public void deleteSupport(int tableId) throws Exception {
        //获取用户
        UserDomain user = userService.getUserBySubject();

        if (!isSupport(tableId, user.getId()))
            throw new BusinessException("没有赞过这个帖子");

        tableDao.removeSupport(tableId, user.getId());
    }

    @Override
    public void deleteTable(int tableId) throws Exception {
        UserDomain user = userService.getUserBySubject();

        tableDao.deleteTable(tableId, user.getId());
    }

    //判断是否点过赞
    private boolean isSupport(int tableId, int userId) throws Exception {
        return tableDao.isSupport(tableId, userId);
    }
}
