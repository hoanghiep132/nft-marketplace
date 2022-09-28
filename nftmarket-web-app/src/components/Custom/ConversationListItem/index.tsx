import React, {useEffect} from 'react';
import shave from 'shave';
import './ConversationListItem.css';

interface IProps{
    data: any
}

const ConversationListItem = (props: IProps) => {
    useEffect(() => {
        shave('.conversation-snippet', 20);
    })

    const { photo, name, text } = props.data;

    return (
        <div className="conversation-list-item">
            <img className="conversation-photo" src={photo} alt="conversation" />
            <div className="conversation-info">
                <h1 className="conversation-title">{ name }</h1>
                <p className="conversation-snippet">{ text }</p>
            </div>
        </div>
    );
}

export default ConversationListItem;
