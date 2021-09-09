package cn.conststar.wall.service;

import cn.conststar.wall.exception.ExceptionMain;
import cn.conststar.wall.mapper.MapperTable;
import cn.conststar.wall.pojo.PojoTable;
import cn.conststar.wall.pojo.PojoUserPublic;
import cn.conststar.wall.utils.UtilsMain;
import com.alibaba.fastjson.JSONArray;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ServiceTable implements MapperTable {

    private MapperTable mapperTable;

    public List<PojoTable> getTablesPage(int pageIndex, int pageSize, int userId) throws Exception {

        int startIndex = (pageIndex - 1) * pageSize;
        return this.getTablesLimit(startIndex, pageSize, userId);
    }

    @Override
    public List<PojoTable> getTablesLimit(int startIndex, int pageSize, int userId) throws Exception {
        if (startIndex < 0 || pageSize < 0)
            throw new ExceptionMain("页数有误");

        return mapperTable.getTablesLimit(startIndex, pageSize, userId);
    }

    public List<PojoTable> getSearchTablesPage(String keyword, int pageIndex, int pageSize, int userId) throws Exception {

        int startIndex = (pageIndex - 1) * pageSize;

        return this.getSearchTablesLimit(keyword, startIndex, pageSize, userId);
    }

    @Override
    public List<PojoTable> getSearchTablesLimit(String keyword, int startIndex, int pageSize, int userId) throws Exception {
        if (keyword.isEmpty())
            throw new ExceptionMain("搜索内容为空");

        if (startIndex < 0 || pageSize < 0)
            throw new ExceptionMain("页数有误");
        return mapperTable.getSearchTablesLimit(keyword, startIndex, pageSize, userId);
    }


    @Override
    public PojoTable getTable(int id, int userId) throws Exception {
        return mapperTable.getTable(id, userId);
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

        List<String> imageList = JSONArray.parseArray(images).toJavaList(String.class);
        if (imageList.isEmpty())
            images = null;
        else if (imageList.size() > 6)
            throw new ExceptionMain("图片最多上传6个");


        int line = mapperTable.addTable(userId, anonymous, sender, senderSex, recipient, recipientSex, content, images);
        if (line != 1) {
            throw new ExceptionMain("数据库操作失败，数据库添加行数为" + line, ExceptionMain.DEADLY); //wait
        }

        //发布成功后移动图片
        UtilsMain.addImages(imageList);
        return line;
    }


    @Override
    public void addSupport(int tableId, int userId) throws Exception {
        if (isSupport(tableId, userId))
            throw new ExceptionMain("已经点过赞了");

        mapperTable.addSupport(tableId, userId);
    }

    @Override
    public void removeSupport(int tableId, int userId) throws Exception {
        if (!isSupport(tableId, userId))
            throw new ExceptionMain("没有赞过这个帖子");

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

    @Override
    public PojoUserPublic getUser(int tableId) throws Exception {
        return mapperTable.getUser(tableId);
    }

    @Override
    public boolean isSupport(int tableId, int userId) throws Exception {
        return mapperTable.isSupport(tableId, userId);
    }
}
