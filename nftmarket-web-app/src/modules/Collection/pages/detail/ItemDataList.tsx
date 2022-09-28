import React, {useEffect, useState} from "react";
import {useHistory} from "react-router";
import ItemView from "./ItemView";
import {Card, Row} from "antd";
import {RootState} from "../../../../redux/reducers";
import {connect, ConnectedProps} from "react-redux";

const mapState = (rootState: RootState) => ({
    auth: rootState.auth.auth
});

const connector = connect(mapState, { });
type PropsFromRedux = ConnectedProps<typeof connector>;

interface IProps extends PropsFromRedux{
    data ?: any
}

const ItemDataList = (props: IProps) => {

    const  [isOwner, setIsOwner] = useState(false);

    const initData = [
        {
            'name': 'Item1',
            'uuid': '11111',
            'image': 'https://joeschmoe.io/api/v1/random',
            'description': 'Item1',
            'price': 20,
            'isFavorite': true
        },{
            'name': 'Item2',
            'uuid': '11112',
            'image': 'https://joeschmoe.io/api/v1/random',
            'description': 'Item2',
            'price': 20,
            'isFavorite': false
        },{
            'name': 'Item3',
            'uuid': '11113',
            'image': 'https://joeschmoe.io/api/v1/random',
            'description': 'Item3',
            'price': 20,
            'isFavorite': true
        },{
            'name': 'Item4',
            'uuid': '11114',
            'image': 'https://joeschmoe.io/api/v1/random',
            'description': 'Item4',
            'price': 20,
            'isFavorite': true
        },{
            'name': 'Item5',
            'uuid': '11115',
            'image': 'https://joeschmoe.io/api/v1/random',
            'description': 'Item5',
            'price': 20,
            'isFavorite': true
        },{
            'name': 'Item6',
            'uuid': '11116',
            'image': 'https://joeschmoe.io/api/v1/random',
            'description': 'Item6',
            'price': 20,
            'isFavorite': false
        },{
            'name': 'Item7',
            'uuid': '11117',
            'image': 'https://joeschmoe.io/api/v1/random',
            'description': 'Item7',
            'price': 20,
            'isFavorite': false
        },{
            'name': 'Item8',
            'uuid': '11118',
            'image': 'https://joeschmoe.io/api/v1/random',
            'description': 'Item8',
            'price': 20,
            'isFavorite': false
        }
    ];

    const [items, setItems] = useState(props.data.items);

    useEffect(() => {
        setItems(props.data.items);
    }, [props.data])

    const renderItemList = () => {
        if(items == undefined){
            return null;
        }
        if(items.length == 0){
            return <p>Bộ sưu tập chưa có vật phẩm nào</p>
        }
        return items.map((item: any) => {
            return <ItemView key={item.uuid} data={item}/>
        });
    }

    return(
        <>
            <Row style={{margin: 'auto', width: '80%' }} >
                {renderItemList()}
            </Row>
        </>
    );
}

export default connector(ItemDataList);
