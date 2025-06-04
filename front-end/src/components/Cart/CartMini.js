import { ShoppingCartOutlined } from '@ant-design/icons';
import { useEffect, useState } from 'react';
import { useDispatch, useSelector } from "react-redux";
import Cookies from "js-cookie";
import { setCart } from '../../actions/cart';
import { getCart } from '../../services/cartService';

function CartMini () {
    const cart = useSelector(state => state.cartReducer);
    const dispatch = useDispatch();
    const [loading, setLoading] = useState(true);

    useEffect(() => {
        const fetchCart = async () => {
            const cartId = Cookies.get("cart");
            if (cartId) {
                const response = await getCart(cartId);
                if (response) {
                    dispatch(setCart(response.bookItems)); // Gửi vào Redux
                }
            }
            setLoading(false);
        };

        fetchCart();
    }, []);

    const count = cart.reduce((sum, item) => sum + item.quantity, 0);

    return (
        <>
            <ShoppingCartOutlined /> Giỏ hàng ({loading ? '...' : count})
        </>
    )
}

export default CartMini;