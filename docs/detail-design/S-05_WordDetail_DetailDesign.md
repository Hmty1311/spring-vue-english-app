# 詳細設計書 | S-05 単語更新画面

## 1. 対象クラス

| 種別 | クラス名 | 責務 |
| -- | -- | -- |
| Controller | WordUpdateController | 単語更新API受付 |
| Facade | WordUpdateFacade | 単語更新処理調整およびDTO変換 |
| Service | WordUpdateService | 単語更新処理 |
| Repository | WordRepository | 単語データアクセス |
| DTO | WordUpdateRequest | 単語更新リクエスト |
| DTO | WordResponse | 更新単語レスポンス |
| Entity | Word | 単語エンティティ |

---

## 2. メソッド設計

| クラス | メソッド | 引数 | 戻り値 | 概要 |
| -- | -- | -- | -- | -- |
| WordUpdateController | updateWord | Long wordId, WordUpdateRequest | WordResponse | 単語更新API |
| WordUpdateFacade | updateWord | Long userId, Long wordId, WordUpdateRequest | WordResponse | 単語更新 |
| WordUpdateService | updateWord | Long userId, Long wordId, WordUpdateRequest | Word | 単語更新処理 |
| WordRepository | findById | Long id | Optional<Word> | 単語取得 |
| WordRepository | save | Word | Word | 単語更新 |

---

## 3. 処理フロー

### 3-1. 正常系
1. WordUpdateController.updateWord(wordId, request) が呼び出される
2. Controllerにて SecurityContext から認証済みユーザーIDを取得する
3. WordUpdateFacade.updateWord(userId, wordId, request) を呼び出す
4. WordUpdateService.updateWord(userId, wordId, request) を呼び出す
5. WordRepository.findById(wordId) を実行する
6. 該当単語が存在しない場合
   ResourceNotFoundException をスローする
7. 取得したWordの userId とログインユーザーIDを比較する
8. 一致しない場合
   AccessDeniedException をスローする
9. Word エンティティの値を更新する
    ```java
    word.update(
        request.word,
        request.meaning
    )
    ```
10. WordRepository.save(word) を実行する
11. 更新されたWordをFacadeに返却する
12. FacadeにてWord → WordResponseに変換する
13. Controllerからレスポンス返却

### 3-2. 異常系
### 未認証
1. リクエストにJWTが存在しない、または不正なJWTである
2. SecurityFilterにて認証エラーを検知する
3. 401 Unauthorized を返却する
4. Controllerには到達しない

---

## 4. バリデーション実装方針

### WordUpdateRequest

| 項目 | 型 | アノテーション |
| -- | -- |-- |
| word | String | @NotBlank |
| meaning | String | @NotBlank |

### 独自チェック
#### チェック内容
- 更新対象の単語が存在するか
- 更新対象の単語がログインユーザーの所有か

#### チェックタイミング
Service層で実施

---

## 5. 例外設計

| 条件 | 例外クラス | HTTP | 備考 |
|------|------------|------|------|
| 未認証 | - | 401 | SecurityFilterにて処理 |
| 単語が存在しない | ResourceNotFoundException | 404 | - |
| 他ユーザーの単語 | AccessDeniedException | 403 | - |
| バリデーションエラー | MethodArgumentNotValidException | 400 | - |

---

## 6. トランザクション設計

| クラス | @Transactional | readOnly |
| -- | -- | -- |
| WordUpdateService | あり | false |

---

## 7. 特記事項

- JWT検証はSecurityFilterにて実装済み
- ControllerでJWTの直接解析を行わない
- 更新対象の単語はログインユーザーの所有データのみ許可する
- 存在しないID指定時は404を返却する