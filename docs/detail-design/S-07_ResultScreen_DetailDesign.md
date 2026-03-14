# 詳細設計書 | S-07 学習結果画面

## 1. 対象クラス

| 種別 | クラス名 | 責務 |
| -- | -- | -- |
| Controller | QuizResultController | 学習結果画面API受付 |
| Facade | QuizResultFacade | 学習結果処理調整 |
| Service | QuizResultService | 学習結果取得処理 |
| Repository | QuizResultRepository | 学習結果取得 |
| Repository | WordRepository | 単語情報取得 |
| DTO | QuizResultResponse | 学習結果レスポンス |
| Entity | QuizResult | 学習結果エンティティ |
| Entity | Word | 単語エンティティ |

---

## 2. メソッド設計

| クラス | メソッド | 引数 | 戻り値 | 概要 |
| -- | -- | -- | -- | -- |
| QuizResultController | getQuizResults | Integer limit | List&lt;QuizResultResponse&gt; | 学習結果取得 |
| QuizResultFacade | getQuizResults | Long userId, Integer limit | List&lt;QuizResultResponse&gt; | 学習結果取得 |
| QuizResultService | getQuizResults | Long userId, Integer limit | List&lt;QuizResultResponse&gt; | 学習結果取得 |
| QuizResultRepository | findRecentResults | Long userId, Integer limit | List&lt;QuizResult&gt; | 最新結果取得 |
| WordRepository | findById | Long wordId | Optional&lt;WOrd&gt; | 単語取得 |

---

## 3. 処理フロー

### 3-1. 学習結果取得
#### 3-1-1. 正常系
1. QuizResultController.getQuizResults(limit) が呼び出される
2. Controllerにて SecurityContext から人宝積ユーザーID取得
3. QuizResultFacade.getQuizResults(userId, limit) を呼び出す
4. QuizResultService.getQuizResults(userId, limit) を呼び出す
5. QuizResultRepository.findRecentResults(userId, limit) を実行
6. 取得したQuizResultからwordIdを取得
7. QuizResultRepository にて Word テーブルとJOINして取得
8. QuizResult + Word を組み合わせて QuizResultResponse を生成
9. List&lt;QuizResultResponse&gt;を生成
10. Facade → Controllerへ返却
11. 学習結果リストをレスポンスとして返却

#### 3-1-2. 異常系
##### 未認証
1. リクエストにJWTが存在しない、または不正なJWTである
2. SecurityFilterにて認証エラーを検知する
3. 401 Unauthorized を返却する
4. Controllerには到達しない

---

## 4. バリデーション実装方針

| 項目 | 型 | アノテーション |
| -- | -- |-- |
| limit | Integer | @Min(1) / @Max(100) |

---

## 5. 例外設計

| 条件 | 例外クラス | HTTP | 備考 |
|------|------------|------|------|
| 未認証 | - | 401 | SecurityFilterにて処理 |
| バリデーションエラー | MethodArgumentNotValidException | 400 | - |

---

## 6. トランザクション設計

| クラス | @Transactional | readOnly |
| -- | -- | -- |
| QuizResultService | あり | true  |

---

## 7. 特記事項

- 表示対象はログインユーザーの学習結果のみ
- 最新の回答から順に表示する
- 表示件数は`limit`パラメータで指定可能
- 単語情報(word, meaning)を合わせて表示する
- 本画面はS-06クイズ終了後に遷移する画面