# A-02 API詳細設計書 | LOGIN ログイン

---

## 1. 基本情報

| 項目 | 内容 | 
| -- | -- |
| API ID | LOGIN |
quiz_result_post も同じ粒度
| API名 | ログイン |
| Method | POST |
| Endpoint | `/api/login` |
| 認証 | - |
| 権限 | - |

---

## 2. 概要

ユーザー名、パスワードを入力してログインする。
ログインに成功した場合は、モード選択画面へ遷移、失敗した場合は、エラーメッセージを表示する。

---

## 3. リクエスト

### 3-1. Path Parameters

なし

### 3-2. Query Parameters

なし

### 3-3. Request Body

| 項目名 | 型 | 必須 | 説明 |
| --- | -- | -- | -- |
| loginId | String | 必須 | ログインID |
| password | String | 必須 | パスワード |

```json
{
    "loginId": "yamada",
    "password": "taro1234#"
}
```

---

## 4. レスポンス

### 4-1. Response Body(成功時)

- Status 200 OK

| 項目 | 型 | 説明 |
| -- | -- | -- |
| token | String | 認証トークン |
| userId | Long | ユーザーID |
| userName | String | ユーザー名 |

```json
{
    "token": "eyJhGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9...",
    "userId": 1,
    "userName": "山田太郎"
}
```


### 4-2. Response Body(失敗時)

| 項目 | 型 | 説明 |
| -- | -- | -- |
| errorCode | String | エラーコード |
| message | String | エラーメッセージ |

```json
{
    "errorCode": "AUTH-001",
    "message": "ログインIDまたはパスワードが正しくありません"
}
```

---

## 5. ステータスコード

| ステータス | 内容 |
| --- | --- |
| 200 OK | 正常取得 |
| 400 Bad Request | バリデーションエラー |
| 401 Unauthorized | 未ログイン |
| 500 Internal Server Error | 予期せぬエラー |

---

## 6. バリデーション・制約

| 項目 | 必須 | 内容 |
| --- | --- | --- |
| loginId | 必須 | ログインID |
| password | 必須 | パスワード |

---

## 7. 処理概要

1. リクエストボディのひっすこうもく（loginId, password）を検証する。不足がある場合は400 Bad Requestを返却する。
2. loginIdをキーにusersテーブルを検索する。
3. 該当ユーザーが存在しない場合は、401 Unauthorized（AUTH-001）を返却する。
4. 入力されたパスワードと、DBに保存されているハッシュ化パスワードを照合する。
5. 認証成功時は、ユーザ情報をもとにJWTを生成する。
6. 生成したJWTおよびユーザー情報をレスポンスとして返却する。