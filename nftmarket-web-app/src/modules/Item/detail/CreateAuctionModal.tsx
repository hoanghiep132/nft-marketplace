import React, {useEffect, useState} from "react";
import {Button, DatePicker, Form, Input, Modal, Space} from "antd";
import {RootState} from "../../../redux/reducers";
import {connect, ConnectedProps} from "react-redux";
import {login} from "../../Auth/redux/actions";
import moment from 'moment';

const mapState = (rootState: RootState) => ({});

const connector = connect(mapState, {login});

type PropsFromRedux = ConnectedProps<typeof connector>;

interface IProps extends PropsFromRedux {
    visible: boolean,
    closeCallback: any,
    onSubmit: any,
    data: any
}


const CreateAuctionModal = (props: IProps) => {

    const [form] = Form.useForm();

    const [time, setTime] = useState('');

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

    const onSubmitClick = (value: any) => {
        props.onSubmit(value.price, value.startTime.format("X"), value.endTime.format("X"));
    }

    useEffect(() => {
        const currTime = getCurrentTime();
        setTime(currTime);
    }, [props.visible]);

    const getCurrentTime = (): string => {
        const currentDate = new Date();
        return currentDate.getDate() + "/"
            + (currentDate.getMonth() + 1) + "/"
            + currentDate.getFullYear() + " "
            + currentDate.getHours() + ":"
            + currentDate.getMinutes() + ":"
            + currentDate.getSeconds();
    }


    return (
        <Modal
            key="auction-modal"
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
            // footer={[
            //     <Button
            //         key="auction-submit-btn"
            //         htmlType="submit"
            //         type="primary"
            //         // onClick={onSubmitClick}
            //         style={{ float: 'right', borderRadius: "8px", width: '100px', height: '50px' }}
            //     >
            //         Đồng ý
            //     </Button>,
            //     <Button
            //         key="auction-cancel-btn"
            //         type="default"
            //         onClick={props.closeCallback}
            //         style={{ float: 'right', borderRadius: "8px", width: '100px', height: '50px', marginRight: '20px' }}
            //     >
            //         Hủy
            //     </Button>
            // ]}
        >
            <Form name="basic"
                  {...formItemLayout}
                  onFinish={onSubmitClick}
            >
                <Form.Item name="price" label="Giá khởi điểm"
                           className="font-title-form"
                           rules={[{required: true, message: 'Vui lòng điền giá đề nghị'}]}>
                    <Input placeholder="Giá" className="input-form"/>
                </Form.Item>

                <Form.Item name="startTime" label="Thời gian bắt đầu"
                           className="date-picker-title-form"
                           initialValue={moment(time, 'DD/MM/yyyy HH:mm:ss')}
                           rules={[{required: true, message: 'Vui lòng chọn thời gian bắt đầu'}]}
                >
                    <DatePicker
                        size="large"
                        showTime={{format: 'HH:mm:ss'}}
                        format="DD/MM/yyyy HH:mm:ss"
                    />
                </Form.Item>

                <Form.Item name="endTime" label="Thời gian kết thúc"
                           className="date-picker-title-form"
                           rules={[{required: true, message: 'Vui lòng chọn thời gian kết thùc'}]}>
                    <DatePicker
                        size="large"
                        showTime={{format: 'HH:mm:ss'}}
                        format="DD/MM/yyyy HH:mm:ss"
                    />
                </Form.Item>
                <div style={{paddingBottom: '40px'}}>
                    <Button
                        key="auction-cancel-btn"
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
                        key="auction-submit-btn"
                        htmlType="submit"
                        type="primary"
                        // onClick={onSubmitClick}
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

export default connector(CreateAuctionModal);