import React from "react";
import {Card, Col, Row, Tag} from "antd";
import detectEthereumProvider from "@metamask/detect-provider";
import {NotificationError} from "../../../components/Notification/Notification";
import Web3 from "web3";
import {META_MASK_DOWNLOAD_LINK, TOKEN_KEY} from "../../../constants/common";
import {connectRequest, connectWallet} from "../../HomePage/redux/services/apis";
import {ConnectRequest} from "../models/constant";
import MetaMaskImage from "../../../assets/images/wallet/MetaMask_Fox.svg.png";
import CoinBaseImage from "../../../assets/images/wallet/coinbase.png";
import PhantomImage from "../../../assets/images/wallet/phantom.jpeg";
import {TokenInfo} from "../../Auth/types";
import {RootState} from "../../../redux/reducers";
import {connect, ConnectedProps} from "react-redux";
import {login} from "../../Auth/redux/actions";

const mapState = (rootState: RootState) => ({});

const connector = connect(mapState, {login});

type PropsFromRedux = ConnectedProps<typeof connector>;

interface IProps extends PropsFromRedux {
}

const ConnectWalletPage = (props: IProps) => {

    const connectMetaMask = async (e: any) => {
        const provider: any = await detectEthereumProvider();
        if (!provider) {
            window.open(META_MASK_DOWNLOAD_LINK, "_blank")
            NotificationError('Cảnh báo','Please install MetaMask!');
            return;
        }
        if(!window.ethereum){
            NotificationError('Cảnh báo','Error!');
            return;
        }

        await provider.request({ method: 'eth_requestAccounts' });
        const web3 = new Web3(provider);
        const accounts = await web3.eth.getAccounts();
        const currAccount: string = accounts[0];

        const connectRequestRs: any = await connectRequest(currAccount);
        if(connectRequestRs.code != 0){
            NotificationError('Thất bại', connectRequestRs.message);
            return;
        }
        console.log("connectRequestRs :" + connectRequestRs.item);

        const signature = await web3.eth.personal.sign(connectRequestRs.item, currAccount, '');
        // const signature = await web3.eth.personal.sign('hello 123', currAccount, '');
        console.log("signature :" + JSON.stringify(signature));

        const connectParam: ConnectRequest = {
            signature: signature,
            walletAddress: currAccount
        }

        const connectRs: any = await  connectWallet(connectParam);
        if(connectRs.code != 0){
            NotificationError('Thất bại', connectRs.message);
            return;
        }

        const tokenInfo: TokenInfo = {
            token: connectRs.token,
            walletAddress: connectRs.walletAddress,
            walletType: connectRs.walletType,
        }
        props.login(tokenInfo);
        localStorage.setItem(TOKEN_KEY, JSON.stringify(tokenInfo));
    }


    return(
        <div style={{alignContent: 'center'}}>
            <br/>
            <br/>
            <br/>
            <br/>
            <h3>Bạn cần có ví điện tử để sử dụng dịch vụ trên marketplace</h3>
            <p>Kết nối tới một trong các loại ví dưới đây</p>

            <div style={{borderRadius: '10px'}}>
                <Card style={{margin: 'auto', width: '300px', cursor: 'pointer', borderRadius: '10px' }} size="small">
                    <Card.Grid style={{width: '300px'}} onClick={connectMetaMask}>
                        <Row align='middle' justify='start'>
                            <Col span={4}>
                                <img src={MetaMaskImage} style={{width: '30px', height: '30px', borderRadius: '5px'}}/>
                            </Col>
                            <Col span={16}>
                                <b style={{float: 'left'}}>MetaMask</b>
                            </Col>
                            <Col span={4} >
                                <Tag style={{ borderRadius: '5px'}} color="blue">Popular</Tag>
                            </Col>
                        </Row>
                    </Card.Grid>
                    <Card.Grid style={{width: '300px'}}>
                        <Row align='middle' justify='start'>
                            <Col span={4}>
                                <img src={PhantomImage} style={{width: '30px', height: '30px', borderRadius: '5px'}}/>
                            </Col>
                            <Col span={16}>
                                <b style={{float: 'left'}}>Phantom</b>
                            </Col>
                            <Col span={4}>
                                <Tag style={{borderRadius: '5px'}}>Solana</Tag>
                            </Col>
                        </Row>
                    </Card.Grid>
                    <Card.Grid style={{width: '300px'}}>
                        <Row align='middle' justify='start'>
                            <Col span={4}>
                                <img src={CoinBaseImage} style={{width: '30px', height: '30px', borderRadius: '5px'}}/>
                            </Col>
                            <Col span={16}>
                                <b style={{float: 'left'}}>Coinbase Wallet</b>
                            </Col>
                            {/*<Col span={4} >*/}
                            {/*    <Tag style={{ borderRadius: '5px'}} color="grey">Solana</Tag>*/}
                            {/*</Col>*/}
                        </Row>
                    </Card.Grid>
                </Card>
            </div>
        </div>
    )
}

export default connector(ConnectWalletPage);

const gridStyle = {
    width: '300px',
    height: '50px',
    textAlign: 'left'
};
