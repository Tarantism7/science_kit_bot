CREATE TABLE users (
    id BIGSERIAL PRIMARY KEY,
    chat_id BIGINT,
    username VARCHAR(255),
    locale VARCHAR(255),
    converter_state VARCHAR(255),
    source_unit VARCHAR(255),
    target_unit VARCHAR(255),
    primary_value DOUBLE PRECISION
);