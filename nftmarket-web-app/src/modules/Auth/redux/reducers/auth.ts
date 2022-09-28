import * as actions from '../actions';
import {LoginAction} from '../actions';
import {LoginResponse, TokenInfo} from '../../types';
import {AppError} from '../../../../models/common';

export interface LoginState {
  loading: boolean;
  data?: TokenInfo;
  error?: AppError;
}

const initialState = {
  loading: false,
};

export default (state = initialState, action: LoginAction): LoginState => {
  switch (action.type) {
    case actions.LOGIN_SUCCESS:
      return {
        ...state,
        loading: false,
        data: action.payload as TokenInfo,
      };

    case actions.LOGOUT:
      return {
        ...state,
        data: {
          token: '',
          walletAddress: '',
          walletType: ''
        }
      }
    default:
      return state;
  }

};
