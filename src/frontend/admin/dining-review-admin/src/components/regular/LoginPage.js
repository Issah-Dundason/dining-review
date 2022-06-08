import { StyledLogin } from "../styled/Styled.LoginPage";
import {StyledButton} from "../styled/Styled.Button"
import LoadingIcon from "./LoadingIcon";
import { useDispatch, useSelector } from "react-redux";
import { getStatus, loginUser } from "../../features/auth/authSlice";
import react from "react";

export default function LoginPage() {
    const status = useSelector(getStatus);
    const dispatch = useDispatch();

    const [userCredentials, setUserCredentials] = react.useState({
        displayName: "",
        password: ""
    });

    function updateCredentials(e) {
        setUserCredentials(old => {
          return  {
              ...old,
              [e.target.name] : e.target.value
          };
        })
    }

    function submit() {
       dispatch(loginUser(userCredentials));
    }

    function onKeyPress(e) {
        if(e.charCode === 13 || e.code === "Enter") {
            submit();
        }
    }

    return(
        <StyledLogin onKeyPress={onKeyPress}>
            <div className="wrapper">
                <div className="form">
                    <p className="heading">Login</p>
                    <div className="field">
                        <label>Display Name</label>
                        <input id="display-name" placeholder="Display Name" name="displayName" 
                        onChange={updateCredentials} value={userCredentials.displayName}/>
                    </div>

                    <div className="field">
                        <label>Password</label>
                        <input id="password" placeholder="Password" type={"password"} 
                        name="password" onChange={updateCredentials} value={userCredentials.password}/>
                    </div>

                    {
                        status === "connecting" ? <LoadingIcon/> : <></>
                    }

                    {
                        status === "failed" ? <p className="failure">❗ Login Failed</p> : <></>
                    }

                    <div className="btn_box">
                        <StyledButton bg={"#0F0E21"} 
                        color={"white"}
                        onClick={submit}>
                            Login
                        </StyledButton>
                    </div>
                </div>
            </div>
        </StyledLogin>
    );
}