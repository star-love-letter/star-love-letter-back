package cn.conststar.wall.service.adminClear;

public interface AdminClearService {

    /**
     * 清理举报
     * @param time 多久之前的举报
     */
    void clearReport(long time);

    /**
     * 清理图片
     * @return  清理的图片数量
     */
    int clearImage() throws Exception;
}
