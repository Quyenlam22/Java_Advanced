import { useDispatch } from "react-redux";
import { deleteItem, updateQuantity } from "../../actions/cart";
import { useState } from "react";
import Cookies from "js-cookie";
import { getCart, updatePatch } from "../../services/cartService";

function CartItem(props) {
    const { item } = props;
    const dispatch = useDispatch();
    const [quantity, setQuantity] = useState(item.quantity);

    const cartId = Cookies.get("cart");

    const handleChangeQuantity = async (change) => {
        const newQuantity = quantity + change;
        if (newQuantity >= 1) {
            setQuantity(newQuantity);

            const cartData = await getCart(cartId);
            if(cartData) {
                const index = cartData.bookItems.findIndex(cartItem => cartItem.bookId === item.id);
                if(index >= 0) {
                    cartData.bookItems[index].quantity += change; 
                }

                const options = {
                    bookItems: cartData.bookItems
                };

                await updatePatch(options, cartId);
            }
            dispatch(updateQuantity(item.id, change));

        }
    };

    const handleClick = async () => {
        const cartData = await getCart(cartId);
        if(cartData) {
            const newBookItems = cartData.bookItems.filter(cartItem => cartItem.bookId !== item.id);
            
            const options = {
                bookItems: newBookItems
            };

            await updatePatch(options, cartId);
        }
        dispatch(deleteItem(item.id));

    }

    return (
        <div className="cart__item">
            <img className="cart__image" src={item.info.thumbnail} alt={item.info.title} />
            <div className="cart__content">
                <h3>{item.info.title}</h3>
                <div className="cart__price-new">
                    {((item.info.price * (1 - item.info.discount)) || item.info.price).toFixed(2)}đ
                </div>
                <div className="cart__price-old">{item.info.price}đ</div>
            </div>
            <div className="cart__quantity">
                <button onClick={() => handleChangeQuantity(-1)}>-</button>
                <input
                    min={1}
                    value={quantity}
                    readOnly
                />
                <button onClick={() => handleChangeQuantity(1)}>+</button>
            </div>
            <button
                className="cart__delete-button"
                onClick={handleClick}
            >
                Delete
            </button>
        </div>
    );
}

export default CartItem;
