package cn.conststar.wall.service.adminSetting.impl;

import cn.conststar.wall.config.GlobalConfig;
import cn.conststar.wall.dao.AdminSettingDao;
import cn.conststar.wall.pojo.model.SettingDomain;
import cn.conststar.wall.pojo.model.UserDomain;
import cn.conststar.wall.service.adminLog.AdminLogService;
import cn.conststar.wall.service.adminSetting.AdminSettingService;
import cn.conststar.wall.service.user.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class AdminSettingServiceImpl implements AdminSettingService {

    @Autowired
    private AdminSettingDao adminSettingDao;

    @Autowired
    private UserService userService;

    @Autowired
    private AdminLogService adminLogService;

    private static Map<String, String> settingMap = new HashMap<>();

    private List<SettingDomain> getSettings() throws Exception {
        return adminSettingDao.getSettings();
    }

    private void updateSetting(SettingDomain setting) throws Exception {
        adminSettingDao.updateSetting(setting);
    }

    public Map<String, String> getSettingMap() throws Exception {
        return settingMap;
    }

    private void loadSettingMap() throws Exception {
        settingMap.clear();
        List<SettingDomain> settings = getSettings();
        for (SettingDomain setting : settings) {
            settingMap.put(setting.getName(), setting.getValue());
        }
    }

    public synchronized void updateSettingMap(Map<String, String> map) throws Exception {
        Map<String, String> settingMap = this.getSettingMap();
        settingMap.putAll(map);

        for (Map.Entry<String, String> entry : map.entrySet()) {
            SettingDomain setting = new SettingDomain();
            setting.setName(entry.getKey());
            setting.setValue(entry.getValue());
            updateSetting(setting);
        }

        UserDomain admin = userService.getUserBySubject();
        adminLogService.addLog("更新设置", map.toString(), admin.getId());

        //重新加载设置
        loadSetting();
    }


    public void loadSetting() throws Exception {
        loadSettingMap();

        //加载SMTP配置
        String smtpHost = settingMap.get("smtp-host");
        String smtpPortStr = settingMap.get("smtp-port");
        String smtpSSLStr = settingMap.get("smtp-ssl");
        String smtpUsername = settingMap.get("smtp-username");
        String smtpPassword = settingMap.get("smtp-password");

        if (smtpPortStr != null) {
            GlobalConfig.smtpPort = Integer.parseInt(smtpPortStr);
        }
        if (smtpSSLStr != null) {
            GlobalConfig.smtpSSL = Boolean.parseBoolean(smtpSSLStr);
        }
        GlobalConfig.smtpHost = smtpHost;
        GlobalConfig.smtpUsername = smtpUsername;
        GlobalConfig.smtpPassword = smtpPassword;

        //加载用户配置
        String userImageMaxNumberDay = settingMap.get("user-imageMaxNumberDay");
        String userNotifyEmailPeriod = settingMap.get("user-notifyEmailPeriod");
        if (userImageMaxNumberDay != null) {
            GlobalConfig.userImageMaxNumberDay = Integer.parseInt(userImageMaxNumberDay);
        }
        if (userNotifyEmailPeriod != null) {
            GlobalConfig.userNotifyEmailPeriod = Long.parseLong(userNotifyEmailPeriod);
        }

        //加载帖子配置
        String tableImageMaxNumber = settingMap.get("table-imageMaxNumber");
        if (tableImageMaxNumber != null) {
            GlobalConfig.tableImageMaxNumber = Integer.parseInt(tableImageMaxNumber);
        }

        //加载评论配置
        String commentImageMaxNumber = settingMap.get("comment-imageMaxNumber");
        if (commentImageMaxNumber != null) {
            GlobalConfig.commentImageMaxNumber = Integer.parseInt(commentImageMaxNumber);
        }

        //加载页面显示设置
        String viewHeader = settingMap.get("view-header");
        String viewFooter = settingMap.get("view-footer");
        String viewStatsCode = settingMap.get("view-statsCode");
        String viewStop = settingMap.get("view-stop");
        String viewStopReason = settingMap.get("view-stopReason");
        GlobalConfig.viewHeader = viewHeader;
        GlobalConfig.viewFooter = viewFooter;
        GlobalConfig.viewStatsCode = viewStatsCode;
        GlobalConfig.viewStopReason = viewStopReason;
        if (viewStop != null) {
            GlobalConfig.viewStop = Boolean.parseBoolean(viewStop);
        }

        //日志设置
        String logMaxNumber = settingMap.get("log-maxNumber");
        if (logMaxNumber != null) {
            GlobalConfig.logMaxNumber = Integer.parseInt(logMaxNumber);
        }

        //跨域设置
        String corsOrigin = settingMap.get("cors-origin");
        GlobalConfig.corsOrigin = corsOrigin;
    }
}
