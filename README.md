# SL-Bank - Modern Banking Application

A full-stack banking application built with React.js, Spring Boot, and PostgreSQL, providing a secure and user-friendly online banking experience.

## 🚀 Features

### User Authentication
- Secure JWT-based authentication
- User registration and login
- Role-based access control

### Account Management
- Create and manage multiple accounts
- View account balances and transaction history
- Fund transfers between accounts
- Transaction history with filtering

### Card Services
- Virtual and physical card management
- Card activation/deactivation
- Transaction limits management

### Exchange Rates
- Real-time currency exchange rates
- Currency conversion

## 🛠️ Technology Stack

### Frontend
- React.js with Vite
- React Router for navigation
- Axios for API calls
- Tailwind CSS for styling
- React Icons

### Backend
- Spring Boot 3.x
- Spring Security with JWT
- Spring Data JPA
- PostgreSQL Database
- Maven for dependency management

### Development Tools
- Git for version control
- Postman/Insomnia for API testing
- ESLint and Prettier for code quality

## 🚀 Getting Started

### Prerequisites
- Node.js (v16 or higher)
- Java 17 or higher
- PostgreSQL (v12 or higher)
- Maven (v3.8 or higher)

### Backend Setup

1. **Database Setup**
   ```bash
   # Create a new PostgreSQL database
   createdb slbank
   ```

2. **Configure Application**
   Update the database configuration in `backend/src/main/resources/application.properties`

3. **Run the application**
   ```bash
   cd backend
   ./mvnw spring-boot:run
   ```
   The backend will be available at `http://localhost:8080`

### Frontend Setup

1. **Install dependencies**
   ```bash
   cd frontend
   npm install
   ```

2. **Start development server**
   ```bash
   npm run dev
   ```
   The frontend will be available at `http://localhost:5173`

## 📚 API Documentation

API documentation is available at `http://localhost:8080/swagger-ui.html` after starting the backend server.

## 🧪 Testing

### Backend Tests
```bash
cd backend
./mvnw test
```

### Frontend Tests
```bash
cd frontend
npm test
```

## 🤝 Contributing

1. Fork the repository
2. Create your feature branch (`git checkout -b feature/AmazingFeature`)
3. Commit your changes (`git commit -m 'Add some AmazingFeature'`)
4. Push to the branch (`git push origin feature/AmazingFeature`)
5. Open a Pull Request

## 📄 License

This project is licensed under the MIT License - see the [LICENSE](LICENSE) file for details.

## 🙏 Acknowledgments

- Built with ❤️ for modern banking solutions
- Thanks to all contributors who have helped shape this project
