# 詳細設計書 | S-03 単語一覧画面

## 1. 対象クラス

| 種別 | クラス名 | 責務 |
| -- | -- | -- |
| Controller | WordListController | 単語一覧取得API受付 |
| Facade | WordListFacade | 単語一覧取得およびDTO変換 |
| Service | WordListService | 単語一覧取得 |
| Repository | WordRepository | 単語取得 |
| DTO | WordListRequest | 検索条件 |
| DTO | WordResponse | 単語情報出力 |

---

## 2. メソッド設計

| クラス | メソッド | 引数 | 戻り値 | 概要 |
| -- | -- | -- | -- | -- |
| WordListController | getWordList | WordListRequest| &lt;PageWordResponse&gt;     | 単語一覧取得API |
| WordListFacade | getWordList | Long userId,  WordListRequest Page&lt;WordResponse&gt;  | Page&lt;WordResponse&gt; | 単語検索およびDTO変換 |
| WordListService | findWords | Long userId, WordListRequest | Page&lt;Word&gt; | 単語検索 |
| WordRepository | findByConditionUserId | Long userId, String keyword, Boolean memorized, Pageable | Page&lt;Word&gt; | ユーザー単語検索 |

---

## 3. 処理フロー

### 3-1. 正常系
1. WordListController.getWordList(request) が呼び出される
2. Controllerにて SecurityContext から認証済ユーザーIDを取得する
3. WordListFacade.getWordList(userId, request) を呼び出す
4. WordListService.findWords(userId, request) を呼び出す
5. WordRepository.findByCondition(...) を実行する
6. 検索条件に一致する単語一覧を取得する
7. WordListFacade にて Word → WordResponse へ変換する
8. Page&lt;WordResponse&gt; を生成する
9. Controller からレスポンス返却

### 3-2. 異常系
#### 未認証
1. リクエストにJWTが存在しない、または不正なJWTである
2. SecurityFilterにて認証エラーを検知する
3. 401 Unauthorized を返却する
4. Controllerには到達しない

---

## 4. バリデーション実装方針

### WordListRequest

| 項目 | 型 | アノテーション |
| -- | -- |-- |
| keyword | String | なし |
| memorized | Boolean | なし |
| page | Integer | @Min(0) |
| size | Integer | @Min(1) |

※ keyword・memorized は任意条件

---

## 5. 例外設計

| 条件 | 例外クラス | HTTP | 備考 |
|------|------------|------|------|
| 未認証 | - | 401 | SecurityFilterにて処理 |

※未認証は SecurityFilter にて処理
※単語0件の場合は空配列を返却する

---

## 6. トランザクション設計

| クラス | @Transactional | readOnly |
| -- | -- | -- |
| WordListService | あり | true |

---

## 7. 特記事項

- JWT検証は SecurityFilter にて実施済み
- ControllerではJWTの直接解析を行わない
- 単語が存在しない場合は空配列を返却する
- 検索条件は任意指定とする
- ページングは Spring Data Pageable を利用する