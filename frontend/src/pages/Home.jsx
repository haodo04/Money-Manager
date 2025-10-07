import Dashboard from '../components/Dashboard'
import { useUser } from '../hooks/useUser'
import InfoCard from '../components/InfoCard';
import { WalletCards } from 'lucide-react';
import { addThousandsSeparator } from '../util/util';

const Home = () => {
  useUser();
  return (
    <div>
      <Dashboard activeMenu="Dashboard">
        <div className='my-5 mx-auto'>
          <div className="grid grid-cols-1 md:grid-cols-3 gap-6">
            {/* Display the cards */}
            <InfoCard
              icon={<WalletCards/>}
              label="Total Balance"
              value={addThousandsSeparator(100000.00)}
              color="bg-purple-800"
            />

            <InfoCard
              icon={<WalletCards/>}
              label="Total Income"
              value={addThousandsSeparator(200000.00)}
              color="bg-green-800"
            />

            <InfoCard
              icon={<WalletCards/>}
              label="Total Expense"
              value={addThousandsSeparator(50000.00)}
              color="bg-red-800"
            />
          </div>
          <div className='grid-cols-1 md:grid-cols-2 gap-6 mt-6'>
            {/* Recent transactions */}

            {/* finance overview chart */}

            {/* Expense transactions */}

            {/* Income transactions */}
          </div>
        </div>
      </Dashboard>
    </div>
  )
}

export default Home