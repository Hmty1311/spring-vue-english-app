# 詳細設計書 | S-04 単語登録画面

## 1. 対象クラス

| 種別 | クラス名 | 責務 |
| -- | -- | -- |
| Controller | WordRegistController | 単語登録API受付 |
| Facade | WordRegistFacade | 単語登録処理調整およびDTO変換 |
| Service | WordRegistService | 単語登録処理 |
| Repository | WordRepository | 単語データアクセス |
| DTO | WordRegistRequest | 単語登録リクエスト |
| DTO | WordResponse | 登録単語レスポンス |
| Entity | Word | 単語エンティティ |

---

## 2. メソッド設計

| クラス | メソッド | 引数 | 戻り値 | 概要 |
| -- | -- | -- | -- | -- |
| WordRegistController | registWord | WordRegistRequest| WordResponse | 単語登録API |
| WordRegistFacade | registWord | Long userId, WordRegistRequest | WordResponse | 単語登録処理 |
| WordRegistService | registWord | Long userId, WordRegistRequest | Word | 単語登録 |
| WordRepository | save | Word | Word | 単語保存 |
| WordRepository | existsByUserIdAndWord | Long userId, String word | boolean | 同一単語存在チェック |

---

## 3. 処理フロー

### 3-1. 正常系
1. WordRegistController.registWord(request) が呼び出される
2. Controllerにて SecurityContext から認証済みユーザーIDを取得する
3. WordRegistFacade.registWord(userId, request) を呼び出す
4. WordRegistService.registWord(userId, request) を呼び出す
5. WordRegistServiceにて同一単語存在チェックを行う
6. WordRepository.existsByUserIdAndWord(userId, request.word) を実行する
7. 存在する場合 DuplicateWordException をスローする
8. WordRegistServiceにて新しいWordエンティティを生成する
9. WordRepository.save(word) を実行する
10. 保存された Word をFacadeに返却する
11. Facadeにて Word → WordResponseに変換する
12. Controllerからレスポンス返却

### 3-2. 異常系
### 未認証
1. リクエストにJWTが存在しない、または不正なJWTである
2. SecurityFilterにて認証エラーを検知する
3. 401 Unauthorized を返却する
4. Controllerには到達しない

---

## 4. バリデーション実装方針

### WordRegistRequest

| 項目 | 型 | アノテーション |
| -- | -- |-- |
| word | String | @NotBlank |
| meaning | String | @NotBlank |

### 独自チェック
#### チェック内容
- 同一ユーザーで同じ単語が既に存在するか

存在する場合
`DuplicateWordException`
をスローする。

#### チェックタイミング
Service層で実施

#### 実装方法
WordRepository.existsByUserIdAndWord(userId, word)

---

## 5. 例外設計

| 条件 | 例外クラス | HTTP | 備考 |
|------|------------|------|------|
| 未認証 | - | 401 | SecurityFilterにて処理 |
| 同一単語登録 | DuplicateWordException | 409 | - |
| バリデーションエラー | MethodArgumentNotValidException | 400 | - |

※未認証は SecurityFilter にて処理

---

## 6. トランザクション設計

| クラス | @Transactional | readOnly |
| -- | -- | -- |
| WordRegistService | あり | false |

---

## 7. 特記事項

- user_id + word にユニーク制約を設定する
- DBユニーク制約違反発生時は
  DataIntegrityViolationException を捕捉し
  DuplicateWordException に変換する