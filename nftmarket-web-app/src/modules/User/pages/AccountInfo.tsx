import React, {useEffect, useState} from "react";
import {useHistory, useParams} from "react-router";
import {getAccountInfo, getUserInfo} from "../services/apis";
import {NotificationError} from "../../../components/Notification/Notification";
import Loading from "../../../components/Loading";
import {Avatar, Card, Col, Image, Row} from "antd";
import styled from "styled-components";
import Icon, {CopyOutlined} from "@ant-design/icons";

const AccountInfo = () => {

    const params: any = useParams();

    const history = useHistory();

    const [loading, setLoading] = useState(false);
    const [address, setAddress] = useState<string>(params.address);

    const initData = {
        "username": "Unnamed",
        "fullName": "nguyen hoang hiep",
        "nickname": "",
        "email": "nguyenhoanghiep1302@gmail.com",
        "description": "123",
        "walletAddress": "0x1D01aeC4c5A78a6D6EB4993B612e716480120F8c",
        "avatar": "https://nhh-nft-market.s3.ap-southeast-1.amazonaws.com/03a4155f-2a06-465b-bf4f-0342cef90eaf_1655991639876",
        "phoneNumber": "0923498712",
        "collectionList": []
    }

    const [data, setData] = useState<any>(initData);

    useEffect(() => {
        setLoading(true);
        getAccountInfo(address).then(rs => {
            setLoading(false);
            if (rs.code !== 0) {
                NotificationError('', '');
                return;
            }
            setData(rs.item);
        }).catch(() => {
            setLoading(false);
            NotificationError('', '');
        })
    }, [params]);

    const onClickCollection = (e: any, uuid: string) => {
        history.push('/collection/' + uuid);
    }

    const renderCollectionList = (): any => {
        if (data.collectionList == undefined || data.collectionList.length === 0) {
            return null;
            // return <p style={{fontWeight: '600', fontSize: '16px'}}>Người dùng chưa có bộ sưu tập nào</p>
        }
        return data.collectionList.map((item: any) => {
            return (
                <Col span={8} key={item.uuid} style={{marginTop: '20px'}}>
                    <Card
                        style={{width: 400, cursor: 'pointer', borderRadius: '8px'}}
                        cover={
                            <img src={item.bannerImg} style={{height: '300px'}}/>
                        }
                        hoverable={true}
                        key={item.name}
                        onClick={(e) => onClickCollection(e, item.uuid)}
                    >
                        <div style={{display: 'flex'}}>
                            <div>
                                <Avatar src={item.imageUrl}
                                        style={{width: '60px', height: '60px', borderRadius: '60px'}}/>
                            </div>
                            <div style={{marginLeft: '10px', textAlign: 'left'}}>
                                <p style={{fontSize: '18px', fontWeight: '600'}}>{item.name} ( {item.symbol})</p>
                                <p style={{fontSize: '16px'}}>{item.description}</p>
                                <p style={{fontSize: '16px'}}> Tổng: {item.total} vật phẩm</p>
                            </div>
                        </div>
                        {/*<Meta*/}
                        {/*    style={{height: '80px', verticalAlign: 'center', textAlign: 'left', marginLeft: '10px'}}*/}
                        {/*    avatar={<Avatar src={item.image} style={{width: '60px', height:'60px'}}/>}*/}
                        {/*    title={item.name}*/}
                        {/*    description={item.description}*/}
                        {/*/>*/}
                    </Card>
                </Col>
            )
        });
    }

    return (
        <Wrapper>
            <br/>
            <Row>
                <Col span={4} style={{textAlign: "left"}}>
                    <Image src={data.avatar} style={{width: '150px', height: '150px', borderRadius: '50%'}}/>
                </Col>
                <Col span={8} style={{textAlign: "left"}}>
                    <br/>
                    <span style={{fontSize: '18px', fontWeight: '400'}}>Tài khoản: {data.username}</span>
                    <br/>
                    <br/>
                    <span style={{fontSize: '18px', fontWeight: '400'}}>Giới thiệu: {data.description}</span>
                </Col>
                <Col span={12} style={{textAlign: "left"}}>
                    <br/>
                    <span style={{fontSize: '18px', fontWeight: '400'}}>Email: {data.email}</span>
                    <br/>
                    <br/>
                    <div style={{display: 'flex'}}>
                        <span style={{fontSize: '18px', fontWeight: '400'}}>Địa chỉ ví: {data.walletAddress}</span>
                        <ButtonIcon>
                            <CopyOutlined  style={{fontSize: '18px'}} onClick={() => {navigator.clipboard.writeText(data.walletAddress);}}/>
                        </ButtonIcon>
                    </div>
                </Col>
            </Row>
            <br/>
            <hr style={{border: '1px solid gray'}}/>
            <br/>
            <Row>
                {renderCollectionList()}
            </Row>
            {loading ? <Loading/> : null}
        </Wrapper>
    )
}

const ButtonIcon = styled.div`
  border: none;
  margin-left: 10px;
  :hover {
    .anticon {
      color: #1890ff;
    }
  }
`;

const Wrapper = styled.div`

  margin-left: 10%;
  width: 80%;
  marginTop: 20px;

  .title-page{
    font-size: 24px;
    font-weight: 600;
    float: left;
  }

  .create-btn-collection{
    width: 200px;
    height: 50px;
    font-size: 16px;
    border-radius: 10px;
    font-weight: 600
  }
`;

export default AccountInfo;

