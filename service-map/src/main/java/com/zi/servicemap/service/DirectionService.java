package com.zi.servicemap.service;

import com.zi.internalcommon.dto.ResponseResult;
import com.zi.internalcommon.response.DirectionResponse;
import org.springframework.stereotype.Service;

@Service
public class DirectionService {

    /**
     * 根据起点经纬度和终点经纬度获取距离（米）和时长（分钟）
     * @param depLongitude
     * @param depLatitude
     * @param destLongitude
     * @param destLatitude
     * @return
     */
    public ResponseResult driving(String depLongitude, String depLatitude, String destLongitude, String destLatitude){

        DirectionResponse directionResponse = new DirectionResponse();
        directionResponse.setDistance(132);
        directionResponse.setDuration(21);
        return ResponseResult.success(directionResponse);
    }
}
