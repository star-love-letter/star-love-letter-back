package cn.conststar.wall.service.file.impl;

import cn.conststar.wall.config.GlobalConfig;
import cn.conststar.wall.service.file.FileService;
import cn.conststar.wall.service.user.UserService;
import cn.conststar.wall.utils.ImageUtils;
import cn.conststar.wall.utils.Tools;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.util.HashMap;
import java.util.Map;

@Service
public class FileServiceImpl implements FileService {

    @Autowired
    UserService userService;

    public static Map<Integer, Integer> userImageSize = new HashMap<Integer, Integer>();

    public String uploadImage(MultipartFile file) throws Exception {
        userService.getUserBySubject(); //检查用户是否登录
        userImageSize.putIfAbsent(userService.getUserBySubject().getId(), 0);

        Integer number = userImageSize.get(userService.getUserBySubject().getId());
        if (number > GlobalConfig.userImageMaxNumberDay) {
            throw new Exception("今日上传图片数量已达上限");
        }

        File imageDir = Tools.getDataTempDirectory();
        String fileName = Tools.getUUID() + ".jpg";
        File filePath = new File(imageDir, fileName);
        ImageUtils.conversionOut(file, filePath);

        userImageSize.put(userService.getUserBySubject().getId(), number + 1);
        return fileName;
    }

}
