# 詳細設計書 | S-06 単語クイズ画面

## 1. 対象クラス

| 種別 | クラス名 | 責務 |
| -- | -- | -- |
| Controller | QuizController | クイズ画面API受付 |
| Facade | QuizFacade | クイズ処理調整 |
| Service | QuizService | クイズ出題・学習結果保存処理 |
| Repository | WordRepository | 出題単語取得 |
| Repository | QuizResultRepository | 学習結果保存 |
| DTO | QuizResponse | クイズ出題レスポンス |
| DTO | QuizResultRequest | 学習結果登録リクエスト |
| Entity | Word | 単語エンティティ |
| Entity | QuizResult | 学習結果エンティティ |

---

## 2. メソッド設計

| クラス | メソッド | 引数 | 戻り値 | 概要 |
| -- | -- | -- | -- | -- |
| QuizController | getQuizzes | Integer count | List&lt;QuizResponse &gt; | クイズ出題取得 |
| QuizController | registerQuizResult | QuizResultRequest | void | 学習結果 |
| QuizFacade | getQuizzes | Long userId, Integer count | List%lt;QuizResponse&gt; | クイズ出題 |
| QuizFacade | registerQuizResult | Long userId, QuizResultRequest | void | 学習結果登録 |
| QuizService | getQuizzes | Long userId, Integer count | List&lt;QuizResponse&gt; | クイズ生成 |
| QuizService | registerQuizResult | Long userId, QuizResultRequest | void | 学習結果保存 |
|  WordRepository | findRandomWords | Long userId, Integer count | List&lt;Word&gt; | 出題単語取得 |
| QuizResultRepository | save | QuizResult | QuizResult | 学習結果保存 |

---

## 3. 処理フロー

### 3-1. クイズ出題
#### 3-1-1. 正常系
1. QuizController.getQuizzes(count) が呼び出される
2. Controllerにて SecurityContext から認証済みユーザーIDを取得する
3. QuizFacade.getQuizzes(userId, count) を呼び出す
4. QuizService.getQuizzes(userId, count) を呼び出す
5. WordRepository.findRandomWords(userId, count) を実行
   ※deleted = false の単語のみ取得
6. 出題単語ごとにクイズ問題を生成する
   ```json
   {
    "word": "apple",
    "choices": [
        "りんご",
        "ぶどう",
        "もも",
        "なし"
    ]
   }
   ```
7. Word → QuizResponseに変換する
8. Facade → Controllerへ返却
9. クイズ問題リストをレスポンスとして返却

#### 3-1-2. 異常系
##### 未認証
1. リクエストにJWTが存在しない、または不正なJWTである
2. SecurityFilterにて認証エラーを検知する
3. 401 Unauthorized を返却する
4. Controllerには到達しない

### 3-2. 学習結果登録
#### 3-2-1. 正常系
1. QuizController.registerQuizResult(request) が呼び出される
2. Controllerにて SecurityContext からユーザーID取得
3. QuizFacade.registerQuizResult(userId, request) を呼び出す
4. QuizService.registerQuizResult(userId, request) を呼び出す
5. QuizResultエンティティ生成
   ```java
   QuizResult result = QuizResult.create(
      userId,
      request.wordId,
      request.isCorrect
   );
   ```
6. QuizResultRepository.save(result)を実行
7. 保存成功後、204 No Contentを返却

#### 3-2-2. 異常系
##### 未認証
1. リクエストにJWTが存在しない、または不正なJWTである
2. SecurityFilterにて認証エラーを検知する
3. 401 Unauthorized を返却する
4. Controllerには到達しない

---

## 4. バリデーション実装方針

### QuizResultRequest

| 項目 | 型 | アノテーション |
| -- | -- |-- |
| wordId | Long | @NotNull |
| isCorrect | Boolean | @NotNull |

### 独自チェック
#### チェック内容
- 対象の単語が存在するか
- 対象の単語がログインユーザーの所有か

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
| QuizService | あり | false  |

---

## 7. 特記事項

- 出題対象はログインユーザーの単語のみ
- deleted=trueの単語は出題対象外
- クイズ問題はランダム取得する
- 学習結果は回答ごとに保存する
- クイズ結果登録完了後、フロントエンド側でS-07 学習結果画面へ遷移する