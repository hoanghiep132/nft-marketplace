import {GET} from "../../../services";

export const getFavoriteList = (param: any): Promise<any> => {
    return GET('api-svc/item/favorites', param);
}

export const getOwnerItemList = (param: any): Promise<any> => {
    return GET('api-svc/item/owner', param);
}