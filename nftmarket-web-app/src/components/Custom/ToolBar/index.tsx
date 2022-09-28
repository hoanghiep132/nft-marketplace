import React from "react";
import './Toolbar.css';

interface IProps{
    title: string,
    leftItems?: any[],
    rightItems: any[]
}

const ToolBar = (props: IProps) => {
    const { title, leftItems, rightItems } = props;
    return (
        <div className="toolbar">
            <div className="left-items">{ leftItems }</div>
            <h1 className="toolbar-title">{ title }</h1>
            <div className="right-items">{ rightItems }</div>
        </div>
    );
}
export default ToolBar;
