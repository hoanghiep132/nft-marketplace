import { combineReducers } from 'redux';
import auth, { AuthModuleState } from '../modules/Auth/redux/reducers';

export interface RootState {
  auth: AuthModuleState;
}

export default combineReducers<RootState>({
  auth,
});
