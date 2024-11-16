/**
 * 首页相关接口
 */

package io.lx.controller;


import io.lx.annotation.Login;
import io.lx.common.exception.RenException;
import io.lx.common.utils.Result;
import io.lx.common.validator.ValidatorUtils;
import io.lx.dto.GetPoiListDTO;
import io.lx.dto.PoiInfoDTO;
import io.lx.service.PoiInfoService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.*;

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

    @PostMapping("getPoiList")
    @Operation(summary = "查询路线相关的Poi")
    @Login
    public Result<List<PoiInfoDTO>> getPoiList(@RequestBody GetPoiListDTO dto){
        //表单校验
        ValidatorUtils.validateEntity(dto);
        return new Result().ok(poiInfoService.getPoiList(dto.getRouteGuideId(),
                dto.getPoiTypeList(),dto.getDateId(),dto.getJourneyType()));
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
