import React, {useState} from "react";
import {Avatar, Card, Col, Row, Tooltip} from "antd";
import {Meta} from "antd/es/list/Item";
import {HeartFilled, HeartOutlined} from "@ant-design/icons";

interface IProps{
    data: any,
    onClickCallback: any
}

const ItemView = (props: IProps) => {

    const {data, onClickCallback} = props;

    const [isFavorite, setIsFavorite] = useState(data.isFavorite || false);

    const onClickBuy = (e: any) => {
    }

    const onClickFavorite = (e: any) => {
        setIsFavorite(!isFavorite);
    }

    return(
        <Col span={4}>
            <Card
                style={{cursor: 'pointer', width: '80%', marginTop:'20px' }}
                cover={
                    <img src={data.image}/>
                }
                hoverable={true}
                key={data.name}
                onClick={() => onClickCallback}
                bodyStyle={{width: '100%'}}
                actions={[
                    <p onClick={onClickBuy}>Mua</p>,
                    isFavorite ?
                        <Tooltip title="Bỏ yêu thích">
                            <HeartFilled style={{color: 'red'}} onClick={onClickFavorite}/>
                        </Tooltip>
                         :
                        <Tooltip title="Yêu thích">
                            <HeartOutlined onClick={onClickFavorite}/>
                        </Tooltip>

                ]}
            >
                <Row style={{margin: 'auto'}}>
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
                </Row>
            </Card>
        </Col>
    )
}

export default ItemView;