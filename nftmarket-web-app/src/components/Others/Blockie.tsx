import React, {useEffect, useState} from "react";
import Blockies from "react-blockies";

function Blockie(props: any) {
    const [walletAddress] = useState('');

    return (
        <Blockies
            seed={walletAddress.toLowerCase()}
            className="identicon"
            {...props}
        />
    );
}

export default Blockie;
