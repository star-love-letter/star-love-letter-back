package cn.conststar.wall.service;

import cn.conststar.wall.exception.ExceptionMain;
import cn.conststar.wall.mapper.MapperTable;
import cn.conststar.wall.pojo.PojoUserPublic;
import cn.conststar.wall.pojo.PojoTable;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ServiceTable implements MapperTable {

    private MapperTable mapperTable;

    public List<PojoTable> getTablesPage(int pageIndex, int pageSize) throws Exception {

        int startIndex = (pageIndex - 1) * pageSize;
        return this.getTablesLimit(startIndex, pageSize);
    }

    @Override
    public List<PojoTable> getTablesLimit(int startIndex, int pageSize) throws Exception {
        if (startIndex < 0 || pageSize < 0)
            throw new ExceptionMain("页数有误");

        return mapperTable.getTablesLimit(startIndex, pageSize);
    }

    public List<PojoTable> getSearchTablesPage(String keyword, int pageIndex, int pageSize) throws Exception {

        int startIndex = (pageIndex - 1) * pageSize;

        return this.getSearchTablesLimit(keyword, startIndex, pageSize);
    }

    @Override
    public List<PojoTable> getSearchTablesLimit(String keyword, int startIndex, int pageSize) throws Exception {
        if (keyword.isEmpty())
            throw new ExceptionMain("搜索内容为空");

        if (startIndex < 0 || pageSize < 0)
            throw new ExceptionMain("页数有误");
        return mapperTable.getSearchTablesLimit(keyword, startIndex, pageSize);
    }

    @Override
    public PojoUserPublic getUser(int tableId) throws Exception {
        return mapperTable.getUser(tableId);
    }

    @Override
    public PojoTable getTable(int id) throws Exception {
        return mapperTable.getTable(id);
    }

    @Override
    public int addTable(int userId, boolean anonymous, String sender, int senderSex, String recipient, int recipientSex, String content, String images) throws Exception {
        if (sender.isEmpty() || recipient.isEmpty())
            throw new ExceptionMain("名称不能为空");

        if (sender.length() > 6 || recipient.length() > 6)
            throw new ExceptionMain("名称不得超过6个字符");

        if (content.isEmpty())
            throw new ExceptionMain("内容不能为空");

        if (content.length() > 160)
            throw new ExceptionMain("内容不得超过160个字符");


        int line = mapperTable.addTable(userId, anonymous, sender, senderSex, recipient, recipientSex, content, images);
        if (line != 1) {
            throw new ExceptionMain("数据库操作失败，数据库添加行数为" + line, ExceptionMain.DEADLY); //wait
        }
        return line;
    }


    @Override
    public void addSupport(int tableId, int userId) throws Exception {
        mapperTable.addSupport(tableId, userId);
    }

    @Override
    public void removeSupport(int tableId, int userId) throws Exception {
        mapperTable.removeSupport(tableId, userId);
    }

    @Override
    public int getCount() throws Exception {
        return mapperTable.getCount();
    }

    @Override
    public int getSearchCount(String keyword) throws Exception {
        return mapperTable.getSearchCount(keyword);
    }
}
