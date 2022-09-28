import React, {useEffect, useState} from 'react';
import {RootState} from "../../../redux/reducers";
import {connect, ConnectedProps} from "react-redux";
import styled from "styled-components";
import {Button, Form, Input, Upload} from "antd";
import TextArea from "antd/es/input/TextArea";
import {NotificationError, NotificationSuccess} from "../../../components/Notification/Notification";
import {getUserInfo, updateUserInfo} from "../services/apis";
import Loading from "../../../components/Loading";
import {LoadingOutlined, PlusOutlined} from "@ant-design/icons";
import {RcFile} from "antd/es/upload/interface";
import {uploadImage} from "../../../helpers/uploadImage";
import {useHistory} from "react-router";

const mapState = (rootState: RootState) => ({
    auth: rootState.auth.auth
});

const connector = connect(mapState, {});
type PropsFromRedux = ConnectedProps<typeof connector>;

interface IProps extends PropsFromRedux {

}

interface UserData{
    nickname: string,
    fullName: string,
    description: string,
    email: string,
    phoneNumber: string,
    walletAddress: string,
    avatarUrl: string
}

const UserDetailPage = (props: IProps) => {

    const history = useHistory();

    const [loading, setLoading] = useState(false);
    const [imageLoading, setImageLoading] = useState(false);
    const [imageList, setImageList] = useState<any>([]);
    const [avatarUrl, setAvatarUrl] = useState('');

    const [form] = Form.useForm();

    useEffect(() => {
        if(props.auth.data?.token == undefined){
            history.push('/connect-wallet');
        }
        setLoading(true);
        getUserInfo().then(rs => {
            setLoading(false);
            if(rs.code !== 0){
                NotificationError('', '');
                return;
            }
            setAvatarUrl(rs.item.avatar);
            form.setFieldsValue({
                nickName: rs.item.nickname,
                fullName: rs.item.fullName,
                description: rs.item.description,
                email: rs.item.email,
                phoneNumber: rs.item.phoneNumber,
                address: rs.item.walletAddress
            });
        }).catch(() => {
            setLoading(false);
            NotificationError('', '');
        })
    }, []);

    const handleImageChange = (info: any) => {
        let fileList: any = [...info.fileList];
        fileList = fileList.slice(-1);
        setImageList(fileList);
    };

    const beforeUploadImage = (file: RcFile) => {
        const isJpgOrPng = file.type === 'image/jpeg' || file.type === 'image/png' || file.type === 'image/jpg';
        if (!isJpgOrPng) {
            NotificationError('', 'You can only upload JPG/PNG file!');
            return;
        }
        // const isLt2M = file.size / 1024 / 1024 < 2;
        // if (!isLt2M) {
        //     NotificationError('', 'Image must smaller than 2MB!');
        //     return;
        // }
        setImageLoading(true);
        uploadImage(file).then(rs => {
            if (rs.code != 0) {
                NotificationError('', 'Upload file error');
                return;
            }
            setAvatarUrl(rs.item);
            setImageLoading(false);
        });
    };

    const layout = {
        labelCol: {span: 24},
        wrapperCol: {span: 24},
    };

    const onSubmitCreate = (value: any) => {
        if(avatarUrl === ''){
            NotificationError('Thất bại', 'Vui lòng upload ảnh')
            return;
        }
        setLoading(true);

        const updateRequest = {
            fullName: value.fullName,
            nickName: value.nickName,
            description: value.description,
            email: value.email,
            phoneNumber: value.phoneNumber,
            avatarUrl: avatarUrl
        }

        updateUserInfo(updateRequest).then(rs => {
            setLoading(false);
            if(rs.code != 0) {
                NotificationError("", "");
            }
            console.log(JSON.stringify(rs));
            NotificationSuccess('Thành công', 'Cập nhật thông tin thành công');
        }).catch(() => {
            setLoading(false);
        })
    }

    return(
        <Wrapper>
            <br/>
            <p style={{fontSize: '24px', fontWeight: '600', display: 'flex'}}>Thông tin người dùng</p>
            <Form name="basic"   {...layout}
                  onFinish={onSubmitCreate}
                  form={form}
            >

                <Form.Item name="avatar" label="Ảnh"
                           className="font-title-form"
                           style={{width: '400px'}}
                >
                    <Upload
                        fileList={imageList}
                        listType="picture-card"
                        className="avatar-uploader"
                        onChange={handleImageChange}
                        beforeUpload={beforeUploadImage}
                        showUploadList={false}>
                        {avatarUrl != '' ?
                            <img src={avatarUrl} alt="avatar" style={{height: '100%'}}/>
                            :
                            (
                                <div>
                                    {imageLoading ? <LoadingOutlined/> : <PlusOutlined/>}
                                    <div style={{marginTop: 8}}>Upload</div>
                                </div>
                            )
                        }
                    </Upload>
                </Form.Item>

                <Form.Item name="nickName" label="Tên người dùng"
                           className="font-title-form"
                           rules={[{ required: true, message: 'Vui lòng điền tên nguời dùng' }]}
                >
                    <Input placeholder="Tên người dùng" className="input-form"/>
                </Form.Item>

                <Form.Item name="fullName" label="Họ và tên"
                           className="font-title-form"
                           rules={[{ required: true, message: 'Vui lòng điền tên nguời dùng' }]}
                >
                    <Input placeholder="Họ và tên" className="input-form"/>
                </Form.Item>

                <Form.Item name="description" label="Giới thiệu bản thân"
                           className="font-title-form"
                           rules={[{ required: true, message: 'Vui lòng điền giới thiệu bản thân' }]}
                >
                    <TextArea placeholder="Giới thiệu bản thân" className="text-area-form"/>
                </Form.Item>

                <Form.Item name="email" label="Email"
                           className="font-title-form"
                           rules={[{ required: true, message: 'Vui lòng điền email' }]}
                >
                    <Input placeholder="Email" className="input-form"/>
                </Form.Item>

                <Form.Item name="phoneNumber" label="Số điện thoại"
                           className="font-title-form"
                >
                    <Input placeholder="Số điện thoại" className="input-form"/>
                </Form.Item>

                <Form.Item name="address" label="Địa chỉ ví"
                           className="font-title-form"
                >
                    <Input readOnly={true} className="input-form"/>
                </Form.Item>

                <br/>
                <br/>
                <Form.Item>
                    <Button type="primary" htmlType="submit" size={"large"}
                            style={{
                                borderRadius: '12px', display: "flex", fontWeight: '600px', width: '150px',
                                height: '70px', fontSize: "24px", paddingLeft: '25px', paddingTop: '15px'
                            }}>
                        Cập nhật
                    </Button>
                </Form.Item>

            </Form>
            {loading ? <Loading/> : null}
        </Wrapper>
    )
}

const Wrapper = styled.div`

  margin-left: 10%;
  width: 80%;
  marginTop: 20px;
 `;

export default connector(UserDetailPage);