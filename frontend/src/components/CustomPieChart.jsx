import {
  ResponsiveContainer,
  PieChart,
  Pie,
  Cell,
  Tooltip,
  Legend,
  Label,
} from "recharts";

const defaultColors = ["#59168B", "#a0090e", "#016630", "#0ea5e9", "#f59e0b"];

const CustomPieChart = ({
  data = [],
  label = "",
  totalAmount = "",
  colors = defaultColors,
  dataKey = "amount",
  nameKey = "name",
  innerRadius = 70,
  outerRadius = 100,
  valueFormatter = (v) => v,
  showLegend = true,
  legendFormatter = (value) => value,
}) => {
  const safeData = Array.isArray(data) ? data : [];
  const total = safeData.reduce((acc, it) => acc + (Number(it?.[dataKey]) || 0), 0);
  const isEmpty = total <= 0 || safeData.length === 0;
  const emptyData = [{ [nameKey]: "No data", [dataKey]: 1 }];

  return (
    <div className="w-full h-80">
      <ResponsiveContainer width="100%" height="100%">
        <PieChart>
          <Tooltip
            formatter={(value, name) => [valueFormatter(value), name]}
            contentStyle={{ backgroundColor: "white", borderRadius: 10, border: "1px solid #E5E7EB" }}
            labelStyle={{ color: "#6b7280", fontWeight: 500 }}
          />

          {showLegend && (
            <Legend verticalAlign="bottom" height={36} formatter={legendFormatter} iconType="circle" />
          )}

          <Pie
            data={isEmpty ? emptyData : safeData}
            dataKey={dataKey}
            nameKey={nameKey}
            innerRadius={innerRadius}
            outerRadius={outerRadius}
            startAngle={90}
            endAngle={-270}
            paddingAngle={2}
            isAnimationActive
            labelLine={false}
            stroke="#ffffff"
            strokeWidth={3}
          >
            {(isEmpty ? emptyData : safeData).map((_, idx) => (
              <Cell key={idx} fill={isEmpty ? "#E5E7EB" : colors[idx % colors.length]} />
            ))}
            <Label
              value={label || "Total Balance"}
              position="center"
              dy={-8}
              fill="#6b7280"
              fontSize={16}
              fontWeight={600}
            />
            <Label
              value={isEmpty ? "No data" : totalAmount}
              position="center"
              dy={12}
              fill="#111827"
              fontSize={18}
              fontWeight={500}
            />
          </Pie>
        </PieChart>
      </ResponsiveContainer>
    </div>
  );
};

export default CustomPieChart;
