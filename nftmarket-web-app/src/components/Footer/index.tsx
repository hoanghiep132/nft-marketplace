import React from 'react';
import logo from '../../assets/images/market/nft_log.jpg';
import location_icon from '../../assets/icons/location.svg';
import phone_icon from '../../assets/icons/phone.svg';
import fax_icon from '../../assets/icons/fax.svg';
import email_icon from '../../assets/icons/email.svg';

const Footer = () => {

    return(
        <div>
            <hr style={{border: '1px solid gray'}}/>
            <div className="homepage-footer-wrapper">
                <div className="homepage-footer">
                    <div className="hp-footer--logo">
                        <img src={logo} />
                    </div>
                    <div className="hp-footer--infor">
                        {/*<div className="hp-footer--headervứ">*/}
                        {/*    <h4>NFT MARKETPLACE</h4>*/}
                        {/*</div>*/}
                        <div className="hp-footer--contact">
                            <ul>
                                <li style={{ marginBottom: '12px' }}>
                                    <img style={{ height: '14px' }} src={location_icon} />
                                    <span>Thịnh Quang, Đống Đa, Hà Nội</span>
                                </li>
                                <li style={{ marginBottom: '12px' }}>
                                    <img style={{ height: '14px' }} src={phone_icon} />
                                    <span>0966027102</span>
                                </li>
                                <li style={{ marginBottom: '12px' }}>
                                    <img style={{ height: '14px' }} src={email_icon} />
                                    <span>nguyenhoanghiep132@gmail.com </span>
                                </li>
                                <li style={{ marginBottom: '12px' }}>
                                    <img style={{ height: '14px' }} src={fax_icon} />
                                    <span>0966027102</span>
                                </li>
                            </ul>
                        </div>
                    </div>
                    {/*<div >*/}
                    {/*    <div style={{marginTop: '10px'}}/>*/}
                    {/*    <Text style={{ display: "block" }}>*/}
                    {/*        ⭐️ Please star this{" "}*/}
                    {/*        <a*/}
                    {/*            href="https://github.com/ethereum-boilerplate/ethereum-boilerplate/"*/}
                    {/*            target="_blank"*/}
                    {/*            rel="noopener noreferrer"*/}
                    {/*        >*/}
                    {/*            boilerplate*/}
                    {/*        </a>*/}
                    {/*        , every star makes us very happy!*/}
                    {/*    </Text>*/}

                    {/*    <Text style={{ display: "block" }}>*/}
                    {/*        🙋 You have questions? Ask them on the {""}*/}
                    {/*        <a*/}
                    {/*            target="_blank"*/}
                    {/*            rel="noopener noreferrer"*/}
                    {/*            href="https://forum.moralis.io/t/ethereum-boilerplate-questions/3951/29"*/}
                    {/*        >*/}
                    {/*            Moralis forum*/}
                    {/*        </a>*/}
                    {/*    </Text>*/}

                    {/*    <Text style={{ display: "block" }}>*/}
                    {/*        📖 Read more about{" "}*/}
                    {/*        <a*/}
                    {/*            target="_blank"*/}
                    {/*            rel="noopener noreferrer"*/}
                    {/*            href="https://moralis.io?utm_source=boilerplatehosted&utm_medium=todo&utm_campaign=ethereum-boilerplat"*/}
                    {/*        >*/}
                    {/*            Moralis*/}
                    {/*        </a>*/}
                    {/*    </Text>*/}
                    {/*    <div style={{marginBottom: '10px'}}/>*/}
                    {/*</div>*/}
                </div>
            </div>
        </div>
    )
}

export default Footer;
