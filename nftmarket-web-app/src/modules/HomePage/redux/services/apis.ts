import {LoginResponse, SendMessageRequest} from "../models";
import {POST, GET} from "../../../../services";
import {ConnectRequest} from "../../../ConnectWallet/models/constant";
import {WalletType} from "../../../../constants/common";


export const connectRequest = (walletAddress?: string): Promise<any> => {
    return GET(`account-svc/auth/connect-request?walletAddress=${walletAddress}`);
};

export const connectWallet = (params?: ConnectRequest): Promise<LoginResponse> => {
    return POST('account-svc/auth/connect', params);
};
