import { useEffect, useState } from "react";
import Dashboard from "../components/Dashboard";
import { useUser } from "../hooks/useUser";
import axiosConfig from "../util/axiosConfig";
import API_ENDPOINTS from "../util/apiEndpoints";
import toast from "react-hot-toast";
import IncomeList from "../components/IncomeList";
import { Plus } from "lucide-react";
import Modal from "../components/Modal";
import AddIncomeForm from "../components/AddIncomeForm";

const Income = () => {
  useUser();
  const [incomeData, setIncomeData] = useState([]);
  const [categories, setCategories] = useState([]);
  const [loading, setLoading] = useState(false);

  const [openAddIncomeModal, setOpenAddIncomeModal] = useState(false);
  const [openDeleteAlert, setOpenDeleteAlert] = useState({
    show: false,
    data: null,
  });

  // Fetch income details from the API
  const fetchIncomeDetails = async () => {
    if (loading) return;

    setLoading(true);

    try {
      const response = await axiosConfig.get(API_ENDPOINTS.GET_ALL_INCOMES);
      if (response.status === 200) {
        console.log("Income list", response.data);
        setIncomeData(response.data);
      }
    } catch (error) {
      console.log("Failed to fetch income details", error);
      toast.error(
        error.response?.data?.message || "Failed to fetch income data"
      );
    } finally {
      setLoading(false);
    }
  };

  // Fetch categories for income
  const fetchIncomeCategories = async () => {
    try {
      const response = await axiosConfig.get(
        API_ENDPOINTS.CATEGORY_BY_TYPE("income")
      );
      if (response.status === 200) {
        console.log("income categories", response.data);
        setCategories(response.data)
      }
    } catch (error) {
      console.log("Failed to fetch income categories", error)
      toast.error(error.data?.message || "Failed to fetch income categories for this category");
    }
  };

  // save the income details
  const handleAddIncome = async (income) => {
    const {name, amount, date, icon, categoryId} = income;

    // validation
    if(!name.trim()) {
      toast.error("Please enter a name");
    }

    if(!amount || isNaN(amount) || Number(amount) <= 0) {
      toast.error("Amount should be a valid number greater than 0")
    }

    if(!date) {
      toast.error("Please select a date")
    }

    const today = new Date().toISOString().split('T')[0];
    if(date > today) {
      toast.error("Date cannot be in the future");
      return;
    }

    if (!categoryId) {
      toast.error("Please select a category");
      return;
    }

    try {
      const response = await axiosConfig.post(API_ENDPOINTS.ADD_INCOME, {
        name,
        amount: Number(amount),
        date,
        icon,
        categoryId
      })

      if(response.status === 201) {
        setOpenAddIncomeModal(false);
        toast.success("Income added successfully");
        fetchIncomeDetails();
        fetchIncomeCategories();
      }
    } catch(error) {
      console.log("Error adding income", error)
      toast.error(error.response?.data?.message || "Failed to adding income");
    }

}
  useEffect(() => {
    fetchIncomeDetails();
    fetchIncomeCategories();
  }, []);

  return (
    <Dashboard activeMenu="Income">
      <div className="my-5 mx-auto">
        <div className="grid grid-cols-1 gap-6">
          <div>
            {/* overview for income with line char */}
            <button
              onClick={() => setOpenAddIncomeModal(true)}
              className="flex items-center gap-2 px-2 py-2  text-sm font-medium rounded-xl shadow"
            >
              <Plus size={15} /> Add Income
            </button>
          </div>
          <IncomeList
            transactions={incomeData}
            onDelete={(id) => console.log("deleting the income", id)}
          />
          {/* Add Income Modal */}
          <Modal
            isOpen={openAddIncomeModal}
            onClose={() => setOpenAddIncomeModal(false)}
            title="Add Income"
          >
            <AddIncomeForm
              onAddIncome={(income) => handleAddIncome(income)}
              categories={categories}
            />
          </Modal>
        </div>
      </div>
    </Dashboard>
  );
};

export default Income;
