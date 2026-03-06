CREATE TABLE IF NOT EXISTS splitwise_clone.refresh_token (
    id BIGSERIAL PRIMARY KEY,
    token VARCHAR(255) NOT NULL UNIQUE,
    user_email VARCHAR(255) NOT NULL,
    expiry_date TIMESTAMP WITH TIME ZONE NOT NULL,
    created_on TIMESTAMP WITH TIME ZONE NOT NULL
);

CREATE INDEX IF NOT EXISTS idx_refresh_token_token ON splitwise_clone.refresh_token(token);
CREATE INDEX IF NOT EXISTS idx_refresh_token_user_email ON splitwise_clone.refresh_token(user_email);

