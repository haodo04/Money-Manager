import { useContext, useEffect } from "react"
import { AppContext } from "../context/AppContext"
import { useNavigate } from "react-router-dom";
import axiosConfig from "../util/axiosConfig";
import API_ENDPOINTS from "../util/apiEndpoints";

export const useUser = () => {
    const {user, setUser, clearUser} = useContext(AppContext);
    const navigate = useNavigate();

    useEffect(() => {
        if (user) {
            return;
        }
        let isMouted = true;

        const fetchUserInfo = async () => {
            try {
              const response = await axiosConfig.get(API_ENDPOINTS.GET_USER_INFO);
                if(isMouted && response.data) {
                    setUser(response.data);
                }
            } catch (error) {
                console.log("Failed to fetch the user info", error);
                if(isMouted) {
                    clearUser();
                    navigate("/login")
                }
            }
        }

        fetchUserInfo();

        return () => {
            isMouted = false;
        }
    }, [setUser, clearUser, navigate]);
}