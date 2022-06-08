import { StyledButton } from "../styled/Styled.Button";
import { StyledForm } from "../styled/Styled.Form";


export default function RestaurantForm() {
    return (
        <StyledForm>

            <input placeholder="Name"/>
            <input placeholder="City"/>
            <input placeholder="State"/>
            <input placeholder="Zip Code"/>
            
            <div className="btn_box">
                <StyledButton bg="#18E913">
                    CREATE
                </StyledButton>
            </div>
            
        </StyledForm>
    );
}