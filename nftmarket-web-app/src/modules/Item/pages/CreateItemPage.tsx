import React, {useEffect, useState} from 'react';
import {Button, Form, Input, Select, Upload} from "antd";
import {RcFile} from "antd/es/upload/interface";
import {NotificationError, NotificationSuccess} from "../../../components/Notification/Notification";
import {uploadImage} from "../../../helpers/uploadImage";
import {LoadingOutlined, PlusOutlined} from "@ant-design/icons";
import TextArea from "antd/es/input/TextArea";
import {createItem, createDefaultItemContract, createItemContract, getOwnCollectionList} from "../service/apis";
import {RootState} from "../../../redux/reducers";
import {connect, ConnectedProps} from "react-redux";
import {getOwnerCollectionOptionList} from "../../Collection/services/apis";
import Loading from "../../../components/Loading";
import {useHistory} from "react-router";

const mapState = (rootState: RootState) => ({
    auth: rootState.auth.auth
});

const connector = connect(mapState, {});
type PropsFromRedux = ConnectedProps<typeof connector>;

interface IProps extends PropsFromRedux {

}


const CreateItemPage = (props: IProps) => {

    const history = useHistory();

    const [collectionOptions, setCollectionOptions] = useState<any>([]);

    const [collectionInfo, setCollectionInfo] = useState<any>();

    useEffect(() => {
        if(props.auth.data?.token == undefined || props.auth.data?.token == ''){
            history.push("/connect-wallet");
            return;
        }

        getOwnerCollectionOptionList().then(rs => {
            if(rs.code != 0){
                NotificationError("", "");
                return;
            }
            const collectionOptionList = rs.rows.map((item: any) => {
                const key = item.collectionUuid + "#" + item.contractAddress;
                return (
                    <Select.Option key={key}>
                        {item.name} ( {item.symbol}  )
                    </Select.Option>
                )
            });
            setCollectionOptions(collectionOptionList);
        }).catch(() => NotificationError("", ""));
    }, []);

    const [loading, setLoading] = useState(false);

    const [imageLoading, setImageLoading] = useState(false);

    const [imageUrl, setImageUrl] = useState('');

    const [imageList, setImageList] = useState<any>([])
    const layout = {
        labelCol: {span: 24},
        wrapperCol: {span: 24},
    };

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
            setImageUrl(rs.item);
            setImageLoading(false);
        });
    };

    const onSubmitCreate = async (value: any) => {
        if(imageUrl === ''){
            NotificationError('Thất bại', 'Vui lòng upload ảnh')
            return;
        }
        setLoading(true);

        // const contractRs = await createDefaultItemContract(testUlr);
        const contractRs = await createItemContract(imageUrl, collectionInfo.contractAddress);
        console.log(JSON.stringify(contractRs));
        const tnxHash = contractRs.transactionHash;
        const blockHash = contractRs.blockHash;
        const tokenId = contractRs.events.Transfer.returnValues.tokenId;


        const createRequest = {
            name: value.name,
            collectionUuid: collectionInfo.uuid,
            description: value.description,
            imageUrl: imageUrl,
            tokenId: tokenId,
            tnxHash: tnxHash,
            blockHash: blockHash
        }

        createItem(createRequest).then(rs => {
            setLoading(false);
            if(rs.code != 0) {
                NotificationError("", "");
            }
            history.push("/item/" + rs.item);
            // console.log(JSON.stringify(rs));
            // NotificationSuccess('Thành công', 'Tạo bộ sưu tập mới thành công');
        }).catch(() => {
            setLoading(false);
        })
    }

    const onChangeCollection = (value: any) => {
        const data: string[] = value.split("#");
        if (data.length != 2) {
            return;
        }
        const collectionInfo = {
            uuid: data[0],
            contractAddress: data[1]
        }
        setCollectionInfo(collectionInfo);
    }

    return (
        <div style={{margin: 'auto', width: '70%'}}>
            <div style={{
                display: "flex", fontWeight: "600", fontSize: "40px", letterSpacing: "0px",
                color: " rgb(4, 17, 29)"
            }}>
                TẠO VẬT PHẨM MỚI
            </div>
            <Form name="basic"   {...layout}
                  onFinish={onSubmitCreate}
            >

                <Form.Item name="name" label="Tên vật phẩm"
                           className="font-title-form"
                    rules={[{ required: true, message: 'Vui lòng điền tên bộ sưu tập' }]}
                >
                    <Input placeholder="Tên vật phẩm" className="input-form"/>
                </Form.Item>

                <Form.Item name="description" label="Mô tả"
                           className="font-title-form"
                    rules={[{ required: true, message: 'Vui lòng điền tên bộ sưu tập' }]}
                >
                    <TextArea placeholder="Mô tả bộ sưu tập" className="text-area-form"/>
                </Form.Item>

                <Form.Item name="collection"
                           label="Bộ sưu tập"
                    // rules={[{ required: true, message: 'Vui lòng điền tên bộ sưu tập' }]}
                >
                    <Select onChange={onChangeCollection}>
                        {collectionOptions}
                    </Select>
                </Form.Item>

                <Form.Item name="image" label="Ảnh"
                           className="font-title-form"
                           style={{width: '400px'}}
                    // rules={[{ required: true, message: 'Vui lòng điền tên bộ sưu tập' }]}
                >
                    <Upload
                        fileList={imageList}
                        listType="picture-card"
                        className="avatar-uploader"
                        onChange={handleImageChange}
                        beforeUpload={beforeUploadImage}
                        showUploadList={false}>
                        {imageUrl != '' ?
                            <img src={imageUrl} alt="avatar" style={{height: '100%'}}/>
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

export default connector(CreateItemPage);
