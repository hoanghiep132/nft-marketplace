import {LoginInput, LoginResponse} from '../types';
import {POST, PUT} from '../../../services';

export const login = (data?: LoginInput): Promise<LoginResponse> => {
  return POST('account-svc/users/auth/login', data);
};

export const changePassword = async (data?: any): Promise<any> => {
  const id = data.id;
  delete data.id;
  return PUT('hrmcoresvc/employees/' + id + '/password', data);
};
