package io.lx.service.impl;

import cn.hutool.core.util.StrUtil;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import io.lx.common.exception.RenException;
import io.lx.common.service.impl.CrudServiceImpl;
import io.lx.dao.RoadDiscussDao;
import io.lx.dto.RoadDiscussDTO;
import io.lx.entity.RoadDiscussEntity;
import io.lx.entity.TokenEntity;
import io.lx.entity.UserEntity;
import io.lx.service.RoadDiscussService;
import io.lx.service.TokenService;
import io.lx.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

/**
 * 
 *
 * @author Mofeng laoniane@gmail.com
 * @since 1.0.0 2024-10-20
 */
@Service
public class RoadDiscussServiceImpl extends CrudServiceImpl<RoadDiscussDao, RoadDiscussEntity, RoadDiscussDTO> implements RoadDiscussService {

    private final TokenService tokenService;
    private final UserService userService;
    @Autowired
    public RoadDiscussServiceImpl(TokenService tokenService, UserService userService) {
        this.tokenService = tokenService;
        this.userService = userService;
    }

    @Override
    public QueryWrapper<RoadDiscussEntity> getWrapper(Map<String, Object> params){
        String id = (String)params.get("id");

        QueryWrapper<RoadDiscussEntity> wrapper = new QueryWrapper<>();
        wrapper.eq(StrUtil.isNotBlank(id), "id", id);

        return wrapper;
    }

    @Override
    public void discussRoadCons(RoadDiscussDTO dto, String token) throws RenException{
        // 获取用户信息
        TokenEntity tokenEntity = tokenService.getByToken(token);
        if (tokenEntity == null) {
            throw new RenException("用户不存在");
        }
        UserEntity user = userService.getUserByUserId(tokenEntity.getUserId());

        // 生成实体status:0-审核不通过，1-审核通过，2-待审核，3-已过期
        RoadDiscussEntity entity = new RoadDiscussEntity();
        entity.setContent(dto.getContent());//评论内容
        entity.setUserId(user.getId());//用户id
        entity.setConditionId(dto.getConditionId());//公告id
        entity.setStatus("2");//待审核
        entity.setNickname(user.getNickname());//昵称
        entity.setAvatarUrl(user.getAvatarUrl());//头像

        // 写入
        baseDao.insert(entity);
    }

    @Override
    public List<RoadDiscussEntity> getDiscussList(Long consId){
        // 查询评论列表
        // 1.获取全部评论
        QueryWrapper<RoadDiscussEntity> wrapper = new QueryWrapper<>();
        wrapper.eq( "condition_id", consId)
                .eq( "status", "1");
        List<RoadDiscussEntity> list = baseDao.selectList(wrapper);

        return list;
    }
}