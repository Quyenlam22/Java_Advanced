import { Button, Col, Row } from "antd";
import cat1 from "../../images/cat-1.jpg";
import cat2 from "../../images/cat-2.jpg";
import cat3 from "../../images/cat-3.jpg";
import "./TopCategory.scss";
import { Link } from "react-router-dom";
import { RightOutlined } from '@ant-design/icons';

function TopCategory () {
    return (
        <>
            <Row gutter={[20, 20]}>
                <Col span={20}>
                    <h1>Các danh mục xu hướng</h1>
                </Col>
                <Col span={4}>
                    <Link to="/categories">Khám phá ngay <RightOutlined /></Link>
                </Col>
            </Row>
            <Row gutter={[20, 20]}>
                <Col span={8}>
                    <div className="top-cat__box">
                        <div className="top-cat__thumbnail">
                            <img src={cat1} alt={"Title"}/>
                        </div>
                        <div className="top-cat__content">
                            <h2 className="top-cat__title">Mới phát hành</h2>
                            <div className="top-cat__desc">50+ Tiêu đề mới</div>
                            <Button>
                                <Link to={'/categories'} size="large" type="primary">Xem chi tiết</Link>
                            </Button>
                        </div>
                    </div>
                </Col>
                <Col span={8}>
                    <div className="top-cat__box">
                        <div className="top-cat__thumbnail">
                            <img src={cat2} alt={"Title"}/>
                        </div>
                        <div className="top-cat__content">
                            <h2 className="top-cat__title">Người chiến thắng</h2>
                            <div className="top-cat__desc">30+ sách</div>
                            <Button>
                                <Link to={'/categories'} size="large" type="primary">Xem chi tiết</Link>
                            </Button>
                        </div>
                    </div>
                </Col>
                <Col span={8}>
                    <div className="top-cat__box">
                        <div className="top-cat__thumbnail">
                            <img src={cat3} alt={"Title"}/>
                        </div>
                        <div className="top-cat__content">
                            <h2 className="top-cat__title">Sách bán chạy nhất</h2>
                            <div className="top-cat__desc">45+ Tiêu đề</div>
                            <Button>
                                <Link to={'/categories'} size="large" type="primary">Xem chi tiết</Link>
                            </Button>
                        </div>
                    </div>
                </Col>
            </Row>
        </>
    )
}

export default TopCategory;