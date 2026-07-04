-- ============================================
-- Migration: V1__Create_course_table
-- Description: Crear tabla course y función de trigger
-- Database: coursedb (Docker container: postgres-course)
-- ============================================

-- Tabla course
CREATE TABLE courses (
                         id BIGSERIAL PRIMARY KEY,
                         title VARCHAR(100) NOT NULL,
                         description VARCHAR(100) NOT NULL,
                         instructor VARCHAR(100),
                         status VARCHAR(10),
                         created_at TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP
);