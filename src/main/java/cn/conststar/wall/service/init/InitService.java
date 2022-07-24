package cn.conststar.wall.service.init;

import cn.conststar.wall.service.adminSetting.AdminSettingService;
import cn.conststar.wall.utils.EmailSend;
import cn.conststar.wall.utils.Tools;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;

@Service
public class InitService {
    Logger logger = Logger.getLogger(InitService.class);

    @Autowired
    private AdminSettingService settingService;

    @PostConstruct
    public void init() throws Exception {
        //初始化数据
        Tools.initData();

        //加载设置
        settingService.loadSetting();

        //加载邮箱资源
        EmailSend.loadEmail();

        logger.info("init success!");
    }

}
