import React from 'react';
import ReactDOM from 'react-dom';
import './index.css';
import App from './App';
import { Auth0Provider } from '@auth0/auth0-react';

ReactDOM.render(
  <React.StrictMode>
    <Auth0Provider domain='dev-5fsypyq5.us.auth0.com' clientId='09EFV6GLQf0gvbYav56CBV98hWWFjyw9' redirectUri={window.location.origin} audience='http://localhost:4000/' scope='openid email' >
      <App />
    </Auth0Provider>
  </React.StrictMode>,
  document.getElementById('root')
);

