import {put, take} from 'redux-saga/effects';
import {login, LOGOUT} from '../actions';
import {TokenInfo} from '../../types';
import {TOKEN_KEY} from "../../../../constants/common";

export function* loginCheckerAsync() {
  while (1) {
    const savedToken = localStorage.getItem(TOKEN_KEY);
    if (savedToken && savedToken !== '{}') {
      const tokeInfo: TokenInfo = JSON.parse(savedToken);
      yield put(login(tokeInfo));
    }
    yield take(LOGOUT);
  }
}

export function* logoutAsync(){
  localStorage.removeItem(TOKEN_KEY);
}
