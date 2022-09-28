package com.hiepnh.nftmarket.gateway.http;

import com.google.common.base.Strings;
import com.hiepnh.nftmarket.gateway.common.Const;
import com.hiepnh.nftmarket.gateway.common.ErrorCodeDefs;
import com.hiepnh.nftmarket.gateway.entities.SessionEntity;
import com.hiepnh.nftmarket.gateway.exception.SessionException;
import com.hiepnh.nftmarket.gateway.jwt.AuthResponse;
import com.hiepnh.nftmarket.gateway.repo.SessionRepository;
import io.jsonwebtoken.*;
import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.server.reactive.ServerHttpRequest;

import java.nio.charset.StandardCharsets;
import java.util.List;
import java.util.Objects;

public class HeaderEnhanceFilter {

    private final Logger logger = LoggerFactory.getLogger(this.getClass());
    public static final String USER_ID_IN_HEADER = "X-USER-ID";
    public static final String USER_ID_ROLE_HEADER = "X-USER-ROLE";
    public static final String PERMISSION_HEADER = "X-PERMISSION";

    public AuthResponse doFilter(ServerHttpRequest request, SessionRepository sessionRepository) {

        String authorization = extractHeaderToken(request);
        AuthResponse response = new AuthResponse(authorization);

        try {

            if (Strings.isNullOrEmpty(authorization)) {
                logger.info("token authorization: {} is null", authorization);
                response.setResult(ErrorCodeDefs.INVALID_JWT_TOKEN, "Token invalid");
                return response;
            }

            if (!isJwtBearerToken(authorization)) {
                logger.info("token authorization: {} !isJwtBearerToken", authorization);
                response.setResult(ErrorCodeDefs.INVALID_JWT_TOKEN, "Token invalid");
                return response;
            }

            logger.info("[check-token] token {}  path {}", authorization, request.getURI().getPath());
            response = validateToken(authorization);

            if (response.getCode() != ErrorCodeDefs.ERROR_CODE_OK) {
                return response;
            }

            SessionEntity entity = null;
            try {
                entity = sessionRepository.getSession(authorization);
            } catch (SessionException e) {
                logger.error("Ex: ", e);
            }

            if (entity == null) {
                logger.info("token authorization: {} can not found user", authorization);
                response.setResult(ErrorCodeDefs.ERROR_CODE_ACCOUNT_NOT_EXISTED, "Tài khoản chưa được đăng nhập, vui lòng đăng nhập lại");
                return response;
            }

            request.mutate()
                    .header(USER_ID_IN_HEADER, entity.getWalletAddress())
                    .build();

            return response;
        } catch (Throwable ex) {
            logger.error("Ex: ", ex);
            response.setResult(ErrorCodeDefs.ERROR_CODE_SYSTEM_BUSY, "System busy");
            return response;
        }

    }

    private String buildPermission(List<String> permission) {
        StringBuilder sb = new StringBuilder();
        if (permission != null && !permission.isEmpty()) {
            for (String p : permission) {
                if (p.length() > 0)
                    sb.append(";");
                sb.append(p);
            }
        }
        return sb.toString();
    }

    private boolean isJwtBearerToken(String token) {
        return StringUtils.countMatches(token, ".") == 2;
    }

    public AuthResponse validateToken(String authToken) {
        AuthResponse response = new AuthResponse(authToken);
        try {
            Claims claims = Jwts.parser()
                    .setSigningKey(Const.SECRET.getBytes(StandardCharsets.UTF_8))
                    .parseClaimsJws(authToken)
                    .getBody();
            response.setUser(claims.getSubject());
            response.setKey((String) claims.get("key"));
            response.setSuccess();
        } catch (SignatureException ex) {
            logger.error("[Token] Invalid signature: ", ex);
            response.setResult(ErrorCodeDefs.INVALID_JWT_SIGNATURE, "Invalid signature token");
        } catch (MalformedJwtException ex) {
            logger.error("[Token] Invalid JWT token");
            response.setResult(ErrorCodeDefs.INVALID_JWT_TOKEN, "Invalid jwt signature");
        } catch (ExpiredJwtException ex) {
            logger.error("[Token] Expired JWT token");
            response.setResult(ErrorCodeDefs.ERR_CODE_TOKEN_TIME_OUT, "Token expire");
        } catch (UnsupportedJwtException ex) {
            logger.error("[Token] Unsupported JWT token");
            response.setResult(ErrorCodeDefs.UNSUPPORTED_JWT_TOKEN, "Unsupported token");
        } catch (IllegalArgumentException ex) {
            logger.error("[Token] JWT claims string is empty.");
            response.setResult(ErrorCodeDefs.JWT_CLAIMS_EMPTY, "JWT claims string is empty");
        } catch (Throwable ex) {
            logger.error("[Token] JWT claims string is empty.");
            response.setResult(ErrorCodeDefs.ERROR_CODE_FAILED, "JWT claims string is empty");
        }
        return response;
    }

    protected String extractHeaderToken(ServerHttpRequest request) {
        List<String> headers = request.getHeaders().get("Authorization");
        if (Objects.nonNull(headers) && headers.size() > 0) {
            return headers.get(0);
        }
        return null;
    }

}
