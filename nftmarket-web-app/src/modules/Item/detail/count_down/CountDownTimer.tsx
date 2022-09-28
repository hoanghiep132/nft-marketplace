import React from 'react';
import useCountdown from "./useCountDown";
import ShowCounter from "./ShowCounter";

interface IProps{
    days?: any,
    hours?: any,
    minutes?: any,
    seconds?: any,
    target: any
}

const CountDownTimer = (props: IProps) => {
    const [days, hours, minutes, seconds] = useCountdown(props.target);

    return(
        <ShowCounter
            days={days}
            hours={hours}
            minutes={minutes}
            seconds={seconds}
        />
    )
}


export default CountDownTimer;