CREATE TABLE tb_user(
    id BIGSERIAL NOT NULL,
    email VARCHAR(100) NOT NULL,
    password VARCHAR(50) NOT NULL,
    created_at TIMESTAMP NOT NULL,
    updated_at TIMESTAMP NOT NULL,
    active BOOLEAN NOT NULL,

    PRIMARY KEY (id),
    CONSTRAINT uk_user_email UNIQUE (email)
);

CREATE INDEX idx_user_email ON tb_user(email);

CREATE TABLE tb_role(
    id BIGSERIAL NOT NULL,
    name VARCHAR(30) NOT NULL,

    PRIMARY KEY (id)
);

CREATE TABLE tb_user_role(
    user_id BIGINT NOT NULL,
    role_id BIGINT NOT NULL,

    PRIMARY KEY (user_id, role_id),

    CONSTRAINT fk_user_id FOREIGN KEY (user_id) REFERENCES tb_user(id),
    CONSTRAINT fk_role_id FOREIGN KEY (role_id) REFERENCES tb_role(id)
);

CREATE INDEX idx_user_role_user_id ON tb_user_role(user_id);
CREATE INDEX idx_user_role_role_id ON tb_user_role(role_id);