package cn.conststar.wall.service.adminClear.impl;

import cn.conststar.wall.constant.ResponseEnumConstant;
import cn.conststar.wall.dao.AdminClearDao;
import cn.conststar.wall.exception.BusinessException;
import cn.conststar.wall.pojo.model.CommentDomain;
import cn.conststar.wall.pojo.model.TableDomain;
import cn.conststar.wall.service.adminClear.AdminClearService;
import cn.conststar.wall.service.adminComment.AdminCommentService;
import cn.conststar.wall.service.adminTable.AdminTableService;
import cn.conststar.wall.utils.Tools;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.File;
import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Service
public class AdminClearServiceImpl implements AdminClearService {

    @Autowired
    private AdminClearDao adminClearDao;

    @Autowired
    private AdminTableService adminTableService;

    @Autowired
    private AdminCommentService adminCommentService;

    @Override
    public void clearReport(long time) {
        long now = System.currentTimeMillis() / 1000L;
        if (now - time > 1000L * 60L * 60L * 24L * 30L) {
            throw new BusinessException("清理时间不能小于30天", ResponseEnumConstant.CODE_201);
        }

        adminClearDao.clearReportTable(time);
        adminClearDao.clearReportComment(time);
    }

    @Override
    public synchronized int clearImage() throws Exception {
        File imageDir = Tools.getDataImageDirectory();
        String[] imageList = imageDir.list((dir, name) -> {
            File file = new File(dir, name);
            return file.isFile();
        });
        Set<String> images = new HashSet<>(Arrays.asList(imageList));

        ObjectMapper mapper = new ObjectMapper();

        // 过滤头像图片
        images.removeAll(Tools.avatars);

        // 过滤帖子中使用的图片
        int tableCount = adminTableService.getTableCount();
        for (int i = 1; i <= Math.ceil(tableCount / 100.0); i++) {
            List<TableDomain> tables = adminTableService.getTables(i, 100, "id", false);
            for (TableDomain table : tables) {
                String imageStr = table.getImages();
                if (imageStr == null || imageStr.isEmpty())
                    continue;

                List<String> temps = Arrays.asList(mapper.readValue(imageStr, String[].class));
                images.removeAll(temps);
            }
        }

        // 过滤评论中使用的图片
        int commentCount = adminCommentService.getCommentCount();
        for (int i = 1; i <= Math.ceil(commentCount / 100.0); i++) {
            List<CommentDomain> comments = adminCommentService.getComments(i, 100, "id", false);
            for (CommentDomain comment : comments) {
                String imageStr = comment.getImages();
                if (imageStr == null || imageStr.isEmpty())
                    continue;

                List<String> temps = Arrays.asList(mapper.readValue(imageStr, String[].class));
                images.removeAll(temps);
            }
        }

        // 删除未使用的图片
        for (String image : images) {
            File file = new File(imageDir, image);
            file.delete();
        }

        return images.size();
    }
}
