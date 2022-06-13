import {GrClose} from "react-icons/gr";
import {TiPen} from "react-icons/ti"

export default function Dish(props) {
    return (
        <div className="food_card">
            <div className="food_desc">
                <p className="food_id">#{props.data.id}</p>
                <p>{props.data.name}</p>
            </div>
            <div className="line"></div>
            <div className="buttons">
                <div className="button" onClick={props.onUpdate}>
                    <TiPen/>
                </div>
                <div className="button" onClick={props.onDelete}>
                    <GrClose/>
                </div>
            </div>
        </div>
    );
}