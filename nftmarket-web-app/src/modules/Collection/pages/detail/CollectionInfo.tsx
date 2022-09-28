import React from "react";
import {Card, Image, Row} from "antd";
import styled from "styled-components";

interface IProps{
    data: any;
}

const CollectionInfo = (props: IProps) => {


    return(
        <Wrapper>
            {/*<Image src={props.data.image}/>*/}
            <div style={{margin: 'auto', display: "flex", alignItems: "center",justifyContent: "center"}}>
                <div style={{
                    width: '100%', height: '300px',
                    position: 'relative', top: '0px',
                    backgroundImage: `url(${props.data.bannerImg})`
                }}>
                    <img src={props.data.image}
                         style={{
                             border: '1px solid gray',
                             width: '200px', height: '200px', borderRadius: '50%',
                             position: 'absolute', top: '200px', alignItems: "center", justifyContent: 'center',
                             display: 'block', left: '50%', transform: 'translateX(-50%)'
                         }}
                    />
                </div>
            </div>
            <br/>
            <br/>
            <br/>
            <br/>
            <br/>
            <span style={{fontSize: '24px', fontWeight: '600'}}>{props.data.name} ({props.data.symbol})</span>
            <br/>
            {/*<div style={{borderRadius: '10px'}}>*/}
            {/*    <Card style={{margin: 'auto', width: '600px', cursor: 'pointer', borderRadius: '10px' }} size="small">*/}
            {/*        <Card.Grid style={{width: '25%'}} >*/}
            {/*            <div style={{alignContent: 'center'}}>*/}
            {/*                <div>*/}
            {/*                    <p>{props.data.totalItem}</p>*/}
            {/*                </div>*/}
            {/*                <div>*/}
            {/*                    <p>Items</p>*/}
            {/*                </div>*/}
            {/*            </div>*/}
            {/*        </Card.Grid>*/}
            {/*        <Card.Grid style={{width: '25%'}} >*/}
            {/*            <div style={{alignContent: 'center'}}>*/}
            {/*                <div>*/}
            {/*                    <p>{props.data.totalItem}</p>*/}
            {/*                </div>*/}
            {/*                <div>*/}
            {/*                    <p>Items</p>*/}
            {/*                </div>*/}
            {/*            </div>*/}
            {/*        </Card.Grid>*/}
            {/*        <Card.Grid style={{width: '25%'}} >*/}
            {/*            <div style={{alignContent: 'center'}}>*/}
            {/*                <div>*/}
            {/*                    <p>{props.data.totalItem}</p>*/}
            {/*                </div>*/}
            {/*                <div>*/}
            {/*                    <p>Min price</p>*/}
            {/*                </div>*/}
            {/*            </div>*/}
            {/*        </Card.Grid>*/}
            {/*        <Card.Grid style={{width: '25%'}} >*/}
            {/*            <div style={{alignContent: 'center'}}>*/}
            {/*                <div>*/}
            {/*                    <p>{props.data.totalItem}</p>*/}
            {/*                </div>*/}
            {/*                <div>*/}
            {/*                    <p>Max price</p>*/}
            {/*                </div>*/}
            {/*            </div>*/}
            {/*        </Card.Grid>*/}
            {/*    </Card>*/}
            {/*</div>*/}
            <span style={{fontSize: '20px'}}>{props.data.description}</span>
        </Wrapper>
    )
};

export default CollectionInfo;


const Wrapper = styled.div`

  .nav-menu {
    font-size: 20px;
    font-weight: 600;
  }
`;
