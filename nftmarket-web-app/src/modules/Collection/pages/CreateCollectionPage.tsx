import React, {useEffect, useState} from 'react';
import {Button, Form, Input, Select, Upload} from 'antd';
import {LoadingOutlined, PlusOutlined} from "@ant-design/icons";
import TextArea from "antd/es/input/TextArea";
import {RootState} from "../../../redux/reducers";
import {connect, ConnectedProps} from "react-redux";
import type {RcFile} from 'antd/es/upload/interface';
import {NotificationError} from "../../../components/Notification/Notification";
import {uploadImage} from "../../../helpers/uploadImage";
import {createCollection, createCollectionContract} from "../services/apis";
import Loading from "../../../components/Loading";
import {useHistory} from "react-router";

const mapState = (rootState: RootState) => ({
    auth: rootState.auth.auth
});

const connector = connect(mapState, {});
type PropsFromRedux = ConnectedProps<typeof connector>;

interface IProps extends PropsFromRedux {

}

const CreateCollectionPage = (props: IProps) => {

    const history = useHistory();

    useEffect(() => {
        if(props.auth.data?.token == undefined || props.auth.data?.token == ''){
            history.push("/connect-wallet");
            return;
        }
    }, []);

    const [loading, setLoading] = useState(false);

    const [imageLoading, setImageLoading] = useState(false);
    const [bannerLoading, setBannerLoading] = useState(false);

    const [imageUrl, setImageUrl] = useState('');
    const [bannerUrl, setBannerUrl] = useState('');

    const [imageList, setImageList] = useState<any>([])
    const [bannerList, setBannerList] = useState([])
    const layout = {
        labelCol: {span: 24},
        wrapperCol: {span: 24},
    };

    const handleImageChange = (info: any) => {
        let fileList: any = [...info.fileList];
        fileList = fileList.slice(-1);
        setImageList(fileList);
    };

    const handleBannerChange = (info: any) => {
        let fileList: any = [...info.fileList];
        fileList = fileList.slice(-1);
        setBannerList(fileList);
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
        //     return ;
        // }
        setImageLoading(true);
        uploadImage(file).then(rs => {
            console.log(JSON.stringify(rs));
            if (rs.code != 0) {
                NotificationError('', 'Upload file error');
                setImageLoading(false);
                return;
            }
            setImageUrl(rs.item);
            setImageLoading(false);
        });
    };

    const beforeUploadBanner = (file: RcFile) => {
        const isJpgOrPng = file.type === 'image/jpeg' || file.type === 'image/png' || file.type === 'image/jpg';
        if (!isJpgOrPng) {
            NotificationError('', 'You can only upload JPG/PNG file!');
            return;
        }
        // const isLt2M = file.size / 1024 / 1024 < 2;
        // if (!isLt2M) {
        //     NotificationError('', 'Image must smaller than 2MB!');
        //     return ;
        // }
        setBannerLoading(true);
        uploadImage(file).then(rs => {
            if (rs.code != 0) {
                NotificationError('', 'Upload file error');
                setBannerLoading(false);
                return;
            }
            setBannerUrl(rs.item);
            setBannerLoading(false);
        });
    };

    const onSubmitCreate = async (value: any) => {
        setLoading(true);
        const contractRs = await createCollectionContract(value.name, value.symbol);
        console.log(JSON.stringify(contractRs));
        const txnHash = contractRs.transactionHash;
        const blockHash = contractRs.blockHash;
        // const collectionAddress = contractRs.events.CreateCollection.returnValues;
        const rawCollectionAddress: string = contractRs.events.CreateCollection.raw.data;
        const contractAddress = rawCollectionAddress.replace('0x000000000000000000000000', '0x');
        console.log("address: " + JSON.stringify(contractAddress));
        const createRequest: any = {
            name: value.name,
            symbol: value.symbol,
            description: value.description,
            categories: value.categories,
            imageUrl: imageUrl,
            bannerUrl: bannerUrl,
            txnHash: txnHash,
            blockHash: blockHash,
            contractAddress: contractAddress
        }

        createCollection(createRequest).then(rs => {
            setLoading(false);
            if(rs.code != 0) {
                NotificationError("", "");
            }
            history.push("/collection/" + rs.item);
            // console.log(JSON.stringify(rs));
            // NotificationSuccess('Thành công', 'Tạo bộ sưu tập mới thành công');
        }).catch(() => {
            setLoading(false);
        });
    }

    return (
        <div style={{margin: 'auto', width: '70%'}}>
            <div style={{
                display: "flex", fontWeight: "600", fontSize: "40px", letterSpacing: "0px",
                color: " rgb(4, 17, 29)"
            }}>TẠO BỘ SƯU TẬP MỚI
            </div>
            <Form name="basic"   {...layout}
                  onFinish={onSubmitCreate}
            >
                <Form.Item name="name" label="Tên bộ sưu tập"
                           className="font-title-form"
                    // rules={[{ required: true, message: 'Vui lòng điền tên bộ sưu tập' }]}
                >
                    <Input placeholder="Tên bộ sưu tập" className="input-form"/>
                </Form.Item>

                <Form.Item name="symbol" label="Ký hiệu"
                           className="font-title-form"
                    // rules={[{ required: true, message: 'Vui lòng điền ký hiệu bộ sưu tập' }]}
                >
                    <Input placeholder="Ký hiệu bộ sưu tập" className="input-form"/>
                </Form.Item>

                <Form.Item name="description" label="Mô tả"
                           className="font-title-form"
                    // rules={[{ required: true, message: 'Vui lòng điền tên bộ sưu tập' }]}
                >
                    <TextArea placeholder="Mô tả bộ sưu tập" className="text-area-form"/>
                </Form.Item>


                <Form.Item name="image" label="Ảnh Logo"
                           className="font-title-form"
                           style={{width: '400px'}}
                    // rules={[{ required: true, message: 'Vui lòng điền tên bộ sưu tập' }]}

                >
                    <Upload
                        name="avatar"
                        fileList={imageList}
                        listType="picture-card"
                        className="avatar-uploader"
                        onChange={handleImageChange}
                        beforeUpload={beforeUploadImage}
                        showUploadList={false}>

                        {imageUrl !== '' ?
                            <img src={imageUrl} alt="avatar" style={{height: '100%'}}/>
                            :
                            <div>
                                {imageLoading ? <LoadingOutlined/> : <PlusOutlined/>}
                                <div style={{marginTop: 8}}>Upload</div>
                            </div>
                        }
                    </Upload>
                </Form.Item>


                <Form.Item name="banner" label="Ảnh Banner"
                           className="font-title-form"
                           style={{width: '400px'}}
                    // rules={[{ required: true, message: 'Vui lòng điền tên bộ sưu tập' }]}
                >
                    <Upload
                        fileList={bannerList}
                        listType="picture-card"
                        className="avatar-uploader"
                        onChange={handleBannerChange}
                        beforeUpload={beforeUploadBanner}
                        showUploadList={false}>
                        {bannerUrl !== '' ?
                            <img src={bannerUrl} alt="avatar" style={{height: '100%'}}/>
                            :
                            (
                                <div>
                                    {bannerLoading ? <LoadingOutlined/> : <PlusOutlined/>}
                                    <div style={{marginTop: 8}}>Upload</div>
                                </div>
                            )
                        }
                    </Upload>
                </Form.Item>

                <Form.Item name="categories" label="Danh mục">
                    <Select mode="multiple">
                        <Select.Option key="art">Art</Select.Option>
                        <Select.Option key="photography">Photography</Select.Option>
                        <Select.Option key="music">Music</Select.Option>
                        <Select.Option key="sport">Sport</Select.Option>
                        <Select.Option key="gaming">Gaming</Select.Option>
                        <Select.Option key="fashion">Fashion</Select.Option>
                        <Select.Option key="toy">Toy</Select.Option>
                    </Select>
                </Form.Item>
                <br/>
                <br/>
                <Form.Item>
                    <Button type="primary" htmlType="submit" size={"large"}
                            style={{
                                borderRadius: '12px', display: "flex", fontWeight: '600px', width: '150px',
                                height: '70px', fontSize: "24px", paddingLeft: '25px', paddingTop: '15px'
                            }}>
                        Tạo mới
                    </Button>
                </Form.Item>
            </Form>
            {loading ? <Loading/> : null}
        </div>
    )
}

export default connector(CreateCollectionPage);
