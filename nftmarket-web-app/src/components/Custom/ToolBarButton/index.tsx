import React from 'react';
import './ToolbarButton.css';
import {Icon} from "antd";

interface IProps{
    icon: string
}

const ToolbarButton = (props: IProps) => {
    const { icon } = props;
    return (
        <>
            {/*<i className={`toolbar-button ${icon}`} />*/}
            <Icon type={icon}/>
        </>
    );
}

export default ToolbarButton;
