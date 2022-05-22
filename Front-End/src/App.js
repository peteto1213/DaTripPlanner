import React from "react"
import './App.css';
import {HashRouter} from "react-router-dom";
import MainComponent from "./components/MainComponent";

 function App() {

  return (
    <div className="App">
      <HashRouter>
        <MainComponent />
      </HashRouter>
    </div>
  );
}

export default App;

