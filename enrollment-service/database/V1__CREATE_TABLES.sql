    -- ============================================
    -- Migration: V1__CREATE_TABLES.sql
    -- Database: enrollmentdb (Docker container: postgres-enrollment)
    -- ============================================

    CREATE TABLE enrollments (
                                 id BIGSERIAL PRIMARY KEY,
                                 user_id BIGINT NOT NULL,
                                 course_id BIGINT NOT NULL,
                                 status VARCHAR(40) DEFAULT 'PENDING_PAYMENT',
                                 created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP
    );

