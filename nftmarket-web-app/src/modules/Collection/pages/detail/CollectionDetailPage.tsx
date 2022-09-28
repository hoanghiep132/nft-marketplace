import React, {useEffect, useState} from "react";
import {useParams} from "react-router";
import CollectionInfo from "./CollectionInfo";
import ItemDataList from "./ItemDataList";
import SearchBar from "./SearchBar";
import {getCollectionDetail, searchItemInCollection} from "../../services/apis";
import {NotificationError} from "../../../../components/Notification/Notification";
import Loading from "../../../../components/Loading";
import {RootState} from "../../../../redux/reducers";
import {connect, ConnectedProps} from "react-redux";

const mapState = (rootState: RootState) => ({
    auth: rootState.auth.auth
});

const connector = connect(mapState, {});
type PropsFromRedux = ConnectedProps<typeof connector>;

interface IProps extends PropsFromRedux {

}

const CollectionDetailPage = (props: IProps) => {

    const params: any = useParams();
    const [uuid, setUuid] = useState<string>('');

    const initData = {
        uuid: '',
        name: '',
        description: '',
        image: '',
        banner: '',
        totalItem: 5000,
        items: [
            {
                uuid: '',
                name: '',
            }
        ]
    };

    const [data, setData] = useState(initData);

    const [loading, setLoading] = useState(false);

    useEffect(() => {
        setLoading(true);
        const param = {
            page: 1,
            size: 100
        }
        const currUuid = params.uuid;
        if(currUuid == uuid){
            return;
        }
        setUuid(currUuid);
        getCollectionDetail(currUuid, param, props.auth.data?.token).then(rs => {
            if(rs.code != 0){
                NotificationError('', '');
                setLoading(false);
                return;
            }
            setLoading(false);
            setData(rs.item);
        }).catch(() => {
            setLoading(false);
            NotificationError('', '')
        });
    }, [params]);


    const onClickSearch = (text: any, status: any, orderSort: any) => {
        const param = {
            page: 1,
            size: 100,
            collectionUuid: uuid,
            text: text,
            status: status,
            orderSort: orderSort
        }
        console.log(JSON.stringify(param));
        searchItemInCollection(param, props.auth.data?.token).then(rs => {
            if(rs.code != 0){
                NotificationError('', '');
                setLoading(false);
                return;
            }
            console.log(rs);
            setLoading(false);
            const newData = {
                ...data,
                items: rs.rows
            };
            setData(newData);
        }).catch(() => {
            setLoading(false);
            NotificationError('', '')
        });
    }

    return(
        <div>
            <CollectionInfo data={data}/>
            <SearchBar onClickSearch={onClickSearch}/>
            <ItemDataList data={data}/>
            {loading ? <Loading/> : null}
        </div>
    )
}

export default connector(CollectionDetailPage);
