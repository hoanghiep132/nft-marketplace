import React, {useEffect, useState} from "react";
import Blockie from '../Others/Blockie'
import {Button, Card, Dropdown, Menu} from "antd";
import {
    HeartOutlined,
    LogoutOutlined,
    PictureOutlined,
    SelectOutlined,
    TableOutlined,
    UserOutlined
} from "@ant-design/icons";
import Address from "../Address";
import {TOKEN_KEY} from "../../constants/common";
import {useHistory} from "react-router";
import {RootState} from "../../redux/reducers";
import {connect, ConnectedProps} from "react-redux";
import {login, logout} from "../../modules/Auth/redux/actions";

const styles = {
    account: {
        height: "42px",
        padding: "0 15px",
        display: "flex",
        justifyContent: "center",
        alignItems: "center",
        width: "fit-content",
        borderRadius: "12px",
        backgroundColor: "rgb(244, 244, 244)",
        cursor: "pointer",
    },
    anonymous: {
        height: "42px",
        padding: "0 15px",
        display: "flex",
        justifyContent: "center",
        alignItems: "center",
        width: "fit-content",
        borderRadius: "12px",
        backgroundColor: "rgb(244, 244, 244)",
        cursor: "pointer",
    },
    text: {
        color: "#21BF96",
    },
};

const mapState = (rootState: RootState) => ({
    auth: rootState.auth.auth
});

const connector = connect(mapState, {login, logout});

type PropsFromRedux = ConnectedProps<typeof connector>;

interface IProps extends PropsFromRedux{
    isLogin: boolean,
    walletAddress ?: string,
}

const Account = (props: IProps) => {
    const history = useHistory();

    const items = [
        {
            label: 'Hồ sơ người dùng',
            key: 'profile',
            icon: <UserOutlined/>,
        },
        {
            label: 'Danh sách yêu thích',
            key: 'your-favorites',
            icon: <HeartOutlined />
        },
        {
            label: 'Bộ sưu tập của bạn',
            key: 'your-collection',
            icon: <TableOutlined />
        },{
            label: 'Danh sách sở hữu',
            key: 'your-items',
            icon: <PictureOutlined />
        },
        {
            label: 'Đăng xuất',
            key: 'logout',
            icon: <LogoutOutlined />
        }
    ];

    const onClickMenu = (e: any) => {
        const key = e.key;
        if(key === 'logout'){
            props.logout();
        }else{
            history.push("/" + key);
        }
    }

    const menu:any = <Menu items={items} onClick={onClickMenu}/>

    const onClickUserIcon = (e: any) => {
        history.push("/connect-wallet")
    }


    return (
        <>
            <Dropdown overlay={menu} placement="bottom">
                {
                    props.auth.data?.token != undefined && props.auth.data?.token !== ''
                        ?
                        (<div style={{cursor: 'pointer', borderRadius: '10px'}}>
                            <Blockie walletAddress={props.walletAddress || ''}/>
                        </div>)
                        :
                        <UserOutlined
                            onClick={onClickUserIcon}
                            style={{borderRadius: '5px', cursor: 'pointer', marginRight:'50px', fontSize:'30px'}}
                        />
                }
            </Dropdown>
        </>
    )
}

export default connector(Account);
