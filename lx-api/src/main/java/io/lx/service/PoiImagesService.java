package io.lx.service;

import io.lx.common.service.CrudService;
import io.lx.dto.PoiImagesDTO;
import io.lx.dto.RoutesGuidesDTO;
import io.lx.dto.SelfDrivingsDTO;
import io.lx.dto.UserMembershipsDTO;
import io.lx.entity.PoiImagesEntity;
import io.lx.entity.RoutesGuidesEntity;
import io.lx.entity.SelfDrivingsEntity;
import io.lx.entity.UserMembershipsEntity;

/**
 * 
 *
 * @author Mofeng laoniane@gmail.com
 * @since 1.0.0 2024-10-10
 */
public interface PoiImagesService extends CrudService<PoiImagesEntity, PoiImagesDTO> {

    /**
     *
     *
     * @author Mofeng laoniane@gmail.com
     * @since 1.0.0 2024-10-19
     */
    interface TbRoutesGuidesService extends CrudService<RoutesGuidesEntity, RoutesGuidesDTO> {

    }

    /**
     *
     *
     * @author Mofeng laoniane@gmail.com
     * @since 1.0.0 2024-10-19
     */
    interface TbSelfDrivingsService extends CrudService<SelfDrivingsEntity, SelfDrivingsDTO> {

    }

    /**
     *
     *
     * @author Mofeng laoniane@gmail.com
     * @since 1.0.0 2024-10-19
     */
    interface TbUserMembershipsService extends CrudService<UserMembershipsEntity, UserMembershipsDTO> {

    }
}