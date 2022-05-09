package cn.conststar.wall.utils;

import cn.conststar.wall.exception.ExceptionMain;
import cn.conststar.wall.response.ResponseCodeEnums;
import org.apache.log4j.Logger;

import java.io.*;
import java.net.URL;
import java.nio.file.Files;
import java.util.*;

public class UtilsMain {

    public static Logger logger = Logger.getLogger(UtilsMain.class);

    static {
        //检测初始资源
        File imageDirectory = UtilsMain.getConfCodeImageDirectory();
        String[] imageList = imageDirectory.list((dir, name) -> {
            if (!new File(dir, name).isFile())
                return false;

            if (!name.toLowerCase().endsWith(".png"))
                return false;

            return true;
        });

        assert imageList != null;
        //如果旋转验证码图片为空 则导出默认图片
        if (imageList.length == 0) {
            URL resource = UtilsMain.class.getResource("/data/codeImage");
            assert resource != null;
            File dir = new File(resource.getFile());
            System.out.println(Arrays.toString(dir.list()));
            for (File image : Objects.requireNonNull(dir.listFiles())) {
                if (image.isFile()) {
                    try {
                        Files.copy(image.toPath(), new File(imageDirectory, image.getName()).toPath());
                    } catch (IOException e) {
                        logger.error("资源初始化错误:" + image.getName());
                        e.printStackTrace();
                    }
                }
            }
        }

        System.out.println("资源初始化完成!");
    }

    public static String getUUID() {
        return UUID.randomUUID().toString().replaceAll("-", "");
    }

    public static String base64(String src) {
        return Base64.getEncoder().encodeToString(src.getBytes());
    }

    public static String deBase64(String src) {
        return new String(Base64.getDecoder().decode(src));
    }

    //获取临时数据目录 自动创建目录
    public static File getDataTempDirectory() {
        String path = getDataTempPath();

        File file = new File(path);
        if (!file.isDirectory()) {
            file.mkdirs();
        }

        return file;
    }

    //获取图片数据目录 并自动创建目录
    public static File getDataImageDirectory() {
        String path = getDataImagePath();

        File file = new File(path);
        if (!file.isDirectory()) {
            file.mkdirs();
        }

        return file;
    }

    public static File getConfCodeImageDirectory() {
        String path = getConfCodeImagePath();

        File file = new File(path);
        if (!file.isDirectory()) {
            file.mkdirs();
        }

        return file;
    }


    //获取配置目录
    public static String getConfPath() {
        return System.getProperty("catalina.base") + "/conf";
    }

    //获取配置图片验证码目录
    public static String getConfCodeImagePath() {
        return getConfPath() + "/codeImage";
//        return "D:\\用户文档\\图片\\codeImage"; //test
    }

    //获取数据目录
    public static String getDataPath() {
        return System.getProperty("catalina.base") + "/data";
    }

    //获取临时数据目录
    public static String getDataTempPath() {
        return getDataPath() + "/temp";
    }

    //获取数据图片目录
    public static String getDataImagePath() {
        return getDataPath() + "/image";
    }


    //将图片 从临时文件夹 添加到 数据文件夹
    public static synchronized void addDataImages(Set<String> files) {
        if (files == null || files.isEmpty())
            return;

        File tempDir = UtilsMain.getDataTempDirectory();
        File imageDir = UtilsMain.getDataImageDirectory();
        long nowTime = System.currentTimeMillis();

        //检测文件
        for (String file : files) {
            File pathFileOld = new File(tempDir, file);

            //文件不存在 或者 文件已经超过一天了
            if (!pathFileOld.isFile() || nowTime - pathFileOld.lastModified() > 1000 * 60 * 60 * 24)
                throw new ExceptionMain("没有找到此文件:" + pathFileOld);
        }

        //从临时文件夹移动到图片文件夹
        for (String file : files) {
            File pathFileOld = new File(tempDir, file);
            File pathFileNew = new File(imageDir, file);
            boolean b = pathFileOld.renameTo(pathFileNew);
            if (!b) {
                throw new ExceptionMain(file.toString() + " 文件移动失败", ResponseCodeEnums.CODE_30001);
            }
        }
    }
}
