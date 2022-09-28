import React, {useEffect, useState} from 'react'
import styled from "styled-components";
import {Card, Col, Pagination, Row} from "antd";
import Loading from "../../../components/Loading";
import {useHistory} from "react-router";
import {getFavoriteList, getOwnerItemList} from "../sevices/apis";
import {NotificationError} from "../../../components/Notification/Notification";

const MyOwnerPage = () => {

    const history = useHistory();

    const [data, setData] = useState<any>();

    const [total, setTotal] = useState(10);

    const [loading, setLoading] = useState(false);

    const [size, setSize] = useState(10);
    const [page, setPage] = useState(1);

    useEffect(() => {
        const paramRequest = {
            page: page,
            size: size
        }
        setLoading(true);
        getOwnerItemList(paramRequest).then(rs => {
            setLoading(false);
            if (rs.code != 0) {
                NotificationError('', '');
                setData([]);
                return;
            }
            setData(rs.rows);
            setTotal(rs.total);
        }).catch(() => {
            setLoading(false);
            NotificationError("", "");
        });
    }, [page]);

    const onClickItem = (e: any, uuid: string) => {
        history.push("/item/" + uuid);
    }

    const renderItemCard = (item: any) => {
        return(
            <Col span={8} key={item.uuid} style={{marginTop: '20px'}}>
                <Card
                    style={{cursor: 'pointer', width: '80%', marginTop: '20px'}}
                    cover={
                        <img src={item.imageUrl} style={{height: '200px'}}/>
                    }
                    hoverable={true}
                    key={item.name}
                    onClick={(e: any) => onClickItem(e, item.uuid)}
                    bodyStyle={{width: '100%'}}
                >
                    <a style={{display: 'flex', fontSize: '18px', fontWeight: '600', color: '#096dd9'}} target="_blank" href={"/collection/" + item.uuid} >#{item.collection.name}</a>
                    <p style={{display: 'flex', fontSize: '16px', fontWeight: '400'}}>{item.name}</p>
                    <div style={{display: 'flex'}}>
                        <img alt="ETH"
                             src="https://openseauserdata.com/files/6f8e2979d428180222796ff4a33ab929.svg"
                             style={{width: '16px'}}/>
                        <b style={{fontSize: '16px', marginLeft: '10px'}}>{item.price} $ </b>
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

    const renderItemList = () => {
        if(data == undefined){
            return ;
        }
        if(data.length == 0){
            return  <p>Bạn chưa có vật phẩm yêu thích nào</p>
        }
        return data.map((item: any) => {
            return renderItemCard(item);
        });
    }

    const onChangePagination = (page: number) => {
        setPage(page);
    }

    function onShowSizeChange(current: number, pageSize: number) {
        setSize(pageSize);
    }

    return(
        <Wrapper>
            <br/>
            <p className="title-page">Danh sách vật phẩm NFT của tôi</p>
            <br/>
            <br/>
            <br/>
            <hr style={{border: '0.5px solid gray' }}/>
            <br/>
            <Row>
                {renderItemList()}
            </Row>
            <br/>
            <br/>
            <Pagination
                defaultCurrent={1}
                pageSize={10}
                onShowSizeChange={onShowSizeChange}
                total={total}
                onChange={onChangePagination}
            />
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

export default MyOwnerPage;