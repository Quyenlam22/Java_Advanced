import Cookies from 'js-cookie';
import { getCart, updatePatch } from '../services/cartService';
import { getUser } from '../services/userService';

export const checkUser = async () => {
    const token = Cookies.get("token");
    if (token) {
        const cartId = Cookies.get("cart");
        const cart = await getCart(cartId);
        if (!cart.userId) {
            const user = await getUser(token);
            const options = {
                userId: user[0].id
            }

            await updatePatch(options, cartId);
        }
    }
}