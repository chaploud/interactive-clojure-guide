# 03. Markdown統合

Clerkの強力な機能の一つは、MarkdownとClojureコードをシームレスに統合できることです。これにより、美しいドキュメントと実行可能なコードを組み合わせることができます。

## ClojureファイルでのMarkdown使用

### コメントとしてのMarkdown

Clojureファイル内でMarkdownを使用する最も簡単な方法は、コメントとして記述することです。

`notebooks/markdown-in-clojure.clj`：

```clojure
(ns markdown-in-clojure)

;; # Markdownの基本

;; ## 見出し
;; Markdownの見出しは`#`で始まります。
;; - `#` - レベル1の見出し
;; - `##` - レベル2の見出し
;; - `###` - レベル3の見出し

;; ## テキストの装飾
;; - **太字** は `**太字**` で記述
;; - *斜体* は `*斜体*` で記述
;; - ***太字斜体*** は `***太字斜体***` で記述
;; - `コード` は `` `コード` `` で記述

(def sample-text "これは通常のClojureコードです")
sample-text

;; ## リスト

;; ### 順序なしリスト
;; - 項目1
;; - 項目2
;;   - サブ項目2.1
;;   - サブ項目2.2
;; - 項目3

;; ### 順序付きリスト
;; 1. 最初の手順
;; 2. 次の手順
;; 3. 最後の手順

(def steps ["準備" "実行" "確認"])
(map-indexed #(str (inc %1) ". " %2) steps)

;; ## リンクと引用

;; [Clerkの公式サイト](https://github.com/nextjournal/clerk)を参照してください。

;; > これは引用ブロックです。
;; > 複数行にわたって
;; > 引用を記述できます。

;; ## コードブロック

;; ```clojure
;; ;; これはコードブロック内のサンプルコードです
;; (defn hello [name]
;;   (str "Hello, " name "!"))
;; ```

;; 実際に実行されるコード：
(defn hello [name]
  (str "こんにちは、" name "さん！"))

(hello "Clerk")
```

## MarkdownファイルでのClojure使用

Clerkは`.md`ファイルもサポートしており、その中にClojureコードを埋め込むことができます。

`notebooks/pure-markdown.md`：

````markdown
# Markdownノートブック

これは純粋なMarkdownファイルですが、Clojureコードを含めることができます。

## 基本的な計算

```clojure
(+ 1 2 3 4 5)
```

## データ構造

```clojure
{:type "Markdownファイル"
 :feature "Clojureコード埋め込み"
 :便利 true}
```

## 関数定義

```clojure
(defn factorial [n]
  (if (<= n 1)
    1
    (* n (factorial (dec n)))))

(factorial 5)
```

## テーブル

Markdownのテーブルも使用できます：

| 言語 | 種類 | 年 |
|------|------|-----|
| Clojure | 関数型 | 2007 |
| Python | 動的型付け | 1991 |
| Rust | システム | 2010 |

## 数式（LaTeX）

インライン数式: $E = mc^2$

ブロック数式:
$$
\sum_{i=1}^{n} i = \frac{n(n+1)}{2}
$$
````

## 高度なMarkdown機能

`notebooks/advanced-markdown.clj`：

```clojure
(ns advanced-markdown)

;; # 高度なMarkdown機能

;; ## タスクリスト
;; - [x] Clerkのインストール
;; - [x] 基本的な使い方を学ぶ
;; - [ ] Markdown統合をマスターする
;; - [ ] 対話的教科書を作成する

;; ## 定義リスト
;; Clerk
;; : Clojureのための対話的ノートブックツール
;; 
;; Markdown
;; : 軽量マークアップ言語
;; 
;; REPL
;; : Read-Eval-Print Loop

;; ## 脚注
;; Clerkは素晴らしいツールです[^1]。
;; 
;; [^1]: 特にデータサイエンスや教育用途に最適です。

;; ## 水平線
;; セクションを区切るには：
;; 
;; ---
;; 
;; または
;; 
;; ***

;; ## HTMLの埋め込み
;; <div style="background-color: #f0f0f0; padding: 10px; border-radius: 5px;">
;; これはHTMLで装飾されたボックスです。
;; </div>

;; ## コードの実行結果と説明の組み合わせ

;; フィボナッチ数列を生成する関数を定義します：
(defn fibonacci [n]
  (take n 
    (map first 
      (iterate (fn [[a b]] [b (+ a b)]) [0 1]))))

;; 最初の10個のフィボナッチ数：
(fibonacci 10)

;; この数列は自然界でよく見られ、**黄金比**と深い関係があります。
```

## Markdownコメントの書き方のコツ

### 1. 構造化されたドキュメント

```clojure
(ns structured-doc)

;; # プロジェクト: データ分析ツール
;; 
;; ## 概要
;; このツールは売上データを分析し、洞察を提供します。
;; 
;; ## 使用方法
;; 1. データを読み込む
;; 2. 前処理を実行
;; 3. 分析を実行
;; 4. 結果を可視化

;; ### データの読み込み
(defn load-data [file-path]
  ;; 実装...
  {:data "loaded"})

;; ### 前処理
;; 欠損値の処理や正規化を行います。
(defn preprocess [data]
  ;; 実装...
  {:data "preprocessed"})
```

### 2. APIドキュメント

```clojure
(ns api-doc)

;; # API リファレンス

;; ## `calculate-stats`
;; 
;; 統計情報を計算します。
;; 
;; ### パラメータ
;; - `data`: 数値のベクター
;; - `options`: オプションのマップ
;;   - `:include-median`: 中央値を含めるか（デフォルト: true）
;;   - `:precision`: 小数点以下の桁数（デフォルト: 2）
;; 
;; ### 戻り値
;; 統計情報を含むマップ
;; 
;; ### 例
(defn calculate-stats [data & [{:keys [include-median precision]
                                :or {include-median true
                                     precision 2}}]]
  {:mean (/ (reduce + data) (count data))
   :min (apply min data)
   :max (apply max data)
   :count (count data)})

(calculate-stats [1 2 3 4 5])
```

## Markdownファイルの構成例

`notebooks/tutorial.md`の構成例：

````markdown
# Clojureチュートリアル

## 目次
1. [基本的なデータ型](#基本的なデータ型)
2. [関数](#関数)
3. [コレクション操作](#コレクション操作)

## 基本的なデータ型

Clojureには以下の基本的なデータ型があります：

```clojure
;; 数値
42
3.14
22/7  ; 分数

;; 文字列
"Hello, World!"

;; キーワード
:keyword
:name
```

## 関数

関数は`defn`で定義します：

```clojure
(defn add [a b]
  (+ a b))

(add 10 20)
```

## コレクション操作

```clojure
;; map
(map inc [1 2 3 4 5])

;; filter
(filter even? (range 10))

;; reduce
(reduce + [1 2 3 4 5])
```
````

## ベストプラクティス

1. **見出しの階層**: 適切な見出しレベルを使用して文書構造を明確に
2. **コードとテキストのバランス**: 説明とコードを適切に配置
3. **視覚的な区切り**: 水平線や空行を使ってセクションを区切る
4. **リンクの活用**: 関連リソースへのリンクを提供
5. **数式の使用**: 必要に応じてLaTeX記法で数式を記述

## 次のステップ

Markdown統合をマスターしたら、[画像とビジュアル要素](04-images-and-visuals.md)の追加方法を学びましょう。