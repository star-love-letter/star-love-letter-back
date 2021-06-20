package cn.conststar.wall.service;

import cn.conststar.wall.exception.ExceptionMain;
import cn.conststar.wall.mapper.MapperPost;
import cn.conststar.wall.pojo.PojoComment;
import cn.conststar.wall.pojo.PojoPost;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.mybatis.spring.support.SqlSessionDaoSupport;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ServicePost implements MapperPost {

    private MapperPost mapperPost;

    public List<PojoPost> getPostsPage(int pageIndex, int pageSize) throws Exception {

        int startIndex = (pageIndex - 1) * pageSize;
        return this.getPostsLimit(pageIndex, pageSize);
    }

    @Override
    public List<PojoPost> getPostsLimit(int startIndex, int pageSize) throws Exception {
        if (startIndex < 0 || pageSize < 0)
            throw new ExceptionMain("页数有误");

        return mapperPost.getPostsLimit(startIndex, pageSize);
    }

    public List<PojoPost> getSearchPostsPage(String keyword, int pageIndex, int pageSize) throws Exception {

        int startIndex = (pageIndex - 1) * pageSize;

        return this.getSearchPostsLimit(keyword, startIndex, pageSize);
    }

    @Override
    public List<PojoPost> getSearchPostsLimit(String keyword, int startIndex, int pageSize) throws Exception {
        if (keyword.isEmpty())
            throw new ExceptionMain("搜索内容为空");

        if (startIndex < 0 || pageSize < 0)
            throw new ExceptionMain("页数有误");
        return mapperPost.getSearchPostsLimit(keyword, startIndex, pageSize);
    }

    @Override
    public PojoPost getPost(int id) throws Exception {
        return mapperPost.getPost(id);
    }

    @Override
    public int addPost(String sender, int senderSex, String recipient, int recipientSex, String content) throws Exception {
        if (sender.isEmpty() || recipient.isEmpty())
            throw new ExceptionMain("名称不能为空");

        if (sender.length() > 6 || recipient.length() > 6)
            throw new ExceptionMain("名称不得超过6个字符");

        if (content.isEmpty())
            throw new ExceptionMain("内容不能为空");

        if (content.length() > 160)
            throw new ExceptionMain("内容不得超过160个字符");


        int line = mapperPost.addPost(sender, senderSex, recipient, recipientSex, content);
        if (line != 1) {
            throw new ExceptionMain("数据库操作失败，数据库添加行数为" + line, ExceptionMain.DEADLY); //wait
        }
        return line;
    }

    public void thumbsUp(int id, boolean flag) throws Exception {
        if (flag) {
            this.incrementThumbsUp(id);
        } else {
            this.incrementUnThumbsUp(id);
        }
    }

    @Override
    public void incrementThumbsUp(int id) throws Exception {
        mapperPost.incrementThumbsUp(id);
    }

    @Override
    public void incrementUnThumbsUp(int id) throws Exception {
        mapperPost.incrementUnThumbsUp(id);
    }

    @Override
    public int getCount() throws Exception {
        return mapperPost.getCount();
    }

    @Override
    public int getSearchCount(String keyword) throws Exception {
        return mapperPost.getSearchCount(keyword);
    }
}
