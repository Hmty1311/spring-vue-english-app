# A-02 API詳細設計書 | WORD-05 単語削除

----

## 1. 基本情報

| 項目 | 内容 |
| --- | --- |
| API ID | WORD-05 |
| API名 | 単語削除 |
| Method | DELETE |
| Endpoint | /api/words/{wordId} |
| 認証 | 必須（ログインユーザー） |
| 権限 | 自分の単語のみ削除可能 |

---

## 2. 概要

指定された単語IDに対応する英単語情報を削除する。
削除対象の単語はログインユーザーが登録したものに限られる。

---

## 3. リクエスト

### 3-1. Path Parameter

| No | パラメータ名 | 型 | 必須 | 説明 |
| -- | -- | -- | -- | -- |
| 1 | wordId | Long | 必須 | 単語ID |

### 3-2. Query Parameter

なし

### 3-3. Request Body

なし

---

### 4. レスポンス

### 4-1. Response Body（成功時）

なし

---

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

| ステータスコード | 内容 |
| --- | --- |
| 204 No Content | 正常削除 |
| 400 Bad Request | バリデーションエラー |
| 401 Unauthorized | 未ログイン |
| 404 Not Found | 単語が存在しない、または他ユーザーの単語 |
| 500 Internal Server Error | 予期せぬエラー |

---

## 6. バリデーション・制約

| 項目 | 必須 | 内容 |
| --- | --- | --- |
| wordId | 必須 | ログインユーザーが所有する単語IDのみ指定可 |

---

## 7. 処理概要

1. 認証情報からログインユーザーを取得
2. Path Parameter から単語IDを取得
3. 対象単語がログインユーザー所有か確認
4. 単語情報を削除
   