import detectEthereumProvider from '@metamask/detect-provider';

import Web3 from 'web3';
import {NotificationError} from "../Notification/Notification";

const connectWallet = async () => {
    const provider: any = await detectEthereumProvider();
    if (!provider) {
        NotificationError('Cảnh báo','Please install MetaMask!');
        return;
    }
    if(!window.ethereum){
        NotificationError('Cảnh báo','Error!');
        return;
    }
    await provider.request({ method: 'eth_requestAccounts' });
    const web3 = new Web3(provider);
}
