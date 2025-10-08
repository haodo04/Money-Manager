import { Download, LoaderCircle, Mail } from "lucide-react";
import TransactionInfoCard from "./TransactionInfoCard";
import moment from "moment";
import { useState } from "react";

const ExpenseList = ({ transactions = [], onDelete, onDownload, onEmail }) => {
  const [loadingEmail, setLoadingEmail] = useState(false);
  const [loadingDownload, setLoadingDownload] = useState(false);

  const handleEmail = async () => {
    setLoadingEmail(true);
    try {
      await onEmail?.();
    } finally {
      setLoadingEmail(false);
    }
  };

  const handleDownload = async () => {
    setLoadingDownload(true);
    try {
      await onDownload?.();
    } finally {
      setLoadingDownload(false);
    }
  };

  return (
    <div className="card">
      <div className="flex items-center justify-between">
        <h5 className="text-lg">Expenses</h5>
        <div className="flex items-center justify-end gap-2">
          <button disabled={loadingEmail} className="card-btn" onClick={handleEmail}>
            {loadingEmail ? (
              <>
                <LoaderCircle className="w-4 h-4 animate-spin" />
                Emailing...
              </>
            ) : (
              <>
                <Mail size={15} className="text-base" />
                Email
              </>
            )}
          </button>
          <button disabled={loadingDownload} className="card-btn" onClick={handleDownload}>
            {loadingDownload ? (
              <>
                <LoaderCircle className="w-4 h-4 animate-spin" />
                Downloading...
              </>
            ) : (
              <>
                <Download size={15} className="text-base" />
                Download
              </>
            )}
          </button>
        </div>
      </div>

      <div className="grid grid-cols-1 md:grid-cols-2">
        {transactions?.map((exp) => (
          <TransactionInfoCard
            key={exp.id}
            title={exp.name}
            icon={exp.icon}
            date={moment(exp.date).format("Do MMM YYYY")}
            amount={exp.amount}
            type="expense"
            onDelete={() => onDelete?.(exp.id)}
          />
        ))}
      </div>
    </div>
  );
};

export default ExpenseList;
