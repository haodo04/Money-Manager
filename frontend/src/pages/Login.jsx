import { useState } from "react";
import { assets } from "../assets/assets";
import { Link, useNavigate } from "react-router-dom";
import Input from "../components/Input";

const Login = () => {
  const [email, setEmail] = useState("");
  const [password, setPassword] = useState("");
  const [error, setError] = useState("");

  const navigate = useNavigate();
  return (
    <div>
      <div className="h-screen w-full relative flex items-center justify-center overflow-hidden">
        {/* {Background image with blur} */}
        <img
          src={assets.login_bg}
          alt="background"
          className="absolute inset-0 w-full h-full object-cover filter blur-sm"
        />
        <div className="relative z-10 w-full max-w-lg px-6">
          <div className="bg-white/95 backdrop-blur-sm rounded-lg shadow-2xl p-8 max-h-[90vh] overflow-y-auto">
            <h3 className="text-2xl font-semibold text-black text-center mb-2">
              Welcome Back
            </h3>
            <p className="text-sm text-slate-700 text-center mb-8">
              Please enter your details to login in
            </p>
            <form className="space-y-4">
              <Input
                value={email}
                onChange={(e) => setEmail(e.target.value)}
                label="Email Address"
                placeholder="name@example.com"
                type="text"
              />

              <Input
                value={password}
                onChange={(e) => setPassword(e.target.value)}
                label="Password"
                placeholder="*********"
                type="password"
              />
              {error && (
                <p className="text-red-800 text-sm text-center bg-red-50 p-2 rounded">
                  {error}
                </p>
              )}
              <button
                className="btn-primary w-full py-3 text-lg font-medium"
                type="submit"
              >
                LOGIN
              </button>
              <p className="text-sm text-slate-800 text-center mt-6">
                Don't have an account
                <Link
                  to="/signup"
                  className="font-medium text-primary underline hover:text-primary-dark transition-colors"
                >
                Signup
                </Link>
              </p>
            </form>
          </div>
        </div>
      </div>
    </div>
  );
};

export default Login;
