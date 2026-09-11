import {
    useEffect,
    useState,
} from "react";

import {
    useNavigate,
} from "react-router-dom";

import {
    getDashboardStats,
} from "../services/dashboardService";


function AdminDashboard() {

    const navigate =
        useNavigate();


    const [stats, setStats] =
        useState(null);

    const [loading, setLoading] =
        useState(true);

    const [error, setError] =
        useState("");


    useEffect(() => {

        const loadStats =
            async () => {

                try {

                    const data =
                        await getDashboardStats();

                    setStats(data);

                } catch (error) {

                    console.error(error);

                    setError(
                        "Unable to load dashboard statistics."
                    );

                } finally {

                    setLoading(false);
                }
            };


        loadStats();

    }, []);


    if (loading) {

        return (

            <div className="page-container">

                Loading dashboard...

            </div>

        );
    }


    if (error) {

        return (

            <div className="page-container">

                {error}

            </div>

        );
    }


    return (

        <div className="dashboard-page">

            <div className="dashboard-header">

                <span>
                    ADMINISTRATION
                </span>

                <h1>
                    Admin Dashboard
                </h1>

                <p>
                    Manage the constitutional
                    awareness platform.
                </p>

            </div>


            <div className="stats-grid">

                <div className="stat-card">

                    <h3>
                        {stats?.totalUsers ?? 0}
                    </h3>

                    <p>
                        Total Users
                    </p>

                </div>


                <div className="stat-card">

                    <h3>
                        {stats?.totalArticles ?? 0}
                    </h3>

                    <p>
                        Constitutional Articles
                    </p>

                </div>


                <div className="stat-card">

                    <h3>
                        {stats?.totalQuizQuestions ?? 0}
                    </h3>

                    <p>
                        Quiz Questions
                    </p>

                </div>

            </div>


            <div className="dashboard-section">

                <h2>
                    Platform Management
                </h2>


                <div className="dashboard-actions">


                    <button
                        type="button"
                        className="action-card"
                        onClick={() =>
                            navigate("/admin/categories")
                        }
                    >

                        <h3>
                            🗂️ Categories
                        </h3>

                        <p>
                            Create and organize
                            content categories.
                        </p>

                    </button>


                    <button
                        type="button"
                        className="action-card"
                        onClick={() =>
                            navigate("/articles/manage")
                        }
                    >

                        <h3>
                            📜 Articles
                        </h3>

                        <p>
                            View and manage
                            constitutional content.
                        </p>

                    </button>


                    <button
                        type="button"
                        className="action-card"
                        onClick={() =>
                            navigate("/quiz/manage")
                        }
                    >

                        <h3>
                            🧠 Quiz
                        </h3>

                        <p>
                            Create and manage
                            quiz questions.
                        </p>

                    </button>


                    <button
                        type="button"
                        className="action-card"
                        onClick={() =>
                            navigate("/admin/users")
                        }
                    >

                        <h3>
                            👥 Users
                        </h3>

                        <p>
                            Monitor platform users.
                        </p>

                    </button>

                </div>

            </div>

        </div>
    );
}


export default AdminDashboard;