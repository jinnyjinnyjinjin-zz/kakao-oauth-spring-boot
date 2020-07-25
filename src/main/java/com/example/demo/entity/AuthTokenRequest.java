package com.example.demo.entity;

import com.google.gson.annotations.SerializedName;
import io.swagger.annotations.ApiModelProperty;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Builder
public class AuthTokenRequest {

    @SerializedName("client_id")
    private String clientId;

    @SerializedName("redirect_uri")
    private String redirectUri;

    private String code;

    @ApiModelProperty(notes = "Optional")
    @SerializedName("client_secret")
    private String clientSecret;
}
