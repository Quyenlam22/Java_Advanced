import { useSelector } from "react-redux";

function Cart () {
    const cart = useSelector(state => state.cartReducer);
    console.log(cart);

    return (
        <>

        </>
    )
}

export default Cart