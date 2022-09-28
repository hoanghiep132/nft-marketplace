import {GET, POST} from "../../../services";

export const getUserInfo = (): Promise<any> => {
    return GET('account-svc/user/detail');
}

export const getAccountInfo = (address: string): Promise<any> => {
    return GET(`api-svc/public/user/info/${address}`);
}

export const updateUserInfo = (param: any): Promise<any> => {
    return POST('account-svc/user/update', param);
}