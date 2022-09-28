import React, {useEffect, useState} from "react";
import {Avatar, Card, Col, Pagination, Row} from "antd";
import {Meta} from "antd/es/list/Item";
import {useHistory, useLocation, useParams} from "react-router";
import {searchCollection} from "../../services/apis";
import {NotificationError} from "../../../../components/Notification/Notification";
import {RootState} from "../../../../redux/reducers";
import {connect, ConnectedProps} from "react-redux";
import Loading from "../../../../components/Loading";


const mapState = (rootState: RootState) => ({
    auth: rootState.auth.auth
});

const connector = connect(mapState, {});
type PropsFromRedux = ConnectedProps<typeof connector>;

interface IProps extends PropsFromRedux {

}

const DataList = (props: IProps) => {

    const history = useHistory();

    const [loading, setLoading] = useState(false);
    const [tab, setTab] = useState('');

    const [size, setSize] = useState(10);
    const [page, setPage] = useState(1);
    const [maxPage, setMaxPage]= useState(1);

    const [data, setData] = useState<any>([]);

    const location2 = useLocation();

    useEffect(() => {
        const location =  window.location.href;
        if(!location.includes('/collection')){
            return;
        }
        const search = window.location.search;
        const params = new URLSearchParams(search);
        let param = params.get('tab');
        if(param == null){
            param = '';
        }
        setTab(param);

        const paramRequest = {
            category: param,
            page: 1
        }
        setLoading(true);
        searchCollection(paramRequest, props.auth.data?.token).then(rs => {
            setLoading(false);
            if (rs.code != 0) {
                NotificationError('', '');
                setData([]);
                setMaxPage(0);
                return;
            }
            setMaxPage(rs.total/10);
            setData(rs.rows);
        }).catch(() => {
            setLoading(false);
            setMaxPage(0);
            NotificationError("", "")
        });
    }, [window.location.href]);

    const renderCollectionCard = (data: any) => {
        return (
            <Col span={8} key={data.name} style={{marginTop: '20px'}}>
                <Card
                    style={{width: 400, cursor: 'pointer'}}
                    cover={
                        <img src={data.bannerImg} style={{height: '300px'}}/>
                    }
                    hoverable={true}
                    key={data.name}
                    onClick={() => onClickCollection(data.uuid)}
                >
                    <Meta
                        style={{height: '80px', verticalAlign: 'center', textAlign: 'left', marginLeft: '10px'}}
                        avatar={<Avatar src={data.image} style={{width: '60px', height: '60px'}}/>}
                        title={data.name}
                        description={data.description}
                    />
                </Card>
            </Col>
        )
    }

    const renderCollectionList = () => {
        return data.map((item: any) => {
            return renderCollectionCard(item);
        });
    }

    const onClickCollection = (uuid: string) => {
        history.push('/collection/' + uuid);
    }

    const onChangePagination = (page: number) => {
        setLoading(true);
        const param = {
            page: page,
            category: tab
        }
        searchCollection(param).then(rs => {
            setLoading(false);
            console.log(JSON.stringify(rs));
            if (rs.code != 0) {
                NotificationError('', '');
                setData([]);
                return;
            }
            setData(rs.rows);
        }).catch(() => {
            setLoading(false);
            NotificationError("", "");
        });
    }

    function onShowSizeChange(current: number, pageSize: number) {
        setPage(current);
        setSize(pageSize);
    }

    return (
        <div style={{margin: 'auto', width: '80%'}}>
            <Row>
                {renderCollectionList()}
            </Row>
            <br/>
            <br/>
            <Pagination
                defaultCurrent={1}
                pageSize={10}
                onShowSizeChange={onShowSizeChange}
                total={maxPage}
                onChange={onChangePagination}
            />
            {loading ? <Loading/> : null}
        </div>
    )
}

export default connector(DataList);
