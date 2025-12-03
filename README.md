Here is a **clean, simple, hand-written README.md** exactly like a real developer would submit for an assignment.
You can copy-paste it directly into your project root as:

```
README.md
```

---

# 📌 README — Spring Boot Notes API (JWT Authentication)

This project is a simple Spring Boot REST API that supports **user registration**, **login with JWT**, and **CRUD-style note creation/listing** for the authenticated user.

It demonstrates clean code, JWT authentication, Spring Security 6 configuration, and basic database operations using H2 + JPA.

---

## 🚀 How to Run the Project

### **Prerequisites**

* Java 17+
* Maven 3+

### **Steps**

1. Clone or download the project
2. Navigate to the project folder
3. Run:

```bash
mvn spring-boot:run
```

The application starts at:

👉 **[http://localhost:8080](http://localhost:8080)**

### H2 Console (optional)

```
http://localhost:8080/h2-console
```

Use:

* JDBC URL: `jdbc:h2:mem:notesdb`
* Username: `sa`
* Password: *(empty)*

---

# 🧩 API Endpoints

## 🔐 **Authentication APIs**

---

### **1. Register User**

**POST** `/auth/register`

**Request Body**

```json
{
  "name": "Himanshu",
  "email": "himanshu@example.com",
  "password": "pass123"
}
```

**Success Response**

```json
{
  "message": "User registered successfully"
}
```

**Errors**

* `400 Bad Request` → invalid email format
* `409 Conflict` → email already exists

---

### **2. Login User (Generate JWT)**

**POST** `/auth/login`

**Request Body**

```json
{
  "email": "himanshu@example.com",
  "password": "pass123"
}
```

**Success Response**

```json
{
  "token": "eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9..."
}
```

Copy this token for use in the protected APIs below.

---

# 📝 Notes APIs (Require JWT Token)

All protected endpoints require this header:

```
Authorization: Bearer <your-token-here>
```

---

### **3. Create Note**

**POST** `/notes`

**Headers**

```
Authorization: Bearer <token>
```

**Request Body**

```json
{
  "title": "My first note",
  "content": "This is a secure note."
}
```

**Response**

```json
{
  "id": 1,
  "title": "My first note",
  "content": "This is a secure note.",
  "createdAt": "2025-01-15T10:20:33.123"
}
```

---

### **4. Get All Notes of Logged-in User**

**GET** `/notes`

**Headers**

```
Authorization: Bearer <token>
```

**Response**

```json
[
  {
    "id": 1,
    "title": "My first note",
    "content": "This is a secure note.",
    "createdAt": "2025-01-15T10:20:33.123"
  }
]

