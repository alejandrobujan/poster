import React from "react";
import { Link } from "react-router-dom";
import "./App.css";

const Test = () => {
  return (
    <div className="App">
      <header className="App-header">
        <img
          src={process.env.PUBLIC_URL + "/assets/logo.svg"}
          className="App-logo"
          alt="logo"
        />
        <h1 className="App-title">This is the page Test</h1>
      </header>
      <p className="App-intro">Testing Route redirection...</p>
      <Link to="/">Home</Link>
    </div>
  );
};

export default Test;
