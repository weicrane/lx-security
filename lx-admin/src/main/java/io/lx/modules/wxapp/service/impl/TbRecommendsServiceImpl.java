package io.lx.modules.wxapp.service.impl;

import cn.hutool.core.util.StrUtil;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import io.lx.common.exception.RenException;
import io.lx.common.page.PageData;
import io.lx.common.service.impl.CrudServiceImpl;
import io.lx.common.utils.ConvertUtils;
import io.lx.modules.wxapp.dao.TbRecommendsDao;
import io.lx.modules.wxapp.dto.TbRecommendsDTO;
import io.lx.modules.wxapp.entity.TbRecommendsEntity;
import io.lx.modules.wxapp.service.TbPartnersService;
import io.lx.modules.wxapp.service.TbRecommendsService;
import io.lx.modules.wxapp.service.TbRoutesGuidesService;
import io.lx.modules.wxapp.service.TbSelfDrivingsService;
import jakarta.annotation.Resource;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.Optional;

/**
 * 首页推荐
 *
 * @author Mofeng laoniane@gmail.com
 * @since 1.0.0 2024-12-18
 */
@Service
public class TbRecommendsServiceImpl extends CrudServiceImpl<TbRecommendsDao, TbRecommendsEntity, TbRecommendsDTO> implements TbRecommendsService {

    @Lazy
    @Resource
    TbRoutesGuidesService tbRoutesGuidesService;
    @Lazy
    @Resource
    TbSelfDrivingsService tbSelfDrivingsService;
    @Lazy
    @Resource
    TbPartnersService tbPartnersService;

    @Override
    public QueryWrapper<TbRecommendsEntity> getWrapper(Map<String, Object> params) {
        String id = (String) params.get("id");

        QueryWrapper<TbRecommendsEntity> wrapper = new QueryWrapper<>();
        wrapper.eq(StrUtil.isNotBlank(id), "id", id);

        return wrapper;
    }

    @Override
    public PageData<TbRecommendsEntity> getTbRecommendsListByPage(Map<String, Object> params) {
        QueryWrapper<TbRecommendsEntity> wrapper = new QueryWrapper<>();

        // 按 order 字段升序排序
        wrapper.orderByAsc("orders");
        // 按 updated_at 字段降序排序
        wrapper.orderByDesc("updated_at");

        // 获取分页对象
        IPage<TbRecommendsEntity> page = baseDao.selectPage(getPage(params, "updated_at", false), wrapper);

        // 转换为 PageData 类型
        List<TbRecommendsEntity> records = page.getRecords();
        long total = page.getTotal();
        int size = (int) page.getSize();
        int current = (int) page.getCurrent();
        int pages = (int) page.getPages();

        // 封装成 PageData
        return new PageData<>(records, total, size, pages, current);
    }

    /**
     * 新增
     */
    @Override
    public void save(TbRecommendsDTO dto) {
        Integer id = dto.getId();
        String type = dto.getType();

        Integer order = Optional.ofNullable(dto.getOrders()).orElse(null);


        // 定义一个通用的entity对象
        Object entity = null;
        switch (type){
            case "03":
                // 路线
                try {
                     entity = tbRoutesGuidesService.selectById(id);
                }catch (Exception e){
                    throw new RenException("查询失败，请联系管理员");
                }
                break;
            case "02":
                // 自驾
                try {
                     entity = tbSelfDrivingsService.selectById(id);
                }catch (Exception e){
                    throw new RenException("查询失败，请联系管理员");
                }
                break;
            case "04":
                // 商家
                try {
                     entity = tbPartnersService.selectById(id);
                }catch (Exception e){
                    throw new RenException("查询失败，请联系管理员");
                }
                break;
            default:
                throw new RenException("无效的类型");
        }
        if (entity == null) {
            throw new RenException("未找到相关记录");
        }

        TbRecommendsEntity newEntity = ConvertUtils.sourceToTarget(entity, currentModelClass());
        newEntity.setId(id);
        newEntity.setType(type);
        newEntity.setOrders(order);

        QueryWrapper<TbRecommendsEntity> wrapper = new QueryWrapper<>();
        wrapper.eq("id", id);
        wrapper.eq("type",type);
        // 新增还是更新
        List<TbRecommendsEntity> entityList =  baseDao.selectList(wrapper);
        if (entityList.isEmpty()){
            insert(newEntity);
        }else{
            TbRecommendsDTO newDto = ConvertUtils.sourceToTarget(newEntity, TbRecommendsDTO.class);
            update(newDto);
        }

    }

    @Override
    public void deleteByIdAndType(Integer id,String type){
        QueryWrapper<TbRecommendsEntity> wrapper = new QueryWrapper<>();
        wrapper.eq("id", id);
        wrapper.eq("type",type);
        baseDao.delete(wrapper);
    }

    /**
     * 循环调用了。。
     * @param id
     * @param type
     * @param title
     * @param subTitle
     * @param coverImgPath
     */
    @Override
    public void updateInfo(Integer id,String type,String title,String subTitle,String coverImgPath){
        // 查询是否存在
        QueryWrapper<TbRecommendsEntity> wrapper = new QueryWrapper<>();
        wrapper.eq("id", id);
        wrapper.eq("type",type);
        List<TbRecommendsEntity> entityList = baseDao.selectList(wrapper);
        if (entityList.isEmpty()){
            // 不存在，直接返回
            return;
        }
        // 存在，更新数据
        TbRecommendsEntity entity = entityList.get(0);
        entity.setTitle(title);
        entity.setSubTitle(subTitle);
        entity.setCoverImgPath(coverImgPath);
        entity.setUpdatedAt(new Date());
        TbRecommendsDTO newDto = ConvertUtils.sourceToTarget(entity, TbRecommendsDTO.class);

        update(newDto);
    }

    @Override
    public void updateSale(Integer id,String type,String saleStatus){
        // 查询是否存在
        QueryWrapper<TbRecommendsEntity> wrapper = new QueryWrapper<>();
        wrapper.eq("id", id);
        wrapper.eq("type",type);
        List<TbRecommendsEntity> entityList = baseDao.selectList(wrapper);
        if (entityList.isEmpty()){
            // 不存在，直接返回
            return;
        }
        // 存在，更新上架状态
        TbRecommendsEntity entity = entityList.get(0);
        entity.setOnsale(saleStatus);
        entity.setUpdatedAt(new Date());
        baseDao.updateById(entity);
    }

}