import { useState } from "react";
import Dashboard from "../components/Dashboard";
import { useUser } from "../hooks/useUser";
import { Search } from "lucide-react";
import toast from "react-hot-toast";
import moment from "moment";
import axiosConfig from "../util/axiosConfig";
import API_ENDPOINTS from "../util/apiEndpoints";
import TransactionInfoCard from "../components/TransactionInfoCard";

const Filter = () => {
  useUser();
  const [type, setType] = useState("income");
  const [startDate, setStartDate] = useState("");
  const [endDate, setEndDate] = useState();
  const [keyword, setKeyword] = useState();
  const [sortField, setSortField] = useState("date");
  const [sortOrder, setSortOrder] = useState("asc")
  const [transactions, setTransactions] = useState([]);
  const [loading, setLoading] = useState(false);

  const handleSearch = async (e) => {
    e.preventDefault();
    setLoading(true);
    try {
      const response = await axiosConfig.post(API_ENDPOINTS.APPLY_FILTERS, {
        type, startDate, endDate, keyword, sortField, sortOrder
      });
      console.log("transactions: ", response.data);
      setTransactions(response.data)
    } catch (error) {
      console.log("Failed to fetch transactions: ", error);
      toast.error(error.message || "Failed to fetch transactions. Please try again");
    } finally {
      setLoading(false);
    }
  }

  return (
    <Dashboard activeMenu="Filters">
      <div className="my-5 mx-auto">
        <div className="flex justify-between items-center mb-4">
          <h2 className="text-2xl font-semibold">Filter transactions</h2>
        </div>
        <div className="card p-4 mb-4">
          <div className="flex items-center justify-between mb-4">
            <h5 className="text-lg font-semibold">Select the filters</h5>
          </div>
          <form className="grid grid-cols-1 sm:grid-cols-3 md:grid-cols-6 gap-4">
            <div>
              <label className="block text-sm font-medium mb-1" htmlFor="type">
                Type
              </label>
              <select 
              value={type} id="type" 
              className="w-full border rounded px-3 py-2"
              onChange={e => setType(e.target.value)}>
                <option value="income">Income</option>
                <option value="expense">Expense</option>
              </select>
            </div>
            <div>
              <label
                htmlFor="startdate"
                className="block text-sm font-medium mb-1"
              >
                Start Date
              </label>
              <input
                id="startdate"
                type="date"
                value={startDate}
                onChange={e => setStartDate(e.target.value)}
                className="w-full border rounded px-3 py-2"
              />
            </div>
            <div>
              <label
                htmlFor="enddate"
                className="block text-sm font-medium mb-1"
              >
                End Date
              </label>
              <input
                id="enddate"
                type="date"
                value={endDate}
                onChange={e => setEndDate(e.target.value)}
                className="w-full border rounded px-3 py-2"
              />
            </div>
            <div>
              <label htmlFor="sortField" className="block text-sm font-medium mb-1">Sort Field</label>
              <select value={sortField} id="sortfield" className="w-full border rounded px-3 py-2" onChange={e => setSortField(e.target.value)}>
                <option value="date">Date</option>
                <option value="amount">Amount</option>
                <option value="category">Category</option>
              </select>
            </div>
            <div>
              <label
                htmlFor="sortorder"
                className="block text-sm font-medium mb-1"
              >
                Sort Order
              </label>
              <select
                id="sortorder"
                value={sortOrder}
                onChange={e => setSortOrder(e.target.value)}
                className="w-full border rounded px-3 py-2"
              >
                <option value="asc">Ascending</option>
                <option value="desc">Descending</option>
              </select>
            </div>
            <div className="sm:col-span-1 md:col-span-1 flex items-end gap-2">
              <div className="flex-1">
                <label
                  htmlFor="keyword"
                  className="block text-sm font-medium mb-1"
                >
                  Search
                </label>
                <input
                  id="keyword"
                  type="text"
                  placeholder="Search..."
                  value={keyword}
                  onChange={e => setKeyword(e.target.value)}
                  className="w-full border rounded px-3 py-2"
                />
              </div>
              <button onClick={handleSearch} className="mb-1 px-4 py-2 bg-purple-800 hover:bg-purple-900 text-white rounded flex items-center justify-center">
                <Search size={20} />
              </button>
            </div>
          </form>
        </div>
        <div className="card p-4">
          <div className="flex justify-between items-center mb-4">
            <h5 className="text-lg font-semibold">Transactions</h5>
          </div>
          {transactions.length === 0 && !loading? (
            <p className="text-gray-500">Select the filters and click apply to filter the transactions</p>
          ) : ""}
          {loading ? (
            <p className="text-gray-500">Loading Transactions</p>
          ) : ("")}
          {transactions.map((transaction) => (
            <TransactionInfoCard
              key={transaction.id}
              title={transaction.name}
              icon={transaction.icon}
              date={moment(transaction.date).format("Do MMM YYYY")}
              amount={transaction.amount}
              type={type}
              hideDeleteBtn
            />
          ))}
        </div>
      </div>
    </Dashboard>
  );
};

export default Filter;
