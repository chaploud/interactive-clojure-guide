# 04. 画像とビジュアル要素

Clerkでは、画像、グラフ、図表などのビジュアル要素を簡単に組み込むことができます。これにより、より理解しやすく魅力的なドキュメントを作成できます。

## 画像の表示

### ローカル画像の表示

`notebooks/images-example.clj`：

```clojure
(ns images-example
  (:require [clojure.java.io :as io]))

;; # 画像の表示

;; ## ローカルファイルから画像を読み込む

;; 画像ファイルを直接表示
(io/file "images/sample.png")

;; ## BufferedImageの表示
(import java.awt.image.BufferedImage)
(import javax.imageio.ImageIO)

(defn load-image [path]
  (ImageIO/read (io/file path)))

;; 画像を読み込んで表示（事前に画像ファイルを用意）
; (load-image "images/diagram.png")

;; ## プログラムで画像を生成
(import java.awt.Color)
(import java.awt.Graphics2D)

(defn create-simple-image []
  (let [width 400
        height 300
        image (BufferedImage. width height BufferedImage/TYPE_INT_RGB)
        g2d (.createGraphics image)]
    ;; 背景を白に
    (.setColor g2d Color/WHITE)
    (.fillRect g2d 0 0 width height)
    ;; 青い円を描画
    (.setColor g2d Color/BLUE)
    (.fillOval g2d 100 75 200 150)
    ;; テキストを追加
    (.setColor g2d Color/BLACK)
    (.drawString g2d "Clerkで生成した画像" 120 160)
    (.dispose g2d)
    image))

(create-simple-image)
```

### Web上の画像を表示

```clojure
;; ## Web画像の表示

;; URLから画像を表示
{:nextjournal.clerk/visibility {:code :hide}}
(def image-url "https://raw.githubusercontent.com/nextjournal/clerk/main/notebooks/images/seattle.jpg")

;; Clerk専用のビューアを使用
^{:nextjournal.clerk/viewer :image}
image-url

;; HTMLタグを使用した表示
^{:nextjournal.clerk/viewer :html}
(str "<img src='" image-url "' width='400' alt='サンプル画像'>")
```

## データの可視化

### 基本的なグラフ

`notebooks/visualization-basics.clj`：

```clojure
(ns visualization-basics)

;; # データの可視化

;; ## Vegaを使用したグラフ

;; 棒グラフのデータ
(def sales-data
  {:data {:values [{:month "1月" :sales 65}
                   {:month "2月" :sales 59}
                   {:month "3月" :sales 80}
                   {:month "4月" :sales 81}
                   {:month "5月" :sales 56}
                   {:month "6月" :sales 55}]}
   :mark "bar"
   :encoding {:x {:field "month" :type "nominal" :axis {:title "月"}}
              :y {:field "sales" :type "quantitative" :axis {:title "売上（万円）"}}}})

^{:nextjournal.clerk/viewer :vega-lite}
sales-data

;; ## 折れ線グラフ
(def temperature-data
  {:data {:values (map #(hash-map :day % 
                                  :temp (+ 20 (* 5 (Math/sin (/ % 5)))))
                       (range 30))}
   :mark "line"
   :encoding {:x {:field "day" :type "quantitative" :axis {:title "日"}}
              :y {:field "temp" :type "quantitative" :axis {:title "気温（℃）"}}}})

^{:nextjournal.clerk/viewer :vega-lite}
temperature-data

;; ## 散布図
(def scatter-data
  {:data {:values (map (fn [_] {:x (rand-int 100) 
                                :y (rand-int 100)
                                :size (+ 50 (rand-int 200))})
                       (range 50))}
   :mark {:type "circle" :opacity 0.6}
   :encoding {:x {:field "x" :type "quantitative"}
              :y {:field "y" :type "quantitative"}
              :size {:field "size" :type "quantitative"}}})

^{:nextjournal.clerk/viewer :vega-lite}
scatter-data
```

### インタラクティブなグラフ

```clojure
;; ## インタラクティブなグラフ

(def interactive-data
  {:data {:url "https://vega.github.io/vega-lite/data/cars.json"}
   :mark "point"
   :encoding {:x {:field "Horsepower" :type "quantitative"}
              :y {:field "Miles_per_Gallon" :type "quantitative"}
              :color {:field "Origin" :type "nominal"}
              :tooltip [{:field "Name" :type "nominal"}
                        {:field "Origin" :type "nominal"}
                        {:field "Horsepower" :type "quantitative"}
                        {:field "Miles_per_Gallon" :type "quantitative"}]}
   :selection {:grid {:type "interval" :bind "scales"}}})

^{:nextjournal.clerk/viewer :vega-lite}
interactive-data
```

## カスタムビジュアル要素

### SVGの使用

`notebooks/svg-graphics.clj`：

```clojure
(ns svg-graphics)

;; # SVGグラフィックス

;; ## HiccupでSVGを生成
(defn create-svg-chart [data]
  [:svg {:width 400 :height 300}
   ;; 背景
   [:rect {:x 0 :y 0 :width 400 :height 300 :fill "#f0f0f0"}]
   ;; 棒グラフを描画
   (map-indexed 
    (fn [idx {:keys [label value]}]
      [:g
       [:rect {:x (+ 50 (* idx 80))
               :y (- 250 (* value 2))
               :width 60
               :height (* value 2)
               :fill "#4285f4"}]
       [:text {:x (+ 80 (* idx 80))
               :y 270
               :text-anchor "middle"
               :font-size 14}
        label]])
    data)])

(def sample-data
  [{:label "A" :value 60}
   {:label "B" :value 80}
   {:label "C" :value 45}
   {:label "D" :value 90}])

^{:nextjournal.clerk/viewer :html}
(str (create-svg-chart sample-data))

;; ## 円グラフ
(defn polar-to-cartesian [cx cy r angle]
  [(+ cx (* r (Math/cos angle)))
   (+ cy (* r (Math/sin angle)))])

(defn create-pie-slice [cx cy r start-angle end-angle color]
  (let [[x1 y1] (polar-to-cartesian cx cy r start-angle)
        [x2 y2] (polar-to-cartesian cx cy r end-angle)
        large-arc (if (> (- end-angle start-angle) Math/PI) 1 0)]
    [:path {:d (str "M " cx " " cy " "
                    "L " x1 " " y1 " "
                    "A " r " " r " 0 " large-arc " 1 " x2 " " y2 " Z")
            :fill color}]))

(defn create-pie-chart [data]
  (let [total (reduce + (map :value data))
        cx 150 cy 150 r 100]
    [:svg {:width 300 :height 300}
     (loop [data data
            start-angle 0
            result []]
       (if (empty? data)
         result
         (let [{:keys [value color]} (first data)
               angle (* (/ value total) 2 Math/PI)
               end-angle (+ start-angle angle)]
           (recur (rest data)
                  end-angle
                  (conj result (create-pie-slice cx cy r start-angle end-angle color))))))]))

(def pie-data
  [{:value 30 :color "#ff6384"}
   {:value 50 :color "#36a2eb"}
   {:value 20 :color "#ffce56"}])

^{:nextjournal.clerk/viewer :html}
(str (create-pie-chart pie-data))
```

### HTMLとCSSの活用

```clojure
;; ## HTMLとCSSでカスタムレイアウト

(defn create-card [title content color]
  (str "<div style='border: 2px solid " color "; "
       "border-radius: 8px; padding: 16px; margin: 8px; "
       "background-color: " color "20;'>"
       "<h3 style='color: " color "; margin-top: 0;'>" title "</h3>"
       "<p>" content "</p>"
       "</div>"))

^{:nextjournal.clerk/viewer :html}
(str "<div style='display: grid; grid-template-columns: 1fr 1fr; gap: 10px;'>"
     (create-card "Clerk" "対話的ノートブック" "#4285f4")
     (create-card "Clojure" "関数型プログラミング" "#34a853")
     (create-card "Markdown" "ドキュメント作成" "#fbbc04")
     (create-card "可視化" "データの理解" "#ea4335")
     "</div>")
```

## 画像とテキストの組み合わせ

`notebooks/image-text-layout.clj`：

```clojure
(ns image-text-layout)

;; # 画像とテキストのレイアウト

;; ## 画像の横にテキストを配置

^{:nextjournal.clerk/viewer :html}
(str "<div style='display: flex; align-items: center; gap: 20px;'>"
     "<div style='flex: 1;'>"
     "<img src='https://via.placeholder.com/200' alt='プレースホルダー' style='width: 100%;'>"
     "</div>"
     "<div style='flex: 2;'>"
     "<h3>画像の説明</h3>"
     "<p>これは画像とテキストを横並びに配置する例です。"
     "Flexboxを使用することで、レスポンシブなレイアウトが実現できます。</p>"
     "</div>"
     "</div>")

;; ## グリッドレイアウトで画像ギャラリー

(defn create-image-gallery [images]
  (str "<div style='display: grid; grid-template-columns: repeat(auto-fit, minmax(150px, 1fr)); gap: 10px;'>"
       (apply str 
         (map #(str "<img src='" % "' style='width: 100%; height: 150px; object-fit: cover; border-radius: 4px;'>")
              images))
       "</div>"))

^{:nextjournal.clerk/viewer :html}
(create-image-gallery 
  ["https://via.placeholder.com/150/FF0000"
   "https://via.placeholder.com/150/00FF00"
   "https://via.placeholder.com/150/0000FF"
   "https://via.placeholder.com/150/FFFF00"
   "https://via.placeholder.com/150/FF00FF"
   "https://via.placeholder.com/150/00FFFF"])
```

## ビジュアル要素のベストプラクティス

1. **適切なフォーマット選択**
   - 写真: JPEG
   - 図表・ロゴ: PNG（透過が必要な場合）
   - アニメーション: GIF
   - ベクター画像: SVG

2. **サイズの最適化**
   - Web用に画像を圧縮
   - 適切な解像度を選択
   - レスポンシブ対応

3. **アクセシビリティ**
   - alt属性を必ず設定
   - 色だけに依存しない情報伝達
   - 十分なコントラスト

4. **レイアウト**
   - 画像とテキストのバランス
   - 適切な余白の確保
   - モバイル対応

## 練習課題

1. 自分のデータを使って棒グラフを作成
2. SVGで簡単なアイコンを描画
3. 画像ギャラリーを作成してキャプションを追加
4. インタラクティブなグラフを作成

## 次のステップ

画像とビジュアル要素の使い方を理解したら、[対話的教科書の作成](05-interactive-textbook.md)に進んで、すべての要素を組み合わせた実践的なドキュメントを作成しましょう。