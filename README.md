# 🇮🇳 Awareness of the Indian Constitution

A full-stack web application designed to make the **Constitution of India easier to learn and understand** through structured constitutional content, quizzes, learning resources, progress tracking, role-based features, and an AI-powered Constitutional Assistant.

The platform provides different capabilities for **Citizens, Educators, and Administrators**, allowing users to learn about the Constitution while enabling authorized users to manage educational content.

---

## 📌 About the Project

The Constitution of India contains important information about citizens' rights, duties, government structure, and the principles of the country. However, understanding constitutional content can sometimes be difficult because of its formal and legal language.

This project aims to provide a **simple and interactive platform for constitutional awareness**.

Users can explore constitutional articles, learn through educational content, take quizzes, track their learning progress, and ask questions through the AI Constitutional Assistant.

Educators and Administrators can manage the educational content and platform features according to their roles.

---

## ✨ Key Features

### 🔐 Authentication & Security

- User registration and login
- JWT-based authentication
- Secure password handling
- Role-based authorization
- Protected API endpoints
- Protected frontend routes
- Role-specific access control

### 📖 Constitution Articles

- Browse constitutional articles
- View detailed article information
- Simplified explanations for easier understanding
- Constitutional text and related information
- Search articles using keywords
- Pagination for article listings
- Organize articles using Parts and Categories
- Published/unpublished article management

### 🧑‍🏫 Educator Features

Educators can manage educational content available to their role.

- Create constitutional articles
- Update articles
- Manage their articles
- Manage quiz questions
- Access an Educator Dashboard
- View relevant platform statistics

### 👨‍💼 Administrator Features

Administrators have broader platform management capabilities.

- Admin Dashboard
- User management
- Activate or deactivate users
- Manage constitutional Categories
- Manage constitutional Parts
- Manage articles
- Manage quiz questions
- Access platform statistics
- Manage educational content

### 📝 Quiz System

- Interactive constitutional quizzes
- Multiple quiz questions
- Quiz submission and evaluation
- Automatic score calculation
- Quiz attempt tracking
- Quiz result information
- Quiz question management for authorized users

### 📊 Learning & Progress Tracking

The platform tracks learning activity to help users understand their progress.

- Quiz attempt tracking
- Correct and incorrect answer tracking
- Score and percentage calculation
- Average performance
- Learning progress information
- Article activity tracking

### 📚 Learning Resources

The application provides a structured learning system containing:

- Learning modules
- Lessons
- Learning resources
- Different resource types
- Structured educational content

### 🤖 AI Constitutional Assistant

The application includes an AI-powered Constitutional Assistant that helps users ask questions related to the Constitution of India.

Users can ask questions about:

- Specific constitutional Articles
- Fundamental Rights
- Constitutional concepts
- General constitutional awareness

The assistant uses the application's constitutional content to provide relevant responses and can identify related constitutional articles when applicable.

### 👤 Profile Management

Authenticated users can:

- View their profile
- Update profile information
- Change their password
- Access features according to their assigned role

### 💬 Chat Sessions

The backend supports structured AI conversation data through:

- Chat sessions
- Chat messages
- Message roles
- Message intents

### ⚠️ Error Handling

The backend includes centralized exception handling for common application errors such as:

- Resource not found
- Duplicate resources
- Validation errors
- API errors

This helps provide consistent responses to the frontend.

---

## 👥 User Roles

| Role | Main Capabilities |
|------|-------------------|
| **Citizen** | Explore articles, learn constitutional concepts, take quizzes, view progress, and use the AI Assistant |
| **Educator** | Manage educational content and quiz questions and access the Educator Dashboard |
| **Administrator** | Manage users, Categories, Parts, content, quizzes, and platform-level features |

Role-based access is enforced on both the **frontend and backend**.

---

## 🏗️ Application Architecture

The project follows a layered full-stack architecture.

```text
                    ┌──────────────────────┐
                    │      React.js        │
                    │      Frontend        │
                    └──────────┬───────────┘
                               │
                         REST API / JSON
                               │
                               ▼
                    ┌──────────────────────┐
                    │    Spring Boot       │
                    │       Backend        │
                    └──────────┬───────────┘
                               │
                ┌──────────────┼──────────────┐
                │              │              │
                ▼              ▼              ▼
        Spring Security     Services    JPA / Hibernate
                │              │              │
                │              │              ▼
                │              │        MySQL Database
                │              │
                ▼              ▼
             JWT Auth     AI Assistant