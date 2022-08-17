package com.zi.servicemap.remote;

import com.zi.internalcommon.constant.AmpConfigConstants;
import com.zi.internalcommon.dto.ResponseResult;
import com.zi.internalcommon.response.DirectionResponse;
import lombok.extern.slf4j.Slf4j;
import net.sf.json.JSON;
import net.sf.json.JSONArray;
import net.sf.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

@Service
@Slf4j
public class MapDirectionClient {

    @Value("${amp.key}")
    private String ampKey;

    @Autowired
    private RestTemplate restTemplate;

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
        //调用高德接口
        ResponseEntity<String> directionEntity = restTemplate.getForEntity(urlBuild.toString(), String.class);
        log.info("高德地图路径规划，返回信息：" + directionEntity);
        String directionString = directionEntity.getBody();
        DirectionResponse directionResponse = parseDirectionEntity(directionString);
        return directionResponse;
    }

    private DirectionResponse parseDirectionEntity(String directionString){
        DirectionResponse directionResponse = null;
        try{
            //最外层
            JSONObject result = JSONObject.fromObject(directionString);
            if(result.has(AmpConfigConstants.STATUS)){
                int status = result.getInt(AmpConfigConstants.STATUS);
                //返回结果正确
                if(status == 1){
                    directionResponse = new DirectionResponse();
                    if(result.has(AmpConfigConstants.ROUTE)){
                        JSONObject routeObject = result.getJSONObject(AmpConfigConstants.ROUTE);
                        JSONArray pathsArray = routeObject.getJSONArray(AmpConfigConstants.PATHS);
                        //pathsArray返回的是一个数组，我们默选择第一个速度最快的
                        JSONObject pathObject = pathsArray.getJSONObject(0);
                        if(pathObject.has(AmpConfigConstants.DISTANCE)){
                            int distance = pathObject.getInt(AmpConfigConstants.DISTANCE);
                            directionResponse.setDistance(distance);
                        }
                        if(pathObject.has(AmpConfigConstants.DURATION)){
                            int duration = pathObject.getInt(AmpConfigConstants.DURATION);
                            directionResponse.setDuration(duration);
                        }
                    }
                }
            }
        } catch (Exception e){
            e.printStackTrace();;
        }
        return directionResponse;
    }
}
