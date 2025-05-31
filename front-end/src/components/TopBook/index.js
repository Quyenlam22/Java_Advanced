import { Col, Row } from "antd";
import "./TopBook.scss";
import { Link } from "react-router-dom";
import { RightOutlined } from '@ant-design/icons';
import { useEffect, useState } from "react";
import { getBook } from "../../services/bookService";
import Paginate from "../Paginate";
import { paginate } from "../../utils/paginate";
import BookItem from "./BookItem";

function TopBook () {
    const [data, setData] = useState([]);
    const [currentPage, setCurrentPage] = useState(1);

    useEffect(() => {
        const fetchApi = async () => {
            const result = await getBook();
            setData(result)
        }
        fetchApi()
    }, [])

    const pagination = paginate(data, currentPage, 3);

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
                <BookItem pagination={pagination}/>
            </Row>
            <Paginate pagination={pagination} setCurrentPage={setCurrentPage}/>
        </>
    )
}

export default TopBook;