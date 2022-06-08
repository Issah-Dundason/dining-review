import styled from 'styled-components';

export const StyledHeader = styled.header`
    width: 100%;
    height: 64px;
    display: flex;
    align-items: center;
    justify-content: space-between;
    background: #0F0E21;
    position: fixed;
    top: 0;
    z-index:100;

    .logo {
        border: 1px solid white;
        border-radius: 4px;
        padding: 4px;
        margin-left: 24px;
        color: #0F0E21;
        background-color: white ;
        display: flex;
        align-items: center;
        justify-content: center;
        margin-right: 20px;
    }

    .menu {
        background: white;
        width: 30px;
        height: 30px;
        display: flex;
        align-items: center;
        justify-content: center;
        border-radius: 50%;
        margin-right: 36px;
        cursor: pointer;
    }

    ul li {
        width: 100%;
        list-style: none;
        padding: 12px;
        margin-bottom: 5px;
        position: relative;
    }

    li:before {
        content: '';
        position: absolute;
        width: 0;
        height: 1px;
        bottom: 0;
        background-color: #fefefe;
        transition: all 0.5s cubic-bezier(0.075, 0.82, 0.165, 1);
    }

    li:hover:before {
        width: 100px;
    }

    .logo-box {
        display: flex;
        justify-content: center;
        align-items: center;
    }

    .logo-box p {
        color: white;
    }

    .visible-menu {
        position: absolute;
        top: 47px;
        background: #0F0E21;
        height: 100vh;
        right: 0;
        padding: 0;
        transition: all 1s ease-in;
        display: block;
        width: 50%;
    }

    .hidden-menu {
        display: none;
    }
`
