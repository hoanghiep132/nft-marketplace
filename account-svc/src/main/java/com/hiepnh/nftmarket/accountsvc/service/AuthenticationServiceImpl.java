package com.hiepnh.nftmarket.accountsvc.service;

import com.hiepnh.nftmarket.accountsvc.common.Common;
import com.hiepnh.nftmarket.accountsvc.common.EnumCommon;
import com.hiepnh.nftmarket.accountsvc.domain.helper.AppUtils;
import com.hiepnh.nftmarket.accountsvc.domain.request.LoginRequest;
import com.hiepnh.nftmarket.accountsvc.domain.request.LogoutRequest;
import com.hiepnh.nftmarket.accountsvc.domain.response.BaseResponse;
import com.hiepnh.nftmarket.accountsvc.domain.response.GetSingleItemResponse;
import com.hiepnh.nftmarket.accountsvc.domain.response.LoginResponse;
import com.hiepnh.nftmarket.accountsvc.entities.SessionEntity;
import com.hiepnh.nftmarket.accountsvc.entities.UserEntity;
import com.hiepnh.nftmarket.accountsvc.repo.SessionRepository;
import com.hiepnh.nftmarket.accountsvc.repo.UserRepository;
import com.hiepnh.nftmarket.accountsvc.utils.SignUtil;
import com.hiepnh.nftmarket.accountsvc.utils.TxGeneratorUtils;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import org.springframework.stereotype.Service;

import java.nio.charset.StandardCharsets;
import java.util.Date;
import java.util.Map;
import java.util.Optional;
import java.util.concurrent.ConcurrentHashMap;

@Service
public class AuthenticationServiceImpl extends BaseService implements AuthenticationService {

    private final SessionRepository sessionRepository;

    private final UserRepository userRepository;

    private final UserService userService;

    private final Map<String, Long> addressNonceMap = new ConcurrentHashMap<>();

    public AuthenticationServiceImpl(SessionRepository sessionRepository, UserRepository userRepository, UserService userService) {
        this.sessionRepository = sessionRepository;
        this.userRepository = userRepository;
        this.userService = userService;
    }

    @Override
    public GetSingleItemResponse<String> loginRequest(String walletAddress) {
        GetSingleItemResponse<String> response = new GetSingleItemResponse<>();

        Long nonce = TxGeneratorUtils.generate();
        addressNonceMap.put(walletAddress, nonce);

//        Optional<UserEntity> userEntityOptional = userRepository.findByWalletAddress(walletAddress);
//        UserEntity userEntity;
//        if(userEntityOptional.isPresent()){
//            userEntity = userEntityOptional.get();
//            userEntity.setNonce(nonce);
//            userRepository.save(userEntity);
//        }else {
//            userService.createNewUser(walletAddress, EnumCommon.WalletType.META_MASK, nonce);
//        }

        response.setItem(String.valueOf(nonce));
        response.setSuccess();
        return response;
    }

    @Override
    public LoginResponse login(LoginRequest request) {
        LoginResponse response = new LoginResponse();

        // verify nonce
        Long nonce = addressNonceMap.get(request.getWalletAddress());
        if(nonce == null || nonce <= 0){
            response.setResult(-1, "Yêu cầu kết nối không hợp lệ");
            return response;
        }
        addressNonceMap.remove(request.getWalletAddress());

//        Optional<UserEntity> userEntityOptional = userRepository.findByWalletAddress(request.getWalletAddress());
//        if(!userEntityOptional.isPresent()){
//            response.setResult(-1, "Yêu cầu kết nối không hợp lệ");
//            return response;
//        }
//
//        if(userEntityOptional.get().getNonce() == null){
//            response.setResult(-1, "Yêu cầu kết nối không tồn tại");
//            return response;
//        }

        try {
            String walletAddress = SignUtil.getAddressUsedToSignHashedMessage(request.getSignature(), nonce.toString());

            if(!request.getWalletAddress().equalsIgnoreCase(walletAddress)){
                response.setResult(-1, "Yêu cầu kết nối không hợp lệ");
                return response;
            }
        }catch (Exception ex){
            logger.error("Connect wallet error");
            response.setResult(-1, "Yêu cầu kết nối không hợp lệ");
            return response;
        }

        Optional<UserEntity> userEntityOptional = userRepository.findByWalletAddress(request.getWalletAddress());
        UserEntity userEntity;
        if(userEntityOptional.isPresent()){
            userEntity = userEntityOptional.get();
            userEntity.setNonce(nonce);
            userRepository.save(userEntity);
        }else {
            userService.createNewUser(request.getWalletAddress(), EnumCommon.WalletType.META_MASK, nonce);
        }


        String token = generateToken(request.getWalletAddress());
        sessionRepository.deleteByUserId(request.getWalletAddress());

        SessionEntity sessionEntity = SessionEntity.builder()
                .token(token)
                .walletAddress(request.getWalletAddress())
                .walletType(EnumCommon.WalletType.META_MASK)
                .build();
        sessionRepository.addSession(sessionEntity);

        response.setAccessToken(token);
        response.setWalletAddress(request.getWalletAddress());
        response.setWalletType(EnumCommon.WalletType.META_MASK);

        response.setSuccess();
        return response;
    }

    private String generateToken(String username) {
        Claims claims = Jwts.claims().setSubject(username);
        Date date = new Date();
        return Jwts.builder()
                .setClaims(claims)
                .signWith(SignatureAlgorithm.HS256, Common.SECRET.getBytes(StandardCharsets.UTF_8))
                .setIssuedAt(date)
                .compact();
    }

    @Override
    public BaseResponse logout(LogoutRequest request) {
        sessionRepository.deleteByUserId(request.getWalletAddress());
        return new BaseResponse(0, "Logout success");
    }

    private static class ConnectWalletRequest {
        private Long time;
        private EnumCommon.WalletType type;

        public Long getTime() {
            return time;
        }

        public void setTime(Long time) {
            this.time = time;
        }

        public EnumCommon.WalletType getType() {
            return type;
        }

        public void setType(EnumCommon.WalletType type) {
            this.type = type;
        }
    }
}
