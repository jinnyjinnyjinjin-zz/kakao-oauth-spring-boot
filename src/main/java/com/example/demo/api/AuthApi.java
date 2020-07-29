package com.example.demo.api;

import com.example.demo.entity.AuthTokenRequest;
import io.swagger.annotations.ApiModelProperty;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.http.*;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponents;
import org.springframework.web.util.UriComponentsBuilder;
import org.thymeleaf.util.StringUtils;

import javax.servlet.http.HttpServletRequest;

@Slf4j
@RestController
@RequestMapping("api/v1/oauth")
public class AuthApi {

    /**
     * 사용자 토큰 요청
     *
     * @param authTokenRequest
     * @return
     */
    @ApiOperation(value = "사용자 토큰 요청", notes = "사용자 토큰 요청")
    @PostMapping(value = "token", produces = "application/json")
    public ResponseEntity token(@RequestBody AuthTokenRequest authTokenRequest) {
        RestTemplate restTemplate = new RestTemplateBuilder().build();

        if (StringUtils.isEmpty(authTokenRequest.getClientId()) || StringUtils.isEmpty(authTokenRequest.getRedirectUri())
        || StringUtils.isEmpty(authTokenRequest.getCode())) {
            return ResponseEntity.badRequest().body("필수 파라미터 불충족");
        }

        String clientSecret = "";
        if (StringUtils.isEmpty(authTokenRequest.getClientSecret())) clientSecret = authTokenRequest.getClientSecret();

        UriComponents builder = UriComponentsBuilder.newInstance()
                .scheme("https").host("kauth.kakao.com")
                .path("/oauth/token")
                .queryParam("grant_type", "authorization_code")
                .queryParam("code", authTokenRequest.getCode())
                .queryParam("redirect_uri", authTokenRequest.getRedirectUri())
                .queryParam("client_id", authTokenRequest.getClientId())
                .queryParam("client_secret", clientSecret)
                .build();

        String response = restTemplate.postForObject(builder.toUriString(), authTokenRequest, String.class);
        return ResponseEntity.ok().body(response);

    }

    /**
     * access token 정보 요청
     *
     * @param accessToken   사용자 토큰 요청에서 받은 access_token
     * @return
     */
    @ApiOperation(value = "access token 정보 요청", notes = "access token 정보 요청")
    @GetMapping(value = "access_token_info", produces = "application/text")
    public ResponseEntity accessTokenInfo(@RequestParam String accessToken) {

        RestTemplate restTemplate = new RestTemplateBuilder().build();

        UriComponents builder = UriComponentsBuilder.newInstance()
                .scheme("https").host("kapi.kakao.com")
                .path("v1/user/access_token_info")
                .build();

        HttpHeaders headers = new HttpHeaders();
        headers.setBearerAuth(accessToken);
        headers.setContentType(MediaType.APPLICATION_FORM_URLENCODED);

        HttpEntity request = new HttpEntity(headers);

        return restTemplate.exchange(
                builder.toUriString(),
                HttpMethod.GET,
                request,
                String.class
        );

    }

}
