import {TokenInfo} from '../../types';
import {ActionBase} from '../../../../models/common';

export const LOGIN = 'LOGIN';
export const LOGIN_SUCCESS = 'LOGIN_SUCCESS';
export const LOGIN_ERROR = 'LOGIN_ERROR';

export const LOGOUT = 'LOGOUT';
export const LOGOUT_SUCCESS = 'LOGOUT_SUCCESS';
export const LOGOUT_ERROR = 'LOGOUT_ERROR';

export interface LoginAction extends ActionBase<TokenInfo> {
}

export const login = (payload: TokenInfo): LoginAction => {
  return {
    type: LOGIN_SUCCESS,
    payload,
  }
};

export const logout = (): LoginAction => {
  return {
    type: LOGOUT
  }
}