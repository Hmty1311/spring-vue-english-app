CREATE TABLE quiz_results (
    id BIGSERIAL PRIMARY KEY,
    quiz_session_id BIGINT NOT NULL,
    word_id BIGINT NOT NULL,
    correct BOOLEAN NOT NULL,
    answered_at TIMESTAMP NOT NULL,
    created_date TIMESTAMP NOT NULL,
    updated_date TIMESTAMP NOT NULL,
    deleted_date TIMESTAMP,

    CONSTRAINT fk_quiz_results_session
        FOREIGN KEY (quiz_session_id)
        REFERENCES quiz_sessions(id),

    CONSTRAINT fk_quiz_results_word
        FOREIGN KEY (word_id)
        REFERENCES words(id)
);