import { ShoppingCartOutlined } from '@ant-design/icons';
import { Button, Col, Rate } from 'antd';
import { Link } from 'react-router-dom';
import cat1 from "../../images/cat-1.jpg";
import { useDispatch, useSelector } from 'react-redux';
import { addToCart, updateQuantity } from "../../actions/cart";

function BookItem (props) {
    const { pagination } = props;
    const dispatch = useDispatch();
    const cart = useSelector(state => state.cartReducer);

    const handleClick = (item) => {
        if(cart.some(itemCart => itemCart.id === item.id)) {
            dispatch(updateQuantity(item.id));
        }
        else {
            dispatch(addToCart(item));
        }
    }

    return (
        <>
            {pagination.currentItems && (
                pagination.currentItems.map(item => (
                    <Col span={8} key={item.id}>
                        <div className="top-book">
                            <div className="top-book__thumbnail">
                                <img src={cat1} alt={item.title}/>
                            </div>
                            <span className="top-book__discount">{item.discount*100}% OFF</span>
                            <div className="top-book__content">
                                <h2 className="top-book__title">{item.title}</h2>
                                <div className="top-book__rate"><Rate className="top-book__rate" allowHalf defaultValue={4.5}/> <span>4.8</span></div>
                                <div className="top-book__price-new">{((item.price) * (1-item.discount)).toFixed(2)} đ</div>                                    <div className="top-book__price">{(item.price)} đ</div>
                                <Button onClick={() => handleClick(item)} type="primary" className="top-book__order">
                                    <Link size="large"><ShoppingCartOutlined /> Thêm vào giỏ hàng</Link>
                                </Button>
                            </div>
                        </div>
                    </Col>
                ))
            )}
        </>
    )
}

export default BookItem;