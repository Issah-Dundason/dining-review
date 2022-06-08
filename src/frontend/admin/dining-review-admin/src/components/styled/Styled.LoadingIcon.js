import styled from "styled-components";


export const StyledLoadingIcon = styled.div`

    width: 30px;
    height: 30px;
    border: 1px solid #0F0E21;
    margin: auto;
    padding: 0;
    margin-top: 24px;
    animation: rotate 3s infinite;

    .progress {
        width:0%;
        height: 100%;
        background: #18E913;
        animation: progress 3s infinite;
    }

    @keyframes rotate {
        0% {
            transform: rotate(10deg);
        }

        25% {
            transform: rotate(25deg);
        }

        50% {
            transform: rotate(60deg); 
        }

        70% {
            transform: rotate(75deg);
        }

        100% {
            transform: rotate(360deg);
        }
    }

    @keyframes progress {
        0% {
            width: 0%;
        }

        25% {
           width: 35%;
        }

        50% {
            width: 45%;
        }

        70% {
            width: 85%;
        }

        100% {
            width: 100%;
        }
    }

`;