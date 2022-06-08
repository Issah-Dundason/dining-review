import { StyledButton } from "../styled/Styled.Button";
import { StyledRestaurantsPage } from "../styled/Styled.RestaurantsPage";
import RestaurantForm from "./RestaurantForm";
import react from "react";
import Modal from "react-modal";

const customStyle = {
    content: {
        height: "fit-content",
        backgroundColor: '#0F0E21',
        top: '15%',
        borderRadius: '26px',
    },
    overlay: {
        position: "fixed",
        zIndex: 3000,
        outline: 'none'
    }
};

export default function Restaurant() {
    const [formOpened, setFormOpened] = react.useState(false);

    function openForm() {
        setFormOpened(true);
    }

    function closeForm() {
        setFormOpened(false);
    }

    return (
       <StyledRestaurantsPage>

        <div className="btn_box">
            <StyledButton bg="#0F0E21" color="white" onClick={openForm}>
                CREATE
            </StyledButton>
        </div>

        <Modal 
            isOpen={formOpened}
            contentLabel="Create Restaurant"
            ariaHideApp={false}
            onRequestClose={closeForm}
            style={customStyle}
        >
           <RestaurantForm/>
        </Modal>

       </StyledRestaurantsPage>
    );
}