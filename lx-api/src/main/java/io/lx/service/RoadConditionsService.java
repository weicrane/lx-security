package io.lx.service;

import io.lx.common.exception.RenException;
import io.lx.common.service.BaseService;
import io.lx.dto.RoadConditionsDTO;
import io.lx.entity.RoadConditionsEntity;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

/**
 * 
 *
 * @author Mofeng laoniane@gmail.com
 * @since 1.0.0 2024-10-03
 */
public interface RoadConditionsService extends BaseService<RoadConditionsEntity> {
    List<RoadConditionsDTO> getRoadConsList();

    List<RoadConditionsDTO> getMyRoadConsList(String token);


    RoadConditionsDTO getRoadConsById(Long id);


    void submitRoadCon(RoadConditionsDTO dto, String token);

    /**
     * 上传图片
     * @param file
     * @return
     */
    String uploadRoadConsImage(MultipartFile file, String token) throws RenException;
}