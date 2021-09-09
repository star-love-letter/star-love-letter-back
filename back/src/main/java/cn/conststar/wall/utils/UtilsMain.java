package cn.conststar.wall.utils;

import cn.conststar.wall.exception.ExceptionMain;
import org.springframework.web.multipart.MultipartFile;

import javax.crypto.Mac;
import javax.crypto.spec.SecretKeySpec;
import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.util.*;
import java.util.List;
import java.util.regex.Pattern;

public class UtilsMain {

    public static String _key;

    static {
        _key = UUID.randomUUID().toString() + new Random().nextLong();
    }

    public static String getUUID() {
        return UUID.randomUUID().toString().replaceAll("-", "");
    }

    public static String HMACSHA256(String data) throws Exception {
        return HMACSHA256(data, _key);
    }

    public static String HMACSHA256(String data, String key) throws Exception {
        Mac sha256_HMAC = Mac.getInstance("HmacSHA256");
        SecretKeySpec secret_key = new SecretKeySpec(key.getBytes("UTF-8"), "HmacSHA256");
        sha256_HMAC.init(secret_key);

        byte[] array = sha256_HMAC.doFinal(data.getBytes("UTF-8"));
        StringBuilder sb = new StringBuilder();
        for (byte item : array) {
            sb.append(Integer.toHexString((item & 0xFF) | 0x100).substring(1, 3));
        }
        return sb.toString().toUpperCase();

    }

    //检查文本是否需要审核
    public static boolean checkText(String text) {

        //域名
        boolean m1 = Pattern.matches("[a-zA-Z0-9][-a-zA-Z0-9]{0,62}(\\.[a-zA-Z0-9][-a-zA-Z0-9]{0,62})+\\.?", text);
        if (m1)
            return true;

        //邮箱
        boolean m2 = Pattern.matches("^\\w+([-+.]\\w+)*@\\w+([-.]\\w+)*\\.\\w+([-.]\\w+)*$", text);
        if (m2)
            return true;

        //手机号
        boolean m3 = Pattern.matches("^(13[0-9]|14[5|7]|15[0|1|2|3|4|5|6|7|8|9]|18[0|1|2|3|5|6|7|8|9])\\d{8}$", text);
        if (m3)
            return true;

        //座机
        boolean m4 = Pattern.matches("((\\d{11})|^((\\d{7,8})|(\\d{4}|\\d{3})-(\\d{7,8})|(\\d{4}|\\d{3})-(\\d{7,8})-(\\d{4}|\\d{3}|\\d{2}|\\d{1})|(\\d{7,8})-(\\d{4}|\\d{3}|\\d{2}|\\d{1}))$)", text);
        if (m4)
            return true;

        //身份证
        boolean m5 = Pattern.matches("(^\\d{15}$)|(^\\d{18}$)|(^\\d{17}(\\d|X|x)$)", text);
        if (m5)
            return true;

        //QQ
        boolean m6 = Pattern.matches("[1-9][0-9]{4,}", text);
        if (m6)
            return true;

        //ipv4
        boolean m7 = Pattern.matches("((2(5[0-5]|[0-4]\\d))|[0-1]?\\d{1,2})(\\.((2(5[0-5]|[0-4]\\d))|[0-1]?\\d{1,2})){3}", text);
        if (m7)
            return true;

        return false;
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
