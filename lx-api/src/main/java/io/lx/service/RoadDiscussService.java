package io.lx.service;

import io.lx.common.service.CrudService;
import io.lx.dto.RoadDiscussDTO;
import io.lx.entity.RoadDiscussEntity;

import java.util.List;

/**
 * 
 *
 * @author Mofeng laoniane@gmail.com
 * @since 1.0.0 2024-10-20
 */
public interface RoadDiscussService extends CrudService<RoadDiscussEntity, RoadDiscussDTO> {
    void discussRoadCons(RoadDiscussDTO dto, String token);

    List<RoadDiscussEntity> getDiscussList(Long consId);
}