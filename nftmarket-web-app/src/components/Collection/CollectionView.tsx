import React, {useState} from 'react';
import {Card, Tooltip, Image, Modal, Badge, Alert, Spin} from "antd";
import {
    FileSearchOutlined,
    RightCircleOutlined,
    ShoppingCartOutlined,
} from "@ant-design/icons";
import Contract from '../../contracts/NftMarket.json';
const {Meta} = Card;

const styles = {
    NFTs: {
        display: "flex",
        flexWrap: "wrap",
        WebkitBoxPack: "start",
        justifyContent: "flex-start",
        margin: "0 auto",
        maxWidth: "1000px",
        gap: "10px",
    },
    banner: {
        display: "flex",
        justifyContent: "space-evenly",
        alignItems: "center",
        margin: "0 auto",
        width: "600px",
        //borderRadius: "10px",
        height: "150px",
        marginBottom: "40px",
        paddingBottom: "20px",
        borderBottom: "solid 1px #e3e3e3",
    },
    logo: {
        height: "115px",
        width: "115px",
        borderRadius: "50%",
        // positon: "relative",
        // marginTop: "-80px",
        border: "solid 4px white",
    },
    text: {
        color: "#041836",
        fontSize: "27px",
        fontWeight: "bold",
    },
};

const CollectionView = (props?: any) => {
    const fallbackImg = "data:image/png;base64,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";
    const [fetchSuccess, setFetchSuccess] = useState(false);

    const initNftTokenId = [
        {
            'image': ''
        },{
            'image': ''
        }
    ]

    const [NFTTokenIds] = useState<any>(initNftTokenId);
    const [totalNFTs, setTotalNFTs] = useState(2);
    const [visible, setVisibility] = useState(false);
    const [nftToBuy, setNftToBuy] = useState<any>(null);
    const [loading, setLoading] = useState(false);
    const [contractProcessor, setContractProcessor] = useState<any>();
    const {chainId, marketAddress, walletAddress} = props;
    const nativeName: string = 'HiepNh';
    const contractABIJson: any = Contract;
    const queryMarketItems: any = {
        data: [
            {
               'createdAt': '123',
               'price': '100',
                'nftContract': '13123',
                'itemId': 1,
                'sold': false,
                'tokenId': 1,
                'seller': 'hiep123',
                'owner': 'hiep',
                'confirmed': false
            },
            {
                'createdAt': '123',
                'price': '100',
                'nftContract': '13123',
                'itemId': 2,
                'sold': false,
                'tokenId': 2,
                'seller': 'hiep123',
                'owner': 'hiep',
                'confirmed': false
            }
        ]
    }
    const fetchMarketItems = JSON.parse(
        JSON.stringify(queryMarketItems.data, [
            "createdAt",
            "price",
            "nftContract",
            "itemId",
            "sold",
            "tokenId",
            "seller",
            "owner",
            "confirmed",
        ])
    );
    const purchaseItemFunction = "createMarketSale";
    const [nftCollections, setNftCollections] = useState<any>();

    async function purchase() {
        // setLoading(true);
    }

    const handleBuyClick = (nft: any) => {
        // setNftToBuy(nft);
        // console.log(nft.image);
        // setVisibility(true);
    };

    function succPurchase() {
        // let secondsToGo = 5;
        // const modal = Modal.success({
        //     title: "Success!",
        //     content: `You have purchased this NFT`,
        // });
        // setTimeout(() => {
        //     modal.destroy();
        // }, secondsToGo * 1000);
    }

    function failPurchase() {
        // let secondsToGo = 5;
        // const modal = Modal.error({
        //     title: "Error!",
        //     content: `There was a problem when purchasing this NFT`,
        // });
        // setTimeout(() => {
        //     modal.destroy();
        // }, secondsToGo * 1000);
    }

    async function updateSoldMarketItem() {
        // const id = getMarketItem(nftToBuy).objectId;
        // const marketList = Moralis.Object.extend("MarketItems");
        // const query = new Moralis.Query(marketList);
        // await query.get(id).then((obj) => {
        //     obj.set("sold", true);
        //     obj.set("owner", walletAddress);
        //     obj.save();
        // });
    }

    const getMarketItem = (nft: any) => {
        // const result = fetchMarketItems?.find(
        //     (e) =>
        //         e.nftContract === nft?.token_address &&
        //         e.tokenId === nft?.token_id &&
        //         e.sold === false &&
        //         e.confirmed === true
        // );
        // return result;
        return [];
    };

    const onClick1 = () => {

    }
    return (
        <>
            <div>
                {contractABIJson.noContractDeployed && (
                    <>
                        <Alert
                            message="No Smart Contract Details Provided. Please deploy smart contract and provide address + ABI in the MoralisDappProvider.js file"
                            type="error"
                        />
                        <div style={{marginBottom: "10px"}}/>
                    </>
                )}
                {props.inputValue !== "explore" && totalNFTs !== undefined && (
                    <>
                        {!fetchSuccess && (
                            <>
                                <Alert
                                    message="Unable to fetch all NFT metadata... We are searching for a solution, please try again later!"
                                    type="warning"
                                />
                                <div style={{marginBottom: "10px"}}/>
                            </>
                        )}
                        <div style={styles.banner}>
                            <Image
                                preview={false}
                                src={NFTTokenIds[0]?.image || "error"}
                                fallback={fallbackImg}
                                alt=""
                                style={styles.logo}
                            />
                            <div style={styles.text}>
                                <>
                                    <div>{`${NFTTokenIds[0]?.name}`}</div>
                                    <div
                                        style={{
                                            fontSize: "15px",
                                            color: "#9c9c9c",
                                            fontWeight: "normal",
                                        }}
                                    >
                                        Collection Size: {`${totalNFTs}`}
                                    </div>
                                </>
                            </div>
                        </div>
                    </>
                )}

                {/*<div style={styles.NFTs}>*/}
                <div>
                    {props.inputValue === "explore" &&
                    nftCollections?.map((nft: any, index: number) => (
                        <Card
                            hoverable
                            actions={[
                                <Tooltip title="View Collection">
                                    <RightCircleOutlined
                                        onClick={() => props.setInputValue(nft?.addrs)}
                                    />
                                </Tooltip>,
                            ]}
                            style={{width: 240, border: "2px solid #e7eaf3"}}
                            cover={
                                <Image
                                    preview={false}
                                    src={nft?.image || "error"}
                                    fallback={fallbackImg}
                                    alt=""
                                    style={{height: "240px"}}
                                />
                            }
                            key={index}
                        >
                            <Meta title={nft.name}/>
                        </Card>
                    ))}

                    {props.inputValue !== "explore" &&
                    NFTTokenIds.slice(0, 20).map((nft: any, index: number) => (
                        <Card
                            hoverable
                            actions={[
                                <Tooltip title="View On Blockexplorer">
                                    <FileSearchOutlined
                                        onClick={onClick1}
                                    />
                                </Tooltip>,
                                <Tooltip title="Buy NFT">
                                    <ShoppingCartOutlined onClick={() => handleBuyClick(nft)}/>
                                </Tooltip>,
                            ]}
                            style={{width: 240, border: "2px solid #e7eaf3"}}
                            cover={
                                <Image
                                    preview={false}
                                    src={nft.image || "error"}
                                    fallback={fallbackImg}
                                    alt=""
                                    style={{height: "240px"}}
                                />
                            }
                            key={index}
                        >
                            {getMarketItem(nft) && (
                                <Badge.Ribbon text="Buy Now" color="green"/>
                            )}
                            <Meta title={nft.name} description={`#${nft.token_id}`}/>
                        </Card>
                    ))}
                </div>
                {getMarketItem(nftToBuy) ? (
                    <Modal
                        title={`Buy ${nftToBuy?.name} #${nftToBuy?.token_id}`}
                        visible={visible}
                        onCancel={() => setVisibility(false)}
                        onOk={() => purchase()}
                        okText="Buy"
                    >
                        <Spin spinning={loading}>
                            <div
                                style={{
                                    width: "250px",
                                    margin: "auto",
                                }}
                            >
                                <Badge.Ribbon
                                    color="green"
                                    // text={`${
                                    //     getMarketItem(nftToBuy).price / ("1e" + 18)
                                    // } ${nativeName}`}
                                    text="2000"
                                >
                                    <img
                                        src={nftToBuy?.image}
                                        style={{
                                            width: "250px",
                                            borderRadius: "10px",
                                            marginBottom: "15px",
                                        }}
                                    />
                                </Badge.Ribbon>
                            </div>
                        </Spin>
                    </Modal>
                ) : (
                    <Modal
                        title={`Buy ${nftToBuy?.name} #${nftToBuy?.token_id}`}
                        visible={visible}
                        onCancel={() => setVisibility(false)}
                        onOk={() => setVisibility(false)}
                    >
                        <img
                            src={nftToBuy?.image}
                            style={{
                                width: "250px",
                                margin: "auto",
                                borderRadius: "10px",
                                marginBottom: "15px",
                            }}
                        />
                        <Alert
                            message="This NFT is currently not for sale"
                            type="warning"
                        />
                    </Modal>
                )}
            </div>
        </>
    );
}

export default CollectionView;
