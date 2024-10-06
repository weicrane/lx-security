/**
 * 首页相关接口
 */

package io.lx.controller;


import io.lx.annotation.Login;
import io.lx.common.constant.Constant;
import io.lx.common.page.PageData;
import io.lx.common.utils.Result;
import io.lx.dto.TravelGuidesDTO;
import io.lx.entity.RoadConditionsEntity;
import io.lx.service.*;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.Parameters;
import io.swagger.v3.oas.annotations.enums.ParameterIn;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.annotation.Resource;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

/**
 * 首页
 *
 * @author wyh
 */
@RestController
@RequestMapping("/home")
@Tag(name = "首页")
@AllArgsConstructor
public class ApiHomeController {

    @Resource
    private HotWordsService hotWordsService;
    @Resource
    private BannersService bannersService;
    @Resource
    private CoreRoutesService coreRoutesService;
    @Resource
    private RoadConditionsService roadConditionsService;
    @Resource
    private TravelGuidesService travelGuidesService;


//    private RouteService routeService;


//    @GetMapping("searchAll")
//    @Operation(summary = "全局搜索路线")
//    public Result searchAll(String keyword) {
//        return new Result().ok(
//                routeService.searchAll(keyword)
//        );
//    }

    @GetMapping("getHotSearchWords")
    @Operation(summary = "获取热门搜索关键字")
    public Result getHotSearchWords(){
        return new Result().ok(
                hotWordsService.getHotSearchWords()
        );
    }

    @GetMapping("getBannerList")
    @Operation(summary = "获取轮播图列表")
    public Result getBannerList(){
        return new Result().ok(
                bannersService.getBannerList()
        );
    }

    @GetMapping("/getCoreList")
    @Operation(summary = "获取金刚区列表")
    public Result getCoreList(){
        return new Result().ok(
                coreRoutesService.getCoreList()
        );
    }

    @GetMapping("/getRoadConsList")
    @Operation(summary = "获取路况公告列表")
    public Result getRoadConsList(){
        return new Result().ok(
                roadConditionsService.getRoadConsList()
        );
    }

    @GetMapping("getRoadConsById")
    @Operation(summary = "获取路况公告详情")
    public Result<RoadConditionsEntity> getRoadConsById(@Parameter(hidden = true) Long id) {
        return new Result<RoadConditionsEntity>().ok(roadConditionsService.getRoadConsById(id));
    }

    @GetMapping("/getTravelGuidesList")
    @Operation(summary = "获取路线攻略列表")
    public Result getTravelGuidesList(){
        return new Result().ok(
                travelGuidesService.getTravelGuidesList()
        );
    }

    @GetMapping("getTravelGuidesListByPage")
    @Operation(summary = "分页")
    @Parameters({
            @Parameter(name = Constant.PAGE, description = "当前页码，从1开始", in = ParameterIn.QUERY, required = true, ref="int") ,
            @Parameter(name = Constant.LIMIT, description = "每页显示记录数", in = ParameterIn.QUERY,required = true, ref="int") ,
            @Parameter(name = Constant.ORDER_FIELD, description = "排序字段", in = ParameterIn.QUERY, ref="String") ,
            @Parameter(name = Constant.ORDER, description = "排序方式，可选值(asc、desc)", in = ParameterIn.QUERY, ref="String")
    })
    @Login
    public Result<PageData<TravelGuidesDTO>> page(@Parameter(hidden = false) @RequestParam Map<String, Object> params){
        PageData<TravelGuidesDTO> page = travelGuidesService.page(params);

        return new Result<PageData<TravelGuidesDTO>>().ok(page);
    }

}
