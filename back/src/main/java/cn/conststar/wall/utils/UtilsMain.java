package cn.conststar.wall.utils;

import cn.conststar.wall.exception.ExceptionMain;
import org.springframework.web.multipart.MultipartFile;

import javax.imageio.IIOImage;
import javax.imageio.ImageIO;
import javax.imageio.ImageWriter;
import javax.imageio.plugins.jpeg.JPEGImageWriteParam;
import javax.imageio.stream.FileImageOutputStream;
import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.Arrays;
import java.util.Iterator;
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
//        BufferedImage im = ImageIO.read(input);

        // 清空原有图片的透明色
        BufferedImage im = ImageIO.read(input);
        Image image = (Image) im;
        ImageIcon imageIcon = new ImageIcon(image);
        BufferedImage bufferedImage = new BufferedImage(imageIcon
                .getIconWidth(), imageIcon.getIconHeight(),
                BufferedImage.TYPE_INT_RGB);


        Graphics2D g2D = (Graphics2D) bufferedImage.getGraphics();
        g2D.drawImage(imageIcon.getImage(), 0, 0, imageIcon
                .getImageObserver());


        //设置白色
        for (int x = 0; x < bufferedImage.getWidth(); x++) {
            for (int y = 0; y < bufferedImage.getHeight(); y++) {
                int rgb = bufferedImage.getRGB(x, y);
                int a = (rgb & 0xff000000) >> 24;
                int R = (rgb & 0x00ff0000) >> 16;
                int G = (rgb & 0x0000ff00) >> 8;
                int B = (rgb & 0xff);

                if (R == 0 && G == 0 && B == 0) {
                    bufferedImage.setRGB(x, y, -1); //set white
                }
            }
        }

        g2D.drawImage(bufferedImage, 0, 0, imageIcon.getImageObserver());
        ImageIO.write(bufferedImage, "jpg", outFile);
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
