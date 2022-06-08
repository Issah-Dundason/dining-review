import styled from "styled-components";

export const StyledButton = styled.button`
    border-radius: 20px;
    padding: 10px;
    outline: none;
    border: none;
    background: ${props => props.bg};
    color: ${props => props.color};
    cursor: pointer;
`

export const StyledSkeumorphicBtn = styled.div`
    cursor: pointer;
    border-radius: 15px;
    padding: 7px 9px;
    border: 1px solid #000207;
    position: relative;
    box-shadow: 0 1px 3px 0 rgba(0, 0, 0, 0.2);
    transition: all 0.1s linear;
    background: ${props => props.bg};

    p {
        color: white;
        text-align: center;
        position: relative;
        z-index: 1;
        padding: 0;
        margin: 0;
        font-weight: 400;
    }

    &:before {
        content: "";
        position: absolute;
        width: 100%;
        height: 89%;
        border-radius: 15px;
        color: ${props => props.color};
        top: 0;
        left: 0;
        background: linear-gradient(
            rgba(255, 255, 255, .8) 0%, 
            rgba(255, 255, 255, .2) 8%,
            rgba(255, 255, 255, .1)) 100%;
    }

    &:hover {
        transform: scale(0.9);
    }
`