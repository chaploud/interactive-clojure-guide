# 05. 対話的教科書の作成

このガイドでは、これまで学んだすべての要素を組み合わせて、Clojureの対話的な教科書を作成する方法を説明します。

## 対話的教科書の設計原則

### 1. 段階的な学習
- 基礎から応用へ順序立てて進む
- 各概念を実行可能なコードで説明
- 読者が実験できる余地を残す

### 2. 視覚的な理解
- 図表やグラフで概念を可視化
- 実行結果を即座に確認
- インタラクティブな要素で理解を深める

### 3. 実践的な例
- 実世界の問題を解く
- 段階的に複雑さを増す
- エラーとその対処法も含める

## 実例：Clojureデータ構造の教科書

`notebooks/clojure-data-structures-textbook.clj`：

```clojure
(ns clojure-data-structures-textbook)

;; # Clojureのデータ構造入門
;; 
;; ## はじめに
;; 
;; Clojureは**イミュータブル**（不変）なデータ構造を中心に設計された言語です。
;; この教科書では、Clojureの主要なデータ構造を対話的に学んでいきます。
;; 
;; ### 学習目標
;; 1. 基本的なデータ構造（リスト、ベクター、マップ、セット）を理解する
;; 2. それぞれの特徴と使い分けを学ぶ
;; 3. 実践的な操作方法を身につける

;; ---

;; ## 第1章：ベクター（Vector）
;; 
;; ベクターは最も一般的に使用されるデータ構造です。
;; インデックスによる高速なアクセスが可能で、末尾への要素の追加も効率的です。

;; ### ベクターの作成
(def my-vector [1 2 3 4 5])
my-vector

;; 空のベクター
(def empty-vector [])
empty-vector

;; `vector`関数を使った作成
(vector 1 2 3 4 5)

;; ### ベクターの操作

;; 要素へのアクセス（0ベースのインデックス）
(get my-vector 2)  ; 3番目の要素
(my-vector 2)      ; 関数として呼び出すことも可能

;; 要素の追加
(conj my-vector 6)  ; 末尾に追加

;; 要素の更新（新しいベクターを返す）
(assoc my-vector 2 100)  ; インデックス2を100に更新

;; ### 練習問題 1-1
;; 以下のベクターから偶数だけを取り出してください：
(def numbers [1 2 3 4 5 6 7 8 9 10])

;; ヒント：`filter`と`even?`を使います
(comment
  ;; ここに解答を書いてください
  )

;; 解答例：
^{:nextjournal.clerk/fold true}
(filter even? numbers)

;; ### ベクターの可視化

(defn visualize-vector-operations []
  [:div {:style {:font-family "monospace"}}
   [:h4 "ベクターの操作の可視化"]
   [:table {:style {:border-collapse "collapse"}}
    [:thead
     [:tr
      [:th {:style {:border "1px solid #ddd" :padding "8px"}} "操作"]
      [:th {:style {:border "1px solid #ddd" :padding "8px"}} "コード"]
      [:th {:style {:border "1px solid #ddd" :padding "8px"}} "結果"]]]
    [:tbody
     [:tr
      [:td {:style {:border "1px solid #ddd" :padding "8px"}} "作成"]
      [:td {:style {:border "1px solid #ddd" :padding "8px"}} "[1 2 3]"]
      [:td {:style {:border "1px solid #ddd" :padding "8px"}} (str [1 2 3])]]
     [:tr
      [:td {:style {:border "1px solid #ddd" :padding "8px"}} "追加"]
      [:td {:style {:border "1px solid #ddd" :padding "8px"}} "(conj [1 2 3] 4)"]
      [:td {:style {:border "1px solid #ddd" :padding "8px"}} (str (conj [1 2 3] 4))]]
     [:tr
      [:td {:style {:border "1px solid #ddd" :padding "8px"}} "更新"]
      [:td {:style {:border "1px solid #ddd" :padding "8px"}} "(assoc [1 2 3] 1 99)"]
      [:td {:style {:border "1px solid #ddd" :padding "8px"}} (str (assoc [1 2 3] 1 99))]]]]])

^{:nextjournal.clerk/viewer :html}
(str (visualize-vector-operations))

;; ---

;; ## 第2章：マップ（Map）
;; 
;; マップはキーと値のペアを格納するデータ構造です。
;; 連想配列やディクショナリとも呼ばれます。

;; ### マップの作成
(def person {:name "太郎"
             :age 25
             :city "東京"})
person

;; ### マップの操作

;; 値の取得
(:name person)
(get person :age)
(person :city)  ; マップを関数として使用

;; デフォルト値付き取得
(get person :phone "未登録")

;; 値の追加・更新
(assoc person :email "taro@example.com")
(assoc person :age 26)  ; 既存のキーを更新

;; 複数の更新
(assoc person 
  :age 26
  :email "taro@example.com"
  :phone "090-1234-5678")

;; キーの削除
(dissoc person :city)

;; ### ネストしたマップ

(def company
  {:name "テックカンパニー"
   :employees [{:id 1 :name "Alice" :department :engineering}
               {:id 2 :name "Bob" :department :sales}
               {:id 3 :name "Charlie" :department :engineering}]
   :departments {:engineering {:budget 1000000}
                 :sales {:budget 500000}}})

;; ネストした値へのアクセス
(get-in company [:departments :engineering :budget])

;; ネストした値の更新
(update-in company [:departments :engineering :budget] * 1.1)

;; ### 練習問題 2-1
;; 以下の社員データから、engineering部門の社員だけを抽出してください：

;; ヒント：`filter`と`:department`を使います
(comment
  ;; ここに解答を書いてください
  )

;; 解答例：
^{:nextjournal.clerk/fold true}
(filter #(= :engineering (:department %)) (:employees company))

;; ### マップの可視化

(def sample-data
  {:fruits {:apple 100 :banana 80 :orange 120}
   :vegetables {:carrot 60 :tomato 90 :lettuce 40}})

^{:nextjournal.clerk/viewer :vega-lite}
{:data {:values (vec (for [[category items] sample-data
                           [item price] items]
                       {:category (name category)
                        :item (name item)
                        :price price}))}
 :mark "bar"
 :encoding {:x {:field "item" :type "nominal" :axis {:title "商品"}}
            :y {:field "price" :type "quantitative" :axis {:title "価格"}}
            :color {:field "category" :type "nominal" :title "カテゴリー"}}}

;; ---

;; ## 第3章：リスト（List）
;; 
;; リストは最も基本的なデータ構造で、LISPの伝統を受け継いでいます。
;; 先頭への要素の追加が高速です。

;; ### リストの作成
(def my-list '(1 2 3 4 5))
my-list

;; `list`関数を使った作成
(list 1 2 3 4 5)

;; ### リストの特徴

;; 先頭への追加が高速
(conj my-list 0)  ; 先頭に追加される

;; リストは評価を防ぐためにクォートが必要
'(+ 1 2)  ; 評価されない
(list + 1 2)  ; 関数も要素として格納可能

;; ---

;; ## 第4章：セット（Set）
;; 
;; セットは重複のない要素の集合です。
;; 要素の存在確認が高速です。

;; ### セットの作成
(def colors #{:red :green :blue})
colors

;; 重複は自動的に削除される
(set [1 2 3 3 2 1])

;; ### セットの操作

;; 要素の追加
(conj colors :yellow)

;; 要素の削除
(disj colors :green)

;; 要素の存在確認
(contains? colors :red)
(colors :red)  ; セットを関数として使用
(colors :purple)  ; 存在しない場合はnil

;; ### 集合演算

(def set-a #{1 2 3 4})
(def set-b #{3 4 5 6})

;; 和集合
(clojure.set/union set-a set-b)

;; 積集合
(clojure.set/intersection set-a set-b)

;; 差集合
(clojure.set/difference set-a set-b)

;; ### 集合演算の可視化

^{:nextjournal.clerk/viewer :html}
(str "<div style='display: flex; justify-content: space-around; align-items: center;'>"
     "<div style='text-align: center;'>"
     "<h5>Set A</h5>"
     "<div style='width: 150px; height: 150px; border-radius: 50%; "
     "background-color: rgba(66, 133, 244, 0.3); display: flex; "
     "align-items: center; justify-content: center;'>"
     (str set-a)
     "</div></div>"
     "<div style='text-align: center;'>"
     "<h5>Set B</h5>"
     "<div style='width: 150px; height: 150px; border-radius: 50%; "
     "background-color: rgba(234, 67, 53, 0.3); display: flex; "
     "align-items: center; justify-content: center;'>"
     (str set-b)
     "</div></div>"
     "<div style='text-align: center;'>"
     "<h5>積集合</h5>"
     "<div style='padding: 20px; background-color: rgba(52, 168, 83, 0.3); "
     "border-radius: 10px;'>"
     (str (clojure.set/intersection set-a set-b))
     "</div></div></div>")

;; ---

;; ## 第5章：実践演習
;; 
;; これまで学んだデータ構造を組み合わせて、実践的な問題を解いてみましょう。

;; ### 演習1：学生の成績管理

(def students
  [{:id 1 :name "山田" :scores {:math 85 :english 78 :science 92}}
   {:id 2 :name "鈴木" :scores {:math 92 :english 88 :science 85}}
   {:id 3 :name "佐藤" :scores {:math 78 :english 95 :science 88}}
   {:id 4 :name "田中" :scores {:math 88 :english 82 :science 90}}])

;; 各学生の平均点を計算
(defn calculate-average [student]
  (let [scores (vals (:scores student))
        total (reduce + scores)
        avg (/ total (count scores))]
    (assoc student :average (double avg))))

(map calculate-average students)

;; ### 演習2：データの集計と可視化

;; 科目別の平均点を計算
(defn subject-averages [students]
  (let [subjects [:math :english :science]]
    (map (fn [subject]
           {:subject (name subject)
            :average (double (/ (reduce + (map #(get-in % [:scores subject]) students))
                               (count students)))})
         subjects)))

(def avg-data (subject-averages students))
avg-data

;; 可視化
^{:nextjournal.clerk/viewer :vega-lite}
{:data {:values avg-data}
 :mark {:type "bar" :cornerRadius 5}
 :encoding {:x {:field "subject" :type "nominal" :axis {:title "科目"}}
            :y {:field "average" :type "quantitative" 
                :axis {:title "平均点"}
                :scale {:domain [0 100]}}
            :color {:field "subject" :type "nominal" :legend nil}}}

;; ---

;; ## まとめ
;; 
;; この教科書では、Clojureの主要なデータ構造について学びました：
;; 
;; 1. **ベクター**: インデックスアクセスが高速、最も汎用的
;; 2. **マップ**: キー・値のペア、連想配列として使用
;; 3. **リスト**: 先頭への追加が高速、関数型プログラミングの基本
;; 4. **セット**: 重複なしの集合、要素の存在確認が高速
;; 
;; ### 次のステップ
;; - より高度なデータ操作（`reduce`、`transduce`など）
;; - 永続データ構造の仕組み
;; - パフォーマンスの最適化

;; ### 参考資料
;; - [Clojure公式ドキュメント](https://clojure.org/reference/data_structures)
;; - [Clojure for the Brave and True](https://www.braveclojure.com/)
```

## インタラクティブな要素の追加

`notebooks/interactive-elements.clj`：

```clojure
(ns interactive-elements)

;; # インタラクティブな要素

;; ## ユーザー入力のシミュレーション

(def quiz-data
  {:question "Clojureでベクターに要素を追加する関数は？"
   :options ["add" "conj" "append" "push"]
   :correct 1})

^{:nextjournal.clerk/viewer :html}
(str "<div style='border: 1px solid #ddd; padding: 20px; border-radius: 8px;'>"
     "<h4>" (:question quiz-data) "</h4>"
     "<div>"
     (apply str
       (map-indexed
         (fn [idx option]
           (str "<div style='margin: 10px 0;'>"
                "<input type='radio' name='quiz' id='opt" idx "'>"
                "<label for='opt" idx "' style='margin-left: 10px;'>" option "</label>"
                "</div>"))
         (:options quiz-data)))
     "</div>"
     "<button onclick='alert(\"正解は: conj\")' "
     "style='margin-top: 10px; padding: 8px 16px; "
     "background-color: #4285f4; color: white; border: none; "
     "border-radius: 4px; cursor: pointer;'>答えを確認</button>"
     "</div>")

;; ## プログレッシブな例

(defn create-progress-example [n]
  (let [fib-seq (fn fib [a b] (lazy-seq (cons a (fib b (+ a b)))))
        fibs (take n (fib-seq 0 1))]
    {:description "フィボナッチ数列の生成"
     :steps (map-indexed 
              (fn [idx val]
                {:step (inc idx)
                 :value val
                 :calculation (if (< idx 2)
                                "初期値"
                                (str (nth fibs (- idx 2)) " + " 
                                     (nth fibs (- idx 1)) " = " val))})
              fibs)}))

(def fib-example (create-progress-example 8))

^{:nextjournal.clerk/viewer :html}
(str "<div style='background-color: #f5f5f5; padding: 20px; border-radius: 8px;'>"
     "<h4>" (:description fib-example) "</h4>"
     "<table style='width: 100%; border-collapse: collapse;'>"
     "<thead>"
     "<tr style='background-color: #e0e0e0;'>"
     "<th style='padding: 8px; text-align: left;'>ステップ</th>"
     "<th style='padding: 8px; text-align: left;'>値</th>"
     "<th style='padding: 8px; text-align: left;'>計算</th>"
     "</tr>"
     "</thead>"
     "<tbody>"
     (apply str
       (map (fn [{:keys [step value calculation]}]
              (str "<tr style='border-bottom: 1px solid #ddd;'>"
                   "<td style='padding: 8px;'>" step "</td>"
                   "<td style='padding: 8px; font-weight: bold;'>" value "</td>"
                   "<td style='padding: 8px; font-style: italic;'>" calculation "</td>"
                   "</tr>"))
            (:steps fib-example)))
     "</tbody>"
     "</table>"
     "</div>")
```

## 教科書作成のベストプラクティス

### 1. 構造化
- 明確な章立て
- 学習目標の提示
- 段階的な難易度

### 2. インタラクティビティ
- 実行可能なコード例
- 即座のフィードバック
- 実験を促す設計

### 3. 視覚的サポート
- 概念の図解
- データの可視化
- プログレス表示

### 4. 練習問題
- 各章に練習問題
- ヒントと解答例
- 段階的な難易度

### 5. 実践的な応用
- 実世界の例
- プロジェクトベース
- エラー処理の学習

## 完成した教科書の公開

### 静的サイトとして公開

```clojure
;; build.clj
(require '[nextjournal.clerk :as clerk])

(clerk/build! {:paths ["notebooks/clojure-textbook.clj"]
               :out-path "public"
               :bundle true})
```

### GitHubページでホスティング

1. リポジトリにpublicディレクトリをコミット
2. GitHub Pagesの設定でpublicディレクトリを指定
3. 自動的に公開される

## まとめ

Clerkを使った対話的教科書の作成により：

- **学習効果の向上**: コードを実行しながら学べる
- **理解の深化**: 視覚的な要素で概念を明確に
- **実践的なスキル**: 実際に動くコードで学習
- **共有の容易さ**: Webで簡単に公開・共有

これで、Clerkを使った対話的なClojure教科書を作成する準備が整いました！