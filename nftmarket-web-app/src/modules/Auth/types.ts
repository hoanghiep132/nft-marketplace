import {ResponseBase, ResponseBase2} from '../../models/common';
import {UserEntity} from "../../models/user";

export interface LoginInput {
  username: string;
  password: string;
}

export interface LoginResponse extends ResponseBase2 {
  user: UserEntity;
  token: string;
  username: string;
  role: number;
}

export interface TokenInfo {
  token: string;
  walletAddress: string;
  walletType: string;
}
