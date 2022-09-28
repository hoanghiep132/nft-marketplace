import {WalletType} from "../../../constants/common";


export interface SendMessageRequest{
    channelId: string,
    content: string,
}

export interface UserModel{
    id:string,
    username: string,
    fullName: string,
    avatar: string,
    status: string
}

export interface LoginResponse{
    accessToken: string,
    walletAddress: string,
    walletType: WalletType
}
