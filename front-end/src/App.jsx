import { Routes, Route, Navigate } from "react-router";
import Login from "./pages/Login";
import Register from "./pages/Register";
import Profile from "./pages/Profile";
import Interests from "./pages/Interests";
import Recommendations from "./pages/Recommendations";
import Feedbacks from "./pages/Feedbacks";
import RecommendationDetails from "./pages/RecommendationDetails.jsx";

export default function App() {
  return (
      <Routes>
        <Route path="/" element={<Navigate to="/login" />} />
        <Route path="/login" element={<Login />} />
        <Route path="/register" element={<Register />} />
        <Route path="/profile" element={<Profile />} />
        <Route path="/interests" element={<Interests />} />
        <Route path="/recommendations" element={<Recommendations />} />
        <Route path="/feedbacks" element={<Feedbacks />} />
        <Route path="/recommendations/:hobbyId" element={<RecommendationDetails />} />
      </Routes>
  );
}