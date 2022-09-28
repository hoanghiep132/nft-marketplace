import React from "react";
import './Compose.css';
import {Button, Form, Icon, Input} from "antd";
import {AudioOutlined} from "@ant-design/icons";

interface IProps {
    rightItems: any
}

const Compose = (props: IProps) => {

    const socket = new WebSocket('ws://localhost:8080/ws')
    socket.onopen = () => {
        console.log("start")
        socket.send("Hello guys");
    }

    socket.onclose = (e: any) => {
        console.log("close")
    }

    socket.onerror = (e: any) => {
        console.log("Error : ", e)
    }

    const onPressKey = (e: any) => {

    }

    return (
        <div className="compose">
            <div style={{width: '10%', marginLeft: '0px'}}>
                <Button icon='file-image' shape="circle" style={{marginLeft: '5px'}}/>
                <Button icon='audio' shape="circle" style={{marginLeft: '5px'}}/>
            </div>
            <div style={{width: '60%'}}>
                {/*<Form>*/}
                {/*    <Form.Item*/}
                {/*    />*/}
                {/*</Form>*/}
                <Input
                    style={{borderRadius: '10px'}}
                    onKeyPress={onPressKey}
                    type="text"
                    className="compose-input"
                    placeholder="Type a message, @name"
                />
            </div>
            <div style={{width: '10%', marginLeft: '10px'}}>
                <Button icon='message' shape="circle" style={{marginLeft: '5px'}}/>
            </div>
            {/*<div>*/}
            {/*    {*/}
            {/*        props.rightItems*/}
            {/*    }*/}
            {/*</div>*/}
        </div>
    );
}

export default Compose;
