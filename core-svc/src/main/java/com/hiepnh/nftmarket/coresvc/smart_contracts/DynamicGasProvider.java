package com.hiepnh.nftmarket.coresvc.smart_contracts;

import lombok.extern.slf4j.Slf4j;
import org.web3j.protocol.Web3j;
import org.web3j.protocol.core.methods.request.Transaction;
import org.web3j.protocol.core.methods.response.EthEstimateGas;
import org.web3j.tx.gas.DefaultGasProvider;

import java.io.IOException;
import java.math.BigInteger;

@Slf4j
public class DynamicGasProvider extends DefaultGasProvider {
    Web3j web3j;

    public DynamicGasProvider(Web3j web3j) {
        super();
        this.web3j = web3j;
    }

    public BigInteger getRealtimeGasPrice() throws IOException {
        return web3j.ethGasPrice().send().getGasPrice();
    }

    public BigInteger getEstimatedGasLimit(String from, String to, BigInteger nonce, BigInteger value, String data) throws IOException {
        EthEstimateGas response = web3j.ethEstimateGas(new Transaction(
                from, nonce, BigInteger.ZERO, BigInteger.ZERO, to, value, data
        )).send();

        BigInteger result;
        if (response.getResult() == null) {
            log.error(response.getError().getMessage());
            return BigInteger.ZERO;
        }
        try {
            result = response.getAmountUsed();
        } catch (Exception exception) {
            log.warn(response.getError().getMessage());
            log.error(exception.toString());
            throw exception;
        }

        return result;
    }
}
