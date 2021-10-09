package cn.conststar.wall.service;

import cn.conststar.wall.exception.ExceptionMain;
import cn.conststar.wall.mapper.MapperComment;
import cn.conststar.wall.pojo.PojoComment;
import cn.conststar.wall.pojo.PojoUserPublic;
import cn.conststar.wall.response.ResponseCodeEnums;
import cn.conststar.wall.utils.UtilsMain;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Arrays;
import java.util.HashSet;
import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ServiceComment implements MapperComment {

    private MapperComment mapperComment;

    public List<PojoComment> getCommentsPage(int tableId, int pageIndex, int pageSize, int userId) throws Exception {

        int startIndex = (pageIndex - 1) * pageSize;
        return this.getCommentsLimit(tableId, startIndex, pageSize, userId);
    }

    @Override
    public List<PojoComment> getCommentsLimit(int tableId, int startIndex, int pageSize, int userId) throws Exception {
        if (startIndex < 0 || pageSize < 0)
            throw new ExceptionMain("页数有误");

        return mapperComment.getCommentsLimit(tableId, startIndex, pageSize, userId);
    }

    @Override
    public int addComment(int tableId, int userId, String content, String images, int status) throws Exception {
        if (content.isEmpty())
            throw new ExceptionMain("内容不能为空");

        if (content.length() > 160)
            throw new ExceptionMain("内容不得超过160个字符");

        ObjectMapper mapper = new ObjectMapper();
        List<String> imageList = null;
        if (!images.isEmpty())
            imageList = Arrays.asList(mapper.readValue(images, String[].class));

        if (imageList == null || imageList.isEmpty())
            images = null;
        else if (imageList.size() > 3)
            throw new ExceptionMain("图片最多上传3个");

        int line = mapperComment.addComment(tableId, userId, content, images, status);
        if (line != 1) {
            throw new ExceptionMain("数据库操作失败，数据库添加行数为" + line, ResponseCodeEnums.CODE_50002); //wait
        }

        //发布成功后移动图片
        UtilsMain.addDataImages(new HashSet<>(imageList));
        return line;
    }

    @Override
    public int getCount(int tableId, int userId) throws Exception {
        return mapperComment.getCount(tableId, userId);
    }

    @Override
    public PojoUserPublic getUser(int commentId) throws Exception {
        return mapperComment.getUser(commentId);
    }
}
