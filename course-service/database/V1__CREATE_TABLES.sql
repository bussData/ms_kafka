-- ============================================
-- Migration: V1__Create_course_table
-- Description: Crear tabla course y función de trigger
-- Database: coursedb (Docker container: postgres-course)
-- ============================================

-- Tabla course
CREATE TABLE courses (
                         id BIGSERIAL PRIMARY KEY,
                         title VARCHAR(200) NOT NULL,
                         description VARCHAR(100) NOT NULL,
                         published BOOLEAN DEFAULT FALSE,
                         created_at TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP
);

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

CREATE TABLE enrollments (
                             id BIGSERIAL PRIMARY KEY,
                             user_id BIGINT NOT NULL,
                             course_id BIGINT NOT NULL,
                             status VARCHAR(40) DEFAULT 'PENDING_PAYMENT',
                             created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP
);