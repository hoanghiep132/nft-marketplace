import React, {useEffect, useState} from 'react';
import Header from '../Header/index';
import Footer from '../Footer/index'
import commonStyled from './styled/commonStyled';
import env from 'src/configs/env';
import {Layout} from "antd";

const {Sider} = Layout;

interface LayoutProps {
    children: React.ReactNode;
}

const DefaultLayout = (props: LayoutProps) => {

    const screenWidth = document.documentElement.clientWidth;
    const [collapsed, setCollapsed] = useState(screenWidth <= env.tabletWidth)

    function toggle() {
        setCollapsed(!collapsed)
    }

    useEffect(() => {

        function updateSize() {
            if (document.documentElement.clientWidth < env.desktopWidth) setCollapsed(true)
            else setCollapsed(false)
        }

        window.addEventListener('resize', updateSize);
        updateSize();
        return () => window.removeEventListener('resize', updateSize);

    }, []);

    return (
        <commonStyled.Container>
            <Layout className="content">
                <Header/>
                <br/>
                <br/>
                {props.children}
                <br/>
                <br/>
                <Footer/>
            </Layout>
        </commonStyled.Container>
    );

};

export default DefaultLayout;
