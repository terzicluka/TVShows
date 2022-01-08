import logo from './logo.svg';
import './App.css';
import { useAuth0 } from "@auth0/auth0-react"
import axios from 'axios'
import React, { useState, useEffect } from 'react';

function App() {

  const { loginWithRedirect, isAuthenticated, logout, getAccessTokenSilently } = useAuth0();
  const [shown, setShown] = useState(false);
  const [user, setUser] = useState();

  function changeShownState() {
    setShown(false);
  }

  async function getUserInfo() {
    try {
      const token = await getAccessTokenSilently();
      const response = await axios.get("http://localhost:9000/profile", { headers: { authorization: `Bearer ${token}` } });
      setUser(response.data);
      setShown(true);
    } catch (err) {
      alert(err.message);
    }
  }

  async function secureApiCall() {
    try {
      const token = await getAccessTokenSilently();
      const returnedCSV = await axios.get("http://localhost:4000/download/CSV", { headers: { authorization: `Bearer ${token}` } });
      let url = window.URL.createObjectURL(new Blob([returnedCSV.data]));
      let link = document.createElement('a');
      link.href = url;
      link.setAttribute('download', 'TVShows.csv');
      document.body.appendChild(link);
      link.click();
      const returnedJSON = await axios.get("http://localhost:4000/download/JSON", { headers: { authorization: `Bearer ${token}` } });
      const fileName = "TVShows";
      const json = JSON.stringify(returnedJSON.data);
      const blob = new Blob([json], { type: 'application/json' });
      const href = await URL.createObjectURL(blob);
      link = document.createElement('a');
      link.href = href;
      link.download = fileName + ".json";
      document.body.appendChild(link);
      link.click();
      document.body.removeChild(link);
    } catch (err) {
      alert(err.message);
    }
  }

  if (isAuthenticated && !shown) {
    return (
      <div className="App">
        <h1>Četvrta laboratorijska vježba</h1>
        <h1>Korisnik {isAuthenticated ? "je prijavljen" : "nije prijavljen"}</h1>
        <button onClick={getUserInfo}>Korisnički profil</button>
        <button onClick={secureApiCall}>Osvježi preslike</button>
        <button onClick={logout}> Logout </button>
      </div>
    );
  } else if (isAuthenticated && shown) {
    return (
      <div className="App">
        <h1>Četvrta laboratorijska vježba</h1>
        <h1>Korisnik {isAuthenticated ? "je prijavljen" : "nije prijavljen"}</h1>
        <button onClick={changeShownState}>Sakrij podatke</button>
        <button onClick={secureApiCall}>Osvježi preslike</button>
        <button onClick={logout}> Logout </button>
        <h3>Email: {user.email}</h3>
        <h3>Name: {user.name}</h3>
        <h3>Nickname: {user.nickname}</h3>
      </div>
    );
  } else {
    return (
      <div className="App">
        <h1>Četvrta laboratorijska vježba</h1>
        <h1>Korisnik {isAuthenticated ? "je prijavljen" : "nije prijavljen"}</h1>
        <button onClick={loginWithRedirect}>Prijava</button>
      </div>
    );
  }
}

export default App;


