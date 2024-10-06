CREATE TABLE tb_user
(
    id         BIGINT AUTO_INCREMENT PRIMARY KEY,
    username   VARCHAR(255) UNIQUE NOT NULL,
    password   VARCHAR(255),
    email      VARCHAR(255) UNIQUE,
    mobile     VARCHAR(20) UNIQUE  NOT NULL,
    gender     ENUM ('MALE', 'FEMALE', 'OTHER') DEFAULT 'OTHER',
    avatar_url VARCHAR(1000),
    created_at DATETIME                         DEFAULT CURRENT_TIMESTAMP,
    updated_at DATETIME                         DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP
);
INSERT INTO tb_user (id, username, mobile, password, created_at)
VALUES (1067246875900000001, 'mark', '13612345678', '8c6976e5b5410415bde908bd4dee15dfb167a9c873fc4bb8a81f6f2ab448a918',
        now());

-- 用户Token表
CREATE TABLE tb_token
(
    id          bigint       NOT NULL COMMENT 'id',
    user_id     bigint       NOT NULL COMMENT '用户ID',
    token       varchar(100) NOT NULL COMMENT 'token',
    expire_date datetime COMMENT '过期时间',
    update_date datetime COMMENT '更新时间',
    PRIMARY KEY (id),
    UNIQUE INDEX (user_id),
    UNIQUE INDEX (token)
);