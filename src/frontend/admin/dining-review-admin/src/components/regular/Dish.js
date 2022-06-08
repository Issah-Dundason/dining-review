import {GrUpdate, GrClose} from "react-icons/gr";

export default function Dish(props) {
    return (
        <div className="food_card">
            <div className="food_desc">
                <p className="food_id">#{props.data.id}</p>
                <p>{props.data.name}</p>
            </div>
            <div className="line"></div>
            <div className="buttons">
                <div className="button" onClick={props.onClick}>
                    <GrUpdate/>
                </div>
                <div className="button">
                    <GrClose/>
                </div>
            </div>
        </div>
    );
}