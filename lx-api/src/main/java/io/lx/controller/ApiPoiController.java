/**
 * 首页相关接口
 */

package io.lx.controller;


import io.lx.annotation.Login;
import io.lx.common.exception.RenException;
import io.lx.common.utils.Result;
import io.lx.dto.PoiInfoDTO;
import io.lx.service.PoiInfoService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * POI相关
 *
 * @author wyh
 */
@RestController
@RequestMapping("/poi")
@Tag(name = "POI相关")
@AllArgsConstructor
public class ApiPoiController {
    private PoiInfoService poiInfoService;

    @GetMapping("getPoiList")
    @Operation(summary = "查询路线相关的Poi")
    @Login
    public Result<List<PoiInfoDTO>> getPoiList(@Parameter Integer routeGuideId,@Parameter String poiType){
        if (routeGuideId == null){
            throw new RenException("路线ID不能为空");
        }
        return new Result().ok(poiInfoService.getPoiList(routeGuideId,poiType));
    }

    @GetMapping("getPoiInfo")
    @Operation(summary = "查询Poi详情")
    @Login
    public Result<PoiInfoDTO> getPoiInfo(@Parameter Long pointId){
        if (pointId == null){
            throw new RenException("poi_id不能为空");
        }
        return new Result().ok(poiInfoService.getPoiInfo(pointId));
    }
}
