import detectEthereumProvider from '@metamask/detect-provider';
import {NotificationError} from "../Notification/Notification";
import Web3 from 'web3';
import CollectionFactory from '../../contracts/NFTCollectionFactory.json';
import DefaultCollection from '../../contracts/NFTDefaultCollection.json';
import Collection from '../../contracts/NFTCollection.json';
import MpecToken from '../../contracts/MpecToken.json';
import Marketplace from '../../contracts/NFTMarketPlace.json';
import Auction from '../../contracts/NFTAcution.json';

const checkWallet = async (): Promise<any> => {
    const provider: any = await detectEthereumProvider();
    if (!provider) {
        NotificationError('Cảnh báo','Please install MetaMask!');
        return null;
    }
    if(!window.ethereum){
        NotificationError('Cảnh báo','Error!');
        return null;
    }
    return provider;
}

export const getCollectionFactoryContract = async (): Promise<any> => {
    const provider: any = await checkWallet();

    await provider.request({ method: 'eth_requestAccounts' });
    const web3 = new Web3(provider);

    const accountRs = await web3.eth.getAccounts();
    const account = accountRs[0];

    const abi: any = CollectionFactory.abi;
    const address: any = CollectionFactory.networks[97].address;
    // const address: any = "0x3b0215489Aad516Ff3B43e520Ba3F4b444B10C98";
    const contract =new web3.eth.Contract(abi, address);

    return {
        contract: contract,
        account: account
    };
}

export const getDefaultCollectionContract = async (): Promise<any> => {
    const provider: any = await checkWallet();

    await provider.request({ method: 'eth_requestAccounts' });
    const web3 = new Web3(provider);

    const accountRs = await web3.eth.getAccounts();
    const account = accountRs[0];

    const abi: any = DefaultCollection.abi;
    const address: any = CollectionFactory.networks[97].address;
    // const address: any = "0x40033D14e89612F44Fa587d38132000fa46D1e85";
    const contract =new web3.eth.Contract(abi, address);

    return {
        contract: contract,
        account: account
    };
}

export const getCollectionContract = async (contractAddress: string): Promise<any> => {
    const provider: any = await checkWallet();

    await provider.request({ method: 'eth_requestAccounts' });
    const web3 = new Web3(provider);

    const accountRs = await web3.eth.getAccounts();
    const account = accountRs[0];

    const abi: any = Collection.abi;
    // const address: any = CollectionFactory.networks[97].address;
    const contract =new web3.eth.Contract(abi, contractAddress);
    return {
        contract: contract,
        account: account
    };
}

export const getMpecTokenContract = async (): Promise<any> => {
    const provider: any = await checkWallet();

    await provider.request({ method: 'eth_requestAccounts' });
    const web3 = new Web3(provider);

    const accountRs = await web3.eth.getAccounts();
    const account = accountRs[0];

    const abi: any = MpecToken.abi;
    const address: any = MpecToken.networks[97].address;
    const contract =new web3.eth.Contract(abi, address);
    return {
        contract: contract,
        account: account
    };
}

export const getNftMarketplaceContract = async (): Promise<any> => {
    const provider: any = await checkWallet();

    await provider.request({ method: 'eth_requestAccounts' });
    const web3 = new Web3(provider);

    const accountRs = await web3.eth.getAccounts();
    const account = accountRs[0];

    const abi: any = Marketplace.abi;
    const address: any = Marketplace.networks[97].address;
    const contract =new web3.eth.Contract(abi, address);
    return {
        contract: contract,
        account: account
    };
}

export const getMarketContractAddress = (): any => {
    return Marketplace.networks[97].address;
}

export const convertToTokenValue = async (price: number): Promise<any> => {
    const provider: any = await checkWallet();
    const web3 = new Web3(provider);

    let decimals = web3.utils.toBN(18);
    let amount =web3.utils.toBN(price);

    return amount.mul(web3.utils.toBN(10).pow(decimals));
}


export const getAuctionContract = async (): Promise<any> => {
    const provider: any = await checkWallet();

    await provider.request({ method: 'eth_requestAccounts' });
    const web3 = new Web3(provider);

    const accountRs = await web3.eth.getAccounts();
    const account = accountRs[0];

    const abi: any = Auction.abi;
    const address: any = Auction.networks[97].address;
    const contract =new web3.eth.Contract(abi, address);
    return {
        contract: contract,
        account: account
    };
}

export const getAuctionContractAddress = (): any => {
    return Auction.networks[97].address;
}

