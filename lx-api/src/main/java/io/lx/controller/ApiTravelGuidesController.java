/**
 * 首页相关接口
 */

package io.lx.controller;


import dm.jdbc.util.StringUtil;
import io.lx.annotation.Login;
import io.lx.common.constant.Constant;
import io.lx.common.exception.RenException;
import io.lx.common.page.PageData;
import io.lx.common.utils.Result;
import io.lx.dto.MyTravelGuidesDTO;
import io.lx.service.TravelGuidesService;
import io.lx.utils.TokenUtils;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.Parameters;
import io.swagger.v3.oas.annotations.enums.ParameterIn;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.annotation.Resource;
import jakarta.servlet.http.HttpServletRequest;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

/**
 * 路书-网盘
 *
 * @author wyh
 */
@RestController
@RequestMapping("/travelguides")
@Tag(name = "路书-网盘")
@AllArgsConstructor
public class ApiTravelGuidesController {

    @Resource
    private TravelGuidesService travelGuidesService;
    private final TokenUtils tokenUtils;

    @GetMapping("getTravelGuidesDetail")
    @Operation(summary = "获取路书（网盘产品）详情")
    public Result getTravelGuidesDetail(@Parameter String id){
        if(!StringUtil.isNotEmpty(id)){
            throw new RenException("id不能为空");
        }
        Integer int_id = Integer.parseInt(id);
        return new Result().ok(
                travelGuidesService.getTravelGuidesDetail(int_id)
        );
    }

    @Login
    @GetMapping("getMyTravelGuidesByPage")
    @Operation(summary = "获取我的路书（网盘产品）资源列表-分页")
    @Parameters({
            @Parameter(name = Constant.PAGE, description = "当前页码，从1开始", in = ParameterIn.QUERY, required = true, ref="int") ,
            @Parameter(name = Constant.LIMIT, description = "每页显示记录数", in = ParameterIn.QUERY,required = true, ref="int") ,
            @Parameter(name = Constant.ORDER_FIELD, description = "排序字段", in = ParameterIn.QUERY, ref="String") ,
            @Parameter(name = Constant.ORDER, description = "排序方式，可选值(asc、desc)", in = ParameterIn.QUERY, ref="String")
    })
    public Result<PageData<MyTravelGuidesDTO>> getMyTravelGuides(@Parameter(hidden = false) @RequestParam Map<String, Object> params, @Parameter String keyword,HttpServletRequest request){
        // 从请求中获取 Token
        String token = tokenUtils.getRequestToken(request);
        PageData<MyTravelGuidesDTO> page = travelGuidesService.getMyTravelGuides(token,keyword,params);
        return new Result<PageData<MyTravelGuidesDTO>>().ok(page);
    }


}
