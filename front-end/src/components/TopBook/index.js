import { Button, Col, Rate, Row } from "antd";
import "./TopBook.scss";
import cat1 from "../../images/cat-1.jpg";
import { Link } from "react-router-dom";
import { RightOutlined, ShoppingCartOutlined } from '@ant-design/icons';


function TopBook () {
    return (
        <>
            <Row gutter={[20, 20]} className="mt-5">
                <Col span={20}>
                    <h1>Sản phẩm nổi bật</h1>
                </Col>
                <Col span={4}>
                    <Link to="/categories">Khám phá ngay <RightOutlined /></Link>
                </Col>
            </Row>
            <Row gutter={[20, 20]}>
                <Col span={8}>
                    <div className="top-book">
                        <div className="top-book__thumbnail">
                            <img src={cat1} alt={"Title"}/>
                        </div>
                        <span className="top-book__discount">15% OFF</span>
                        <div className="top-book__content">
                            <h2 className="top-book__title">Mới phát hành</h2>
                            <div className="top-book__rate"><Rate className="top-book__rate" allowHalf defaultValue={4.5}/> <span>4.8</span></div>
                            <div className="top-book__price">{(1000000).toLocaleString()}đ</div>
                            <Button type="primary" className="top-book__order">
                                <Link size="large"><ShoppingCartOutlined /> Thêm vào giỏ hàng</Link>
                            </Button>
                        </div>
                    </div>
                </Col>
                <Col span={8}>
                    <div className="top-book">
                        <div className="top-book__thumbnail">
                            <img src={cat1} alt={"Title"}/>
                        </div>
                        <span className="top-book__discount">15% OFF</span>
                        <div className="top-book__content">
                            <h2 className="top-book__title">Mới phát hành</h2>
                            <div className="top-book__rate"><Rate className="top-book__rate" allowHalf defaultValue={4.5}/> <span>4.8</span></div>
                            <div className="top-book__price">{(1000000).toLocaleString()}đ</div>
                            <Button type="primary" className="top-book__order">
                                <Link size="large"><ShoppingCartOutlined /> Thêm vào giỏ hàng</Link>
                            </Button>
                        </div>
                    </div>
                </Col>
                <Col span={8}>
                    <div className="top-book">
                        <div className="top-book__thumbnail">
                            <img src={cat1} alt={"Title"}/>
                        </div>
                        <span className="top-book__discount">15% OFF</span>
                        <div className="top-book__content">
                            <h2 className="top-book__title">Mới phát hành</h2>
                            <div className="top-book__rate"><Rate className="top-book__rate" allowHalf defaultValue={4.5}/> <span>4.8</span></div>
                            <div className="top-book__price">{(1000000).toLocaleString()}đ</div>
                            <Button type="primary" className="top-book__order">
                                <Link size="large"><ShoppingCartOutlined /> Thêm vào giỏ hàng</Link>
                            </Button>
                        </div>
                    </div>
                </Col>
                <Col span={8}>
                    <div className="top-book">
                        <div className="top-book__thumbnail">
                            <img src={cat1} alt={"Title"}/>
                        </div>
                        <span className="top-book__discount">15% OFF</span>
                        <div className="top-book__content">
                            <h2 className="top-book__title">Mới phát hành</h2>
                            <div className="top-book__rate"><Rate className="top-book__rate" allowHalf defaultValue={4.5}/> <span>4.8</span></div>
                            <div className="top-book__price">{(1000000).toLocaleString()}đ</div>
                            <Button type="primary" className="top-book__order">
                                <Link size="large"><ShoppingCartOutlined /> Thêm vào giỏ hàng</Link>
                            </Button>
                        </div>
                    </div>
                </Col>
                <Col span={8}>
                    <div className="top-book">
                        <div className="top-book__thumbnail">
                            <img src={cat1} alt={"Title"}/>
                        </div>
                        <span className="top-book__discount">15% OFF</span>
                        <div className="top-book__content">
                            <h2 className="top-book__title">Mới phát hành</h2>
                            <div className="top-book__rate"><Rate className="top-book__rate" allowHalf defaultValue={4.5}/> <span>4.8</span></div>
                            <div className="top-book__price">{(1000000).toLocaleString()}đ</div>
                            <Button type="primary" className="top-book__order">
                                <Link size="large"><ShoppingCartOutlined /> Thêm vào giỏ hàng</Link>
                            </Button>
                        </div>
                    </div>
                </Col>
                <Col span={8}>
                    <div className="top-book">
                        <div className="top-book__thumbnail">
                            <img src={cat1} alt={"Title"}/>
                        </div>
                        <span className="top-book__discount">15% OFF</span>
                        <div className="top-book__content">
                            <h2 className="top-book__title">Mới phát hành</h2>
                            <div className="top-book__rate"><Rate className="top-book__rate" allowHalf defaultValue={4.5}/> <span>4.8</span></div>
                            <div className="top-book__price">{(1000000).toLocaleString()}đ</div>
                            <Button type="primary" className="top-book__order">
                                <Link size="large"><ShoppingCartOutlined /> Thêm vào giỏ hàng</Link>
                            </Button>
                        </div>
                    </div>
                </Col>
            </Row>
        </>
    )
}

export default TopBook;