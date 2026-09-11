import {
  BrowserRouter,
  Routes,
  Route,
} from "react-router-dom";

import Navbar from "./components/Navbar";

import Home from "./pages/Home";

import Articles from "./pages/Articles";
import ArticleDetails from "./pages/ArticleDetails";

import ManageArticles from "./pages/ManageArticles";
import ArticleForm from "./pages/ArticleForm";

import Quiz from "./pages/Quiz";
import Progress from "./pages/Progress";

import Login from "./pages/Login";
import Register from "./pages/Register";

import AdminDashboard from "./pages/AdminDashboard";
import EducatorDashboard from "./pages/EducatorDashboard";
import Categories from "./pages/Categories";

import ProtectedRoute from "./components/ProtectedRoute";

import AiAssistant from "./components/AiAssistant";

import Users from "./pages/Users";

import ManageQuiz from "./pages/ManageQuiz";

import Profile from "./pages/Profile";

import "./App.css";


function App() {

  return (

    <BrowserRouter>

      <Navbar />


      <main className="main-content">

        <Routes>


          {/* =====================================
              HOME
          ===================================== */}

          <Route
            path="/"
            element={<Home />}
          />


          {/* =====================================
              PUBLIC ARTICLES
          ===================================== */}

          <Route
            path="/articles"
            element={<Articles />}
          />


          <Route
            path="/articles/:id"
            element={<ArticleDetails />}
          />


          {/* =====================================
              ARTICLE MANAGEMENT

              ADMIN:
              - Manage all articles
              - Create articles
              - Edit articles
              - Publish / Unpublish
              - Delete articles

              EDUCATOR:
              - Manage own articles
              - Create articles
              - Edit own articles
              - Publish / Unpublish own articles
          ===================================== */}

          <Route
            path="/articles/manage"
            element={
              <ProtectedRoute
                allowedRoles={[
                  "ADMIN",
                  "EDUCATOR",
                ]}
              >
                <ManageArticles />
              </ProtectedRoute>
            }
          />


          {/* =====================================
              CREATE ARTICLE
          ===================================== */}

          <Route
            path="/articles/create"
            element={
              <ProtectedRoute
                allowedRoles={[
                  "ADMIN",
                  "EDUCATOR",
                ]}
              >
                <ArticleForm />
              </ProtectedRoute>
            }
          />


          {/* =====================================
              EDIT ARTICLE
          ===================================== */}

          <Route
            path="/articles/:id/edit"
            element={
              <ProtectedRoute
                allowedRoles={[
                  "ADMIN",
                  "EDUCATOR",
                ]}
              >
                <ArticleForm />
              </ProtectedRoute>
            }
          />


          {/* =====================================
              QUIZ
          ===================================== */}

          <Route
            path="/quiz"
            element={<Quiz />}
          />


          {/* =====================================
              CITIZEN PROGRESS
          ===================================== */}

          <Route
            path="/progress"
            element={
              <ProtectedRoute
                allowedRoles={[
                  "CITIZEN",
                ]}
              >
                <Progress />
              </ProtectedRoute>
            }
          />


          {/* =====================================
              USER PROFILE

              Available for:
              - ADMIN
              - EDUCATOR
              - CITIZEN
          ===================================== */}

          <Route
            path="/profile"
            element={
              <ProtectedRoute
                allowedRoles={[
                  "ADMIN",
                  "EDUCATOR",
                  "CITIZEN",
                ]}
              >
                <Profile />
              </ProtectedRoute>
            }
          />


          {/* =====================================
              AUTHENTICATION
          ===================================== */}

          <Route
            path="/login"
            element={<Login />}
          />


          <Route
            path="/register"
            element={<Register />}
          />


          {/* =====================================
              ADMIN DASHBOARD
          ===================================== */}

          <Route
            path="/admin/dashboard"
            element={
              <ProtectedRoute
                allowedRoles={[
                  "ADMIN",
                ]}
              >
                <AdminDashboard />
              </ProtectedRoute>
            }
          />


          {/* =====================================
              USER MANAGEMENT
          ===================================== */}

          <Route
            path="/admin/users"
            element={
              <ProtectedRoute
                allowedRoles={[
                  "ADMIN",
                ]}
              >
                <Users />
              </ProtectedRoute>
            }
          />


          {/* =====================================
              CATEGORY MANAGEMENT
          ===================================== */}

          <Route
            path="/admin/categories"
            element={
              <ProtectedRoute
                allowedRoles={[
                  "ADMIN",
                ]}
              >
                <Categories />
              </ProtectedRoute>
            }
          />


          {/* =====================================
              QUIZ MANAGEMENT

              ADMIN:
              Full quiz management

              EDUCATOR:
              Manage quiz questions
              and view quiz attempts
          ===================================== */}

          <Route
            path="/quiz/manage"
            element={
              <ProtectedRoute
                allowedRoles={[
                  "ADMIN",
                  "EDUCATOR",
                ]}
              >
                <ManageQuiz />
              </ProtectedRoute>
            }
          />


          {/* =====================================
              EDUCATOR DASHBOARD
          ===================================== */}

          <Route
            path="/educator/dashboard"
            element={
              <ProtectedRoute
                allowedRoles={[
                  "EDUCATOR",
                ]}
              >
                <EducatorDashboard />
              </ProtectedRoute>
            }
          />


        </Routes>

      </main>


      {/* =====================================
          GLOBAL AI ASSISTANT
      ===================================== */}

      <AiAssistant />

    </BrowserRouter>

  );

}


export default App;