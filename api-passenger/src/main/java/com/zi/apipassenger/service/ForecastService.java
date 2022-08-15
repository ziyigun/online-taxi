package com.zi.apipassenger.service;

import com.zi.internalcommon.dto.ResponseResult;
import com.zi.internalcommon.response.ForecastPriceResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Service
@Slf4j
public class ForecastService {

    /**
     * 根据出发地和目的地计算预估价格
     * @param depLongitude
     * @param depLatitude
     * @param destLongitude
     * @param destLatitude
     * @return
     */
    public ResponseResult forecastPrice(String depLongitude, String depLatitude, String destLongitude, String destLatitude){

        log.info("出发地经度：" + depLongitude);
        log.info("出发地维度：" + depLatitude);
        log.info("目的地经度：" + destLongitude);
        log.info("目的地维度：" + destLatitude);

        ForecastPriceResponse forecastPriceResponse = new ForecastPriceResponse();
        forecastPriceResponse.setPrice(12.34);
        return ResponseResult.success(forecastPriceResponse);
    }
}
