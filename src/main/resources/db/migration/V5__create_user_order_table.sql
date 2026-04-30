CREATE TABLE IF NOT EXISTS user_order
(
    id               BIGSERIAL PRIMARY KEY,
    user_id          BIGINT         NOT NULL,
    stock_id         BIGINT         NOT NULL,
    direction        VARCHAR(20)    NOT NULL,
    quantity         INTEGER        NOT NULL,
    price_per_share  NUMERIC(19, 4) NOT NULL,
    status           VARCHAR(20)    NOT NULL DEFAULT 'PENDING',
    rejection_reason VARCHAR(255),

    CONSTRAINT fk_user_order_user
        FOREIGN KEY (user_id)
            REFERENCES users (id),

    CONSTRAINT fk_user_order_stock
        FOREIGN KEY (stock_id)
            REFERENCES stock (id),

    CONSTRAINT chk_user_order_direction
        CHECK (direction IN ('BUY', 'SELL')),

    CONSTRAINT chk_user_order_status
        CHECK (status IN ('PENDING', 'APPROVED', 'REJECTED')),

    CONSTRAINT chk_user_order_quantity_positive
        CHECK (quantity > 0),

    CONSTRAINT chk_user_order_price_per_share_non_negative
        CHECK (price_per_share >= 0)
);