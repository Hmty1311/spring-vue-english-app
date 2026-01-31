# A-02 API詳細定義書 | WORD-01 単語一覧取得

---

## 1. 基本情報

| 項目 | 内容 |
| --- | --- |
| API ID | WORD-01 |
| API名 | 単語一覧取得 |
| Method | GET |
| Endpoint | `/api/words` |
| 認証 | 必須(ログインユーザー) |
| 権限 | 自分の単語のみ取得可能 |

---

## 2. 概要

ログインユーザーが登録した英単語の一覧を取得する。
タグ・暗記フラグ等による絞り込み、およびページングを行う。

---

## 3. リクエスト

### 3-1. Query Parameters

| No | パラメータ名 | 型 | 必須 | 説明 |
| -- | --- | --- | --- | --- |
| 1 | tagId | Long | 任意 | 指定タグで絞り込み |
| 2 | memorized | Boolean | 任意 | 暗記済みフラグ |
| 3 | keyword | String | 任意 | 英単語・意味の部分一致 |
| 4 | page | Integer | 任意 | ページ番号(0始まり) |
| 5 | size | Integer | 任意 | 1ページ件数(デフォルト20) |
| 6 | sort | String | 任意 | 並び順(例: `createdAt,desc`) |

---

## 4. レスポンス

### 4-1. Response Body(成功時)

| 項目名 | 型 | 説明 |
| --- | --- | --- |
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
  "content": [
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
  ],
  "page": 0,
  "size": 20,
  "totalElements": 35,
  "totalPages": 2
}
```

### 4-2. Response Body(失敗時)

| 項目 | 型 | 説明 |
| -- | -- | -- |
| errorCode | String | エラーコード |
| message | String | エラーメッセージ |

```json
{
    "errorCode": "UNAUTHORIZED",
    "message": "認証が必要です"
}
```

---

## 5. ステータスコード

| ステータス | 内容 |
| --- | --- |
| 200 OK | 正常取得 |
| 401 Unauthorized | 未ログイン |
| 500 Internal Server Error | 予期せぬエラー |

---

## 6. バリデーション•制約

| 項目 | 内容 |
| --- | --- |
| size | 最大100まで |
| page | 0以上 |
| tagId | 自分が作成したタグのみ指定可 |

---

## 7. 処理概要(内部)

1. 認証情報からログインユーザーID取得
2. 検索条件(タグ・暗記・キーワード)組み立て
3. ユーザーIDでデータスコープ制限
4. ページング・ソート適用
5. 単語+タグ情報を取得
6. レスポンスDTOに変換して返却

---
