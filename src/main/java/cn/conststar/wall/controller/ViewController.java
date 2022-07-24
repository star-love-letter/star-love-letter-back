package cn.conststar.wall.controller;

import cn.conststar.wall.config.GlobalConfig;
import cn.conststar.wall.constant.ResponseEnumConstant;
import cn.conststar.wall.handler.FormatHandler;
import cn.conststar.wall.handler.GenericHandler;
import cn.conststar.wall.pojo.dto.ViewDto;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;


@RestController
@RequestMapping("/api/view")
@Api(tags = "页面显示")
public class ViewController {

    @GetMapping("/getView")
    @ApiOperation(value = "获取页面显示")
    public GenericHandler<ViewDto> getView() {

        ViewDto view = new ViewDto();
        view.setHeader(GlobalConfig.viewHeader);
        view.setFooter(GlobalConfig.viewFooter);
        view.setStatsCode(GlobalConfig.viewStatsCode);
        view.setStop(GlobalConfig.viewStop);
        view.setStopReason(GlobalConfig.viewStopReason);

        return FormatHandler.retParam(ResponseEnumConstant.CODE_200, view);
    }
}
