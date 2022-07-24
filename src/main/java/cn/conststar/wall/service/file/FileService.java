package cn.conststar.wall.service.file;

import org.springframework.web.multipart.MultipartFile;

public interface FileService {
    /**
     * 上传图片
     *
     * @param file 图片文件
     * @return 图片名称
     */
    String uploadImage(MultipartFile file) throws Exception;
}
