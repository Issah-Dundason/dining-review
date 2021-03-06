import { Toaster } from "react-hot-toast";
import { useDispatch, useSelector } from "react-redux";
import { BrowserRouter, Route, Routes } from "react-router-dom";
import FoodsPage from "./components/regular/FoodsPage";
import Header from "./components/regular/Header";
import LoginPage from "./components/regular/LoginPage";
import RestaurantsPage from "./components/regular/RestaurantsPage";
import { checkAndAssignToken, getAuthenticated } from "./features/auth/authSlice";


function App() {

  const isAuthenticated = useSelector(getAuthenticated);
  const dispatch = useDispatch();

  checkAndAssignToken(isAuthenticated, dispatch);

  if(!isAuthenticated) {
    return <LoginPage/>
  }

  return (
    <BrowserRouter>

      <Header/>
      <Toaster/>
      <Routes>
        <Route path="/" element={<RestaurantsPage/>}/>
        <Route path="/food" element={<FoodsPage/>} />
      </Routes>

    </BrowserRouter>
  );

}

export default App;
