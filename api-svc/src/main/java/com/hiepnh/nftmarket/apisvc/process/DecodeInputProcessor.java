package com.hiepnh.nftmarket.apisvc.process;

import com.hiepnh.nftmarket.apisvc.domain.request.CreateCollectionRequest;
import com.hiepnh.nftmarket.apisvc.domain.request.CreateItemRequest;
import com.hiepnh.nftmarket.apisvc.domain.request.ListItemToMarketRequest;
import com.hiepnh.nftmarket.apisvc.domain.response.BaseResponse;
import org.apache.commons.codec.DecoderException;
import org.apache.commons.codec.binary.Hex;
import org.apache.commons.lang.ArrayUtils;
import org.springframework.stereotype.Component;

import java.math.BigInteger;
import java.nio.charset.StandardCharsets;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

@Component
public class DecodeInputProcessor {

    // uint 32 bytes
    // address 32 bytes

    public BaseResponse decodeCreateCollectionInput(String tnxHash, CreateCollectionRequest request) {
        String decodedInput = SenderManager.getInstance().getTransactionByHash(tnxHash);

        String value = decodedInput.substring(10);

        String textCountHex = value.substring(64, 128);
        int textCount = getIntFromHex(textCountHex).intValue() * 2;

        int a = textCount % 64;
        if (a != 0) {
            textCount += (64 - a);
        }

        String textValueHex = value.substring(128, 128 + textCount);
        try {
            byte[] bytes = Hex.decodeHex(textValueHex.toCharArray());
            byte[] newBytes = filterString(bytes);
            String textValue = new String(newBytes, StandardCharsets.UTF_8);
            textValue = textValue.replace("\b", "");

            String[] split = textValue.split("\f");
            if (split.length != 2) {
                return new BaseResponse(-1, "Decode Error");
            }
            String name = split[0];
            String symbol = split[1];

            if (request.getName().equals(name) && request.getSymbol().equals(symbol)) {
                return new BaseResponse(0, "");
            } else {
                return new BaseResponse(-1, "Decode Invalid Input");
            }

        } catch (DecoderException e) {
            return new BaseResponse(-1, "Decode Error");
        }

    }

    public BaseResponse decodeMinItemInput(String tnxHash, CreateItemRequest request) {
        String decodedInput = SenderManager.getInstance().getTransactionByHash(tnxHash);

        String value = decodedInput.substring(10);

        String uriCountHex = value.substring(64, 128);
        int uriCount = getIntFromHex(uriCountHex).intValue() * 2;

        int a = uriCount % 64;
        if (a != 0) {
            uriCount += (64 - a);
        }

        String uriValueHex = value.substring(128, 128 + uriCount);

        try {
            byte[] bytes = Hex.decodeHex(uriValueHex.toCharArray());
            byte[] newBytes = filterString(bytes);
            String uriValue = new String(newBytes, StandardCharsets.UTF_8);

            if (request.getImageUrl().equals(uriValue)) {
                return new BaseResponse(0, "");
            } else {
                return new BaseResponse(-1, "Decode Invalid Input");
            }

        } catch (DecoderException e) {
            return new BaseResponse(-1, "Decode Error");
        }
    }

    // uint
    public BaseResponse decodeBuyItemInput(String tnxHash, Long tokenIdRequest) {
        String decodedInput = SenderManager.getInstance().getTransactionByHash(tnxHash);

        String identify = decodedInput.substring(2, 10);

        String tokenIdHex = decodedInput.substring(10);
        if (tokenIdHex.length() != 64) {
            return new BaseResponse(-1, "Decode Error");
        }
        BigInteger tokenId = getIntFromHex(tokenIdHex);

        if(tokenIdRequest.equals((long) tokenId.intValue())){
            return new BaseResponse(0, "");
        } else {
            return new BaseResponse(-1, "Decode Invalid Input");
        }
    }

    // address, uint, uint
    public BaseResponse decodeListItemInput(String tnxHash, ListItemToMarketRequest request) {
        String decodedInput = SenderManager.getInstance().getTransactionByHash(tnxHash);

        String identify = decodedInput.substring(2, 10);

        String value = decodedInput.substring(10);

        String address = value.substring(0, 64);

        String tokenIdHex = value.substring(64, 128);
        BigInteger tokenId = new BigInteger(tokenIdHex, 16);

        String priceHex = value.substring(128, 192);
        BigInteger priceRaw = getIntFromHex(priceHex);
        BigInteger price = convertTokenValue(priceRaw);

        if(request.getItemMarketId().equals((long) price.intValue())
            && request.getPrice().equals((double) price.intValue())){
            return new BaseResponse(0, "");
        } else {
            return new BaseResponse(-1, "Decode Invalid Input");
        }
    }


    private byte[] filterString(byte[] bytes) {
        List<Byte> byteList = IntStream.range(0, bytes.length)
                .mapToObj(i -> bytes[i]).filter(e -> e != 0).collect(Collectors.toList());
        Byte[] newBytes = new Byte[byteList.size()];
        byteList.toArray(newBytes);
        return ArrayUtils.toPrimitive(newBytes);
    }

    private BigInteger getIntFromHex(String hexInput) {
        return new BigInteger(hexInput, 16);
    }

    private BigInteger convertTokenValue(BigInteger input) {
        return input.divide(new BigInteger("10").pow(18));
    }

    public static void main(String[] args) {
        new DecodeInputProcessor().decodeListItemInput("0x116130143b88b4916dafabecb0af417c947ce7584b21c3361e33c002c29378bd", null);
    }
}
