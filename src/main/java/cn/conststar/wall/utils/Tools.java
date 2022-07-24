package cn.conststar.wall.utils;

import cn.conststar.wall.exception.BusinessException;
import cn.conststar.wall.constant.ResponseEnumConstant;
import org.apache.log4j.Logger;
import org.springframework.core.io.ClassPathResource;

import java.io.*;
import java.util.*;

public class Tools {

    public static Logger logger = Logger.getLogger(Tools.class);

    public static final String CONF_PATH = "./conf/";

    public static final String DATA_PATH = "./data/";
    public static final String DATA_IMAGE_PATH = DATA_PATH + "image/";
    public static final String DATA_TEMP_PATH = DATA_PATH + "temp/";
    public static final String DATA_CODE_IMAGE_PATH = DATA_PATH + "codeImage/"; //验证码图片存放路径

    public static Set<String> avatars = new HashSet<>();

    public static void initData() throws IOException {

        //初始化验证码图片
        initImageCode();

        //初始化头像列表
        initAvatars();
        if (avatars.isEmpty()) {
            unzipData();
            initAvatars();
        }

        //初始化数据库
        initDataBase();

    }

    //解压数据文件
    private static void unzipData() throws IOException {
        ClassPathResource classPathResource = new ClassPathResource("data/data.zip");

        File codeImageFile = new File(DATA_PATH, "data.zip");
        InputStream inputStream = classPathResource.getInputStream();
        copyInputStreamToFile(inputStream, codeImageFile);
        ZipUtils.unzip(codeImageFile.getPath(), DATA_PATH);
        codeImageFile.delete();
    }

    //初始化验证码文件
    private static void initImageCode() throws IOException {
        File imageCodeDirectory = Tools.getConfCodeImageDirectory();
        String[] imageCodeList = imageCodeDirectory.list((dir, name) -> {
            if (!new File(dir, name).isFile())
                return false;

            return name.toLowerCase().endsWith(".png");
        });

        if (imageCodeList == null || imageCodeList.length == 0) {
            unzipData();
        }
    }

    private static void initAvatars() {
        File avatarDirectory = Tools.getDataImageDirectory();
        avatars.clear();
        avatars.addAll(List.of(avatarDirectory.list((dir, name) -> {
            return name.toLowerCase().startsWith("avatar");
        })));
    }

    private static void initDataBase() {
        File h2 = new File(DATA_PATH, "sqlite.db");
        if (!h2.exists()) {
            try {
                InputStream inputStream = new ClassPathResource("data/sqlite.db").getInputStream();
                copyInputStreamToFile(inputStream, h2);
            } catch (IOException e) {
                logger.error("初始化数据库错误:" + h2.getName());
                e.printStackTrace();
            }
        }
    }

    public static void copyInputStreamToFile(InputStream input, File file)
            throws IOException {
        int index;
        byte[] bytes = new byte[1024];
        FileOutputStream downloadFile = new FileOutputStream(file);
        while ((index = input.read(bytes)) != -1) {
            downloadFile.write(bytes, 0, index);
            downloadFile.flush();
        }
        input.close();
        downloadFile.close();

    }

    public static String getUUID() {
        return UUID.randomUUID().toString().replaceAll("-", "");
    }

    //获取临时数据目录 自动创建目录
    public static File getDataTempDirectory() {
        String path = DATA_TEMP_PATH;

        File file = new File(path);
        if (!file.isDirectory()) {
            file.mkdirs();
        }

        return file;
    }

    //获取图片数据目录 并自动创建目录
    public static File getDataImageDirectory() {
        String path = DATA_IMAGE_PATH;

        File file = new File(path);
        if (!file.isDirectory()) {
            file.mkdirs();
        }

        return file;
    }

    public static File getConfCodeImageDirectory() {
        String path = DATA_CODE_IMAGE_PATH;

        File file = new File(path);
        if (!file.isDirectory()) {
            file.mkdirs();
        }

        return file;
    }

    //将图片 从临时文件夹 添加到 数据文件夹
    public static synchronized void addDataImages(Set<String> files) {
        if (files == null || files.isEmpty())
            return;

        File tempDir = Tools.getDataTempDirectory();
        File imageDir = Tools.getDataImageDirectory();
        long nowTime = System.currentTimeMillis();

        //检测文件
        for (String file : files) {
            File pathFileOld = new File(tempDir, file);

            //文件不存在 或者 文件已经超过一天了
            if (!pathFileOld.isFile() || nowTime - pathFileOld.lastModified() > 1000 * 60 * 60 * 24)
                throw new BusinessException("没有找到此文件:" + pathFileOld);
        }

        //从临时文件夹移动到图片文件夹
        for (String file : files) {
            File pathFileOld = new File(tempDir, file);
            File pathFileNew = new File(imageDir, file);
            boolean b = pathFileOld.renameTo(pathFileNew);
            if (!b) {
                throw new BusinessException(file.toString() + " 文件移动失败", ResponseEnumConstant.CODE_30001);
            }
        }
    }
}
