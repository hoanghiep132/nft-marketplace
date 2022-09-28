import React, {useState} from "react";
import {WalletOutlined} from "@ant-design/icons";




interface Iprops {
    isLogin: boolean
}

const Account = (props: Iprops) => {

    return(
        <WalletOutlined style={{fontSize:'30px', marginRight: '20px', cursor:'pointer'}}/>
    )
}

export default Account;
