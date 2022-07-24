package cn.conststar.wall.controller;

import cn.conststar.wall.constant.ResponseEnumConstant;
import cn.conststar.wall.handler.FormatHandler;
import cn.conststar.wall.handler.GenericHandler;
import cn.conststar.wall.service.file.FileService;
import cn.conststar.wall.utils.Tools;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.*;

@RestController
@RequestMapping("/api/file")
@Api(tags = "文件操作")
public class FileController {

    @Autowired
    FileService fileService;


    @PostMapping(value = "/image")
    @ApiOperation(value = "上传图片", notes = "上传图片，返回上传的图片名")
    public GenericHandler<String> uploadImage(
            @ApiParam("文件") @RequestParam("file") MultipartFile file) throws Exception {

        String fileName = fileService.uploadImage(file);

        return FormatHandler.retParam(ResponseEnumConstant.CODE_200, fileName, "上传成功");
    }


    @GetMapping("/image/{image:.+}")
    @ApiOperation(value = "获取图片", notes = "获取图片，返回图片文件")
    public String getTableImage(
            @ApiParam("图片文件名") @PathVariable String image,
            HttpServletRequest request, HttpServletResponse response) throws Exception {

        File file = new File(Tools.DATA_IMAGE_PATH, image);
        //读取文件--输入流
        FileInputStream input = new FileInputStream(file);

        //写出文件--输出流
        OutputStream out = response.getOutputStream();

        //设置response 响应头
        response.reset(); //设置页面不缓存,清空buffer
        response.setCharacterEncoding("UTF-8"); //字符编码
        response.setContentType("image/jpeg"); //二进制传输数据

        byte[] buff = new byte[1024];
        int index = 0;
        //执行 写出操作
        while ((index = input.read(buff)) != -1) {
            out.write(buff, 0, index);
            out.flush();
        }

        out.close();
        input.close();
        return null;
    }
}
