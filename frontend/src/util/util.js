export const addThousandsSeparator = (num) => {
  if (num == null || isNaN(num)) return "";

  const [integerPart, fractionalPart] = num.toString().split(".");

  const formattedInteger = integerPart.replace(/\B(?=(\d{3})+(?!\d))/g, ".");

  return fractionalPart
    ? `${formattedInteger},${fractionalPart}`
    : formattedInteger;
};


export const formatCurrency = (num) => {
  const formatted = addThousandsSeparator(num);
  return formatted ? `${formatted} ₫` : "";
};


export const prepareIncomeLineChartData = (transactions = []) => {
  if (!Array.isArray(transactions)) return [];
  const grouped = transactions.reduce((acc, item) => {
    const date = item.date?.split("T")[0]; 
    if (!acc[date]) {
      acc[date] = { date, amount: 0, details: [] };
    }
    acc[date].amount += Number(item.amount) || 0;
    acc[date].details.push(`${item.name}: ${item.amount}`);
    return acc;
  }, {});

  const result = Object.values(grouped).sort(
    (a, b) => new Date(a.date) - new Date(b.date)
  );

  return result;
};

const groupSumByDate = (transactions = []) => {
  if (!Array.isArray(transactions)) return [];
  const map = transactions.reduce((acc, item) => {
    const date = (item?.date || "").toString().split("T")[0]; // YYYY-MM-DD
    if (!date) return acc;
    if (!acc[date]) acc[date] = { date, amount: 0, details: [] };
    const amt = Number(item?.amount) || 0;
    acc[date].amount += amt;
    acc[date].details.push(`${item?.name ?? ""}: ${amt}`);
    return acc;
  }, {});
  return Object.values(map).sort((a, b) => new Date(a.date) - new Date(b.date));
};

export const prepareExpenseLineChartData = (transactions = []) => {
  return groupSumByDate(transactions);
};

