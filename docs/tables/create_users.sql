CREATE TABLE users (

    id BIGSERIAL PRIMARY KEY,

    login_id VARCHAR(16) NOT NULL UNIQUE,

    password VARCHAR(255) NOT NULL,

    name VARCHAR(100) NOT NULL,

    created_date TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,

    updated_date TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,

    deleted_date TIMESTAMP

);

COMMENT ON TABLE users IS 'ユーザー';

COMMENT ON COLUMN users.id IS 'ユーザーID';

COMMENT ON COLUMN users.login_id IS 'ログイン用ID';

COMMENT ON COLUMN users.password IS 'ハッシュ化パスワード';

COMMENT ON COLUMN users.name IS '表示名';

COMMENT ON COLUMN users.created_date IS '作成日時';

COMMENT ON COLUMN users.updated_date IS '更新日時';

COMMENT ON COLUMN users.deleted_date IS '削除日時';