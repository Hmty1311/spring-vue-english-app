# 詳細設計書 | S-02 モード選択画面

## 1. 対象クラス

| 種別 | クラス名 | 責務 |
| -- | -- | -- |
| Controller | ModeController | ログインユーザー取得API受付 |
| Facade | ModeFacade | ユーザー情報取得処理調整 |
| Service | ModeService | ログインユーザー取得 |
| Repository | UserRepository | ユーザー取得 |
| DTO | UserResponse | ユーザー情報出力 |

※GETのため入力DTOなし

---

## 2. メソッド設計

| クラス | メソッド | 引数 | 戻り値 | 概要 |
| -- | -- | -- | -- | -- |
| ModeController | getLoginUser | - | UserResponse | ログインユーザー取得API |
| ModeFacade | getLoginUser | Long userId | UserResponse | ユーザー取得およびDTO変換 |
| ModeService | findById | Long userId | User | ユーザー検索 |
| UserRepository | findById | Long userId | Optional<User> | ユーザー検索 |

---

## 3. 処理フロー

### 3-1. 正常系
1. ModeController.getLoginUser() が呼び出される
2. ModeController にて SecurityContext から認証済ユーザーIDを取得する
3. ModeFacade.getLoginUser(userId) を呼び出す
4. ModeService.findById(userId) を呼び出す
5. UserRepository.findById(userId) を実行する
6. ユーザーが存在することを確認する
7. ModeFacade にて User → UserResponse へ変換する
8. ModeController からレスポンスを返却する

### 3-2. 異常系
#### ケース1: 未認証
1. リクエストにJWTが存在しない、または不正なJWTである
2. SecurityFilterにて認証エラーを検知する
3. 401 Unauthorized を返却する
4. Controllerには到達しない

#### ケース2: ユーザーが存在しない
1. ModeService.findById(userId) 実行時にユーザーが存在しない
2. ResourceNotFoundException をスローする
3. GlobalExceptionHandler にて 404 Not Found を返却する

---

## 4. バリデーション実装方針

なし（入力パラメータなし）

---

### 5. 例外設計

| 条件 | 例外クラス | HTTP | 備考 |
|------|------------|------|------|
| 未認証 | - | 401 | SecurityFilterにて処理 |
| ユーザー不存在 | ResourceNotFoundException | 404 | GlobalExceptionHandlerにて変換 |

※HTTPステータス変換は GlobalExceptionHandler にて行う

---

## 6. トランザクション設計

| クラス | @Transactional | readOnly |
| -- | -- | -- |
| ModeService | あり | true |

※参照処理のみのため readOnly=true とする

---

## 7. 特記事項
- JWTの検証は SecurityFilter で実施済であることを前提とする
- Controller では JWT の直接解析を行わない
- レスポンスにはパスワードなどの機密情報を含めない