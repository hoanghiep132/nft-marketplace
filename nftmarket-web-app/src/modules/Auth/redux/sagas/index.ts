import {all, fork, takeLatest} from 'redux-saga/effects';
import {loginCheckerAsync, logoutAsync} from './auth';
import {changePasswordAsync} from './changePassword';
import {CHANGE_PASSWORD_ACTION, LOGOUT} from '../actions';

export default function* root() {
  return all([
    yield fork(loginCheckerAsync),
    yield takeLatest(CHANGE_PASSWORD_ACTION, changePasswordAsync),
    yield takeLatest(LOGOUT, logoutAsync)
  ]);
}
