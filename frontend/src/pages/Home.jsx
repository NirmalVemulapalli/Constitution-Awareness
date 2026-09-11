import { Link } from "react-router-dom";

function Home() {

  return (

    <div className="home-page">

      <section className="hero">

        <div className="hero-content">

          <span className="hero-badge">
            🇮🇳 संविधान • Constitution of India
          </span>

          <h1>
            Understand Your Constitution.
            <br />
            <span>
              Understand Your Rights.
            </span>
          </h1>

          <p>
            An interactive platform designed to help
            citizens explore the Constitution of India,
            understand fundamental rights, and learn
            through simplified explanations.
          </p>

          <div className="hero-actions">

            <Link
              to="/articles"
              className="primary-button"
            >
              Explore Constitution →
            </Link>

            <a
              href="#features"
              className="secondary-button"
            >
              Learn More
            </a>

          </div>

        </div>


        <div className="hero-card">

          <div className="constitution-symbol">
            ⚖
          </div>

          <h2>
            Constitution Awareness
          </h2>

          <p>
            Knowledge is the foundation of responsible citizenship.
          </p>

          <div className="hero-card-footer">
            <span>Articles</span>
            <span>Rights</span>
            <span>Democracy</span>
          </div>

        </div>

      </section>


      <section
        id="features"
        className="features-section"
      >

        <div className="section-heading">

          <span>
            LEARN • UNDERSTAND • PARTICIPATE
          </span>

          <h2>
            Explore the Constitution
            in a simpler way
          </h2>

        </div>


        <div className="features-grid">

          <div className="feature-card">

            <div className="feature-icon">
              📜
            </div>

            <h3>
              Constitutional Articles
            </h3>

            <p>
              Browse important constitutional articles
              with structured and simplified information.
            </p>

          </div>


          <div className="feature-card">

            <div className="feature-icon">
              🔎
            </div>

            <h3>
              Smart Search
            </h3>

            <p>
              Quickly discover constitutional concepts
              using keywords and categories.
            </p>

          </div>


          <div className="feature-card">

            <div className="feature-icon">
              🎓
            </div>

            <h3>
              Simplified Learning
            </h3>

            <p>
              Complex constitutional concepts explained
              in a more accessible way.
            </p>

          </div>

        </div>

      </section>


      <section className="cta-section">

        <h2>
          The Constitution belongs to every citizen.
        </h2>

        <p>
          Start exploring and understanding the
          principles that shape our democracy.
        </p>

        <Link
          to="/articles"
          className="primary-button"
        >
          Start Exploring →
        </Link>

      </section>

    </div>

  );
}

export default Home;