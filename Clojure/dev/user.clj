(ns user
  (:require [nextjournal.clerk :as clerk]))

(defn start []
  (clerk/serve! {:browse true}))

(defn show [file]
  (clerk/show! file))

(defn dev
  "開発中のファイルを自動で監視"
  []
  (clerk/serve! {:watch-paths ["notebooks"]}))

(comment
  ;; REPLでの利用例
  (start)
  (show "notebooks/hello.clj")
  (dev))