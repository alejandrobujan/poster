import React from "react";

import { Route, Routes } from "react-router-dom";

import Home from "./Home";
import Test from "./Test";

const Body = () => {
  return (
    <Routes>
      <Route path="/">
        <Route index exact element={<Home />} />
        <Route path="/test" element={<Test />} />
      </Route>
    </Routes>
  );
};

export default Body;
