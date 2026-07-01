CREATE TABLE whisky_inventory (
    id          BIGSERIAL    PRIMARY KEY,
    age_label   VARCHAR(20)  NOT NULL,
    dist_year   VARCHAR(20),
    ttl         VARCHAR(20),
    year_values JSONB        NOT NULL DEFAULT '{}'::jsonb,
    sort_order  INTEGER      NOT NULL DEFAULT 0,
    created_at  TIMESTAMP    NOT NULL DEFAULT NOW(),
    updated_at  TIMESTAMP    NOT NULL DEFAULT NOW()
);
