import React, {useState} from "react";
import {Button, Form, Input, Modal} from "antd";
import {RootState} from "../../../redux/reducers";
import {connect, ConnectedProps} from "react-redux";
import {login} from "../../Auth/redux/actions";

const mapState = (rootState: RootState) => ({});

const connector = connect(mapState, {login});

type PropsFromRedux = ConnectedProps<typeof connector>;

interface IProps extends PropsFromRedux {
    visible: boolean,
    closeCallback: any,
}

const OfferItemModal = (props: IProps) => {

    const formItemLayout = {
        labelCol: {
            xs: { span: 24 },
            sm: { span: 8 },
        },
        wrapperCol: {
            xs: { span: 24 },
            sm: { span: 16 },
        },
    };

    const [price, setPrice] = useState();

    const onOfferClick = (e: any) => {

    }

    const onChangePrice = (e: any) => {
        setPrice(e.target.value);
    }

    return(
        <Modal
            zIndex={2}
            maskClosable={false}
            title={'Đề nghị mua sản phẩm'}
            centered={true}
            width="550px"
            onCancel={() => {
                // resetFields();
                props.closeCallback();
            }}
            visible={props.visible}
            footer={null}
        >
            <Form name="basic"   {...formItemLayout}>
                <Form.Item name="price" label="Giá"
                           className="font-title-form"
                           rules={[{ required: true, message: 'Vui lòng điền giá đề nghị' }]}>
                    <Input placeholder="Giá" className="input-form" onChange={onChangePrice}/>
                </Form.Item>
                <div style={{paddingBottom: '40px'}}>
                    <Button
                        key="offer-cancel-btn"
                        type="default"
                        onClick={props.closeCallback}
                        style={{
                            float: 'right',
                            borderRadius: "8px",
                            width: '100px',
                            height: '50px',
                            marginRight: '20px'
                        }}
                    >
                        Hủy
                    </Button>
                    <Button
                        key="offer-submit-btn"
                        htmlType="submit"
                        type="primary"
                        onClick={onOfferClick}
                        style={{
                            float: 'right',
                            borderRadius: "8px",
                            width: '100px',
                            height: '50px',
                            marginRight: '20px'
                        }}
                    >
                        Đồng ý
                    </Button>
                </div>
            </Form>
        </Modal>
    )
}

export default connector(OfferItemModal);