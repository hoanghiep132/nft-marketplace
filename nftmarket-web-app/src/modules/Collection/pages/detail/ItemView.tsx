import React, {useEffect, useState} from "react";
import {Card, Col, Row, Tooltip} from "antd";
import {HeartFilled, HeartOutlined} from "@ant-design/icons";
import {useHistory} from "react-router";
import {RootState} from "../../../../redux/reducers";
import {connect, ConnectedProps} from "react-redux";
import auth from "../../../Auth/redux/reducers/auth";
import {NotificationError, NotificationInfo} from "../../../../components/Notification/Notification";
import {likeItem, unlikeItem} from "../../../Item/service/apis";

const mapState = (rootState: RootState) => ({
    auth: rootState.auth.auth
});

const connector = connect(mapState, {});
type PropsFromRedux = ConnectedProps<typeof connector>;

interface IProps extends PropsFromRedux {
    data: any,
}

const ItemView = (props: IProps) => {

    const history = useHistory();

    const {data} = props;

    const [isLogin, setIsLogin] = useState(false);

    const [isFavorite, setIsFavorite] = useState(data.isFavorite || false);

    useEffect(() => {
        if (props.auth.data?.token) {
            setIsLogin(true);
        }
    }, []);

    const onClickBuy = (e: any) => {
        e.stopPropagation();
        if (!isLogin) {
            NotificationInfo("Cảnh báo", "Vui lòng đăng nhập")
            return;
        }
    }

    const onClickFavorite = async (e: any) => {
        e.stopPropagation();
        if (!isLogin) {
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

    const onClickItem = (e: any) => {
        history.push(`/item/${data.uuid}`)
    }

    return (
        <Col span={6}>
            <Card
                style={{cursor: 'pointer', width: '80%', marginTop: '20px'}}
                cover={
                    <img src={data.imageUrl} style={{height: '150px'}}/>
                }
                hoverable={true}
                key={data.name}
                onClick={onClickItem}
                bodyStyle={{width: '100%'}}
                actions={[
                    <p onClick={onClickBuy}>Mua</p>,
                    (
                        isFavorite ?
                            <Tooltip title="Bỏ yêu thích">
                                <HeartFilled style={{color: 'red'}} onClick={onClickFavorite}/>
                            </Tooltip>
                            :
                            <Tooltip title="Yêu thích">
                                <HeartOutlined onClick={onClickFavorite}/>
                            </Tooltip>
                    )
                ]}
            >
                <p style={{display: 'flex', fontSize: '16px', fontWeight: '400'}}>{data.name}</p>
                <div style={{display: 'flex'}}>
                    <img alt="ETH"
                         src="https://openseauserdata.com/files/6f8e2979d428180222796ff4a33ab929.svg"
                         style={{width: '16px'}}/>
                    <b style={{fontSize: '16px', marginLeft: '10px'}}>{data.price} $ </b>
                </div>
                {/*  <Row style={{margin: 'auto'}}>
                    <Col span={4}>
                        <div style={{float: 'left'}}>
                            <p>{data.name}</p>
                        </div>
                    </Col>
                    <Col span={20}>
                        <div style={{float: 'right'}}>
                            <p>{data.price}</p>
                        </div>
                    </Col>
                </Row>*/}
            </Card>
        </Col>
    )
}

export default connector(ItemView);
