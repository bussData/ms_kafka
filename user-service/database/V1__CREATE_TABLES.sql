-- ============================================
-- Migration: V1__Create_users_table
-- Description: Crear tabla users y función de trigger
-- Database: userdb (Docker container: postgres-user)
-- ============================================

--tabla users
CREATE TABLE users (
                       id BIGSERIAL PRIMARY KEY,
                       full_name VARCHAR(150) NOT NULL,
                       email VARCHAR(120) UNIQUE NOT NULL,
                       status VARCHAR(30) DEFAULT 'ACTIVE',
                       created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP
);

--data Users:
INSERT INTO users (full_name, email, status) VALUES ('Juan Perez', 'juan.perez@example.com', 'ACTIVE');
INSERT INTO users (full_name, email, status) VALUES ('Jhon Rodriguez', 'jrodriguez@example.com', 'ACTIVE');
INSERT INTO users (full_name, email, status) VALUES ('Diana Tolentino', 'dtolentino@example.com', 'ACTIVE');
commit;
