import {GET, POST} from "../../../services";
import {getCollectionFactoryContract} from "../../../components/ConnectContract";

export const createCollection = (param: any): Promise<any> => {
    return POST('api-svc/collection/create', param);
};

export const searchCollection = (param ?: any, token?: any): Promise<any> => {
    if(token == undefined){
        return GET('api-svc/public/collection/search', param);
    }else {
        return GET('api-svc/public/collection/search', param);
    }
}

export const searchItemInCollection = (param?:  any, token?: any): Promise<any> => {
    if(token == undefined){
        return GET(`api-svc/public/item/search`, param);
    }else {
        return GET(`api-svc/item/search`, param);
    }
}

export const getCollectionDetail = (uuid: string, param?:  any, token?: any): Promise<any> => {
    if(token == undefined){
        return GET(`api-svc/public/collection/detail/${uuid}`, param);
    }else {
        return GET(`api-svc/collection/detail/${uuid}`, param);
    }
}

export const getOwnerCollectionList = (param?:  any): Promise<any> => {
    return GET('api-svc/collection/owner', param);
}

export const getOwnerCollectionOptionList = (): Promise<any> => {
    return GET('api-svc/collection/owner/list');
}

export const createCollectionContract = async (name: string, symbol: string): Promise<any> => {
    const contractRs = await getCollectionFactoryContract();
    return contractRs.contract.methods.create(name, symbol).send({from: contractRs.account});
}
