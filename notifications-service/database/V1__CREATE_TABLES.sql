-- ============================================
-- Migration: V1__CREATE_TABLES.sql
-- Database: notificationdb (Docker container: postgres-notification)
-- ============================================
CREATE TABLE notifications (
                               id BIGSERIAL PRIMARY KEY,
                               user_id BIGINT NOT NULL,
                               message TEXT NOT NULL,
                               sent BOOLEAN DEFAULT FALSE,
                               created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP
);