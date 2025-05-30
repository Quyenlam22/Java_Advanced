import { ShoppingCartOutlined } from '@ant-design/icons';
import { useSelector } from 'react-redux';

function CartMini () {
    const cart = useSelector(state => state.cartReducer);
    const count = cart.reduce((sum, item) => sum + item.quantity, 0);

    return (
        <>
            <ShoppingCartOutlined /> Giỏ hàng ({count})
        </>
    )
}

export default CartMini;