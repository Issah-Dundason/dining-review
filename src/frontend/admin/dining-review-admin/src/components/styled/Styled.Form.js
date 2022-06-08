import styled from 'styled-components';


export const StyledForm = styled.div`
    display: flex;
    align-items: center;
    flex-direction: column;

    input {
        outline: none;
        border: none;
        padding: 10px;
        font-size: 0.9rem;
        background: rgba(255, 255, 255, 0.1);
        border-radius: 20px;
        color: white;
        margin-bottom: 12px;
    }

    .btn_box {
        display: flex;
        justify-content: flex-end;
        width: 100%;
    }

    .close_modal {
        margin: 12px 0;
    }
`;