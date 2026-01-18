# A-02 API詳細設計書 | WORD-02 単語詳細取得

---

## 1. 基本情報

| 項目 | 内容 |
| --- | --- |
| API ID | WORD-02 |
| API名 | 単語詳細取得 |
| Method | GET |
| Endpoint | `/api/words/{wordId}` |
| 認証 | 必須（ログインユーザー） |
| 権限 | 自分の単語のみ取得可能 |

---

## 2. 概要

指定された単語IDに対する英単語の詳細情報を取得する。
単語情報に加え、紐づくタグ情報もあわせて返却する。

---

## 3. リクエスト

### 3-1. Path Parameters

| No | パラメータ名 | 型　｜ 必須 | 説明 |
| -- | --- | --- | --- | --- |
| 1 | wordId | Long | 必須 | 単語ID |

---

## 4. レスポンス

### 4-1. Response Body（成功時）

| 項目名 | 型 | 説明 |
| --- | -- | -- |
| wordOd | Long | 単語ID |
| meaning | String | 英単語 | 
| english | String | 日本語訳 |
| example | String | 例文 |
| memorized | Boolean | 暗記済みフラグ |
| tags | Array | 紐づけタグ一覧 |
| createdAt | LocalDateTime | 登録日時 |
| updatedAt | LocalDateTime | 更新日時 |

```json
{
    "wordId": 1,
    "english": "apple",
    "meaning": "りんご",
    "example": "I eat an apple every day.",
    "memorized": false,
    "tags": [
        {
            "tagId": 1,
            "tagName": "食べ物"
        }
    ],
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
    "errorCode": "WORD_NOT_FOUND",
    "message": "指定された単語が存在しません"
}
```

---

## 5. ステータスコード

| ステータス | 内容 |
| -- | -- | -- |
| 200 OK | 正常取得 |
| 401 Unauthorized | 未ログイン |
| 404 Not Found | 単語が存在しない |
| 500 Internal Server Error | 予期せぬエラー |

---

## 6. バリデーション・制約

| 項目 | 内容 |
| --- | --- |
| wordId | ログインユーザーが所有する単語IDのみ指定可 |

---

## 7. 処理概要（内部）

1. 人相情報からログインユーザーIDを取得
2. Path Parameterから単語IDを取得
3. ユーザーIDでデータスコープを制限
4. 単語及び紐づくタグ情報を取得
5. レスポンスDTOに変換して返却

---
