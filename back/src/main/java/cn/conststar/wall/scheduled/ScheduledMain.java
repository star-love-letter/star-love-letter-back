package cn.conststar.wall.scheduled;

import cn.conststar.wall.service.ServiceUser;
import cn.conststar.wall.utils.UtilsEmail;
import cn.conststar.wall.utils.UtilsMain;
import org.apache.log4j.Logger;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Controller;

import java.io.File;

@Controller
public class ScheduledMain {
    Logger logger = Logger.getLogger(ScheduledMain.class);

    private void clearTempFile() {
        logger.info("clearTempFile");
        String path = UtilsMain.getTempPath();
        System.out.println(path);
        long nowTime = System.currentTimeMillis();

        File tempDirectory = new File(path);
        if (!tempDirectory.isDirectory())
            tempDirectory.mkdir();

        File[] tempFiles = tempDirectory.listFiles();
        for (File file : tempFiles) {
            long fileTime = file.lastModified();
            //删除2天前的文件
            if (nowTime - fileTime > 1000 * 60 * 60 * 24 * 2)
                file.delete();
        }
    }

    private void clearVerifyCode() {
        ServiceUser.clearOverdue();
    }

    //每天0点 运行
    @Scheduled(cron = "0 0 0 * * ?")
    public void D0H() {
        clearTempFile();
    }

    //每天12点和0点运行
    @Scheduled(cron = "0 0 0,12 * * ?")
    public void D0_12H() {
        clearVerifyCode();
    }
}
