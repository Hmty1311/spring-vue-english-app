# テーブル定義書

## 共通方針

- DB: PostgreSQL
- 主キー: BIGINT
- 論理削除: deleted_date IS NOT NULL を削除済みとする
- 日時カラム: TIMESTAMP
- 文字コード: UTF-8

---

## USERS（ユーザー）

| 物理名 | 論理名 | 型 | PK | FK | NOT NULL | 備考 |
| --- | --- | --- | --- | --- | --- | --- |
| id | ユーザーID | BIGINT | ○ | | ○ | |
| login_id | ログイン用ID | VARCHAR(16) | | | ○ | UNIQUE |
| password | ハッシュ化パスワード | VARCHAR(255) | | | ○ | |
| name | 表示名 | VARCHAR(100) | | | ○ | |
| created_date | 作成日時 | TIMESTAMP | | | ○ | |
| updated_date | 更新日時 | TIMESTAMP | | | ○ | |
| deleted_date | 削除日時 | TIMESTAMP | | | | |

---

## WORDS（単語）

| 物理名 | 論理名 | 型 | PK | FK | NOT NULL | 備考 |
| --- | --- | --- | --- | --- | --- | --- |
| id | 単語ID | BIGINT | ○ | | ○ | |
| user_id | 所有ユーザーID | BIGINT | | USERS.id | ○ | |
| word | 英単語 | VARCHAR(100) | | | ○ | |
| meaning | 意味 | VARCHAR(255) | | | ○ | |
| example | 例文 | TEXT | | | | |
| memorized | 暗記済みフラグ | BOOLEAN | | | ○ | |
| created_date | 作成日時 | TIMESTAMP | | | ○ | |
| updated_date | 更新日時 | TIMESTAMP | | | ○ | |
| deleted_date | 削除日時 | TIMESTAMP | | | | |

---

## 論理削除ルール

- deleted_date IS NULL : 有効データ
- deleted_date IS NOT NULL : 削除済み
