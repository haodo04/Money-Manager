import { useContext } from "react";
import MenuBar from "./MenuBar";
import Sidebar from "./Sidebar";
import { AppContext } from "../context/AppContext";

const Dashboard = ({ children, activeMenu }) => {
    const {user} = useContext(AppContext);
  return (
    <div>
      <MenuBar activeMenu={activeMenu}/>
      
      {user && (
        <div className="flex">
        <div className="max-[1080px]:hidden">
          {/* Side bar content */}
          <Sidebar activeMenu={activeMenu} />
        </div>
        <div className="grow mx-5">{children}</div>
      </div>
      )}
    </div>
  );
};

export default Dashboard;
