import { useState, useRef, useEffect } from "react";
import { askAiQuestion } from "../services/aiService";
import "./AiAssistant.css";

function AiAssistant() {
  const [isOpen, setIsOpen] = useState(false);

  const [messages, setMessages] = useState([
    {
      role: "assistant",
      content:
        "Hello! I'm your Constitutional AI Assistant. Ask me anything about the Indian Constitution.",
    },
  ]);

  const [question, setQuestion] = useState("");

  const [sessionId, setSessionId] = useState(null);

  const [loading, setLoading] = useState(false);

  const messagesEndRef = useRef(null);


  useEffect(() => {
    messagesEndRef.current?.scrollIntoView({
      behavior: "smooth",
    });
  }, [messages, loading]);


  const handleSubmit = async (event) => {
    event.preventDefault();

    const trimmedQuestion = question.trim();

    if (!trimmedQuestion || loading) {
      return;
    }


    const userMessage = {
      role: "user",
      content: trimmedQuestion,
    };


    setMessages((previousMessages) => [
      ...previousMessages,
      userMessage,
    ]);


    setQuestion("");

    setLoading(true);


    try {
      const response = await askAiQuestion(
        trimmedQuestion,
        sessionId
      );


      if (response.sessionId) {
        setSessionId(response.sessionId);
      }


      const assistantMessage = {
        role: "assistant",
        content:
          response.answer ||
          "I couldn't generate a response. Please try again.",

        relatedArticle:
          response.relatedArticle || null,
      };


      setMessages((previousMessages) => [
        ...previousMessages,
        assistantMessage,
      ]);

    } catch (error) {

      console.error(
        "AI Assistant Error:",
        error
      );


      let errorMessage =
        "Something went wrong while contacting the AI assistant.";


      if (
        error.response?.data?.message
      ) {
        errorMessage =
          error.response.data.message;
      }


      setMessages((previousMessages) => [
        ...previousMessages,
        {
          role: "assistant",
          content: errorMessage,
        },
      ]);

    } finally {

      setLoading(false);

    }
  };


  const startNewConversation = () => {

    setSessionId(null);

    setMessages([
      {
        role: "assistant",
        content:
          "Hello! I'm your Constitutional AI Assistant. What would you like to know?",
      },
    ]);

  };


  return (
    <div className="ai-widget">

      {/* Chat Window */}

      {isOpen && (

        <div className="ai-chat-container">


          {/* Header */}

          <div className="ai-chat-header">

            <div className="ai-header-title">

              <div className="ai-header-symbol">
                ⚖
              </div>


              <div>

                <h3>
                  Constitution AI
                </h3>

                <span>
                  Your constitutional learning assistant
                </span>

              </div>

            </div>


            <button
              className="new-chat-button"
              onClick={startNewConversation}
            >
              New Chat
            </button>

          </div>



          {/* Messages */}

          <div className="ai-chat-messages">

            {messages.map(
              (message, index) => (

                <div
                  key={index}
                  className={`message-row ${message.role}`}
                >

                  <div
                    className={`message-bubble ${message.role}`}
                  >

                    <div className="message-content">

                      {message.content}

                    </div>


                    {message.relatedArticle && (

                      <div className="related-article">

                        📜 {message.relatedArticle}

                      </div>

                    )}

                  </div>

                </div>

              )
            )}


            {loading && (

              <div className="message-row assistant">

                <div className="message-bubble assistant">

                  <div className="typing-indicator">

                    <span></span>

                    <span></span>

                    <span></span>

                  </div>

                </div>

              </div>

            )}


            <div ref={messagesEndRef} />

          </div>



          {/* Input */}

          <form
            className="ai-chat-input-container"
            onSubmit={handleSubmit}
          >

            <input
              type="text"
              placeholder="Ask about the Constitution..."
              value={question}
              onChange={(event) =>
                setQuestion(event.target.value)
              }
              disabled={loading}
            />


            <button
              type="submit"
              disabled={
                loading ||
                !question.trim()
              }
            >
              Send
            </button>

          </form>


          {/* Footer */}

          <div className="ai-chat-footer">

            Educational assistance only. Not legal advice.

          </div>

        </div>

      )}


      {/* Floating Button */}

      <button
        className="ai-floating-button"
        onClick={() => setIsOpen(!isOpen)}
        aria-label="Open Constitution AI Assistant"
      >

        <span className="ai-button-icon">
          ⚖
        </span>


        <span className="ai-button-text">
          Ask Constitution AI
        </span>


        <span className="ai-online-indicator"></span>

      </button>

    </div>
  );
}

export default AiAssistant;