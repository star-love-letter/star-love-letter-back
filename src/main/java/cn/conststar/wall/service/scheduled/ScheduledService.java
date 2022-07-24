package cn.conststar.wall.service.scheduled;

import cn.conststar.wall.service.adminLog.AdminLogService;
import cn.conststar.wall.service.file.impl.FileServiceImpl;
import cn.conststar.wall.utils.Tools;
import cn.conststar.wall.service.verifyCode.VerifyCodeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import java.io.File;

@Service
public class ScheduledService {
    @Autowired
    private AdminLogService adminLogService;

    @Scheduled(cron = "0 0 0 * * 0-7")
    public void clear() throws Exception {

        //清理过期的验证码
        VerifyCodeService.clearOverdue();

        //清理临时文件
        long nowTime = System.currentTimeMillis();

        File tempDirectory = Tools.getDataTempDirectory();
        if (!tempDirectory.isDirectory())
            tempDirectory.mkdir();

        File[] tempFiles = tempDirectory.listFiles();
        for (File file : tempFiles) {
            long fileTime = file.lastModified();
            //删除12小时前的文件
            if (nowTime - fileTime > 1000 * 60 * 60 * 12)
                file.delete();
        }

        //重置用户上传的图片数量
        FileServiceImpl.userImageSize.clear();

        //清除过期日志
        adminLogService.clearLogExpire();
    }
}
