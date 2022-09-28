import React, {useEffect, useState} from 'react';
import {Anchor, Button, Card, Col, Image, Row, Table, Tooltip} from "antd";
import {useHistory, useParams} from "react-router";
import {ColumnProps} from "antd/es/table";
import styled from "styled-components";
import {RootState} from "../../../redux/reducers";
import {connect, ConnectedProps} from "react-redux";
import {login} from "../../Auth/redux/actions";
import OfferItemModal from "./OfferItemModal";
import {NotificationError, NotificationInfo, NotificationSuccess} from "../../../components/Notification/Notification";
import Loading from "../../../components/Loading";
import {
    approveItemToAuction,
    approveItemToMarket,
    approveMpecToken, approveMpecTokenForAuction, bidAuctionContract, bidAuctionItem,
    buyItem,
    buyItemContract,
    cancelListingItem,
    cancelListItemToMarket, checkOwner,
    createAuctionItem,
    getItemDetail, likeItem,
    listingItem,
    listItemToMarket,
    startAuctionContract, unlikeItem
} from "../service/apis";
import ListingItemModal from "./ListingItemModal";
import CreateAuctionModal from "./CreateAuctionModal";
import CountDownTimer from "./count_down/CountDownTimer";
import BidItemModal from "./BidItemModal";
import {HeartFilled, HeartOutlined} from "@ant-design/icons";

const {Link} = Anchor;

const mapState = (rootState: RootState) => ({
    auth: rootState.auth.auth
});

const connector = connect(mapState, {login});

type PropsFromRedux = ConnectedProps<typeof connector>;

interface IProps extends PropsFromRedux {
}

const ItemInfo = (props: IProps) => {

    const params: any = useParams();
    const [uuid] = useState<string>(params.uuid);

    const [isOwner, setIsOwner] = useState(false);

    const [dataStatus, setDataStatus] = useState(0);

    const history = useHistory();

    const [loading, setLoading] = useState(false);

    const [isFavorite, setIsFavorite] = useState(false);

    const initData = {
        'name': '',
        'uuid': '',
        'image': '',
        'description': '',
        'price': 0,
        'isFavorite': false,
        'collectionUuid': '',
        'collectionName': '',
        'owner': '',
        'itemMarketId': 0,
        'itemAuctionId': 0,
        'contractDetail': {
            'address': '',
            'tokenId': 0,
            'standard': '',
            'network': ''
        },
        'collection': {
            uuid: ''
        },
        'history': [
            {
                'id': 1,
                'address': '',
                'price': 0,
                'time': ''
            },
            {
                'id': 2,
                'address': '',
                'price': 20,
                'time': ''
            },
            {
                'id': 3,
                'address': '',
                'price': 20,
                'time': ''
            },
            {
                'id': 4,
                'address': '',
                'price': 20,
                'time': ''
            },
            {
                'id': 5,
                'address': '',
                'price': 20,
                'time': ''
            },
            {
                'id': 6,
                'address': '',
                'price': 20,
                'time': ''
            }
        ],
        'bidHistory': [],
        'others': [],
        'status': 0,
        'remain': 0,
        'endAt': 0,
        'itemActionId': 0
    }

    const [data, setData] = useState(initData);

    const [resetData, setResetData] = useState(true);

    const [title, setTitle] = useState('');

    const [isVisibleOfferModal, setIsVisibleOfferModal] = useState(false);

    const [isVisibleListingModal, setIsVisibleListingModal] = useState(false);

    const [isVisibleAuctionModal, setIsVisibleAuctionModal] = useState(false);

    const [isVisibleBidModal, setIsVisibleBidModal] = useState(false);

    useEffect(() => {
        setLoading(true);
        getItemDetail(uuid, props.auth.data?.token).then(rs => {
            setLoading(false);
            if(rs.code != 0){
                NotificationError('', '');
                return;
            }
            switch (rs.item.status){
                case 1:
                    setTitle('Sản phẩm chưa được mở bán');
                    break;
                case 2:
                    setTitle('Sản phẩm đang được mở bán');
                    break;
                case 3:
                    setTitle('Sản phẩm đang được đấu giá');
                    break;
                default:
                    setTitle('Sản phẩm không khả dụng');
            }
            if(rs.item.owner === props.auth.data?.walletAddress){
                setIsOwner(true);
            }
            setData(rs.item);
            setIsFavorite(rs.item.isFavorite);
            setDataStatus(rs.item.status);
        }).catch(() => {
            NotificationError('', '');
            setLoading(false);
        })

       /* checkOwner('0xeb65f83c2c0cbd606c23972210dbd32633747dbb', 0).then(rs => {
            console.log(rs);
        });*/
    }, [resetData]);

    const onClickCollectionName = (e: any) => {
        history.push("/collection/" + data.collection.uuid);
    }

    const onClickOtherItems = (e: any, uuid: string) => {
        history.push("/item/" + uuid);
    }

    const closeOfferModalCallBack = () => {
        setIsVisibleOfferModal(false);
    }

    const closeListingModalCallBack = () => {
        setIsVisibleListingModal(false);
    }

    const closeAuctionModalCallBack = () => {
        setIsVisibleAuctionModal(false);
    }

    const onClickListingItem = async (e: any) => {
        setIsVisibleListingModal(true);
    }

    const onClickConfirmListingItem = async (e: any, price: any) => {
        setLoading(true);
        if(props.auth.data?.token == undefined){
            NotificationInfo("", "Vui lòng đăng nhập");
            return;
        }

        const approveRs = await approveItemToMarket(data.contractDetail.tokenId, data.contractDetail.address);
        console.log("approveRs: " + JSON.stringify(approveRs));

        const listingRs = await listItemToMarket(data.contractDetail.tokenId, data.contractDetail.address, price);
        console.log("listingRs: " + JSON.stringify(listingRs));

        const tnxHash = listingRs.transactionHash;
        const blockHash = listingRs.blockHash;
        const itemMarketId = listingRs.events.ListItem.returnValues["0"];
        const listingParam = {
            uuid: uuid,
            price: price,
            tnxHash: tnxHash,
            blockHash: blockHash,
            itemMarketId: itemMarketId,
        }

        listingItem(listingParam).then(rs => {
            setLoading(false);
            setIsVisibleListingModal(false);
            if(rs.code != 0) {
                NotificationError("", "");
            }
            console.log(JSON.stringify(rs));
            setResetData(!resetData);
            NotificationSuccess('Thành công', 'Mở bán vật phẩm thành công');
        }).catch(() => {
            setLoading(true);
            NotificationError("", "");
        });

    }

    const onClickConfirmAuctionItem = async (price: any, startTime: any, endTime: any) => {
        if(props.auth.data?.token == undefined){
            NotificationInfo("", "Vui lòng đăng nhập");
            return;
        }
        setLoading(true);

        const approveRs = await approveItemToAuction(data.contractDetail.tokenId, data.contractDetail.address);
        console.log("approveRs: " + JSON.stringify(approveRs));

        const param = {
            collectionContractAddress: data.contractDetail.address,
            tokenId: data.contractDetail.tokenId,
            startTime: startTime,
            startPrice: price,
            endTime: endTime
        }

        console.log(JSON.stringify(param));

        const auctionRs = await startAuctionContract(param);
        console.log("auctionRs: " + JSON.stringify(auctionRs));

        const tnxHash = auctionRs.transactionHash;
        const blockHash = auctionRs.blockHash;
        const auctionMapId = auctionRs.events.CreateAuction.returnValues["5"];
        const auctionParam = {
            itemUuid: uuid,
            startPrice: price,
            tnxHash: tnxHash,
            blockHash: blockHash,
            startTime: startTime,
            endTime: endTime,
            auctionMapId: auctionMapId,
        }

        createAuctionItem(auctionParam).then(rs => {
            setLoading(false);
            setIsVisibleAuctionModal(false);
            if(rs.code != 0) {
                NotificationError("", "");
            }
            console.log(JSON.stringify(rs));
            setResetData(!resetData);
            NotificationSuccess('Thành công', 'Mở đấu giá bán vật phẩm thành công');
        }).catch(() => {
            setLoading(true);
            NotificationError("", "");
        });
    }

    const onClickCancelListingItem = async (e: any) => {
        if(props.auth.data?.token  == undefined){
            NotificationInfo("", "Vui lòng đăng nhập");
            return;
        }
        setLoading(true);
        const cancelRs = await cancelListItemToMarket(data.itemMarketId, data.contractDetail.address);
        console.log("cancelRs: " + JSON.stringify(cancelRs));
        const tnxHash = cancelRs.transactionHash;
        const blockHash = cancelRs.blockHash;
        const cancelParam = {
            uuid: data.uuid,
            tnxHash: tnxHash,
            blockHash: blockHash
        }

        cancelListingItem(cancelParam).then(rs => {
            setLoading(false);
            if(rs.code != 0) {
                NotificationError("", "");
                return;
            }
            console.log(JSON.stringify(rs));
            setResetData(!resetData);
            NotificationSuccess('Thành công', 'Hủy mở bán vật phẩm thành công');
        }).catch(() => {
            setLoading(true);
            NotificationError("", "");
        });
    }

    const onClickStartAuctionItem = (e: any) => {
        if(props.auth.data?.token == undefined){
            NotificationInfo("", "Vui lòng đăng nhập");
            return;
        }
        setIsVisibleAuctionModal(true);
    }


    const onClickCancelAuctionItem = (e: any) => {
        if(props.auth.data?.token  == undefined){
            NotificationInfo("", "Vui lòng đăng nhập");
            return;
        }
    }

    const onClickBuyItem = async (e: any) => {
       if(props.auth.data?.token == undefined){
            NotificationInfo("", "Vui lòng đăng nhập");
            return;
        }
        setLoading(true);

        const approveRs = await approveMpecToken(data.price);
        console.log("approveRs: " + JSON.stringify(approveRs));

        const buyRs = await buyItemContract(data.itemMarketId);
        console.log("buyRs: " + JSON.stringify(buyRs));
        const tnxHash = buyRs.transactionHash;
        const blockHash = buyRs.blockHash;

        const param = {
            itemUuid: uuid,
            tnxHash: tnxHash,
            blockHash: blockHash
        }

        buyItem(param).then(rs => {
            setLoading(false);
            if(rs.code != 0) {
                NotificationError("", "");
            }
            console.log(JSON.stringify(rs));
            setResetData(!resetData);
            NotificationSuccess('Thành công', 'Mua vật phẩm thành công');
        }).catch(() => {
            setLoading(true);
            NotificationError("", "");
        });

    }

    const onClickBidItem = async (e: any) => {
        setIsVisibleBidModal(true);
    }

    const onClickCloseBidItem = async (e: any) => {
        setIsVisibleBidModal(false);
    }

    const onClickSubmitBidItem = async (e: any, price: any) => {
        if(props.auth.data?.token == undefined){
            NotificationInfo("", "Vui lòng đăng nhập");
            return;
        }

        if(price <= data.price){
            NotificationInfo("", "Giá tiền không hợp lệ");
            return;
        }
        setLoading(true);
        const approveRs = await approveMpecTokenForAuction(price);
        console.log("approveRs: " + JSON.stringify(approveRs));

        const bidParamContract = {
            auctionId: data.itemActionId,
            value: price
        }
        const bidRs = await bidAuctionContract(bidParamContract);
        console.log("bidRs: " + JSON.stringify(bidRs));
        const tnxHash = bidRs.transactionHash;
        const blockHash = bidRs.blockHash;

        const bidParam= {
            itemUuid: uuid,
            price: price,
            tnxHash: tnxHash,
            blockHash: blockHash
        }
        bidAuctionItem(bidParam).then(rs => {
            setLoading(false);
            if(rs.code != 0) {
                NotificationError("", "");
            }
            setIsVisibleBidModal(false);
            setResetData(!resetData);
            NotificationSuccess('Thành công', 'Đấu giá vật phẩm thành công');
        }).catch(() => {
            setLoading(true);
            NotificationError("", "");
        });
    }

    const onClickOfferItem = (e: any) => {
        setIsVisibleOfferModal(true);
    }

    const onClickFavorite = async (e: any) => {
        e.stopPropagation();
        if (props.auth.data?.token == undefined) {
            NotificationInfo("Cảnh báo", "Vui lòng đăng nhập")
            return;
        }
        let resp: any;
        if (isFavorite) {
            resp = await unlikeItem(data.uuid);
        } else {
            resp = await likeItem(data.uuid)
        }
        if (resp.code != 0) {
            NotificationError('', '');
            return;
        }
        setIsFavorite(!isFavorite);
    }

    const renderButton = () => {
        if(isOwner){
            if(dataStatus == 1){
                return (
                    <div>
                        <Button type="primary"
                                className="action-btn"
                                onClick={onClickListingItem}
                        >
                            Mở bán
                        </Button>
                        <Button type="default"
                                className="action-btn"
                                onClick={onClickStartAuctionItem}
                        >
                            Mở đấu giá
                        </Button>
                    </div>
                );
            }else if(dataStatus == 2){
                return (
                    <div>
                        <Button type="default"
                                className="action-btn"
                                onClick={onClickCancelListingItem}
                        >
                            Hủy bán
                        </Button>
                    </div>
                );
            }else if(dataStatus == 3){
                return (
                   <div>
                       <div>
                           <CountDownTimer target={data.endAt}/>
                       </div>
                       <div>
                           <Button type="default"
                                   className="action-btn"
                                   style={{marginLeft: '10px', float: 'left'}}
                                   onClick={onClickCancelAuctionItem}
                           >
                               Hủy đấu giá
                           </Button>
                       </div>
                   </div>
                );
            }else {
                return null;
            }
        }else {
            if(dataStatus == 1){
                return (
                    <div>
                        <Button type="default"
                                className="action-btn"
                                onClick={onClickOfferItem}
                        >
                            Đề nghị
                        </Button>
                    </div>
                );
            }else if(dataStatus == 2){
                return (
                    <div>
                        <Button type="primary"
                                className="action-btn"
                                onClick={onClickBuyItem}
                        >
                            Mua
                        </Button>
                        {/*<Button type="default"*/}
                        {/*        className="action-btn"*/}
                        {/*        onClick={onClickOfferItem}*/}
                        {/*>*/}
                        {/*    Đề nghị*/}
                        {/*</Button>*/}
                    </div>
                );
            }else if(dataStatus == 3){
                return (
                    <div>
                        <div>
                            <CountDownTimer target={data.endAt}/>
                        </div>
                        <div>
                            <Button type="default"
                                    className="action-btn"
                                    onClick={onClickBidItem}
                                    style={{marginLeft: '10px', float: 'left'}}
                            >
                                Đấu giá
                            </Button>
                        </div>
                    </div>
                );
            }else {
                return null;
            }
        }
    }

    const renderHistory = (): any => {
        // auction
        if(dataStatus === 3){
            return (
                <Table
                    key="bid-history-table"
                    className="custom-table-2"
                    dataSource={data.bidHistory}
                    columns={columns}
                    rowKey="id"
                    locale={{
                        emptyText: 'Không tìm thấy kết quả tương ứng',
                    }}
                />
            )
        }else {
            return (
                <Table
                    key="buy-history-table"
                    className="custom-table-2"
                    dataSource={data.history}
                    columns={columns}
                    rowKey="id"
                    locale={{
                        emptyText: 'Không tìm thấy kết quả tương ứng',
                    }}
                />
            )
        }
    }

    const renderOtherItems = (): any => {
        if (data.others == undefined) {
            return <p>Không có sản phẩm nào tương ứng</p>;
        }
        return data.others.map((item: any) => {
            return (
                <Card
                    style={{minWidth: '280px', marginLeft: '20px', borderRadius: '5px'}}
                    hoverable
                    key={item.uuid}
                    cover={<img alt="example" src={item.imageUrl} style={{height: '150px', margin: "auto"}}/>}
                    onClick={e => onClickOtherItems(e, item.uuid)}
                >
                    <div style={{float: 'left'}}>
                        <p style={{fontSize: '16px', color: 'gray'}}>{item.name}</p>
                        <div style={{display: 'flex'}}>
                            <img src="https://openseauserdata.com/files/6f8e2979d428180222796ff4a33ab929.svg"
                                 style={{width: '16px'}}/>
                            <p style={{
                                marginLeft: '10px',
                                fontSize: '16px',
                                fontWeight: '600',
                                paddingTop: '15px'
                            }}>{item.price}</p>
                        </div>
                    </div>
                </Card>
            );
        });
    }

    const columns: ColumnProps<any>[] = [
        {
            title: 'Địa chỉ',
            dataIndex: 'address',
            width: 200,
        },
        {
            title: 'Giá',
            dataIndex: 'price',
            width: 200,
        },
        {
            title: 'Thời gian',
            dataIndex: 'time',
            width: 200,
        },
    ]

    return (
        <Wrapper>
            <br/>
            <Row>
                <Col span={8}>
                    <div>
                        <Image
                            width="80%"
                            src={data.image}
                        />
                    </div>
                    <br/>
                    <div style={{margin: 'auto', width: '80%'}}>
                        <Card title="Mô tả" style={{borderRadius: '10px'}}>
                            <p style={{display: 'flex'}}>
                                {data.description}
                            </p>
                        </Card>
                    </div>
                </Col>
                <Col span={12}>
                    <div>
                        <p style={{
                            display: 'flex',
                            color: 'rgb(32, 129, 226)',
                            fontSize: '24px',
                            cursor: 'pointer'
                        }} onClick={onClickCollectionName}>
                            {data.collectionName}
                        </p>
                        <div style={{display: 'flex'}}>
                            <b style={{fontSize: '24px', display: 'flex'}}>{data.name}</b>
                            {
                                isFavorite ?
                                    <Tooltip title="Bỏ yêu thích">
                                        <HeartFilled style={{color: 'red', fontSize: '28px', marginLeft: '50px', paddingTop: '5px'}} onClick={onClickFavorite}/>
                                    </Tooltip>
                                    :
                                    <Tooltip title="Yêu thích">
                                        <HeartOutlined style={{fontSize: '28px', marginLeft: '50px', paddingTop: '5px'}} onClick={onClickFavorite}/>
                                    </Tooltip>
                            }
                        </div>
                        <span style={{fontSize: '18px', display: 'flex'}}> Chủ sở hữu:
                           <a target="_blank" href={"/account/" + data.owner}
                              style={{color: 'rgb(32, 129, 226)', marginLeft: '10px'}}>{data.owner}</a>
                        </span>
                        <br/>
                        <Row>
                            <Card title={title} style={{width: '100%', borderRadius: '10px'}}>
                                <div>
                                    <span style={{display: 'flex'}}>Giá sản phẩm: </span>
                                    <br/>
                                    <Row>
                                        <Col span={1}>
                                            <img alt="ETH"
                                                 src="https://openseauserdata.com/files/6f8e2979d428180222796ff4a33ab929.svg"
                                                 style={{width: '24px'}}/>
                                        </Col>
                                        <Col span={1}/>
                                        <Col span={18}>
                                            <b style={{display: 'flex', fontSize: '24px'}}>{data.price} MPECT </b>
                                        </Col>
                                    </Row>
                                </div>
                                <br/>
                                <div style={{display: 'flex'}}>
                                    {renderButton()}
                                </div>
                            </Card>
                        </Row>
                    </div>
                    <div>
                        <Card title="Thông tin Contract">
                            <div style={{display: 'flex', justifyContent: 'space-between'}}>
                                <div style={{textAlign: 'left'}}>
                                    <p>Contract Address </p>
                                    <p>Token ID</p>
                                    <p>Token Standard</p>
                                    <p>Blockchain</p>
                                </div>
                                <div style={{textAlign: 'right'}}>
                                    <a target="_blank" href={"https://testnet.bscscan.com/address/" + data.contractDetail.address}
                                       style={{color: 'rgb(32, 129, 226)'}}>{data.contractDetail.address}</a>
                                    <p>{data.contractDetail.tokenId}</p>
                                    <p>{data.contractDetail.standard}</p>
                                    <p>{data.contractDetail.network}</p>
                                </div>
                            </div>
                        </Card>
                    </div>
                </Col>
            </Row>
            <br/>
            <br/>
            <div>
                <b style={{fontSize: '20px'}}>Lịch sử giao dịch vật phẩm</b>
                <br/>
                {renderHistory()}
            </div>

            <div>
                <Card title="Các sản phẩm tương tự" style={{borderRadius: '10px', width: '100%'}}>
                    <div style={{display: 'flex', overflow: 'auto', paddingBottom: '20px'}}>
                        {renderOtherItems()}
                    </div>
                </Card>
                <br/>
                <Button type="default" className="view-collection-btn" onClick={onClickCollectionName}>
                    Xem chi tiết bộ sưu tập
                </Button>
            </div>
            <OfferItemModal visible={isVisibleOfferModal} closeCallback={closeOfferModalCallBack}/>
            <ListingItemModal visible={isVisibleListingModal} closeCallback={closeListingModalCallBack} onSubmit={onClickConfirmListingItem} data={data}/>
            <CreateAuctionModal visible={isVisibleAuctionModal} closeCallback={closeAuctionModalCallBack} onSubmit={onClickConfirmAuctionItem} data={data}/>
            <BidItemModal visible={isVisibleBidModal} closeCallback={onClickCloseBidItem} onSubmit={onClickSubmitBidItem}/>
            {loading ? <Loading/> : null}
        </Wrapper>
    )
}

export default connector(ItemInfo);


const Wrapper = styled.div`

  margin: auto;
  width: 80%;
  marginTop: 20px;

  .nav-menu {
    font-size: 20px;
    font-weight: 600;
  }
  
  .view-collection-btn{
    width: 200px;
    height: 50px;
    font-size: 16px;
    border-radius: 10px;
    color: rgb(32, 129, 226);
    font-weight: 600
  }
  
  .action-btn{
    border-radius: 10px;
    width: 150px;
    height: 70px;
    font-size: 20px;
    margin-left: 20px
  }
`;
