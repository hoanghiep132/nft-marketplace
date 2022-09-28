import React from "react";
import {Button, Col, Form, Input, Row, Select} from "antd";
import styled from "styled-components";

interface IProps {
    onClickSearch: any;
}

const SearchBar = (props: IProps) => {

    const onSubmitForm = (value: any) => {
        const text = value.text == undefined ? '' : value.text;
        const status = value.status == undefined ? '' : value.status;
        const orderSort = value.orderSort == undefined ? '' : value.orderSort;
        props.onClickSearch(text, status, orderSort);
    }

    return(
        <div style={{margin: 'auto', width: '81%'}}>
            <Form onFinish={onSubmitForm}>
                <Row style={{margin: 'auto'}} gutter={16}>
                    <Col className="gutter-row" span={6}>
                        <Form.Item name="text">
                            <Input placeholder="Từ khóa" className="input-form2" />
                        </Form.Item>
                    </Col>

                    <Col className="gutter-row" span={6}>
                        <Form.Item name="status" >
                            <SelectStyled size={"large"} allowClear={true} placeholder="Trạng thái">
                                <Select.Option key="1">Tạo mới</Select.Option>
                                <Select.Option key="2">Đang bán</Select.Option>
                                <Select.Option key="3">Đấu giá</Select.Option>
                            </SelectStyled>
                        </Form.Item>
                    </Col>

                    <Col className="gutter-row" span={6}>
                        <Form.Item name="orderSort">
                            <SelectStyled size={"large"} allowClear={true} placeholder="Thứ tự sắp xếp">
                                <Select.Option key="LOW2HIGH">Giá tăng dần</Select.Option>
                                <Select.Option key="HIGH2LOW">Giá giảm dần</Select.Option>
                                <Select.Option key="RECENTLY_CREATED">Được tạo mới gần đây</Select.Option>
                                <Select.Option key="MOST_FAVORITE">Được yêu thích nhất</Select.Option>
                            </SelectStyled>
                        </Form.Item>
                    </Col>

                    <Col className="gutter-row" span={6}>
                        <Form.Item name="orderSort">
                            <Button
                                key="auction-submit-btn"
                                htmlType="submit"
                                type="primary"
                                style={{
                                    height: '38px',
                                    borderRadius: '8px',
                                    width: '150px'
                                }}
                            >
                                Tìm kiếm
                            </Button>
                        </Form.Item>
                    </Col>
                </Row>
            </Form>
        </div>
    )
}

const SelectStyled = styled(Select)`
    border-radius: 8px;
    width: 1000px;
    
    .ant-select-selector {
        border-radius: 8px !important;

    }
`

export default SearchBar;