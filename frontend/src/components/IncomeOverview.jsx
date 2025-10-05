import { useEffect, useState } from "react";
import CustomLineChart from "../components/CustomLineChart";
import { prepareIncomeLineChartData } from "../util/util";
import { Plus } from "lucide-react";

const IncomeOverview = ({ transactions, onAddIncome }) => {
  const [chartData, setChartData] = useState([]);
  useEffect(() => {
    const result = prepareIncomeLineChartData(transactions);
    console.log(result);
    setChartData(result);

    return () => {};
  }, [transactions]);
  return (
    <div className="card">
      <div className="flex items-center justify-between">
        <div>
          <h5 className="text-lg">Income Overview</h5>
          <p className="text-xs text-gray-400 mt-0 5">
            Track your earnings over time and analyze your income trends.
          </p>
        </div>
        <button
          onClick={onAddIncome}
          className="flex items-center gap-2 px-2 py-2  text-sm font-medium rounded-xl shadow"
        >
          <Plus size={15} /> Add Income
        </button>
      </div>
      <div className="mt-10">
        <CustomLineChart data={chartData} />
      </div>
    </div>
  );
};

export default IncomeOverview;
