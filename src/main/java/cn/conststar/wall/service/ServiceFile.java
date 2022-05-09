package cn.conststar.wall.service;

import cn.conststar.wall.utils.UtilsImage;
import cn.conststar.wall.utils.UtilsMain;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;

public class ServiceFile {

    public String uploadImage(MultipartFile file) throws Exception {
        File imageDir = UtilsMain.getDataTempDirectory();
        String fileName = UtilsMain.getUUID() + ".jpg";
        File filePath = new File(imageDir, fileName);
        UtilsImage.conversionOut(file, filePath);

        return fileName;
    }

}
