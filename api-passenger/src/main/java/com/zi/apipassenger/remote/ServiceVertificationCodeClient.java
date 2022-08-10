package com.zi.apipassenger.remote;

import com.zi.internalcommon.dto.ResponseResult;
import com.zi.internalcommon.response.NumberCodeResponse;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.*;

@FeignClient("service-vertificationcode")
public interface ServiceVertificationCodeClient {

    @RequestMapping(method = RequestMethod.GET, value = "/numberCode/6")
    public ResponseResult<NumberCodeResponse> getNumberCode();
}
