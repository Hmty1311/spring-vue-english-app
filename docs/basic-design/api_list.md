# API設計書 | API一覧（api_list.md）

## 1. 概要

本ドキュメントは、英単語学習アプリにおける
**バックエンドAPIの一覧と責務を整理すること**を目的とする。

- 認証方式: ユーザーID + パスワード（セッション / JWT想定）
- API種別: REST API
- Base URL: `/api`

---

## 2. 認証・認可系 API

| No | API名 | Method | Endpoint | 説明 |
| -- | --- | --- | --- | --- |
| AUTH-01 | ログイン | POST | `/auth/login` | ユーザーID・パスワードでログイン |
| AUTH-02 | ログアウト | POST | `/auth/logout` | ログアウト処理 |
| AUTH-03 | ログインユーザー取得 | GET | `/auth/me` | ログイン中ユーザー情報取得 |

---

## 3. ユーザー管理 API

| No | API名 | Method | Endpoint | 説明 |
| -- | --- | --- | --- | --- |
| USER-01 | ユーザー登録 | POST | `/users` | 新規ユーザー作成 |
| USER-02 | ユーザー取得 | GET | `/users/{userId}` | ユーザー情報取得 |
| USER-03 | ユーザー更新 | PUT | `/users/{userId}` | ユーザー情報更新 |

---

## 4. 単語管理 API

| No | API名 | Method | Endpoint | 説明 |
| -- | --- | --- | --- | --- |
| WORD-01 | 単語一覧取得 | GET | `/words` | 単語一覧取得（条件検索可） |
| WORD-02 | 単語詳細取得 | GET | `/words/{wordId}` | 単語詳細取得 |
| WORD-03 | 単語登録 | POST | `/words` | 新規単語登録 |
| WORD-04 | 単語更新 | PUT | `/words/{wordId}` | 単語更新 |
| WORD-05 | 単語削除 | DELETE | `/words/{wordId}` | 単語削除 |

---

## 5. タグ管理 API

| No | API名 | Method | Endpoint | 説明 |
| -- | --- | --- | --- | --- |
| TAG-01 | タグ一覧 | GET | `/tags` | タグ一覧取得 |
| TAG-02 | タグ登録 | POST | `/tags` | タグ作成 |
| TAG-03 | タグ更新 | PUT | `/tags/{tagId}` | タグ更新 |
| TAG-04 | タグ削除 | DELETE | `/tags/{tagId}` | タグ削除 |

---

## 6. 単語×タグ関連 API

| No | API名 | Method | Endpoint | 説明 |
| -- | --- | --- | --- | --- |
| WT-01 | 単語にタグ付与 | POST | `/words/{wordId}/tags` | 単語にタグを紐付け |
| WT-02 | 単語のタグ解除 | DELETE | `/words/{wordId}/tags/{tagId}` | タグ紐付け解除 |

---

## 7. クイズ・学習 API

| No | API名 | Method | Endpoint | 説明 |
| --- | --- | --- | --- | --- |
| QUIZ-01 | クイズ出題取得 | GET | `/quizzes` | クイズ用単語取得 |
| QUIZ-02 | 学習結果登録 | POST | `/quiz-results` | クイズ結果保存 |
| QUIZ-03 | 学習履歴取得 | GET | `/quiz-results` | 学習履歴一覧取得 |

---

## 8. 備考

- 各APIの **Request / Response 詳細**は  
  `api_detail_xxx.md` にて別途定義する
- 認可（本人チェック）は Controller または Filter で実施
- 一覧系APIは将来的にページング対応を想定
