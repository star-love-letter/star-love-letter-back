package cn.conststar.wall.service;

import cn.conststar.wall.utils.UtilsMain;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;

public class ServiceFile {

    public String uploadImage(MultipartFile file) throws Exception {
        File imageDir = UtilsMain.getTempDirectory();
        String fileName = UtilsMain.getUUID() + ".jpg";
        File filePath = new File(imageDir, fileName);
        UtilsMain.ConversionOut(file, filePath);

        return fileName;
    }

}
