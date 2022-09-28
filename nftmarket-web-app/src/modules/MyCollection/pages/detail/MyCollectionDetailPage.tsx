import React, {useState} from "react";
import {useParams} from "react-router";
import CollectionInfo from "./CollectionInfo";
import ItemDataList from "./ItemDataList";
import SearchBar from "./SearchBar";

const MyCollectionDetailPage = () => {

    const params: any = useParams();
    const [uuid] = useState<string>(params.uuid);

    const initData = {
        uuid: '123123',
        name: 'Collection1',
        description: 'Collection1',
        image: 'https://joeschmoe.io/api/v1/random',
        banner: 'https://gw.alipayobjects.com/zos/rmsportal/JiqGstEfoWAOHiTxclqi.png',
        totalItem: 5000,
    };

    const [data, setData] = useState(initData);

    return(
        <div>
            <CollectionInfo data={data}/>
            <SearchBar/>
            <ItemDataList/>
        </div>
    )
}

export default MyCollectionDetailPage;
