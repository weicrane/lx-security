package io.lx.service.impl;

import cn.hutool.core.util.ObjectUtil;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import io.lx.common.exception.RenException;
import io.lx.common.service.impl.BaseServiceImpl;
import io.lx.common.utils.ConvertUtils;
import io.lx.common.utils.FileUtil;
import io.lx.dao.RoadConditionsDao;
import io.lx.dto.RoadConditionsDTO;
import io.lx.entity.RoadConditionsEntity;
import io.lx.entity.RoadDiscussEntity;
import io.lx.entity.TokenEntity;
import io.lx.entity.UserEntity;
import io.lx.service.RoadConditionsService;
import io.lx.service.RoadDiscussService;
import io.lx.service.TokenService;
import io.lx.service.UserService;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.util.List;

/**
 * @author Mofeng laoniane@gmail.com
 * @since 1.0.0 2024-10-03
 */
@Service
public class RoadConditionsServiceImpl extends BaseServiceImpl<RoadConditionsDao, RoadConditionsEntity> implements RoadConditionsService {
    @Value("${web.upload-path}")
    private String uploadPath;
    private final TokenService tokenService;
    private final UserService userService;
    private final RoadDiscussService roadDiscussService;
    @Autowired
    public RoadConditionsServiceImpl(TokenService tokenService, UserService userService,RoadDiscussService roadDiscussService) {
        this.tokenService = tokenService;
        this.userService = userService;
        this.roadDiscussService = roadDiscussService;
    }

    @Override
    public List<RoadConditionsDTO> getRoadConsList() {
        QueryWrapper<RoadConditionsEntity> wrapper = new QueryWrapper<>();
        wrapper.eq( "status", "1");
        // TODO:添加按时间逆序排序
        List<RoadConditionsEntity> list = baseDao.selectList(wrapper);
        return ConvertUtils.sourceToTarget(list, RoadConditionsDTO.class);
    }

    @Override
    public List<RoadConditionsDTO> getMyRoadConsList(String token) {
        // 获取用户信息
        TokenEntity tokenEntity = tokenService.getByToken(token);
        if (tokenEntity == null) {
            throw new RenException("用户不存在");
        }
        QueryWrapper<RoadConditionsEntity> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("user_id", tokenEntity.getUserId())
                .eq("user_type", "1"); // 添加查询条件


        List<RoadConditionsEntity> list = baseDao.selectList(queryWrapper);
        return ConvertUtils.sourceToTarget(list, RoadConditionsDTO.class);
    }

    @Override
    public RoadConditionsDTO getRoadConsById(Long id) {
        // 查询公告详情
        RoadConditionsEntity roadConditionsEntity = baseDao.selectById(id);
        if (!"1".equals(roadConditionsEntity.getStatus())){
            throw new RenException("公告未通过审核");
        }
        // 查询评论列表
        List<RoadDiscussEntity> list = roadDiscussService.getDiscussList(id);
        //构造返回值
        RoadConditionsDTO dto = new RoadConditionsDTO();
        // 自动映射相同字段
        BeanUtils.copyProperties(roadConditionsEntity,dto);
        dto.setDiscussList(list);
        return dto;
    }

    @Override
    public void submitRoadCon(RoadConditionsDTO dto, String token) throws RenException{
        // 获取用户信息
        TokenEntity tokenEntity = tokenService.getByToken(token);
        if (tokenEntity == null) {
            throw new RenException("用户不存在");
        }
        UserEntity user = userService.getUserByUserId(tokenEntity.getUserId());

        // 生成实体
        RoadConditionsEntity entity = new RoadConditionsEntity();
        entity.setContent(dto.getContent()); //内容
        entity.setTitle(dto.getTitle()); //标题
        entity.setStatus("2"); //:0-审核不通过，1-审核通过，2-待审核，3-已过期
        entity.setUserId(user.getId());
        entity.setUserType("1");//0-管理员，1-用户
        entity.setImgPath(dto.getImgPath());//图片链接
        entity.setType(dto.getType());
        entity.setLatitude(dto.getLatitude());
        entity.setLongitude(dto.getLongitude());
        entity.setAddress(dto.getAddress());

        // 写入
        baseDao.insert(entity);
    }

    @Override
    public String uploadRoadConsImage(MultipartFile file, String token) throws RenException {
        TokenEntity tokenEntity = tokenService.getByToken(token);
        if (tokenEntity == null) {
            throw new RenException("用户不存在");
        }
        if (ObjectUtil.isEmpty(file)) {
            throw new RenException("请上传图片文件");
        }
        if (FileUtil.isNotImage(FileUtil.fileExtensionName(file.getOriginalFilename()))) {
            throw new RenException("请上传图片类型文件");
        }

        UserEntity user = userService.getUserByUserId(tokenEntity.getUserId());
        // 文件名默认为【手机号_时间戳.jpg】
        String originalFilename = file.getOriginalFilename();
        String filename = user.getMobile() + "_" + System.currentTimeMillis() + "." + cn.hutool.core.io.FileUtil.extName(originalFilename);
        // 上传路径默认为【/head/】
        String path = "/road-cons/" + filename;
        File destFile = cn.hutool.core.io.FileUtil.file(uploadPath + path);
        cn.hutool.core.io.FileUtil.touch(destFile);
        try {
            cn.hutool.core.io.FileUtil.writeBytes(file.getBytes(), destFile);
        } catch (IOException e) {
            throw new RenException("请上传头像文件");
        }

        return path;
    }


}