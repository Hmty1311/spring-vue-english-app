# 詳細設計書 | S-01 Login

## 1. 対象クラス

| 種別 | クラス名 | 責務 |
| -- | -- | -- |
| Controller | LoginController | ログインAPI受付 |
| Facade | LoginFacade | 認証処理調整 |
| Service | LoginService | ユーザー取得および資格情報検証 |
| Repository | UserRepository | ユーザー取得 |
| DTO | LoginRequest | 入力 |
| DTO | LoginResponse | 出力 |

---

## 2. メソッド設計

| クラス | メソッド | 引数 | 戻り値 | 概要 |
| -- | -- | -- | -- | -- |
| LoginController | login | LoginRequest | LoginResponse | ログインAPI |
| LoginFacade | login | String userName, String password | LoginResponse | 認証 + JWT生成 |
| LoginService | authenticate | String userName, String password | User | 認証処理 |
| UserRepository | findByUserName | String userName | Optional<User> | ユーザ検索 |

---

## 3. 処理フロー

### 3-1. 正常系

1. LoginController.login(LoginRequest request)が呼び出される
2. @Valid により入力チェックを行う
3. LoginFacade.login(request.getUserName(), request.getPassword()) を呼び出す
4. LoginService.authenticate(userName, password) を呼び出す
5. UserRepository.findByUserName(userName) を実行する
6. ユーザーが存在することを確認する
7. パスワード検証を実施する
8. 認証成功時、User を返却する
9. LoginFacadeにてJwtProviderを用いてトークン生成
10. LoginResponseを生成する
11. LoginControllerからレスポンスを返却する

### 3-2. 異常系

#### ケース1: ユーザ未登録

1. UserRepository.findByUserName(userName) がemptyを返す
2. AuthenticationException をスロー
3. 401 を返却

#### ケース2: パスワード不一致

1. passwordEncoder.matches が false
2. AuthenticationException スロー
3. 401 を返却

---

## 4. バリデーション実装方針

### LoginRequest

| 項目 | アノテーション |
| -- | -- |
| userName | @NotBlank |
| password | @NotBlank |

---

## 5. 例外設計

AuthenticationException は独自業務例外とする。
RuntimeException を継承し、認証失敗時にスローする。

| 条件 | 例外クラス | HTTP |
| -- | -- | -- |
| ユーザー未登録 | AuthenticationException | 401 |
| パスワード不一致 | AuthenticationException | 401 |
| 入力不正 | MethodArgumentNotValidException | 400 |

※HTTPステータス変換は GlobalExceptionHandler にて行う。

---

## 6. トランザクション設計

| メソッド | @Transactional | readOnly |
| -- | -- | -- |
| authenticate | あり | true |
| login | なし | - |

※更新処理無し、読み取りのみであるため

---

## 7. 特記事項

- パスワード平文ログ出力禁止
- ユーザー存在有無を区別するメッセージを返却しない
- 将来的に以下をFacadeへ追加可能
  - ログイン履歴保存
  - 失敗回数管理
  - アカウントロック
  - 最終ログイン日時更新
