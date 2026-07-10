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
