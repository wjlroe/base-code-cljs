(defproject base-code "1.0.0-SNAPSHOT"
  :description "Base Code for my Ludum Dare #24 entry."
  :dependencies [[org.clojure/clojure "1.4.0"]
                 [org.clojure/clojurescript "0.0-1450"]]
  :plugins [[lein-cljsbuild "0.2.3"]]
  :source-path "src/clj"
  :cljsbuild {:builds [{
                        :source-path "src/cljs"
                        :compiler {
                                   :output-to "resources/public/game.js"
                                   :optimizations :whitespace
                                   :pretty-print true}}]})
