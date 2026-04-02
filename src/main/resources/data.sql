-- =====================================================
-- Default Categories + Admin User (PostgreSQL)
-- =====================================================

-- ===============================
-- 1. Insert ADMIN user
-- ===============================
-- Email    : admin@expense.com
-- Password : admin@123 (BCrypt)

INSERT INTO users (email, password, full_name, role, confirm_password)
VALUES (
           'admin@expense.com',
           '$2a$10$y0K.J720sZYqJ3ia11.AQ.edaLd5EiYjGZTD/gIr78E9lI.JrZ9qy',
           'System Admin',
           'ADMIN',
           'admin@123'
       )
    ON CONFLICT (email) DO NOTHING;

-- ===============================
-- 2. Insert Default Categories
-- ===============================

INSERT INTO categories (name, category_color, icon, is_default, user_id)
VALUES
    ('FOOD', 'hsl(210, 80%, 55%)', NULL, true, NULL),
    ('TRANSPORT', 'hsl(210, 80%, 55%)', NULL, true, NULL),
    ('ENTERTAINMENT', 'hsl(210, 80%, 55%)', NULL, true, NULL),
    ('BILLS', 'hsl(210, 80%, 55%)', NULL, true, NULL),
    ('OTHERS', 'hsl(210, 80%, 55%)', NULL, true, NULL)
    ON CONFLICT DO NOTHING;