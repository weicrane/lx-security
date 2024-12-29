/**
 * 首页相关接口
 */

package io.lx.controller;


import dm.jdbc.util.StringUtil;
import io.lx.annotation.Login;
import io.lx.annotation.LoginUser;
import io.lx.common.constant.Constant;
import io.lx.common.exception.RenException;
import io.lx.common.page.PageData;
import io.lx.common.utils.Result;
import io.lx.dto.PicsDTO;
import io.lx.dto.RoutesGuidesDTO;
import io.lx.entity.UserEntity;
import io.lx.service.HighlightsService;
import io.lx.service.JourneyService;
import io.lx.service.PicsService;
import io.lx.service.RoutesGuidesService;
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
 * 玩法线路指南
 *
 * @author wyh
 */
@RestController
@RequestMapping("/routesguides")
@Tag(name = "玩法线路指南")
@AllArgsConstructor
public class ApiRouteGuidesController {

    @Resource
    private RoutesGuidesService routesGuidesService;
    @Resource
    private JourneyService journeyService;
    @Resource
    private PicsService picsService;
    @Resource
    private HighlightsService highlightsService;

    @GetMapping("getAllRoutesGuidesByPage")
    @Operation(summary = "获取玩法路线指南列表-分页、搜索")
    @Parameters({
            @Parameter(name = Constant.PAGE, description = "当前页码，从1开始", in = ParameterIn.QUERY, required = true, ref="int") ,
            @Parameter(name = Constant.LIMIT, description = "每页显示记录数", in = ParameterIn.QUERY,required = true, ref="int") ,
            @Parameter(name = Constant.ORDER_FIELD, description = "排序字段", in = ParameterIn.QUERY, ref="String") ,
            @Parameter(name = Constant.ORDER, description = "排序方式，可选值(asc、desc)", in = ParameterIn.QUERY, ref="String")
    })
    @Login
    public Result<PageData<RoutesGuidesDTO>> getAllRoutesGuidesByPage(@Parameter(hidden = false) @RequestParam Map<String, Object> params,
                                                                      @Parameter String keyword,
                                                                      @Parameter String season){
        // 从请求中获取 Token
        PageData<RoutesGuidesDTO> page = routesGuidesService.getAllRoutesGuidesByPage(keyword,season,params);
        return new Result<PageData<RoutesGuidesDTO>>().ok(page);
    }

    @GetMapping("getRoutesGuidesInfoById")
    @Operation(summary = "根据id获取玩法详情信息")
    public Result<RoutesGuidesDTO> get(@Parameter Long id){
        RoutesGuidesDTO data = routesGuidesService.get(id);

        return new Result<RoutesGuidesDTO>().ok(data);
    }


    @GetMapping("getRoutesGuidesDetail")
    @Operation(summary = "获取玩法详情")
    @Login
    public Result getRoutesGuidesDetail(@Parameter Integer guideId){
        if(guideId == null){
            throw new RenException("玩法id不能为空");
        }
        return new Result().ok(
                routesGuidesService.getRoutesGuidesDetail(guideId)
        );
    }

    @GetMapping("getPicsByPage")
    @Operation(summary = "获取玩法路线指南照片墙-分页")
    @Parameters({
            @Parameter(name = Constant.PAGE, description = "当前页码，从1开始", in = ParameterIn.QUERY, required = true, ref="int") ,
            @Parameter(name = Constant.LIMIT, description = "每页显示记录数", in = ParameterIn.QUERY,required = true, ref="int") ,
            @Parameter(name = Constant.ORDER_FIELD, description = "排序字段", in = ParameterIn.QUERY, ref="String") ,
            @Parameter(name = Constant.ORDER, description = "排序方式，可选值(asc、desc)", in = ParameterIn.QUERY, ref="String")
    })
    public Result<PageData<PicsDTO>> getAllRoutesGuidesByPage(@Parameter(hidden = false) @RequestParam Map<String, Object> params,
    @Parameter Integer guideId){
        if (guideId==null){
            throw new RenException("玩法id不能为空");
        }
        PageData<PicsDTO> page = picsService.getPicsByPage(params,guideId);
        return new Result<PageData<PicsDTO>>().ok(page);
    }

    /**
     * 获取行程信息
     * 1.判断用户是否购买
     * 2.已购买，返回全部
     * 3.未购买，只返回第一天
     *
     * @param
     * @return
     */
    @GetMapping("getJourney")
    @Operation(summary = "获取行程信息")
    @Login
    public Result<Map<String,Object>> getJourney(@Parameter Integer guideId,@Parameter String journeyType, @LoginUser UserEntity user){
        if(guideId == null){
            throw new RenException("玩法id不能为空");
        }
        if (StringUtil.isEmpty(journeyType)){
            throw new RenException("行程类型不能为空");
        }
        return new Result().ok(
                journeyService.getMainJourney(guideId,journeyType,user)
        );
    }

    @GetMapping("getJourneyDetail")
    @Operation(summary = "获取行程详情")
    @Login
    public Result getJourneyDetail(@Parameter Integer journeyId){
        if(journeyId == null){
            throw new RenException("日程id不能为空");
        }
        return new Result().ok(
                highlightsService.getJourneyDetail(journeyId)
        );
    }

    @Login
    @GetMapping("getMyGuidesByPage")
    @Operation(summary = "获取我的玩法指南列表-分页")
    @Parameters({
            @Parameter(name = Constant.PAGE, description = "当前页码，从1开始", in = ParameterIn.QUERY, required = true, ref="int") ,
            @Parameter(name = Constant.LIMIT, description = "每页显示记录数", in = ParameterIn.QUERY,required = true, ref="int") ,
            @Parameter(name = Constant.ORDER_FIELD, description = "排序字段", in = ParameterIn.QUERY, ref="String") ,
            @Parameter(name = Constant.ORDER, description = "排序方式，可选值(asc、desc)", in = ParameterIn.QUERY, ref="String")
    })
    public Result<PageData<RoutesGuidesDTO>> getMyTravelGuides(@Parameter(hidden = false) @RequestParam Map<String, Object> params, @Parameter String keyword,@LoginUser UserEntity user){
        PageData<RoutesGuidesDTO> page = routesGuidesService.getMyGuidesByPage(keyword,params,user);
        return new Result<PageData<RoutesGuidesDTO>>().ok(page);
    }


    @GetMapping("getPathCoordinates")
    @Operation(summary = "获取行程路径坐标列表")
    @Login
    public Result<Map<String,Object>> getJourney(@Parameter Integer journeyId){
        if(journeyId == null){
            throw new RenException("行程id不能为空");
        }
        return new Result().ok(
                journeyService.getPathCoordinates(journeyId)
        );
    }



}
