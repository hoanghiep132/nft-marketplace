import React, {useState} from "react";
import {Button, Form, Input, Modal} from "antd";
import styled from "styled-components";

interface IProps {
    visible: boolean,
    closeCallback: any,
    onSubmit: any
    data: any
}

const ListingItemModal = (props: IProps) => {
    const formItemLayout = {
        labelCol: {
            xs: {span: 24},
            sm: {span: 8},
        },
        wrapperCol: {
            xs: {span: 24},
            sm: {span: 16},
        },
    };

    const [price, setPrice] = useState();

    const onListingClick = (e: any) => {
        props.onSubmit(e, price);
    }

    const onChangePrice = (e: any) => {
        setPrice(e.target.value);
    }

    return (
        <Wrapper>
            <Modal
                key='listing-modal'
                zIndex={2}
                maskClosable={false}
                title={'Mở bán sản phẩm'}
                centered={true}
                width="550px"
                onCancel={() => {
                    props.closeCallback();
                }}
                visible={props.visible}
                footer={null}
            >
                <Form name="basic"   {...formItemLayout}>
                    <Form.Item name="price" label="Giá"
                               className="font-title-form"
                               rules={[{required: true, message: 'Vui lòng điền giá đề nghị'}]}>
                        <Input placeholder="Giá" className="input-form" onChange={onChangePrice}/>
                    </Form.Item>

                    <div style={{paddingBottom: '40px'}}>
                        <Button
                            key="listing-cancel-btn"
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
                            key="listing-submit-btn"
                            htmlType="submit"
                            type="primary"
                            onClick={onListingClick}
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
</Wrapper>
)
}

export default ListingItemModal;

const Wrapper = styled.div`

  .ant-modal-footer {
    height: 80px
  }
`;