# Clerkガイド - 対話的なClojure教科書を作ろう

このガイドでは、Clerkを使ってClojureの対話的な教科書を作成する方法を段階的に学びます。

## 目次

### [01. セットアップ](01-setup.md)
- 前提条件の確認
- プロジェクトの作成
- Clerkの起動
- 開発環境の設定
- エディタの設定

### [02. 基本的な使い方](02-basic-usage.md)
- 基本的なコード評価
- 結果の可視化
- コードの可視性制御
- インクリメンタル評価
- ファイル監視モード
- エラーハンドリング

### [03. Markdown統合](03-markdown-integration.md)
- ClojureファイルでのMarkdown使用
- MarkdownファイルでのClojure使用
- 高度なMarkdown機能
- ドキュメント構造の設計
- APIドキュメントの作成

### [04. 画像とビジュアル要素](04-images-and-visuals.md)
- ローカル画像の表示
- Web画像の表示
- データの可視化（グラフ、チャート）
- SVGグラフィックス
- HTMLとCSSの活用
- レイアウトテクニック

### [05. 対話的教科書の作成](05-interactive-textbook.md)
- 教科書の設計原則
- 実例：Clojureデータ構造の教科書
- インタラクティブな要素の追加
- 練習問題とクイズ
- 教科書の公開方法

## このガイドの使い方

1. **順番に進む**: 各章は前の章の知識を前提としています
2. **コードを実行**: すべてのコード例は実際に実行してみてください
3. **実験する**: 提供されたコードを変更して、どうなるか確認しましょう
4. **プロジェクトを作る**: 学んだことを使って、自分の教科書を作成してください

## 必要な環境

- Java 11以上
- Clojure CLI
- お好みのテキストエディタ（VS Code、Emacs、IntelliJなど）

## サンプルプロジェクトの構造

```
my-clerk-textbook/
├── deps.edn              # プロジェクトの依存関係
├── dev/
│   └── user.clj         # 開発用の設定
├── notebooks/           # ノートブックファイル
│   ├── intro.clj       # はじめに
│   ├── basics.clj      # 基本編
│   └── advanced.clj    # 応用編
├── images/             # 画像ファイル
└── public/             # 静的サイト出力先
```

## クイックスタート

```bash
# プロジェクトディレクトリを作成
mkdir my-clerk-textbook
cd my-clerk-textbook

# deps.ednを作成
echo '{:deps {org.clojure/clojure {:mvn/version "1.11.1"}
              io.github.nextjournal/clerk {:mvn/version "0.18.1150"}}}' > deps.edn

# REPLを起動
clj

# Clerkを起動
(require '[nextjournal.clerk :as clerk])
(clerk/serve! {:browse true})
```

## コミュニティとリソース

- [Clerk公式リポジトリ](https://github.com/nextjournal/clerk)
- [Clerkデモサイト](https://github.clerk.garden/nextjournal/clerk-demo/)
- [Book of Clerk](https://book.clerk.vision)
- [Clojure公式サイト](https://clojure.org/)

## フィードバック

このガイドに関する質問や提案がある場合は、お気軽にお問い合わせください。

Happy Clerking! 🎉