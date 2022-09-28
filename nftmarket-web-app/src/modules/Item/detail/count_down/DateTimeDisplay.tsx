import React from 'react';

interface IProps{
    value: any,
    type: any,
    isDanger: boolean
}

const DateTimeDisplay = (props: IProps) => {
    return (
        <div className={props.isDanger ? 'countdown danger' : 'countdown'}>
            <p>{props.value >= 0 ? props.value : 0}</p>
            <span>{props.type}</span>
        </div>
    );
};

export default DateTimeDisplay;