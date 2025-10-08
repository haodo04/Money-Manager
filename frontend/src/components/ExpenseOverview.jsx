import { useEffect, useState } from "react";
import CustomLineChart from "../components/CustomLineChart";
import { prepareExpenseLineChartData } from "../util/util";
import { Plus } from "lucide-react";

const ExpenseOverview = ({ transactions = [], onAddExpense }) => {
  const [chartData, setChartData] = useState([]);

  useEffect(() => {
    const result = prepareExpenseLineChartData(transactions);
    setChartData(result);
    return () => {};
  }, [transactions]);

  return (
    <div className="card">
      <div className="flex items-center justify-between">
        <div>
          <h5 className="text-lg">Expense Overview</h5>
          <p className="text-xs text-gray-400 mt-0.5">
            Monitor your spending trends and stay on budget.
          </p>
        </div>
        <button
          onClick={onAddExpense}
          className="flex items-center gap-2 px-2 py-2 text-sm font-medium rounded-xl shadow"
        >
          <Plus size={15} /> Add Expense
        </button>
      </div>

      <div className="mt-10">
        <CustomLineChart data={chartData} color="#ef4444" />{/* red theme */}
      </div>
    </div>
  );
};

export default ExpenseOverview;
