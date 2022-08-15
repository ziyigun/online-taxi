package com.zi.apipassenger.controller;

import com.zi.apipassenger.service.TokenService;
import com.zi.internalcommon.dto.ResponseResult;
import com.zi.internalcommon.response.TokenResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
public class TokenController {

    @Autowired
    private TokenService tokenService;

    @PostMapping("/token-refresh")
    public ResponseResult refreshToken(@RequestBody TokenResponse tokenResponse){
        String refreshTokenSrc = tokenResponse.getRefreshToken();
        return tokenService.refreshToken(refreshTokenSrc);
    }
}
