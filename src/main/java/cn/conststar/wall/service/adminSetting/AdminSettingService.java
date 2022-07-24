package cn.conststar.wall.service.adminSetting;

import java.util.Map;

public interface AdminSettingService {

    /**
     * 获取设置
     *
     * @return 设置键值对
     */
     Map<String, String> getSettingMap() throws Exception;

    /**
     * 更新设置
     *
     * @param map 新的设置
     */
    void updateSettingMap(Map<String, String> map) throws Exception;

    /**
     * 加载设置
     */
    void loadSetting() throws Exception;
}
