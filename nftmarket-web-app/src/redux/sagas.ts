import { all } from 'redux-saga/effects';
import authSaga from '../modules/Auth/redux/sagas';

export default function* rootSaga() {
  yield all([
    authSaga(),
  ]);
}
