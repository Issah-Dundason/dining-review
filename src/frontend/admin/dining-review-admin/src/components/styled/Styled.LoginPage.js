import styled from "styled-components";


export const StyledLogin = styled.div`

    .wrapper {
        width: 100vw;
        height: 100vh;
        background: #0F0E21;
    }

    .form {
        position: absolute;
        top: 50%;
        left: 50%;
        transform: translate(-50%, -50%);
        width: 50%;
        background: rgba(255, 255, 255, 0.83);
        padding: 20px;
    }

    .heading {
        text-align: center;
        font-weight: bold;
        font-size: 1.2rem;
        margin: 0;
    }

    .field {
        display: flex;
        flex-direction: column;
    }

    label {
        margin-top: 13px;
        margin-bottom: 12px;
        font-weight: bold;
    }

    input {
        padding: 10px;
        background: #bfc3c3;
        outline: none;
        border: none;
        font-size: 1.2rem;
    }
    
    .btn_box {
        margin-top: 12px;
        display: flex;
        justify-content: end;
    }

    .failure {
        text-align: center;
    }
`;