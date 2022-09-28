package com.hiepnh.nftmarket.accountsvc.utils;

import org.web3j.crypto.Keys;
import org.web3j.crypto.Sign;
import org.web3j.utils.Numeric;

import java.nio.charset.StandardCharsets;
import java.security.SignatureException;

public class SignUtil {

    public static final String GETH_SIGN_PREFIX = "\u0019Ethereum Signed Message:\n";

    /**
     * This method is expecting the signed message to be a hash of the original message. The length of the message is
     * then hardcoded to 32. Also, this might only work for messages signed by geth, not sure if other clients
     * add the prefix to the signed message.
     *
     * @param signedHash
     * @param originalMessageHashInHex
     * @return
     * @throws SignatureException
     */
    public static String getAddressUsedToSignHashedMessage(String signedHash, String originalMessageHashInHex) throws SignatureException {

        String r = signedHash.substring(0, 66);
        String s = "0x" + signedHash.substring(66, 130);
        String v = "0x" + signedHash.substring(130, 132);

        int iv = Integer.decode(v);
        if (iv == 0 || iv == 1) {
            v = "0x" + Integer.toHexString(iv + 27);
        }

        byte[] messageHashBytes = originalMessageHashInHex.getBytes(StandardCharsets.UTF_8);
        byte[] messageLengthBytes = String.valueOf(originalMessageHashInHex.length()).getBytes(StandardCharsets.UTF_8);

        byte[] msgBytes = new byte[GETH_SIGN_PREFIX.getBytes().length + messageLengthBytes.length + messageHashBytes.length];
        byte[] prefixBytes = GETH_SIGN_PREFIX.getBytes();

        System.arraycopy(prefixBytes, 0, msgBytes, 0, prefixBytes.length);
        System.arraycopy(messageLengthBytes, 0, msgBytes, prefixBytes.length, messageLengthBytes.length);
        System.arraycopy(messageHashBytes, 0, msgBytes, prefixBytes.length + messageLengthBytes.length, messageHashBytes.length);

        String pubkey = Sign.signedMessageToKey(msgBytes,
                        new Sign.SignatureData(Numeric.hexStringToByteArray(v)[0],
                                Numeric.hexStringToByteArray(r),
                                Numeric.hexStringToByteArray(s)))
                .toString(16);
        return Keys.toChecksumAddress("0x" + Keys.getAddress(pubkey));
    }

    public static String getAddressUsedToSignHashedMessageMobi(String signedHash, String originalMessageHashInHex) throws SignatureException {
        String r = signedHash.substring(0, 64);
        String s = signedHash.substring(64, 128);
        String v = signedHash.substring(128, 130);

        byte[] messageHashBytes = originalMessageHashInHex.getBytes();

        String pubkey = Sign.signedMessageToKey(messageHashBytes,
                        new Sign.SignatureData(
                                Numeric.hexStringToByteArray(v),
                                Numeric.hexStringToByteArray(r),
                                Numeric.hexStringToByteArray(s)))
                .toString(16);
        return Keys.toChecksumAddress("0x" + Keys.getAddress(pubkey));
    }
}
