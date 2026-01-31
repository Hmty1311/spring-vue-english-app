# A-02 API詳細設計書 | WORD-03 単語登録

---

## 1. 基本情報

| 項目 | 内容 |
| --- | --- |
| API ID | WORD-03 |
| API名 | 単語登録 |
| Method | POST |
| Endpoint | `/api/words` |
| 認証 | 必須（ログインユーザー） |
| 権限 | 自分の単語のみ登録可能 |

---

## 2. 概要

英単語を新規登録する。
登録された単語はログインユーザーに紐づいて保存される。
登録後は、登録された単語情報を返却する。

---

## 3. リスエスト

 ### 3-1. Path Parameters

 なし

 ### 3-2. Query Parameters

 なし

 ### 3-3. Request Body

| 項目名 | 型 | 必須 | 説明 |
| --- | -- | -- | -- |
| word | String | 必須 | 英単語 | 
| meaning | String | 必須 |日本語訳 |
| example | String | 任意 |例文 |
| memorized | Boolean | 任意 |暗記済みフラグ |
| tagIds | List<Long> | 任意|紐づけタグID一覧 |

```json
{
    "word": "apple",
    "meaning": "りんご",
    "example": "I eat an apple every day.",
    "memorized": false,
    "tagIds": [1]
}
```

---

## 4. レスポンス

### 4-1. Response Body（成功時）

| 項目名 | 型 | 説明 |
| --- | -- | -- |
| wordId | Long | 単語ID |
| word | String | 英単語 | 
| meaning | String | 日本語訳 |
| example | String | 例文 |
| memorized | Boolean | 暗記済みフラグ |
| tagIds | List<Long> | 紐づけタグID一覧 |
| createdAt | LocalDateTime | 登録日時 |
| updatedAt | LocalDateTime | 更新日時 |

```json
{
    "wordId": 1,
    "word": "apple",
    "meaning": "りんご",
    "example": "I eat an apple every day.",
    "memorized": false,
    "tagIds": [1],
    "createdAt": "2026-01-01T10:00:00",
    "updatedAt": "2026-01-01T10:00:00"
}
```

### 4-2. Response Body（失敗時）

| 項目 | 型 | 説明 |
| -- | -- | -- |
| errorCode | String | エラーコード |
| message | String | エラーメッセージ |

```json
{
    "errorCode": "VALIDATION_ERROR",
    "message": "英単語は必須です"
}
```

---

## 5. ステータスコード

| ステータス | 内容 |
| --- | -- |
| 201 Created | 正常登録 |
| 400 Bad Request | バリデーションエラー |
| 401 Unauthorized | 未ログイン |
| 404 Not Found | 指定されたタグが存在しない、または他ユーザーのタグ |
| 500 Internal Server Error | 予期せぬエラー |

---

## 6. バリデーション・制約

| 項目 | 必須 | 内容 |
| -- | -- | -- |
| word | 必須 | 1 ~ 100文字 |
| meaning | 必須 | 1~255文字 |
| tagIds | 任意 | ログインユーザーが所有するタグIDのみ指定可 |
| memorized | 任意 | 未指定時は false |

---

## 7. 処理概要(内部)

1. 認証情報からログインユーザーを取得
2. リクエストBodyを取得
3. 入力値のバリデーションチェックを実施
4. 指定されたタグIDがログインユーザー所有か確認
5. 単語情報を新規登録
6. 単語とタグの関連情報を新規登録
7. 登録結果をレスポンスDTOに変換
8. レスポンスとして返却

---