CREATE TABLE tasks (
    id BIGSERIAL PRIMARY KEY,
    vin VARCHAR(255) NOT NULL,
    task_type VARCHAR(50) NOT NULL,
    task_status VARCHAR(50) NOT NULL,
    notes TEXT
);