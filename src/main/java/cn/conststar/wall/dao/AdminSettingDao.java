package cn.conststar.wall.dao;

import cn.conststar.wall.pojo.model.SettingDomain;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;

import java.util.List;

@Mapper
@Repository
public interface AdminSettingDao {

    // 获取所有设置
    List<SettingDomain> getSettings() throws Exception;

    // 更新设置
    void updateSetting(SettingDomain setting) throws Exception;
}
