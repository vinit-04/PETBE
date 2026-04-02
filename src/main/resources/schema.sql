-- =====================================================
-- SCHEMA FIXES & CONSTRAINTS
-- =====================================================

-- ===============================
-- USERS TABLE (if not exists)
-- ===============================
CREATE TABLE IF NOT EXISTS users (
                                     id BIGSERIAL PRIMARY KEY,
                                     email VARCHAR(255) NOT NULL,
    password VARCHAR(255) NOT NULL,
    full_name VARCHAR(255),
    confirm_password varchar(255) NOT NULL,
    role VARCHAR(50) NOT NULL
    );

-- Unique email
CREATE UNIQUE INDEX IF NOT EXISTS uq_users_email
    ON users(email);

-- ===============================
-- CATEGORIES TABLE (FIXED)
-- ===============================
CREATE TABLE IF NOT EXISTS categories (
                                          id BIGSERIAL PRIMARY KEY,
                                          name VARCHAR(100) NOT NULL,
    category_color VARCHAR(100) NOT NULL DEFAULT 'hsl(210, 80%, 55%)',
    icon VARCHAR(100),
    is_default BOOLEAN NOT NULL,
    user_id BIGINT NULL,
    CONSTRAINT fk_category_user
    FOREIGN KEY (user_id) REFERENCES users(id)
    );

-- Prevent duplicate categories per user
CREATE UNIQUE INDEX IF NOT EXISTS uq_category_user
    ON categories(name, user_id);

-- Prevent duplicate DEFAULT categories (user_id IS NULL)
CREATE UNIQUE INDEX IF NOT EXISTS uq_default_categories
    ON categories(name)
    WHERE user_id IS NULL;