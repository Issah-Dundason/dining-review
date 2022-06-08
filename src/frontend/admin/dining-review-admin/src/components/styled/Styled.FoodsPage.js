import styled from "styled-components";
import { MainPage } from "./Styled.MainPage";


export const StyledFoodsPage = styled(MainPage)`

    .message {
        display: flex;
        justify-content: center;
        align-items: center;
        flex-direction: column;
        height: 70vh;
    }

    .food_container {
        display: flex;
        flex-wrap: wrap;
        margin: 0 auto;
        margin-top: 25px;
        width: 80%;
        justify-content: space-around;
    }

    .food_card {
        color: white;
        padding: 20px;
        border-radius: 25px;
        width: 150px;
        position: relative;
        background: #0F0E21;
        margin-bottom: 10px;
    }

    p {
        margin: 0;
    }

    .food_desc {
        display: flex;
        justify-content: flex-start;
        align-items: center;
    }

    .food_id {
        width: 50px;
        height: 50px;
        border-radius: 50%;
        background: rgba(255, 255, 255, 0.5);
        display: flex;
        justify-content: center;
        align-items: center;
        margin-right: 10px;
    }

    .line {
        width: 100%;
        height: 2px;
        background: rgba(255, 255, 255, 0.3);
        margin-top: 10px;
    }


    .buttons {
        margin-top: 5px;
        display: flex;
        justify-content: end;
    }

    .button {
        padding: 5px;
        border-radius: 5px;
        background: #c7c747;
        display: flex;
        justify-content: center;
        align-items: center;
        margin-left: 10px;
        cursor: pointer;
    }

    .refresh_btn {
        margin: 0 12px;
    }
`;