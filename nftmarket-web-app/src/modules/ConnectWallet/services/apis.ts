import {SendMessageRequest} from "../../HomePage/redux/models";
import {POST} from "../../../services";

export const connectRequest = (params?: SendMessageRequest): Promise<any> => {
    return POST('account-svc/message', params);
};
