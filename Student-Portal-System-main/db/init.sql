CREATE DATABASE student_portal CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci;
CREATE USER 'portal_user' @'%' IDENTIFIED BY 'PortalPass123!';
GRANT ALL PRIVILEGES ON student_portal.* TO 'portal_user' @'%';
FLUSH PRIVILEGES;
EXIT;
CREATE TABLE users (
  id BIGINT AUTO_INCREMENT PRIMARY KEY,
  name VARCHAR(255),
  email VARCHAR(255) UNIQUE,
  password VARCHAR(255),
  role VARCHAR(50)
);
CREATE TABLE course (
  id BIGINT AUTO_INCREMENT PRIMARY KEY,
  code VARCHAR(50),
  title VARCHAR(255),
  description TEXT,
  credits INT
);
CREATE TABLE enrollment (
  id BIGINT AUTO_INCREMENT PRIMARY KEY,
  student_id BIGINT,
  course_id BIGINT,
  grade DOUBLE,
  FOREIGN KEY (student_id) REFERENCES users(id),
  FOREIGN KEY (course_id) REFERENCES course(id)
);
INSERT INTO users(name, email, password, role)
VALUES (
    'Alice',
    'alice@example.com',
    '$2a$10$...bcrypthash...',
    'STUDENT'
  );
INSERT INTO course(code, title, description, credits)
VALUES ('CS101', 'Intro to CS', 'Programming basics', 3);
-- enrollment rows...