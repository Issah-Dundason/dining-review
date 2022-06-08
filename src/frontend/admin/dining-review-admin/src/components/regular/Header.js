import { StyledHeader } from "../styled/Styled.Header";
import {FaStar} from "react-icons/fa";
import {HiMenu} from "react-icons/hi";
import {GrClose} from "react-icons/gr";
import {useDispatch, useSelector} from "react-redux";
import { toggle} from "../../features/menu/menuSlice";
import { Link } from "react-router-dom";
import react from "react";

export default function Header() {
    const isMenuVisible = useSelector((state) => state.menuVisibility.value);
    const dispatch = useDispatch();
    const [pageTitle, setPageTitle] = react.useState("Dining Review")
    
    return(
        <StyledHeader isMenuVisible>
            <div className="logo-box">
                <div className="logo">
                    <FaStar/>
                </div>
                <p>{pageTitle}</p>
            </div>
            <div className="menu" onClick={() => dispatch(toggle(!isMenuVisible))}>
                {isMenuVisible ? <GrClose /> : <HiMenu/>}
            </div>
            <ul className= {`${isMenuVisible ? "visible-menu" : "hidden-menu"}`}>

            <Link to="/" style={{ textDecoration: 'none', color: "white" }} 
                onClick={() => setPageTitle("Restaurants")}>
                <li>Restaurants</li>
            </Link>

            <Link to="/food" style={{ textDecoration: 'none', color: "white"}} 
                onClick={() => setPageTitle("Food")}>
                <li>Food</li>
            </Link>
            <Link to="/pending-review" style={{ textDecoration: 'none', color: "white" }} 
                onClick={() => setPageTitle("Pending Reviews")}>
                <li>Pending</li>
            </Link>
            </ul>
        </StyledHeader>
    );
}