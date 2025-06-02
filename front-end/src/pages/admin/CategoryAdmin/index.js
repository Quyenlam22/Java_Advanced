import { useEffect, useState } from 'react';
import { Button, Divider, Radio, Table } from 'antd';
import { getCategories } from '../../../services/categoryService';
import { Link } from 'react-router-dom';

const columns = [
  {
    title: 'Tiêu đề',
    dataIndex: 'name',
    render: text => <a>{text}</a>,
  },
  {
    title: 'Mô tả',
    dataIndex: 'description',
  },
  {
    title: 'Thời gian tạo',
    dataIndex: 'created_at',
  }, 
  {
    title: 'Hành động',
    width: 160,
    dataIndex: 'actions'
  },
];

const rowSelection = {
  onChange: (selectedRowKeys, selectedRows) => {
    console.log(`selectedRowKeys: ${selectedRowKeys}`, 'selectedRows: ', selectedRows);
  },
};

function CategoryAdmin () {
    const [selectionType, setSelectionType] = useState('checkbox');
    const [record, setRecord] = useState([]);

    useEffect(() => {
        const fetchApi = async () => {
            const result = await getCategories();
            setRecord(result);
        }
        fetchApi();
    }, [])

    const data = record.map(item => {
        const date = new Date(item.created_at).toLocaleDateString();

        return {
            key: item.id,
            name: item.name,
            description: item.description,
            created_at: date,
            actions: (
              <>
                  <Link to={`edit/${item.id}`} className='mr-1'><Button type='primary'>Sửa</Button></Link>
                  <Button type='primary' danger>Xóa</Button>
              </>
            )
        }
    })

    return (
        <>
            <h1>Danh mục</h1>
            <Radio.Group onChange={e => setSelectionType(e.target.value)} value={selectionType}>
                <Radio value="checkbox">Checkbox</Radio>
                <Radio value="radio">radio</Radio>
            </Radio.Group>
            <Divider />
            <Table
                rowSelection={Object.assign({ type: selectionType }, rowSelection)}
                columns={columns}
                dataSource={data}
                pagination={{pageSize: 5}}
            />
        </>
    )
}

export default CategoryAdmin;