package cn.conststar.wall.utils;

import cn.conststar.wall.exception.ExceptionMain;
import org.apache.commons.io.IOUtils;
import org.springframework.web.multipart.MultipartFile;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.*;
import java.util.Arrays;
import java.util.Base64;

public class UtilsImage {

    //图片格式转换
    public static void conversionOut(MultipartFile file, File outFile) throws IOException, ExceptionMain {
        String fileName = file.getOriginalFilename();
        String fileType = "";

        int typeIndex = fileName.lastIndexOf(".");
        if (typeIndex != -1 && typeIndex != fileName.length() - 1)
            fileType = fileName.substring(typeIndex + 1);

        if (!Arrays.asList(ImageIO.getReaderFormatNames()).contains(fileType))
            throw new ExceptionMain("不支持的文件格式");

        conversionOut(file.getInputStream(), outFile);
    }


    //图片格式转换
    public static void conversionOut(InputStream input, File outFile) throws IOException {
//        BufferedImage im = ImageIO.read(input);

        // 清空原有图片的透明色
        BufferedImage im = ImageIO.read(input);
        Image image = im;
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


    //图片转Base64
    public static String base64(BufferedImage image, String formatName) throws IOException {
        ByteArrayOutputStream out = new ByteArrayOutputStream();
        ImageIO.write(image, formatName, out);
        return base64(out, formatName);
    }

    //图片转Base64
    public static String base64(ByteArrayOutputStream out, String formatName) throws IOException {
        String base64 = Base64.getEncoder().encodeToString(out.toByteArray());

        return "data:image/" + formatName + ";base64," + base64;
    }

    public static BufferedImage rotate(File file, int angel) throws IOException {
        BufferedImage src = ImageIO.read(file);
        return rotate(src, angel);
    }

    /**
     * 旋转角度
     *
     * @param src   源图片
     * @param angel 角度
     * @return 目标图片
     */
    public static BufferedImage rotate(Image src, int angel) {
        int src_width = src.getWidth(null);
        int src_height = src.getHeight(null);
        // calculate the new image size
        Rectangle rect_des = CalcRotatedSize(new Rectangle(new Dimension(
                src_width, src_height)), angel);

        BufferedImage res = null;
        res = new BufferedImage(rect_des.width, rect_des.height,
                BufferedImage.TYPE_INT_ARGB_PRE);
        Graphics2D g2 = res.createGraphics();
        // transform(这里先平移、再旋转比较方便处理；绘图时会采用这些变化，绘图默认从画布的左上顶点开始绘画，源图片的左上顶点与画布左上顶点对齐，然后开始绘画，修改坐标原点后，绘画对应的画布起始点改变，起到平移的效果；然后旋转图片即可)
        //平移（原理修改坐标系原点，绘图起点变了，起到了平移的效果，如果作用于旋转，则为旋转中心点）
        g2.translate((rect_des.width - src_width) / 2, (rect_des.height - src_height) / 2);
        //旋转（原理transalte(dx,dy)->rotate(radians)->transalte(-dx,-dy);修改坐标系原点后，旋转90度，然后再还原坐标系原点为(0,0),但是整个坐标系已经旋转了相应的度数 ）
        g2.rotate(Math.toRadians(angel), src_width / 2, src_height / 2);
//        //先旋转（以目标区域中心点为旋转中心点，源图片左上顶点对准目标区域中心点，然后旋转）
//        g2.translate(rect_des.width/2,rect_des.height/ 2);
//        g2.rotate(Math.toRadians(angel));
//        //再平移（原点恢复到源图的左上顶点处（现在的右上顶点处），否则只能画出1/4）
//        g2.translate(-src_width/2,-src_height/2);


        g2.drawImage(src, null, null);
        return res;
    }

    /**
     * 计算转换后目标矩形的宽高
     *
     * @param src   源矩形
     * @param angel 角度
     * @return 目标矩形
     */
    private static Rectangle CalcRotatedSize(Rectangle src, int angel) {
        double cos = Math.abs(Math.cos(Math.toRadians(angel)));
        double sin = Math.abs(Math.sin(Math.toRadians(angel)));
        int des_width = (int) (src.width * cos) + (int) (src.height * sin);
        int des_height = (int) (src.height * cos) + (int) (src.width * sin);
        return new java.awt.Rectangle(new Dimension(des_width, des_height));
    }

}
