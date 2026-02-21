# A-02 API詳細設計書 | QUIZ-01 クイズ出題取得

---

## 1. 基本情報

| 項目 | 内容 |
| --- | --- |
| API ID | QUIZ-01 |
| API名 | クイズ出題取得 |
| Method | GET |
| Endpoint | /api/quizzes |
| 認証 | 必須（ログインユーザー） |
| 権限 | 自分の単語のみ削除可能 |

---

## 2. 概要

クイズ開始時に出題する単語一覧を取得する。

ログイン中のユーザーに対し、出題対象の単語をランダムに取得し、クライアントへ返却する。
クライアントは取得したデータをもとにクイズ画面を表示する。

---

## 3. リクエスト

### 3-1. Path Parameters

なし

### 3-2. Query Parameters

なし
※将来的に出題数を指定する場合は ?count=10 などを追加可能

### 3-3. Request Body

なし（GETのため）

---

## 4. レスポンス

### 4-1. Response Body(成功時)

- Status 200 OK

| 項目 | 型 | 説明 |
| -- | -- | -- |
| quizzes　| Array | 出題一覧 |

#### quizzes要素

| 項目 | 型 | 説明 |
| -- | -- | -- |
| wordId | Long | 単語ID |
| question | String | 問題文 |
| choices | Array | 選択肢一覧 |

### choices要素

| 項目 | 型 | 説明 |
| -- | -- | -- |
| choiceText | String | 選択肢文 |

```json
{
  "quizzes": [
    {
      "wordId": 1,
      "question": "apple の意味はどれ？",
      "choices": [
        { "choiceText": "りんご" },
        { "choiceText": "みかん" },
        { "choiceText": "ぶどう" },
        { "choiceText": "ばなな" }
      ]
    },
    {
      "wordId": 2,
      "question": "dog の意味はどれ？",
      "choices": [
        { "choiceText": "ねこ" },
        { "choiceText": "いぬ" },
        { "choiceText": "とり" },
        { "choiceText": "さかな" }
      ]
    }
  ]
}
```

### 4-2. Response Body(失敗時)

| 項目 | 型 | 説明 |
| -- | -- | -- |
| errorCode | String | エラーコード |
| message | String | エラーメッセージ |

```json
{
    "errorCode":  "QUIZ-002",
    "message": "クイズの取得に失敗しました"
}
```

---

## 5. ステータスコード

| ステータス | 内容 |
| --- | --- |
| 200 OK | 正常登録 |
| 401 Unauthorized | 未認証 |
| 500 Internal Server Error | 予期せぬエラー |

---

## 6. バリデーション・制約

なし

---

## 7. 処理概要

1. 認証トークンを検証する。無効な場合は401を返却する。
2. しゅつだい出題対象単語をデータベースから取得する。
3. 取得した単語をランダムに指定件数抽出する。
4. 各単語に対し、選択肢を生成する。
5. クライアント用のレスポンス形式に整形する。
6. 出題一覧を返却する。