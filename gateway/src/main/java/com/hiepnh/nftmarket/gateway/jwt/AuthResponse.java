package com.hiepnh.nftmarket.gateway.jwt;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.hiepnh.nftmarket.gateway.common.ErrorCodeDefs;
import com.hiepnh.nftmarket.gateway.response.BaseResponse;
import lombok.Data;
import lombok.EqualsAndHashCode;

@EqualsAndHashCode(callSuper = true)
@Data
public class AuthResponse extends BaseResponse {

    @JsonIgnore
    private String user;

    @JsonIgnore
    private String key;

    public AuthResponse(String token) {
        code = ErrorCodeDefs.ERROR_CODE_FAILED;
    }

    public void setSuccess() {
        this.code = ErrorCodeDefs.ERROR_CODE_OK;
    }

    public String toJson() {
        ObjectMapper mapper = new ObjectMapper();
        try {
            return mapper.writeValueAsString(this);
        } catch (JsonProcessingException e) {
            e.printStackTrace();
        }
        return "UNKNOWN";
    }

}
