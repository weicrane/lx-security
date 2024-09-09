package io.lx.modules.model.controller;
import io.lx.modules.model.dto.ModelParamsDto;
import io.lx.modules.model.service.ModelParamsService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * 模型管理
 *
 * @author wyh
 */
@RestController
@RequestMapping("/model")
@Tag(name = "模型管理")
@AllArgsConstructor
public class ModelParamsController {
    private final ModelParamsService modelParamsService;

    @GetMapping("getModelParamsList")
    @Operation(summary = "获取模型参数列表")
    public List<ModelParamsDto> getModelParamsList(){
        return modelParamsService.getModelParamsList();
    }
}
