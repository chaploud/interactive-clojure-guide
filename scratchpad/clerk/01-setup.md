# 01. Clerkのセットアップ

このガイドでは、Clerkを使い始めるための環境構築について説明します。

## 前提条件

Clerkを使用するには、以下がインストールされている必要があります：

- **Java 11以上**: ClojureはJVM上で動作します
- **Clojure CLI**: プロジェクト管理とREPL実行に必要です

### Javaのインストール確認

```bash
java -version
```

Java 11以上がインストールされていることを確認してください。

### Clojure CLIのインストール

macOSの場合（Homebrew使用）：
```bash
brew install clojure/tools/clojure
```

Linuxの場合：
```bash
curl -L -O https://github.com/clojure/brew-install/releases/latest/download/linux-install.sh
chmod +x linux-install.sh
sudo ./linux-install.sh
```

詳細は[Clojure公式サイト](https://clojure.org/guides/install_clojure)を参照してください。

## プロジェクトの作成

### 1. プロジェクトディレクトリの作成

```bash
mkdir my-clerk-notebook
cd my-clerk-notebook
```

### 2. deps.ednファイルの作成

プロジェクトのルートに`deps.edn`ファイルを作成します：

```clojure
{:deps {org.clojure/clojure {:mvn/version "1.11.1"}
        io.github.nextjournal/clerk {:mvn/version "0.18.1150"}}}
```

### 3. 最初のノートブックファイル

`notebooks`ディレクトリを作成し、最初のノートブックを作成します：

```bash
mkdir notebooks
```

`notebooks/hello.clj`を作成：

```clojure
(ns hello)

;; # はじめてのClerkノートブック

;; Clerkへようこそ！これは実行可能なClojureコードです。

(+ 1 2 3)

;; 文字列も表示できます
"Hello, Clerk!"

;; データ構造も美しく表示されます
{:name "Clerk"
 :type "対話的ノートブック"
 :features ["ライブリロード" "静的サイト生成" "Markdown統合"]}
```

## Clerkの起動

### 1. REPLの起動

プロジェクトのルートディレクトリで：

```bash
clj
```

### 2. Clerkの読み込みと起動

REPLで以下のコマンドを実行：

```clojure
;; Clerkを読み込む
(require '[nextjournal.clerk :as clerk])

;; Clerkサーバーを起動（ブラウザが自動で開きます）
(clerk/serve! {:browse true})

;; ノートブックを表示
(clerk/show! "notebooks/hello.clj")
```

ブラウザが開き、ノートブックが表示されれば成功です！

## 開発環境の設定

### user.cljの作成（オプション）

開発をスムーズにするため、`dev/user.clj`ファイルを作成することをお勧めします：

```bash
mkdir dev
```

`dev/user.clj`：

```clojure
(ns user
  (:require [nextjournal.clerk :as clerk]))

(defn start []
  (clerk/serve! {:browse true}))

(defn show [file]
  (clerk/show! file))

;; 開発中のファイルを自動で監視
(defn dev []
  (clerk/serve! {:watch-paths ["notebooks"]}))

(comment
  ;; REPLで実行する例
  (start)
  (show "notebooks/hello.clj")
  (dev))
```

`deps.edn`を更新して、開発用のパスを追加：

```clojure
{:deps {org.clojure/clojure {:mvn/version "1.11.1"}
        io.github.nextjournal/clerk {:mvn/version "0.18.1150"}}
 :paths ["dev"]}
```

これで、REPLで`(start)`を実行するだけでClerkが起動します。

## エディタの設定

### VS Code + Calva

1. VS Codeに[Calva拡張機能](https://marketplace.visualstudio.com/items?itemName=betterthantomorrow.calva)をインストール
2. `Ctrl+Shift+P`（macOSは`Cmd+Shift+P`）でコマンドパレットを開く
3. "Calva: Start a Project REPL and Connect"を選択
4. deps.ednプロジェクトを選択

### Emacs + CIDER

`.emacs`または`init.el`に追加：

```elisp
(defun clerk-show ()
  (interactive)
  (when-let
      ((filename (buffer-file-name)))
    (save-buffer)
    (cider-interactive-eval
     (concat "(nextjournal.clerk/show! \"" filename "\")"))))

(define-key clojure-mode-map (kbd "<M-return>") 'clerk-show)
```

これで`M-return`でファイルをClerkで表示できます。

## トラブルシューティング

### ポートが使用中の場合

デフォルトのポート7777が使用中の場合：

```clojure
(clerk/serve! {:port 7778 :browse true})
```

### ファイルが見つからない場合

相対パスの代わりに絶対パスを使用：

```clojure
(clerk/show! (str (System/getProperty "user.dir") "/notebooks/hello.clj"))
```

### 依存関係の問題

最新バージョンを確認：
- [Clojars](https://clojars.org/io.github.nextjournal/clerk)でClerkの最新バージョンを確認
- `deps.edn`のバージョンを更新

## 次のステップ

セットアップが完了しました！次は[基本的な使い方](02-basic-usage.md)に進んで、Clerkの基本機能を学びましょう。