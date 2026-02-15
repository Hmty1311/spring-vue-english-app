# A-01 画面×API対応表

## 1. 概要

本ドキュメントは、**各画面からどのAPIが呼び出されるかを整理し、画面•API•DBの対応関係を明確にすること**を目的とする。

- 対象フェーズ : 基本設計
- 関連ドキュメント : 
    - B-05 API設計書(API一覧)
    - 各画面基本設計書(S-xx)

---

## 2. 画面×API対応一覧

### S-01 ログイン画面

| No | 操作内容 | API ID | Method | Endpoint | 備考 |
| -- | --- | --- | --- | --- | --- |
| 1 | ログイン | AUTH-01 | POST | `/auth/login` | 認証成功時トップへ遷移 |
| 2 | ログアウト | AUTH-02 | POST | `/auth/logout` | 任意操作 ※ヘッダー等の共通UIから呼び出される想定 | 
| 3 | ログインユーザー取得 | AUTH-03 | GET | `/auth/me` | 初期表示 |

---

### S-02 モード選択画面

| No | 操作内容 | API ID | Method | Endpoint | 備考 |
| -- | --- | --- | --- | --- | --- |
| 1 | ログインユーザー取得 | AUTH-03 | GET | `/auth/me` | ユーザー確認 |

※モード選択時は画面内じ状態管理のみ（API呼び出しなし）

---

### S-03 単語一覧画面

| No | 操作内容 | API ID | Method | Endpoint | 備考 |
| -- | --- | --- | --- | --- | --- |
| 1 | 単語一覧表示 | WORD-01 | GET | `/words` | 暗記フラグ条件 |

---

### S-04 単語登録画面

| No | 操作内容 | API ID | Method | Endpoint | 備考 |
| -- | --- | --- | --- | --- | --- |
| 1 | 単語登録 | WORD-03 | POST | `/words` | 辞書API連携含む |

---

### S-05 単語詳細画面

| No | 操作内容 | API ID | Method | Endpoint | 備考 |
| -- | --- | --- | --- | --- | --- |
| 1 | 単語詳細取得 | WORD-02 | GET | `/words/{wordId}` | 初期表示 |
| 2 | 単語更新 | WORD-04 | PUT | `/words/{wordId}` | 編集保存 |
| 3 | 単語削除 | WORD-05 | DELETE | `/words/{wordId}` | 論理削除 |

---

### S-06 クイズ画面

| No | 操作内容 | API ID | Method | Endpoint | 備考 |
| -- | --- | --- | --- | --- | --- |
| 1 | クイズ出題取得 | QUIZ-01 | GET | `/quizzes` | 出題条件指定 |
| 2 | 学習結果登録 | QUIZ-02 | POST | `/quiz-results` | 回答ごとに保存 |

---

### S-07 学習結果画面

| No | 操作内容 | API ID | Method | Endpoint | 備考 |
| -- | --- | --- | --- | --- | --- |
| 1 | 学習履歴取得 | QUIZ-03 | GET | `/quiz-results` | 一覧表示 |

---

## 3. 備考

- 本対応表は **画面設計・API設計の整合確認用** として使用する
- 実装時は本表をもとに Controller / View の接続を行う
- API詳細（Request / Response）はA-02で定義する
