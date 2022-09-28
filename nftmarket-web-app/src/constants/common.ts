import { EEndpointType, EHttpMethod } from '../models/common';
import { EPolices } from '../models/common'

export const bscTestnet = "https://data-seed-prebsc-1-s1.binance.org:8545";
export const TOKEN_KEY = 'auth-n-token';
export const META_MASK_DOWNLOAD_LINK = 'https://metamask.io/download/';
export const OWNER_ADDRESS = '0x116e461E233f9Ed8579A2659FA92E3b50D8B4737';

export const enum WalletType{
  META_MASK='META_MASK',
  PHANTOM='PHANTOM'
}

export const validateNormalString = (text: string): boolean => {
  if(/^[a-zA-Z0-9_.-]*$/.test(text)){
    return true;
  }else {
    return false;
  }
}

export const validatePath = (text: string): boolean => {
  const regex = /^[a-zA-Z0-9\/{}_-]*$/ig
  if(regex.test(text)){
    return true;
  }else {
    return false;
  }
}

export const validateUrl = (text: string): boolean => {
  if(/[-a-zA-Z0-9@:%._\+~#=]{1,256}\.[a-zA-Z0-9()]{1,6}\b([-a-zA-Z0-9()@:%_\+.~#?&//=]*)?/.test(text)){
    return true;
  }else {
    return false;
  }
}

export const validatePassword = (text: string): boolean => {
  if(/^(?=.*?[A-Z])(?=.*?[a-z])(?=.*?[0-9])(?=.*?[#?!@$%^&*-]).{9,18}$/.test(text)){
    return true;
  }else {
    return false;
  }
}

export const validateEmail = (text: string): boolean => {
  if (
    /^(([^<>()[\]\\.,;:\s@"]+(\.[^<>()[\]\\.,;:\s@"]+)*)|(".+"))@((\[[0-9]{1,3}\.[0-9]{1,3}\.[0-9]{1,3}\.[0-9]{1,3}\])|(([a-zA-Z\-0-9]+\.)+[a-zA-Z]{2,}))$/.test(text)
  ) {
    return true;
  } else {
    return false;
  }
}

export const validatePhoneNumber = (text: string): boolean => {
  if(/(03|05|07|08|09|01[2|6|8|9])+([0-9]{8})\b/.test(text)){
    return true;
  }else {
    return false;
  }
}



