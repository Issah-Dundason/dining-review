import { StyledFoodsPage } from "../styled/Styled.FoodsPage";
import { StyledForm } from "../styled/Styled.Form";
import react from "react";
import Modal from "react-modal/lib/components/Modal";
import { useDispatch, useSelector } from "react-redux";
import { getDishes, getFetchingStatus, getSavedFoods, getSavingStatus, saveFood, updateSavingStatus } from "../../features/food/foodSlice";
import Dish from "./Dish";
import LoadingIcon from "./LoadingIcon";
import SkeumorphicBtn from "./SkeumorphicBtn";
import {MdClose, MdDone, MdError, MdRestaurantMenu} from "react-icons/md"


const customStyle = {
    content: {
        height: "fit-content",
        backgroundColor: '#0F0E21',
        top: '15%',
        borderRadius: '26px',
        width: '50%',
        margin: "0 auto",
    },
    overlay: {
        position: "fixed",
        zIndex: 3000,
        outline: 'none'
    }
};

export default function Food() {
    const [formOpened, setFormOpened] = react.useState(false);
    const dishes = useSelector(getDishes);
    const dishesStatus = useSelector(getFetchingStatus);
    const savingStatus = useSelector(getSavingStatus);
    const dispatch = useDispatch();
    const [food, setFood] = react.useState({name: ''});
    const [modalPurpose, setModalPurpose] = react.useState("save");
    const [dishId, setDishId] = react.useState(-1);

    react.useEffect(() => {
       if(dishesStatus === 'done') 
           return;

       dispatch(getSavedFoods());
    }, []);

    function openForm() {
        console.log("Clicked")
        setFormOpened(true);
    }

    function closeForm() {
        setFormOpened(false);
    }

    function changeFood(e) {
        setFood(old => {
            return {
                ...old,
                [e.target.name] : e.target.value
            }
        })
    }

    function createFood() {
        dispatch(saveFood(food));
    }

    function generateContent() {
        if(dishesStatus === "connecting") {
            return (<div className="message"> <LoadingIcon/> </div>);
        } 
        if(dishesStatus === "failed") {
            return (<div className="message"><p>❗ Couldn't fetch data</p></div>);
        }
        if(dishes.length === 0) {
            return (<div className={"message"}>
                    <p><MdRestaurantMenu fontSize="7em"/></p>
                    <p>No Saved Food</p>
            </div>);
        }

        return (<div className="food_container">
        {createDishes()}
    </div>);
    }

    function clearModalContent() {
        setFood({name: ''});
        dispatch(updateSavingStatus(''));
    }

    function createDishes() {
        return dishes.map((dish, i) => (
            <Dish key={i} data={dish} onClick={() => onDishUpdate(dish)}/>
        ));
    }

    function onDishUpdate(dish) {
        setDishId(dish.id);
        setFood({name: dish.name});
        setModalPurpose("update");
        openForm();
    }

    function saveNewFood() {
        setModalPurpose('save');
        openForm();
    }

    function genereteModalActionBtn() {
        if(modalPurpose === 'save') {
            return (<SkeumorphicBtn label="Save" onClick={createFood}/>);
        }
        return (<SkeumorphicBtn label="update" />);
    }

    return (
        <StyledFoodsPage>
            
            <div className="btn_box">
                <SkeumorphicBtn label="CREATE" bg="red" onClick={saveNewFood}/>
                <div className="refresh_btn">
                    <SkeumorphicBtn label="REFRESH" bg="linear-gradient(
        45deg, #050aa7, #361212)" onClick={() => dispatch(getSavedFoods())}/>
                </div>
            </div>

            <Modal
                onAfterClose={clearModalContent} 
                isOpen={formOpened}
                contentLabel="Create Restaurant"
                ariaHideApp={false}
                onRequestClose={closeForm}
                style={customStyle}
                shouldCloseOnOverlayClick={false}
            >
                <StyledForm>
                    <div className="btn_box close_modal">
                        <MdClose color="white" fontSize="1.8em" onClick={closeForm}/>
                    </div>
                    <input placeholder="Name" onChange={changeFood} value={food.name} name="name"/>
                    <div className="saving_status">
                           {savingStatus === 'connecting' ? <LoadingIcon/>: <></>}
                           {savingStatus === 'done' ? <MdDone color="white" fontSize="1.8em"/> : <></>}
                           {savingStatus === 'failed'? <MdError color="white" fontSize="1.8em"/> : <></>}
                    </div>
                    <div className="btn_box">
                        {genereteModalActionBtn()}
                    </div>
                </StyledForm>
            </Modal>
            {generateContent()}
        </StyledFoodsPage>
    );
}