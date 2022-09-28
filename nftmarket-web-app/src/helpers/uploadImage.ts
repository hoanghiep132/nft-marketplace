import {POSTIMAGE} from "../services";

export const uploadImage = (file?: any): Promise<any> => {
    let formData = new FormData();
    formData.append("file", file);
    return POSTIMAGE('api-svc/file/upload', formData, {
    // return POSTIMAGE('file/upload', formData, {
        headers: {
            'Content-Type': 'multipart/form-data',
        },
    });
}