import { Card, Col, Row, Typography, Avatar, Button } from "antd";
import { useEffect, useState } from "react";
import { getAuthors } from "../../services/authorService"; // API gọi danh sách tác giả

const { Title, Text } = Typography;

function FeaturedAuthors() {
  const [authors, setAuthors] = useState([]);

  useEffect(() => {
    const fetchAuthors = async () => {
      const result = await getAuthors();
      const featured = result.slice(0, 4); // Giới hạn 4 tác giả nổi bật
      setAuthors(featured);
    };
    fetchAuthors();
  }, []);

  return (
    <div style={{ padding: '24px 0' }}>
      <Title level={3}>Tác giả nổi bật</Title>
      <Row gutter={[16, 16]}>
        {authors.map(author => (
          <Col key={author.id} xs={24} sm={12} md={6}>
            <Card
              hoverable
              style={{ borderRadius: 12 }}
              cover={
                <Avatar
                  size={96}
                  src={author.profile_image || "https://via.placeholder.com/150"}
                  style={{ margin: '16px auto' }}
                />
              }
            >
              <Title level={4} style={{ textAlign: 'center' }}>{author.name}</Title>
              <Text type="secondary" style={{ display: 'block', textAlign: 'center' }}>
                {author.bio}
              </Text>
              <div style={{ textAlign: 'center', marginTop: 12 }}>
                <Button size="large" type="primary">
                  Xem thêm
                </Button>
              </div>
            </Card>
          </Col>
        ))}
      </Row>
    </div>
  );
}

export default FeaturedAuthors;
