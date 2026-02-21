# A-02 API詳細設計書 | QUIZ-02 学習結果登録

---

## 1. 基本情報

| 項目 | 内容 |
| --- | --- |
| API ID | QUIZ-02 |
| API名 | クイズ結果学習 |
| Method | POST |
| Endpoint | /api/quiz-results |
| 認証 | 必須（JWT認証） |

---

## 2. 概要

クイズ終了時にユーザーの解答結果を登録する。
クライアントはクイズ終了時に、各問題の正誤情報をまとめて送信する。
サーバーは受信した結果をquiz_resultsテーブルへ保存し、正解数・正答率を集計した結果をレスポンスとして返却する。

---

## 3. リクエスト

### 3-1. Path Parameters

なし

### 3-2. Query Parameters

なし

### 3-3. Request Body

| 項目名 | 型 | 必須 | 説明 |
| --- | -- | -- | -- |
| results | Array | 必須 | 解答結果一覧 |

#### results要素

| 項目名 | 型 | 必須 | 説明 |
| --- | -- | -- | -- |
| wordId | Long | 必須 | 単語ID |
| isCorrect | Boolean | 必須 | 正誤フラグ |

```json
{
  "results": [
    { "wordId": 1, "isCorrect": true },
    { "wordId": 2, "isCorrect": false },
    { "wordId": 3, "isCorrect": true }
  ]
}
```

---

## 4. レスポンス

### 4-1. Response Body(成功時)

- Status 200 OK

| 項目 | 型 | 説明 |
| -- | -- | -- |
| totalCount | Integer | 出題数 |
| correctCount | Integer | 正解数数 |
| incorrectCount | Integer | 不正解数 |
| correctRate | Integer | 正答率（％） |

```json
{
    "totalCount": 3,
    "correctCount": 2,
    "incorrectCount": 1,
    "correctRate": 67
}
```


### 4-2. Response Body(失敗時)

| 項目 | 型 | 説明 |
| -- | -- | -- |
| errorCode | String | エラーコード |
| message | String | エラーメッセージ |

```json
{
    "errorCode":  "QUIZ-001",
    "message": "リクエスト形式が不正です"
}
```

---

## 5. ステータスコード

| ステータス | 内容 |
| --- | --- |
| 200 OK | 正常登録 |
| 400 Bad Request | バリデーションエラー |
| 401 Unauthorized | 未認証 |
| 500 Internal Server Error | 予期せぬエラー |

---

## 6. バリデーション・制約

| 項目 | 必須 | 内容 |
| --- | --- | --- |
| results | 必須 | 1件以上必須 |
| wordId | 必須 | 存在する単語IDであること |
| isCorrect | 必須 | true/false |

---

## 7. 処理概要

1. 認証トークンを検証する。無効な場合は401を返却する。
2. リクエストのバリデーションを実施する。
3. resultsの各要素について以下を実施する
    - wordIdの存在確認
    - quiz_resultsテーブルへ登録
4. 登録完了後、正解数を集計する。
5. totalCount / correctCount /incorrectCount / correctRate を算出する。
6. 集計結果をレスポンスとして返却する。