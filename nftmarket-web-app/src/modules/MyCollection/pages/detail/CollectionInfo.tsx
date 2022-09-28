import React from "react";
import {Card, Image, Row} from "antd";

interface IProps{
    data: any;
}

const CollectionInfo = (props: IProps) => {


    return(
        <>
            <Image src={props.data.image}/>
            <br/>
            <h1>{props.data.name}</h1>
            <div style={{borderRadius: '10px'}}>
                <Card style={{margin: 'auto', width: '600px', cursor: 'pointer', borderRadius: '10px' }} size="small">
                    <Card.Grid style={{width: '25%'}} >
                        <div style={{alignContent: 'center'}}>
                            <div>
                                <p>{props.data.totalItem}</p>
                            </div>
                            <div>
                                <p>Items</p>
                            </div>
                        </div>
                    </Card.Grid>
                    <Card.Grid style={{width: '25%'}} >
                        <div style={{alignContent: 'center'}}>
                            <div>
                                <p>{props.data.totalItem}</p>
                            </div>
                            <div>
                                <p>Items</p>
                            </div>
                        </div>
                    </Card.Grid>
                    <Card.Grid style={{width: '25%'}} >
                        <div style={{alignContent: 'center'}}>
                            <div>
                                <p>{props.data.totalItem}</p>
                            </div>
                            <div>
                                <p>Min price</p>
                            </div>
                        </div>
                    </Card.Grid>
                    <Card.Grid style={{width: '25%'}} >
                        <div style={{alignContent: 'center'}}>
                            <div>
                                <p>{props.data.totalItem}</p>
                            </div>
                            <div>
                                <p>Max price</p>
                            </div>
                        </div>
                    </Card.Grid>
                </Card>
            </div>

            <p style={{fontSize: '20px', margin: '10px'}}>{props.data.description}</p>
        </>
    )
};

export default CollectionInfo;