/**
 * 首页相关接口
 */

package io.lx.controller;


import io.lx.common.constant.Constant;
import io.lx.common.exception.RenException;
import io.lx.common.page.PageData;
import io.lx.common.utils.Result;
import io.lx.common.validator.ValidatorUtils;
import io.lx.dto.RoadConditionsDTO;
import io.lx.dto.RoadDiscussDTO;
import io.lx.dto.TravelGuidesDTO;
import io.lx.service.*;
import io.lx.utils.TokenUtils;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.Parameters;
import io.swagger.v3.oas.annotations.enums.ParameterIn;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.annotation.Resource;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

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
    @Resource
    private RoadDiscussService roadDiscussService;
    @Resource
    private TokenUtils tokenUtils;



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

    @GetMapping("/getMyRoadConsList")
    @Operation(summary = "根据token获取我提交的路况公告列表")
    public Result getMyRoadConsList(HttpServletRequest request){
        // 从请求中获取 Token
        String token = tokenUtils.getRequestToken(request);
        return new Result().ok(
                roadConditionsService.getMyRoadConsList(token)
        );
    }

    @GetMapping("getRoadConsById")
    @Operation(summary = "获取路况公告详情")
    public Result<RoadConditionsDTO> getRoadConsById(@NotNull @Parameter Long id) {

        return new Result<RoadConditionsDTO>().ok(roadConditionsService.getRoadConsById(id));
    }

    @PostMapping("uploadRoadConsImage")
    @Operation(summary = "上传公告图片")
    public Result uploadRoadConsImage(@RequestBody MultipartFile file, HttpServletRequest request) throws RenException {
        // 从请求中获取 Token
        String token = tokenUtils.getRequestToken(request);
        return new Result().ok(roadConditionsService.uploadRoadConsImage(file,token));
    }
    @PostMapping("submitRoadCon")
    @Operation(summary = "用户提交路况公告")
    public Result submitRoadCon(@RequestBody RoadConditionsDTO dto, HttpServletRequest request)throws RenException {
        //表单校验
        ValidatorUtils.validateEntity(dto);
        // 从请求中获取 Token
        String token = tokenUtils.getRequestToken(request);
        roadConditionsService.submitRoadCon(dto,token);
        return new Result();
    }

    @PostMapping("discussRoadCons")
    @Operation(summary = "用户评论路况公告")
    public Result discussRoadCons(@RequestBody RoadDiscussDTO dto, HttpServletRequest request)throws RenException {
        //表单校验
        ValidatorUtils.validateEntity(dto);
        // 从请求中获取 Token
        String token = tokenUtils.getRequestToken(request);
        roadDiscussService.discussRoadCons(dto,token);
        return new Result();
    }

//    @GetMapping("/getTravelGuidesList")
//    @Operation(summary = "获取路线攻略列表")
//    public Result getTravelGuidesList(){
//        return new Result().ok(
//                travelGuidesService.getTravelGuidesList()
//        );
//    }

    @GetMapping("getTravelGuidesListByPage")
    @Operation(summary = "分页")
    @Parameters({
            @Parameter(name = Constant.PAGE, description = "当前页码，从1开始", in = ParameterIn.QUERY, required = true, ref="int") ,
            @Parameter(name = Constant.LIMIT, description = "每页显示记录数", in = ParameterIn.QUERY,required = true, ref="int") ,
            @Parameter(name = Constant.ORDER_FIELD, description = "排序字段", in = ParameterIn.QUERY, ref="String") ,
            @Parameter(name = Constant.ORDER, description = "排序方式，可选值(asc、desc)", in = ParameterIn.QUERY, ref="String")
    })
    public Result<PageData<TravelGuidesDTO>> page(@Parameter(hidden = false) @RequestParam Map<String, Object> params){
        PageData<TravelGuidesDTO> page = travelGuidesService.page(params);

        return new Result<PageData<TravelGuidesDTO>>().ok(page);
    }

    @GetMapping("/getTravelGuidesList")
    @Operation(summary = "获取路线攻略列表")
    public Result getTravelGuidesList(@Parameter String keyword){
        return new Result().ok(
                travelGuidesService.getTravelGuidesList(keyword)
        );
    }

}
