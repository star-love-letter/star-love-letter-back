package cn.conststar.wall.service;

import cn.conststar.wall.exception.ExceptionMain;
import cn.conststar.wall.mapper.MapperComment;
import cn.conststar.wall.pojo.PojoComment;
import cn.conststar.wall.pojo.PojoUserPublic;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ServiceComment implements MapperComment {

    private MapperComment mapperComment;

    public List<PojoComment> getCommentsPage(int tableId, int pageIndex, int pageSize) throws Exception {

        int startIndex = (pageIndex - 1) * pageSize;
        return this.getCommentsLimit(tableId, startIndex, pageSize);
    }

    @Override
    public List<PojoComment> getCommentsLimit(int tableId, int startIndex, int pageSize) throws Exception {
        if (startIndex < 0 || pageSize < 0)
            throw new ExceptionMain("页数有误");

        return mapperComment.getCommentsLimit(tableId, startIndex, pageSize);
    }

//    @Override
//    public PojoUserPublic getUser(int commenId) throws Exception {
//        return mapperComment.getUser(commenId);
//    }


    @Override
    public int addComment(int tableId, int userId, String name, boolean anonymous, String content, String images) throws Exception {
        if (content.isEmpty())
            throw new ExceptionMain("内容不能为空");

        if (content.length() > 160)
            throw new ExceptionMain("内容不得超过160个字符");

        int line = mapperComment.addComment(tableId, userId, name, anonymous, content, images);
        if (line != 1) {
            throw new ExceptionMain("数据库操作失败，数据库添加行数为" + line, ExceptionMain.DEADLY); //wait
        }
        return line;
    }

    @Override
    public int getCount(int tableId) throws Exception {
        return mapperComment.getCount(tableId);
    }
}
