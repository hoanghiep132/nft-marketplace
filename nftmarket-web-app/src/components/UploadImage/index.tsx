import React from 'react';
import {LoadingOutlined, PlusOutlined} from '@ant-design/icons';
import {NotificationError} from "../Notification/Notification";
import {Upload} from "antd";

interface IProps {

}

const UploadImage = (props: IProps) => {

    function getBase64(img: any, callback: any) {
        const reader = new FileReader();
        reader.addEventListener('load', () => callback(reader.result));
        reader.readAsDataURL(img);
    }

    function beforeUpload(file: any) {
        const isJpgOrPng = file.type === 'image/jpeg' || file.type === 'image/png';
        if (!isJpgOrPng) {
            NotificationError('', '');
            // message.error('You can only upload JPG/PNG file!');
        }
        const isLt2M = file.size / 1024 / 1024 < 2;
        if (!isLt2M) {
            NotificationError('', '');
            // message.error('Image must smaller than 2MB!');
        }
        return isJpgOrPng && isLt2M;
    }

    const handleChange = (info: any) => {
        if (info.file.status === 'uploading') {
            return;
        }
        if (info.file.status === 'done') {
            // Get this url from response in real world.
            // getBase64(info.file.originFileObj, imageUrl =>
            //     this.setState({
            //         imageUrl,
            //         loading: false,
            //     }),
            // );
        }
    };

    const render = () => {
        // const {loading, imageUrl} = this.state;
        const uploadButton = (
            <div>
                {/*{loading ? <LoadingOutlined/> : <PlusOutlined/>}*/}
                <div style={{marginTop: 8}}>Upload</div>
            </div>
        );
    }

        return (
            <Upload
                name="avatar"
                listType="picture-card"
                className="avatar-uploader"
                showUploadList={false}
                beforeUpload={beforeUpload}
                onChange={handleChange}
            >
                {/*{imageUrl ? <img src={imageUrl} alt="avatar" style={{width: '100%'}}/> : uploadButton}*/}
            </Upload>
        );
    }

    export default UploadImage;
