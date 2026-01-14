CREATE TABLE IF NOT EXISTS limits (
    account_id UUID NOT NULL PRIMARY KEY,
    total_limit DECIMAL(19, 2) NOT NULL,
    reserved_limit DECIMAL(19, 2) NOT NULL,
    available_limit DECIMAL(19, 2) NOT NULL
);

CREATE INDEX IF NOT EXISTS idx_limits_1 ON limits(account_id);

