package com.zi.servicemap.controller;

import com.zi.internalcommon.dto.ResponseResult;
import com.zi.servicemap.service.DirectionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/direction")
public class DirectionController {

    @Autowired
    private DirectionService directionService;

    @GetMapping("/driving")
    public ResponseResult driving(String depLongitude, String depLatitude, String destLongitude, String destLatitude){
        return directionService.driving(depLongitude, depLatitude, destLongitude, destLatitude);
    }
}
