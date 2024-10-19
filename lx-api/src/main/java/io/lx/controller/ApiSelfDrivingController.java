/**
 * 首页相关接口
 */

package io.lx.controller;


import io.lx.service.HotWordsService;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.annotation.Resource;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * 自驾活动
 *
 * @author wyh
 */
@RestController
@RequestMapping("/self-driving")
@Tag(name = "自驾活动")
@AllArgsConstructor
public class ApiSelfDrivingController {

    @Resource
    private HotWordsService hotWordsService;

//    @GetMapping("getSelfDrivingListByPage")
//    @Operation(summary = "分页")
//    @Parameters({
//            @Parameter(name = Constant.PAGE, description = "当前页码，从1开始", in = ParameterIn.QUERY, required = true, ref="int") ,
//            @Parameter(name = Constant.LIMIT, description = "每页显示记录数", in = ParameterIn.QUERY,required = true, ref="int") ,
//            @Parameter(name = Constant.ORDER_FIELD, description = "排序字段", in = ParameterIn.QUERY, ref="String") ,
//            @Parameter(name = Constant.ORDER, description = "排序方式，可选值(asc、desc)", in = ParameterIn.QUERY, ref="String")
//    })
//    @Login
//    public Result<PageData<TravelGuidesDTO>> page(@Parameter(hidden = false) @RequestParam Map<String, Object> params){
//        PageData<TravelGuidesDTO> page = travelGuidesService.page(params);
//
//        return new Result<PageData<TravelGuidesDTO>>().ok(page);
//    }

}
