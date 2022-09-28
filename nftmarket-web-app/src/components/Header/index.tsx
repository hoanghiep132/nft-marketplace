import React from 'react';
import {NavLink} from 'react-router-dom';
import {Menu} from 'antd';
import SearchCollections from "../Others/SearchCollections";
import {Logo} from "../../modules/HomePage/components/HomePage";
import Account from "../Account";
import Wallet from "../Wallet";
import {RootState} from "../../redux/reducers";
import {connect, ConnectedProps} from "react-redux";

const styles = {
    content: {
        display: "flex",
        justifyContent: "center",
        fontFamily: "Roboto, sans-serif",
        color: "#041836",
        marginTop: "130px",
        padding: "10px",
    },
    header: {
        position: "fixed",
        zIndex: 1,
        width: "100%",
        background: "#fff",
        display: "flex",
        justifyContent: "space-between",
        alignItems: "center",
        fontFamily: "Roboto, sans-serif",
        borderBottom: "2px solid rgba(0, 0, 0, 0.06)",
        padding: "0 10px",
        boxShadow: "0 1px 10px rgb(151 164 175 / 10%)",
    },
    headerRight: {
        display: "flex",
        gap: "20px",
        alignItems: "center",
        fontSize: "15px",
        fontWeight: "600",
    },
};

const mapState = (rootState: RootState) => ({
    authState: rootState.auth.auth,
});
const connector = connect(mapState, { });
type PropsFromRedux = ConnectedProps<typeof connector>;


interface IProps extends PropsFromRedux{
    // handleShowSidebar: Function;
}

export const Header = (props: IProps) => {

    return (
        <div style={{
            position: "fixed",
            zIndex: 1,
            width: "100%",
            background: "#fff",
            display: "flex",
            justifyContent: "space-between",
            alignItems: "center",
            fontFamily: "Roboto, sans-serif",
            borderBottom: "2px solid rgba(0, 0, 0, 0.06)",
            padding: "0 10px",
            boxShadow: "0 1px 10px rgb(151 164 175 / 10%)",
        }}>
            <Logo/>
            <SearchCollections />
            <Menu
                theme="light"
                mode="horizontal"
                style={{
                    display: "flex",
                    fontSize: "17px",
                    fontWeight: "500",
                    marginLeft: "50px",
                    width: "100%",
                    height: "50px"
                }}
                defaultSelectedKeys={["nftMarket"]}
            >
                <Menu.Item key="nftMarket">
                    <NavLink to="/collection">🛒 Thị trường</NavLink>
                </Menu.Item>
                <Menu.Item key="nft">
                    <NavLink to="/your-collection">🖼 Bộ sưu tập của bạn</NavLink>
                </Menu.Item>
                <Menu.Item key="create">
                    <NavLink to="/new-item/create">🖼 Tạo mới</NavLink>
                </Menu.Item>
            </Menu>
            <div style={{
                display: "flex",
                gap: "20px",
                alignItems: "center",
                fontSize: "15px",
                fontWeight: "600",
                }}>
                <Account isLogin={props.authState.data?.token != undefined} walletAddress={props.authState.data?.walletAddress}/>
                <Wallet isLogin={props.authState.data?.token != undefined}/>
            </div>
        </div>
    );
};

export default connector(Header);
