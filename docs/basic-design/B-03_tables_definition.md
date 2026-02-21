# テーブル定義書

## 共通方針

- DB: PostgreSQL
- 文字コード: UTF-8
- 主キー: BIGINT GENERATED ALWAYS AS IDENTITY（自動採番）
- 日付型: TIMESTAMP
- 論理削除: deleted_date IS NOT NULL を削除済みとする
- created_date: INSERT時に自動設定
- updated_date: UPDATE時に自動更新

---

## users（ユーザー）

### テーブル概要
ログイン及び利用者情報を管理する。

### カラム定義

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

## words（単語）

| 物理名 | 論理名 | 型 | PK | FK | NOT NULL | 備考 |
| --- | --- | --- | --- | --- | --- | --- |
| id | 単語ID | BIGINT | ○ | | ○ | |
| user_id | 所有ユーザーID | BIGINT | | users.id | ○ | |
| word | 英単語 | VARCHAR(100) | | | ○ | |
| meaning | 意味 | VARCHAR(255) | | | ○ | |
| example | 例文 | TEXT | | | | |
| memorized | 暗記済みフラグ | BOOLEAN | | | ○ | |
| created_date | 作成日時 | TIMESTAMP | | | ○ | |
| updated_date | 更新日時 | TIMESTAMP | | | ○ | |
| deleted_date | 削除日時 | TIMESTAMP | | | | |

---

## quiz_results（学習結果）

| 物理名 | 論理名 | 型 | PK | FK | NOT NULL | 備考 |
| --- | --- | --- | --- | --- | --- | --- | --- |
| id | 結果ID | BIGINT | ○ | | ○ | IDENTITY | |
| user_id | ユーザーID | BIGINT | | users.id | ○ | | |
| word_id | 単語ID | BIGINT | | words.id | ○ | | |
| correct | 正誤フラグ | BOOLEAN | | | ○ | FALSE | |
| answered_date | 回答日時 | TIMESTAMP | | | ○ | CURRENT_TIMESTAMP | |
| created_date | 作成日時 | TIMESTAMP | | | ○ | CURRENT_TIMESTAMP | |
| updated_date | 更新日時 | TIMESTAMP | | | ○ | CURRENT_TIMESTAMP | |
| deleted_date | 削除日時 | TIMESTAMP | | | | NULL | |

---

## 論理削除ルール

| 状態 | 条件 |
| -- | -- |
| 有効データ | deleted_date IS NULL |
| 削除済み | deleted_date IS NOT NULL |