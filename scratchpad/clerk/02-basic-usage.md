# 02. Clerkの基本的な使い方

このガイドでは、Clerkの基本的な機能と使い方を学びます。

## 基本的なコード評価

Clerkは通常のClojureファイルをノートブックとして扱います。コードは上から下へ順番に評価されます。

`notebooks/basic-examples.clj`を作成：

```clojure
(ns basic-examples)

;; # Clerkの基本

;; ## 式の評価

;; 算術演算
(+ 10 20 30)

;; 文字列操作
(str "Hello" " " "Clerk!")

;; ## データ構造の表示

;; ベクター
[1 2 3 4 5]

;; マップ
{:language "Clojure"
 :tool "Clerk"
 :version "0.18.1150"}

;; ネストしたデータ構造
{:users [{:name "Alice" :age 30}
         {:name "Bob" :age 25}
         {:name "Charlie" :age 35}]
 :total 3}

;; ## 変数の定義と使用

(def greeting "こんにちは、Clerk!")
greeting

(def numbers [1 2 3 4 5])
(map #(* % %) numbers)
```

## 結果の可視化

Clerkは様々なデータ型を自動的に適切な形式で表示します。

`notebooks/visualization.clj`：

```clojure
(ns visualization)

;; # データの可視化

;; ## テーブル表示

;; マップのシーケンスは自動的にテーブルとして表示されます
(def employees
  [{:id 1 :name "田中" :department "開発" :salary 500000}
   {:id 2 :name "佐藤" :department "営業" :salary 450000}
   {:id 3 :name "鈴木" :department "開発" :salary 520000}
   {:id 4 :name "高橋" :department "人事" :salary 480000}])

employees

;; ## 大きなデータセット

;; 大きなデータは自動的にページネーションされます
(range 100)

;; ## 集合と頻度

(def words ["apple" "banana" "apple" "cherry" "banana" "apple"])
(frequencies words)
```

## コードの可視性制御

コードブロックの表示を制御できます。

`notebooks/visibility.clj`：

```clojure
(ns visibility)

;; # コードの可視性制御

;; ## 通常の表示
(+ 1 2 3)

;; ## コードを隠す
^{:nextjournal.clerk/hide true}
(def secret-value 42)

;; 結果は表示されるが、コードは隠れる
secret-value

;; ## 結果だけを隠す
^{:nextjournal.clerk/show false}
(println "この出力は表示されません")

;; ## コードを折りたたみ可能にする
^{:nextjournal.clerk/fold true}
(defn complex-function [x]
  ;; 長い実装...
  (reduce + (map #(* % %) (range x))))

(complex-function 10)
```

## インクリメンタル評価

Clerkの強力な機能の一つは、変更された部分だけを再評価することです。

`notebooks/incremental.clj`：

```clojure
(ns incremental)

;; # インクリメンタル評価

;; ## 依存関係の追跡

(def base-value 10)

(def derived-value (* base-value 2))

(def final-value (+ derived-value 5))

;; base-valueを変更すると、dependent-valueとfinal-valueも自動的に更新されます
final-value

;; ## 計算のキャッシング

;; 重い計算
^{:nextjournal.clerk/fold true}
(defn expensive-computation [n]
  (Thread/sleep 1000) ; 1秒待機をシミュレート
  (reduce + (range n)))

;; この計算は初回のみ実行され、結果はキャッシュされます
(expensive-computation 1000000)
```

## ファイル監視モード

開発中は、ファイルの変更を自動的に検出して再評価できます。

REPLで：

```clojure
;; 特定のディレクトリを監視
(clerk/serve! {:watch-paths ["notebooks"]})

;; 複数のディレクトリを監視
(clerk/serve! {:watch-paths ["notebooks" "src"]})

;; フィルタ関数を使って特定のファイルのみ表示
(clerk/serve! {:watch-paths ["notebooks"] 
               :show-filter-fn #(clojure.string/ends-with? % ".clj")})
```

## 名前空間の操作

`notebooks/namespaces.clj`：

```clojure
(ns namespaces
  (:require [clojure.string :as str]
            [clojure.set :as set]))

;; # 名前空間の使用

;; ## requireした関数の使用
(str/upper-case "hello clerk")

(set/union #{1 2 3} #{3 4 5})

;; ## 自分の関数を定義
(defn greet [name]
  (str "こんにちは、" name "さん！"))

(greet "Clerk")

;; ## 他のファイルから関数をインポート
;; （事前に src/utils.clj を作成する必要があります）
; (require '[utils :as u])
; (u/helper-function 42)
```

## エラーハンドリング

Clerkはエラーを分かりやすく表示します。

`notebooks/errors.clj`：

```clojure
(ns errors)

;; # エラーの表示

;; ## 構文エラー
;; コメントを外すとエラーが表示されます
; (+ 1 2  ; 括弧が閉じていない

;; ## 実行時エラー
(defn divide [a b]
  (/ a b))

;; 正常な場合
(divide 10 2)

;; エラーが発生する場合（コメントを外してみてください）
; (divide 10 0)

;; ## エラーの捕捉
(try
  (divide 10 0)
  (catch ArithmeticException e
    (str "エラーが発生しました: " (.getMessage e))))
```

## REPLとの統合

ClerkはREPLと密接に統合されています：

```clojure
;; REPLで現在のファイルを再評価
(clerk/recompute!)

;; キャッシュをクリア
(clerk/clear-cache!)

;; 特定のファイルを表示
(clerk/show! "notebooks/basic-examples.clj")

;; 文字列から直接ノートブックを作成
(clerk/show! (java.io.StringReader. 
  ";; # 動的ノートブック\n(+ 1 2 3)"))
```

## ベストプラクティス

1. **ファイル構成**: 1つのトピックにつき1つのノートブックファイルを作成
2. **名前空間**: ファイル名と名前空間を一致させる
3. **評価順序**: コードは常に上から下へ評価されることを意識
4. **パフォーマンス**: 重い計算には`^{:nextjournal.clerk/fold true}`を使用
5. **可視性**: 実装の詳細は`hide`、長いコードは`fold`を活用

## 練習問題

以下の課題に挑戦してみましょう：

1. 1から100までのフィボナッチ数列を計算して表示
2. CSVデータを読み込んでテーブル表示
3. 簡単な統計情報（平均、中央値、標準偏差）を計算

## 次のステップ

基本的な使い方を理解したら、[Markdown統合](03-markdown-integration.md)に進んで、より豊かなドキュメントを作成する方法を学びましょう。