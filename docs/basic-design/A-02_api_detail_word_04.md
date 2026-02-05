# A-02 API詳細設計書 | WORD-04 単語更新

---

## 1. 基本情報

| 項目 | 内容 |
| --- | --- |
| API ID | WORD-04 |
| API名 | 単語更新 |
| Method | PUT |
| Endpoint | /api/words/{wordId} |
| 認証 | 必須（ログインユーザー） |
| 権限 | 自分の単語のみ更新可能 |

---

## 2. 概要

指定された単語IDに対応する英単語情報を更新する。
更新対象の単語はログインユーザーが登録したものに限られる。
更新後は、最新の単語情報を返却する。

---

## 3. リクエスト

### 3-1. Path Parameters

| No | パラメータ名 | 型 | 必須 | 説明 |
| -- | -- | -- | -- | -- |
| 1 | wordId | Long | 必須 | 単語ID |

### 3-2. Query Parameters

なし

### 3-3. Request Body

| 項目名 | 型　| 必須 | 説明 | 
| --- | -- | -- | -- |
| word | String | 必須 | 英単語 |
| meaning | String | 必須 | 日本語訳 |
| example | String | 任意 | 例文 |
| memorized | Boolean | 任意 | 暗記済みフラグ |
| tagIds | List<Long> | 任意 | 紐づけタグID一覧 |

```json
{
    "word": "apple",
    "meaning": "りんご（果物）",
    "example": "I eat an apple every day.",
    "memorized": true,
    "tagIds": [1,2]
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
    "meaning": "りんご（果物）",
    "example": "I eat an apple every day.",
    "memorized": true,
    "tagIds": [1, 2],
    "createdAt": "2026-01-01T10:00:00",
    "updatedAt": "2026-01-02T09:00:00"
}
```

### 4-2. Response Body（失敗時）

| 項目 | 型 | 説明 |
| --- | -- | -- |
| errorCode | String | エラーコード |
| message | String | エラーメッセージ |

```json
{
    "errorCode": "WORD_NOT_FOUND",
    "message": "指定された単語が存在しません"
}
```

---

## 5. ステータスコード

| ステータス | 内容 |
| --- | --- |
| 200 OK | 正常更新 |
| 400 Bad Request | バリデーションエラー |
| 401 Unauthorized | 未ログイン |
| 404 Not Found | 単語またはタグが存在しない／他ユーザー |
| 500 Internal Server Error | 予期せぬエラー |

---

## 6. バリデーション・制約

| 項目 | 必須 | 内容 |
| --- | --- | --- |
| word | 必須 | 1~100文字 |
| meaning | 必須 | 1~255文字 |
| tagIds | 任意 | ログインユーザーが所有するタグIDのみ指定可 |
| wordId | 必須 | ログインユーザーが所有する単語IDのみ指定可 |

---

## 7. 処理概要（内部）

1. 認証情報からログインユーザーを取得
2. Path Parameter から単語IDを取得
3. 対象単語がログインユーザー所有か確認
4. リクエストBodyのバリデーションチェック
5. 指定されたタグIDの所有チェック
6. 単語情報を更新
7. 単語とタグの関連情報を更新
8. 更新結果をレスポンスDTOに変換
9. レスポンスとして返却
