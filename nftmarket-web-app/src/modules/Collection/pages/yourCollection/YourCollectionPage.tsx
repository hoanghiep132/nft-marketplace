import React, {useEffect, useState} from "react";
import {RootState} from "../../../../redux/reducers";
import {connect, ConnectedProps} from "react-redux";
import styled from "styled-components";
import {Avatar, Button, Card, Col, Row} from "antd";
import {useHistory} from "react-router";
import {Meta} from "antd/es/list/Item";
import {getOwnerCollectionList} from "../../services/apis";
import {NotificationError} from "../../../../components/Notification/Notification";
import Loading from "../../../../components/Loading";

const mapState = (rootState: RootState) => ({
    auth: rootState.auth.auth
});

const connector = connect(mapState, { });
type PropsFromRedux = ConnectedProps<typeof connector>;

interface IProps extends  PropsFromRedux{

}

const YourCollectionPage = (props: IProps) => {

    const history = useHistory();

    const [data, setData] = useState<any>();

    const [loading, setLoading] = useState(false);

    useEffect(() => {
        if(props.auth.data?.token == undefined || props.auth.data?.token == ''){
            history.push("/connect-wallet");
            return;
        }

        setLoading(true);
        getOwnerCollectionList().then(rs => {
            if(rs.code != 0){
                NotificationError('', '');
                setData([]);
                setLoading(false);
                return;
            }
            setLoading(false);
            setData(rs.rows);
        }).catch(() => {
            setLoading(false);
            NotificationError('', '')
        });
    }, []);

    const onClickCreateCollection = (e: any) => {
        history.push("/new-collection/create");
    }

    const onClickCreateItem = (e: any) => {
        history.push("/new-item/create");
    }

    const onClickCollection = (e: any, uuid: string) => {
        history.push('/collection/' + uuid);
    }

    const renderCollectionList = (): any => {
        if(data == undefined){
            return null;
            // return <p style={{fontWeight: '600', fontSize: '16px'}}>Người dùng chưa có bộ sưu tập nào</p>
        }
        return data.map((item: any) => {
            return (
                <Col span={8} key={item.uuid} style={{marginTop: '20px'}}>
                    <Card
                        style={{ width: 400, cursor: 'pointer', borderRadius: '8px' }}
                        cover={
                            <img src={item.bannerImg} style={{height: '300px'}}/>
                        }
                        hoverable={true}
                        key={item.name}
                        onClick={(e) => onClickCollection(e, item.uuid)}
                    >
                        <div style={{display: 'flex'}}>
                            <div>
                                <Avatar src={item.imageUrl} style={{width: '60px', height:'60px', borderRadius: '60px'}}/>
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

    return(
        <Wrapper>
            <div>
                <br/>
                <p className="title-page">Danh sách bộ sưu tập vật phẩm NFT của tôi</p>
                <br/>
                <br/>
                <br/>
               <div style={{display: 'flex'}}>
                   <Button type="primary" className="create-btn-collection" onClick={onClickCreateCollection}>
                       Tạo mới bộ sưu tập
                   </Button>
                   <Button type="default" className="create-btn-collection"  onClick={onClickCreateItem} style={{marginLeft: '50px'}}>
                       Tạo mới vật phẩm
                   </Button>
               </div>
            </div>
            <br/>
            <hr style={{border: '0.5px solid gray' }}/>
            <br/>
            <Row>
                {renderCollectionList()}
            </Row>
            {loading ? <Loading/> : null}
        </Wrapper>
    )
}

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

export default connector(YourCollectionPage);

