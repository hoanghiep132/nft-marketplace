import React from 'react';
import './App.css';
import history from './history';
import {Redirect, Route, Router} from 'react-router-dom';
import DefaultLayout from "./components/layout/DefaultLayout";
import Routes from './routes';
import AuthLayout from "./components/layout/AuthLayout";
import {useAppSelector} from "./redux/hooks";
import HomePage from "./modules/HomePage/components/HomePage";
import ConnectWalletPage from "./modules/ConnectWallet/pages/ConnectWalletPage";

function App() {
    // const isLogin = useAppSelector(state => !!state.auth.auth.data?.token);
    const isLogin = true;

    return (
        <div className="App">
            <Router history={history}>
                {isLogin ? (
                    <DefaultLayout>
                        <Routes/>
                    </DefaultLayout>
                ) : (
                    <AuthLayout>
                        <HomePage/>
                    </AuthLayout>
                )}
            </Router>
        </div>
    );
}

export default App;
