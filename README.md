# Full Stack Expense Tracker

A full-stack desktop expense tracking application built with **JavaFX**, **Spring Boot**, and **MySQL**.

This project allows users to register, log in, manage transactions, organise categories, view monthly summaries, and analyse spending through charts.  
It also includes **bilingual localisation support (English / 中文)** for the user interface.

---

## Project Overview

The system is divided into two modules:

- **expense-tracker-client** → JavaFX desktop frontend  
- **expense-tracker-springboot-server** → Spring Boot backend REST API

The frontend communicates with the backend using HTTP requests.

---

## Features

### User Management
- User registration
- User login
- Session-based dashboard access

### Transaction Management
- Add new transaction
- Edit existing transaction
- Delete transaction
- Income / Expense support

### Categories
- Create custom categories
- Edit categories
- Delete categories

### Dashboard & Reports
- View all transactions
- Year filtering
- Monthly income / expense summary
- Financial charts

### Internationalisation
- English interface
- Chinese interface
- Runtime language switching

---

## Tech Stack

### Frontend
- Java
- JavaFX
- CSS Styling

### Backend
- Spring Boot
- Spring Web
- Spring Data JPA

### Database
- MySQL

### Other Tools
- Git / GitHub

---

## Project Structure

```text
fullstack-expense-tracker
│
├── expense-tracker-client
│   ├── controllers
│   ├── views
│   ├── models
│   ├── dialogs
│   ├── utils
│   └── resources
│
└── expense-tracker-springboot-server
    ├── controllers
    ├── entities
    ├── repositories
    ├── services
    └── resources
