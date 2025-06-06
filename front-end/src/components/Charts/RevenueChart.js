import { Line } from '@ant-design/plots';

function RevenueChart() {
  const dataRevenue = [
    { month: 'Jan', revenue: 12000000 },
    { month: 'Feb', revenue: 13500000 },
    { month: 'Mar', revenue: 15000000 },
    { month: 'Apr', revenue: 16500000 },
    { month: 'May', revenue: 18000000 },
    { month: 'Jun', revenue: 22000000 },
  ];

  const configRevenue = {
    data: dataRevenue,
    xField: 'month',
    yField: 'revenue',
    yAxis: {
      label: {
        formatter: (v) => `${(v / 1000000).toFixed(1)}tr`,
      },
    },
    point: {
      size: 5,
      shape: 'circle',
      style: {
        fill: 'white',
        stroke: '#5B8FF9',
        lineWidth: 2,
      },
    },
    tooltip: {
      formatter: (datum) => ({
        name: 'Doanh thu',
        value: `${datum.revenue.toLocaleString()} đ`,
      }),
    },
    smooth: true,
  };

  return (
    <>
      <h2>Doanh thu theo tháng</h2>
      <Line {...configRevenue} />
    </>
  );
}

export default RevenueChart;
