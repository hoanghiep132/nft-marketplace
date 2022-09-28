import {GET, POST} from "../../../services";
import {
    convertToTokenValue, getAuctionContract, getAuctionContractAddress,
    getCollectionContract,
    getDefaultCollectionContract, getMarketContractAddress, getMpecTokenContract,
    getNftMarketplaceContract
} from "../../../components/ConnectContract";
import {OWNER_ADDRESS} from "../../../constants/common";

export const createItem = (param?: any): Promise<any> => {
    return POST('api-svc/item/create', param);
};

export const getOwnCollectionList = (): Promise<any> => {
    return GET('api-svc/collection/owner/list');
}

export const getItemDetail = (uuid: string, token?: any): Promise<any> => {
    if(token == undefined){
        return GET(`api-svc/public/item/${uuid}`);
    }else {
        return GET(`api-svc/item/detail/${uuid}`);
    }
}


export const buyItem = (param: any): Promise<any> => {
    return POST('api-svc/item/buy', param);
}

export const likeItem = (itemUuid: string): Promise<any> => {
    return GET(`api-svc/item/like/${itemUuid}`);
}

export const unlikeItem = (itemUuid: string): Promise<any> => {
    return GET(`api-svc/item/unlike/${itemUuid}`);
}

export const listingItem = (param: any): Promise<any> => {
    return POST('api-svc/item/listing', param);
}

export const cancelListingItem = (param: any): Promise<any> => {
    return POST('api-svc/item/cancel-listing', param);
}

export const createAuctionItem = (param: any): Promise<any> => {
    return POST('api-svc/auction/create', param);
}

export const cancelAuctionItem = (param: any): Promise<any> => {
    return POST('api-svc/auction/cancel', param);
}

export const bidAuctionItem = (param: any): Promise<any> => {
    return POST('api-svc/auction/bid', param);
}

export const createDefaultItemContract = async (tokenUri: string): Promise<any> => {
    const contractRs = await getDefaultCollectionContract();
    return contractRs.contract.methods.mint(tokenUri).send({from: contractRs.account});
}

export const createItemContract = async (tokenUri: string, contractAddress: string): Promise<any> => {
    const contractRs = await getCollectionContract(contractAddress);
    return contractRs.contract.methods.mint(tokenUri).send({from: contractRs.account});
}

export const approveItemToMarket = async (tokenId: number, collectionContractAddress: string): Promise<any> => {
    const contractRs = await getCollectionContract(collectionContractAddress);
    const marketContractAddress = getMarketContractAddress();
    return contractRs.contract.methods.approve(marketContractAddress, tokenId).send({from: contractRs.account});
}

export const listItemToMarket = async (tokenId: number, collectionContractAddress: string, price: number): Promise<any> => {
    const contractRs = await getNftMarketplaceContract();
    price = await convertToTokenValue(price);
    return contractRs.contract.methods.listItem(collectionContractAddress, tokenId, price).send({from: contractRs.account});
}

export const cancelListItemToMarket = async (itemMarketId: number, collectionContractAddress: string): Promise<any> => {
    console.log(itemMarketId)
    console.log(collectionContractAddress)
    const contractRs = await getNftMarketplaceContract();
    return contractRs.contract.methods.cancelSelling(collectionContractAddress, itemMarketId).send({from: contractRs.account});
}

export const buyItemContract= async (itemMarketId: number): Promise<any> => {
    const contractRs = await getNftMarketplaceContract();
    return contractRs.contract.methods.buyItem(itemMarketId).send({from: contractRs.account});
}

export const approveMpecToken = async (price: number): Promise<any> => {
    const contractRs = await getMpecTokenContract();
    price = await convertToTokenValue(price);
    const marketContractAddress = getMarketContractAddress();
    return contractRs.contract.methods.approve(marketContractAddress, price).send({from: contractRs.account});
    // return contractRs.contract.methods.transfer("0xF8D2E46A49EE99F9c3C3980076dEb0878Cd8c4a2", price).send({from: contractRs.account});
}

export const approveMpecTokenForAuction = async (price: number): Promise<any> => {
    const contractRs = await getMpecTokenContract();
    price = await convertToTokenValue(price);
    const marketContractAddress = getAuctionContractAddress();
    return contractRs.contract.methods.approve(marketContractAddress, price).send({from: contractRs.account});
    // return contractRs.contract.methods.transfer("0xF8D2E46A49EE99F9c3C3980076dEb0878Cd8c4a2", price).send({from: contractRs.account});
}

export const sendMpecToken = async (price: number): Promise<any> => {
    const contractRs = await getMpecTokenContract();
    price = await convertToTokenValue(price);
    return contractRs.contract.methods.transfer("0xF8D2E46A49EE99F9c3C3980076dEb0878Cd8c4a2", price).send({from: contractRs.account});
}

export const transferItemContract= async (itemMarketId: number): Promise<any> => {
    const contractRs = await getNftMarketplaceContract();
    return contractRs.contract.methods.transferNft(itemMarketId).send({from: contractRs.account});
}

export const transferTokenContract= async (itemMarketId: number): Promise<any> => {
    const contractRs = await getNftMarketplaceContract();
    return contractRs.contract.methods.transferToken(itemMarketId).send({from: contractRs.account});
}

export const approveItemToAuction = async (tokenId: number, collectionContractAddress: string): Promise<any> => {
    const contractRs = await getCollectionContract(collectionContractAddress);
    const auctionContractAddress = getAuctionContractAddress();
    return contractRs.contract.methods.approve(auctionContractAddress, tokenId).send({from: contractRs.account});
}

export const startAuctionContract = async (param: any): Promise<any> => {
    const contractRs = await getAuctionContract();
    const price = await convertToTokenValue(param.startPrice);
    return contractRs.contract.methods.createAuction(
        param.collectionContractAddress,
        param.tokenId,
        param.startTime,
        price,
        param.endTime,
    ).send({from: contractRs.account});
}

export const bidAuctionContract = async (param: any) : Promise<any> => {
    const contractRs = await getAuctionContract();
    const price = await convertToTokenValue(param.value);
    return contractRs.contract.methods.bid(
        param.auctionId,
        price,
    ).send({from: contractRs.account});
}

export const cancelAuctionContract = async (auctionId: any) : Promise<any> => {
    const contractRs = await getAuctionContract();
    return contractRs.contract.methods.cancelAuction(auctionId).send({from: contractRs.account});
}

export const checkOwner = async (contractAddress: string, tokenId: number): Promise<any> => {
    const contractRs = await getCollectionContract(contractAddress);
    return contractRs.contract.methods.ownerOf(tokenId).call();
}