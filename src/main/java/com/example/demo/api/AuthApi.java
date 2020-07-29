package com.example.demo.api;

import com.example.demo.entity.AuthTokenRequest;
import com.example.demo.enums.PropertyKey;
import com.google.gson.Gson;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.http.*;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponents;
import org.springframework.web.util.UriComponentsBuilder;
import org.thymeleaf.util.StringUtils;

import java.util.ArrayList;

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
    @ApiOperation(value = "1. 사용자 토큰 요청", notes = "1. 사용자 토큰 요청")
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
    @ApiOperation(value = "2. access token 정보 요청", notes = "2. access token 정보 요청")
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

    /**
     * 요청 'property_keys' 에 따른 사용자 정보 요청
     * property_keys 는 enum 타입으로 선택 제한 하도록 함
     *
     * @param accessToken
     * @param propertyKeys
     * @return
     */
    @ApiOperation(value = "3. 사용자 정보 요청", notes = "property_keys 값에 따른 사용자의 정보 요청")
    @GetMapping(value = "user")
    public ResponseEntity user(@RequestParam String accessToken, @RequestParam(required = false) PropertyKey[] propertyKeys) {

        RestTemplate restTemplate = new RestTemplateBuilder().build();

        ArrayList<String> properties = new ArrayList<>();

        // enum 타입을 string 으로 바꿔서 arraylist 에 저장
        for (PropertyKey p : propertyKeys) {
            properties.add(p.value());
        }

        // 헤더에 accessToken 담기 (Admin 키도 가능)
        HttpHeaders headers = new HttpHeaders();
        headers.setBearerAuth(accessToken);

        // 요청 uri 생성
        UriComponents builder = UriComponentsBuilder.newInstance()
                .scheme("https").host("kapi.kakao.com")
                .path("v2/user/me")
                .queryParam("property_keys", new Gson().toJson(properties))
                .build();

        log.info("Request URI: {}", builder.toUriString());

        HttpEntity request = new HttpEntity(headers);

        return restTemplate.exchange(
                builder.toUriString(),
                HttpMethod.GET,
                request,
                String.class
        );
    }

}
