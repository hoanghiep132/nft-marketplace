package com.hiepnh.nftmarket.coresvc.smart_contracts;

import org.web3j.abi.FunctionEncoder;
import org.web3j.abi.datatypes.Function;
import org.web3j.crypto.Credentials;
import org.web3j.protocol.Web3j;
import org.web3j.protocol.core.RemoteFunctionCall;
import org.web3j.protocol.core.methods.response.TransactionReceipt;
import org.web3j.protocol.exceptions.TransactionException;
import org.web3j.tx.TransactionManager;
import org.web3j.utils.RevertReasonExtractor;

import java.io.IOException;
import java.math.BigInteger;

public class AuctionDynamicGas extends NftAuction {

    protected AuctionDynamicGas(String contractAddress, Web3j web3j, Credentials credentials, DynamicGasProvider contractGasProvider) {
        super(contractAddress, web3j, credentials, contractGasProvider);
    }

    protected AuctionDynamicGas(String contractAddress, Web3j web3j, TransactionManager transactionManager, DynamicGasProvider contractGasProvider) {
        super(contractAddress, web3j, transactionManager, contractGasProvider);
    }

    public static AuctionDynamicGas load(String contractAddress, Web3j web3j, Credentials credentials, DynamicGasProvider contractGasProvider) {
        return new AuctionDynamicGas(contractAddress, web3j, credentials, contractGasProvider);
    }

    public static AuctionDynamicGas load(String contractAddress, Web3j web3j, TransactionManager transactionManager, DynamicGasProvider contractGasProvider) {
        return new AuctionDynamicGas(contractAddress, web3j, transactionManager, contractGasProvider);
    }

    @Override
    protected RemoteFunctionCall<TransactionReceipt> executeRemoteCallTransaction(Function function) {
        return new RemoteFunctionCall<>(function, () -> this.executeTransactionDynamicGas(FunctionEncoder.encode(function), BigInteger.ZERO));
    }

    protected RemoteFunctionCall<TransactionReceipt> executeRemoteCallTransactionWeiValue(Function function, BigInteger weiValue) {
        return new RemoteFunctionCall<>(function, () -> this.executeTransactionDynamicGas(FunctionEncoder.encode(function), weiValue));
    }

    TransactionReceipt executeTransactionDynamicGas(String data, BigInteger weiValue) throws TransactionException, IOException {
        DynamicGasProvider gasProvider = (DynamicGasProvider) this.gasProvider;

        BigInteger gasPrice = gasProvider.getRealtimeGasPrice();
        BigInteger gasLimit = gasProvider.getEstimatedGasLimit(
                this.transactionManager.getFromAddress(),
                this.contractAddress,
                BigInteger.ZERO,
                weiValue,
                data
        );
        TransactionReceipt receipt = this.send(this.contractAddress, data, weiValue, gasPrice, gasLimit, false);
        if (!receipt.isStatusOK()) {
            throw new TransactionException(String.format("Transaction %s has failed with status: %s. Gas used: %s. Revert reason: '%s'.", receipt.getTransactionHash(), receipt.getStatus(), receipt.getGasUsedRaw() != null ? receipt.getGasUsed().toString() : "unknown", RevertReasonExtractor.extractRevertReason(receipt, data, this.web3j, true)), receipt);
        } else {
            return receipt;
        }
    }

}
