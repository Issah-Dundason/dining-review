import { StyledSkeumorphicBtn } from "../styled/Styled.Button";


export default function SkeumorphicBtn(props) {
    return (
        <StyledSkeumorphicBtn bg={props.bg} color={props.color} onClick={props.onClick}>
            <p>{props.label}</p>
        </StyledSkeumorphicBtn>
    );
}