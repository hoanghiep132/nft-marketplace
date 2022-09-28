import React from 'react';
import {Redirect, Route, Switch} from 'react-router-dom';
import {connect, ConnectedProps} from 'react-redux';

import {RootState} from './redux/reducers';
import HomePage from "./modules/HomePage/components/HomePage";
import ConnectWalletPage from "./modules/ConnectWallet/pages/ConnectWalletPage";
import CreateCollectionPage from "./modules/Collection/pages/CreateCollectionPage";
import CreateItemPage from "./modules/Item/pages/CreateItemPage";
import CollectionListPage from "./modules/Collection/pages/list/CollectionListPage";
import CollectionDetailPage from "./modules/Collection/pages/detail/CollectionDetailPage";
import ItemDetailPage from "./modules/Item/detail/ItemDetailPage";
import YourCollectionPage from "./modules/Collection/pages/yourCollection/YourCollectionPage";
import UserDetailPage from "./modules/User/pages/UserDetailPage";
import MyFavoritePage from "./modules/MyFavorite/pages/MyFavoritePage";
import AccountInfo from "./modules/User/pages/AccountInfo";
import MyOwnerItemPage from "./modules/MyFavorite/pages/MyOwnerItemPage";

const mapStateToProps = (state: RootState) => {
  return {
    isLogin: !!state.auth.auth.data?.token,
    auth: state.auth.auth.data,
  };
};

const connector = connect(mapStateToProps, {});
type PropsFromRedux = ConnectedProps<typeof connector>;

const Routes = (props: PropsFromRedux) => {
  return (
    <div>
      <Switch>
        <Route exact path="/">
          <Redirect to="/collection" />
        </Route>
        <Route path='/home' component={HomePage} />
        <Route exact path='/connect-wallet' component={ConnectWalletPage} />
        <Route exact path='/collection' component={CollectionListPage} />
        <Route exact path='/your-collection' component={YourCollectionPage} />
        <Route exact path='/your-favorites' component={MyFavoritePage} />
        <Route exact path='/your-items' component={MyOwnerItemPage} />
        <Route exact path='/collection/:uuid' component={CollectionDetailPage} />
        <Route exact path='/new-collection/create' component={CreateCollectionPage} />
        <Route exact path='/new-item/create' component={CreateItemPage} />
        <Route exact path='/item/:uuid' component={ItemDetailPage} />
        <Route exact path='/profile' component={UserDetailPage}/>
        <Route exact path='/account/:address' component={AccountInfo}/>
      </Switch>
    </div>
  );
};

export default  connector(Routes);
