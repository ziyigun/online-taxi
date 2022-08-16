package com.zi.servicemap.remote;

import com.zi.internalcommon.constant.AmpConfigConstants;
import com.zi.internalcommon.dto.ResponseResult;
import com.zi.internalcommon.response.DirectionResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

@Service
@Slf4j
public class MapDirectionClient {

    @Value("${amp.key}")
    private String ampKey;

    public DirectionResponse direction(String depLongitude, String depLatitude, String destLongitude, String destLatitude){
        //组装请求调用的url
        /**
         * e3fbc0da5dca2eda3799544233e92d93
         *
         */
        StringBuilder urlBuild = new StringBuilder();
        urlBuild.append(AmpConfigConstants.DIRECTION_URL);
        urlBuild.append("?");
        urlBuild.append("origin=" + depLongitude + "," + depLatitude);
        urlBuild.append("&");
        urlBuild.append("destination=" + destLongitude + "," + destLatitude);
        urlBuild.append("&");
        urlBuild.append("extensions=base");
        urlBuild.append("&");
        urlBuild.append("output=json");
        urlBuild.append("&");
        urlBuild.append("key=" + ampKey);

        log.info("urlBuild:" + urlBuild.toString() );
        return new DirectionResponse();
    }
}
