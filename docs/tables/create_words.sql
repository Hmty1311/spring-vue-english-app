CREATE TABLE words (

    id BIGSERIAL PRIMARY KEY,

    user_id BIGINT NOT NULL,

    word VARCHAR(100) NOT NULL,

    meaning VARCHAR(255) NOT NULL,

    example TEXT,

    memorized BOOLEAN NOT NULL DEFAULT false,

    created_date TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,

    updated_date TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,

    deleted_date TIMESTAMP,

    CONSTRAINT fk_words_user
        FOREIGN KEY (user_id)
        REFERENCES users(id)

);

COMMENT ON TABLE words IS '単語';

COMMENT ON COLUMN words.id IS '単語ID';

COMMENT ON COLUMN words.user_id IS '所有ユーザーID';

COMMENT ON COLUMN words.word IS '英単語';

COMMENT ON COLUMN words.meaning IS '意味';

COMMENT ON COLUMN words.example IS '例文';

COMMENT ON COLUMN words.memorized IS '暗記済みフラグ';

COMMENT ON COLUMN words.created_date IS '作成日時';

COMMENT ON COLUMN words.updated_date IS '更新日時';

COMMENT ON COLUMN words.deleted_date IS '削除日時';