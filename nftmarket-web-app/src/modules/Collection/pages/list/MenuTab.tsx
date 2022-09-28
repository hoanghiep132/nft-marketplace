import React, {useEffect, useState} from "react";
import {Menu} from "antd";
import {NavLink} from "react-router-dom";
import styled from "styled-components";
import {searchCollection} from "../../services/apis";
import {NotificationError} from "../../../../components/Notification/Notification";
import {set} from "immer/dist/utils/common";

interface IProps{
}

const MenuTab = (props: IProps) => {

    const [defaultTab, setDefaultTab] = useState('all');

    useEffect(() => {
        const location =  window.location.href;
        if(!location.includes('/collection')){
            return;
        }
        const search = window.location.search;
        const params = new URLSearchParams(search);
        let param = params.get('tab');
        if(param == null){
            param = 'all';
        }
        setDefaultTab(param);
    }, [window.location.href]);

    return(
        <Wrapper>
            <Menu style={{margin: "auto", justifyContent: 'center'}} mode="horizontal" defaultSelectedKeys={[defaultTab]}>
                <Menu.Item key="all" >
                    <NavLink to="/collection" className="nav-menu">All</NavLink>
                </Menu.Item>
                {/*<Menu.Item key="trending" >*/}
                {/*    <NavLink to="/collection?tab=trending" className="nav-menu">Trending</NavLink>*/}
                {/*</Menu.Item>*/}
                <Menu.Item key="art" >
                    <NavLink to="/collection?tab=art" className="nav-menu">Art</NavLink>
                </Menu.Item>
                <Menu.Item key="photography" >
                    <NavLink to="/collection?tab=photography" className="nav-menu">Photography</NavLink>
                </Menu.Item>
                <Menu.Item key="music" >
                    <NavLink to="/collection?tab=music" className="nav-menu">Music</NavLink>
                </Menu.Item>
                <Menu.Item key="sport" >
                    <NavLink to="/collection?tab=sport" className="nav-menu">Sport</NavLink>
                </Menu.Item>
                <Menu.Item key="gaming" >
                    <NavLink to="/collection?tab=gaming" className="nav-menu">Gaming</NavLink>
                </Menu.Item>
                <Menu.Item key="fashion" >
                    <NavLink to="/collection?tab=fashion" className="nav-menu">Fashion</NavLink>
                </Menu.Item>
                <Menu.Item key="toy" >
                    <NavLink to="/collection?tab=toy" className="nav-menu">Toy</NavLink>
                </Menu.Item>
            </Menu>
        </Wrapper>
    )
}

export default MenuTab;

const Wrapper = styled.div`

  .nav-menu {
    font-size: 20px;
    font-weight: 600;
  }
`;
