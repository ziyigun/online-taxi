package com.zi.apipassenger.controller;

import com.zi.apipassenger.service.ForecastService;
import com.zi.internalcommon.dto.ResponseResult;
import com.zi.internalcommon.request.ForecastPriceDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class ForecastPriceController {

    @Autowired
    private ForecastService forecastService;

    @PostMapping("/forecast-price")
    public ResponseResult forecastPrice(@RequestBody ForecastPriceDTO forecastPriceDTO){
        String depLongitude = forecastPriceDTO.getDepLongitude();//目的地经度
        String depLatitude = forecastPriceDTO.getDepLatitude();//目的地维度
        String destLongitude = forecastPriceDTO.getDestLongitude();
        String destLatitude = forecastPriceDTO.getDestLatitude();

        return forecastService.forecastPrice(depLongitude, depLatitude, destLongitude, destLatitude);
    }
}
