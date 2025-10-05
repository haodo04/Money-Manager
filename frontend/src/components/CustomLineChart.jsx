import {
  LineChart,
  Line,
  XAxis,
  YAxis,
  CartesianGrid,
  Tooltip,
  ResponsiveContainer,
} from "recharts";

const CustomLineChart = ({ data, dataKey = "amount", color = "#8b5cf6" }) => {
  return (
    <div className="w-full h-80">
      <ResponsiveContainer width="100%" height="100%">
        <LineChart
          data={data}
          margin={{ top: 20, right: 20, left: 0, bottom: 0 }}
        >
          <defs>
            <linearGradient id="colorGradient" x1="0" y1="0" x2="0" y2="1">
              <stop offset="5%" stopColor={color} stopOpacity={0.3} />
              <stop offset="95%" stopColor={color} stopOpacity={0} />
            </linearGradient>
          </defs>


          <CartesianGrid strokeDasharray="3 3" stroke="#E5E7EB" />


          <XAxis
            dataKey="date"
            tick={{ fill: color, fontSize: 12 }}
            axisLine={false}
            tickLine={false}
          />
          <YAxis
            tick={{ fill: color, fontSize: 12 }}
            axisLine={false}
            tickLine={false}
          />


          <Tooltip
            contentStyle={{
              backgroundColor: "white",
              borderRadius: "10px",
              border: "1px solid #E5E7EB",
            }}
            labelStyle={{ color: "#6b7280", fontWeight: "500" }}
            itemStyle={{ color }}
          />

          <Line
            type="monotone"
            dataKey={dataKey}
            stroke={color}
            strokeWidth={3}
            dot={{ r: 5, fill: color, strokeWidth: 2, stroke: "#fff" }}
            activeDot={{ r: 7 }}
            fill="url(#colorGradient)"
          />
        </LineChart>
      </ResponsiveContainer>
    </div>
  );
};

export default CustomLineChart;
