CREATE TABLE IF NOT EXISTS USER_ASSET
(
    id             BIGSERIAL PRIMARY KEY,
    user_id        BIGINT  NOT NULL,
    stock_id       BIGINT  NOT NULL,
    quantity       NUMERIC NOT NULL,
    price_per_share NUMERIC NOT NULL,

    CONSTRAINT fk_user_asset_user_details
        FOREIGN KEY (user_id)
            REFERENCES USER_DETAILS (id),

    CONSTRAINT fk_user_asset_stock
        FOREIGN KEY (stock_id)
            REFERENCES STOCK (id)
);