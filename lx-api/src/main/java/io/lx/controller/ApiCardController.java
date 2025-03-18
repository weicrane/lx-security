/**
 * 首页相关接口
 */

package io.lx.controller;


import io.lx.annotation.Login;
import io.lx.annotation.LoginUser;
import io.lx.common.utils.Result;
import io.lx.dto.CardDTO;
import io.lx.entity.UserEntity;
import io.lx.service.CardService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * 卡密兑换
 *
 * @author wyh
 */
@RestController
@RequestMapping("/card")
@Tag(name = "卡密兑换")
@AllArgsConstructor
public class ApiCardController {
    private CardService cardService;

    @PostMapping("exchangeCard")
    @Operation(summary = "兑换卡密")
    @Login
    public Result exchangeCard(@RequestBody CardDTO dto, @LoginUser UserEntity user) {
        // 进行兑换
        cardService.exchangeCard(dto,user);
        return new Result();
    }
}
