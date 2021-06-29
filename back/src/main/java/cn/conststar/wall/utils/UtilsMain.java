package cn.conststar.wall.utils;

import cn.conststar.wall.exception.ExceptionMain;
import org.springframework.web.multipart.MultipartFile;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.Arrays;
import java.util.List;
import java.util.UUID;

public class UtilsMain {
    public static String getUUID() {
        return UUID.randomUUID().toString().replaceAll("-", "");
    }


    public static File getDataDirectory() {
        String path = getDataPath();
        File file = new File(path);
        if (!file.isDirectory()) {
            file.mkdirs();
        }

        return file;
    }

    public static File getTempDirectory() {
        String path = getTempPath();

        File file = new File(path);
        if (!file.isDirectory()) {
            file.mkdirs();
        }

        return file;
    }

    public static File getImageDirectory() {
        String path = getImagePath();

        File file = new File(path);
        if (!file.isDirectory()) {
            file.mkdirs();
        }

        return file;
    }


    public static String getDataPath() {
        return System.getProperty("catalina.base") + "/data";
    }

    public static String getTempPath() {
        return getDataPath() + "/temp";
    }

    public static String getImagePath() {
        return getDataPath() + "/image";
    }

    public static void ConversionOut(MultipartFile file, File outFile) throws IOException, ExceptionMain {
        String fileName = file.getOriginalFilename();
        String fileType = "";

        int typeIndex = fileName.lastIndexOf(".");
        if (typeIndex != -1 && typeIndex != fileName.length() - 1)
            fileType = fileName.substring(typeIndex + 1);

        if (!Arrays.asList(ImageIO.getReaderFormatNames()).contains(fileType))
            throw new ExceptionMain("不支持的文件格式");

        ConversionOut(file.getInputStream(), outFile);
    }

    //图片格式转换
    public static void ConversionOut(InputStream input, File outFile) throws IOException {
        BufferedImage im = ImageIO.read(input);
        ImageIO.write(im, "jpg", outFile);
    }

    //添加图片文件
    public static void addImages(List<String> files) throws Exception {
        if (files == null || files.isEmpty())
            return;

        File tempDir = UtilsMain.getTempDirectory();
        File imageDir = UtilsMain.getImageDirectory();
        long nowTime = System.currentTimeMillis();

        for (String file : files) {
            File pathFileOld = new File(tempDir, file);

            //文件不存在 或者 文件已经超过一天了
            if (!pathFileOld.isFile() || nowTime - pathFileOld.lastModified() > 1000 * 60 * 60 * 24)
                throw new ExceptionMain("没有找到此文件:" + pathFileOld);
        }

        for (String file : files) {
            File pathFileOld = new File(tempDir, file);
            File pathFileNew = new File(imageDir, file);
            pathFileOld.renameTo(pathFileNew);
        }
    }

}
