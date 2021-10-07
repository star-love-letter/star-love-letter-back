package cn.conststar.wall.controller;

import cn.conststar.wall.pojo.PojoUser;
import cn.conststar.wall.response.ResponseCodeEnums;
import cn.conststar.wall.response.ResponseFormat;
import cn.conststar.wall.response.ResponseGeneric;
import cn.conststar.wall.service.ServiceFile;
import cn.conststar.wall.service.ServiceUser;
import cn.conststar.wall.utils.UtilsMain;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.*;

@RestController
@Api(tags = "文件操作")
@RequestMapping("/api/file")
public class ControllerFile {

    @Autowired
    @Qualifier("serviceFile")
    ServiceFile serviceFile;

    @Autowired
    @Qualifier("serviceUser")
    ServiceUser serviceUser;


    @PostMapping(value = "/image", produces = {"application/json;charset=UTF-8"})
    @ApiOperation(value = "上传图片", notes = "上传图片，返回上传的图片名")
    public ResponseGeneric<String> uploadImage(
            @ApiParam("文件") @RequestParam("file") MultipartFile file,
            @ApiParam("token") @RequestHeader(value = "token", required = false) String token) throws Exception {

        PojoUser user = serviceUser.getUser(token); //验证用户登录状态
        String fileName = serviceFile.uploadImage(file);

        return ResponseFormat.retParam(ResponseCodeEnums.CODE_200, fileName, "上传成功");
    }


    @GetMapping("/image/{image:.+}")
    @ApiOperation(value = "获取图片", notes = "获取图片，返回图片文件")
    public String getTableImage(
            @ApiParam("图片文件名") @PathVariable String image,
            HttpServletRequest request, HttpServletResponse response) throws Exception {

        //要下载的图片地址
        String path = UtilsMain.getDataImagePath();

        File file = new File(path, image);
        //读取文件--输入流
        FileInputStream input = new FileInputStream(file);

        //写出文件--输出流
        OutputStream out = response.getOutputStream();

        //设置response 响应头
        response.reset(); //设置页面不缓存,清空buffer
        response.setCharacterEncoding("UTF-8"); //字符编码
        response.setContentType("image/jpeg"); //二进制传输数据
//        //设置响应头
//        response.setHeader("Content-Disposition",
//                "attachment;fileName=" + URLEncoder.encode(image, "UTF-8"));

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
