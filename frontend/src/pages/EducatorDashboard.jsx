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


function EducatorDashboard() {


    const navigate =
        useNavigate();


    const [stats, setStats] =
        useState(null);

    const [loading, setLoading] =
        useState(true);

    const [error, setError] =
        useState("");


    /*
     * =====================================
     * LOAD DASHBOARD STATISTICS
     * =====================================
     */

    useEffect(() => {

        const loadStats =
            async () => {

                try {

                    const data =
                        await getDashboardStats();


                    setStats(
                        data
                    );

                } catch (error) {

                    console.error(
                        error
                    );


                    setError(
                        "Unable to load dashboard statistics."
                    );

                } finally {

                    setLoading(
                        false
                    );
                }
            };


        loadStats();

    }, []);


    /*
     * =====================================
     * LOADING
     * =====================================
     */

    if (loading) {

        return (

            <div className="page-container">

                Loading dashboard...

            </div>

        );
    }


    /*
     * =====================================
     * ERROR
     * =====================================
     */

    if (error) {

        return (

            <div className="page-container">

                {error}

            </div>

        );
    }


    /*
     * =====================================
     * DASHBOARD
     * =====================================
     */

    return (

        <div className="dashboard-page">


            {/* =====================================
                HEADER
            ===================================== */}

            <div className="dashboard-header">

                <span>
                    EDUCATOR PORTAL
                </span>


                <h1>
                    Educator Dashboard
                </h1>


                <p>
                    Create educational content,
                    manage quiz questions, and
                    monitor citizen learning.
                </p>

            </div>


            {/* =====================================
                STATISTICS
            ===================================== */}

            <div className="stats-grid">


                <div className="stat-card">

                    <h3>
                        {stats?.totalArticles ?? 0}
                    </h3>

                    <p>
                        Articles Available
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


            {/* =====================================
                CONTENT MANAGEMENT
            ===================================== */}

            <div className="dashboard-section">


                <h2>
                    Content Management
                </h2>


                <div className="dashboard-actions">


                    {/* =====================================
                        MANAGE ARTICLES
                    ===================================== */}

                    <button
                        type="button"
                        className="action-card"
                        onClick={() =>
                            navigate(
                                "/articles/manage"
                            )
                        }
                    >

                        <h3>
                            📜 Manage Articles
                        </h3>


                        <p>
                            View, edit, and manage
                            your constitutional
                            learning content.
                        </p>

                    </button>


                    {/* =====================================
                        CREATE ARTICLE
                    ===================================== */}

                    <button
                        type="button"
                        className="action-card"
                        onClick={() =>
                            navigate(
                                "/articles/create"
                            )
                        }
                    >

                        <h3>
                            ✍️ Create Article
                        </h3>


                        <p>
                            Add new educational
                            constitutional content.
                        </p>

                    </button>


                    {/* =====================================
                        MANAGE QUIZ
                    ===================================== */}

                    <button
                        type="button"
                        className="action-card"
                        onClick={() =>
                            navigate(
                                "/quiz/manage"
                            )
                        }
                    >

                        <h3>
                            📝 Manage Quiz
                        </h3>


                        <p>
                            Create and manage
                            quiz questions for
                            citizens.
                        </p>

                    </button>


                    {/* =====================================
                        CITIZEN PERFORMANCE
                    ===================================== */}

                    <button
                        type="button"
                        className="action-card"
                        onClick={() =>
                            navigate(
                                "/quiz/manage"
                            )
                        }
                    >

                        <h3>
                            📊 Citizen Performance
                        </h3>


                        <p>
                            View quiz attempts
                            and monitor citizen
                            learning performance.
                        </p>

                    </button>


                </div>

            </div>


        </div>
    );
}


export default EducatorDashboard;