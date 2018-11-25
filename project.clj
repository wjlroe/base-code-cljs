(defproject base-code "1.0.0-SNAPSHOT"
  :description "Base Code for Ludum Dare"
  :url "https://github.com/wjlroe/base-code"
  :license {:name "Eclipse Public License"
            :url "http://www.eclipse.org/legal/epl-v10.html"}
  :min-lein-version "2.7.1"
  :dependencies [[org.clojure/clojure "1.9.0"]
                 [org.clojure/clojurescript "1.10.339"]]
  :plugins [[lein-cljsbuild "1.1.7"]]
  :source-path "src/clj"
  :aliases {"fig"       ["trampoline" "run" "-m" "figwheel.main"]
            "fig:build" ["trampoline" "run" "-m" "figwheel.main" "-b" "dev" "-r"]
            "fig:min"   ["run" "-m" "figwheel.main" "-O" "advanced" "-bo" "dev"]}
  :profiles {:dev {:dependencies [[com.bhauman/figwheel-main "0.1.9"]
                                  [com.bhauman/rebel-readline-cljs "0.1.4"]]
                   :resource-paths ["target"]
                   :clean-targets ^{:protect false} ["target"]}}
  :cljsbuild {:builds [{:id "dev"
                        :source-paths ["src/cljs"]
                        :compiler {
                                   :main game.dev
                                   :output-to "resources/public/js/compiled/out/game.js"
                                   :output-dir "resources/public/js/compiled/out"
                                   :optimizations :none
                                   :cache-analysis true
                                   :source-map true
                                   :asset-path "js/compiled/out"}}
                       {:id "release"
                        :source-paths ["src/cljs"]
                        :compiler {
                                   :main game.main
                                   :output-to "resources/public/js/compiled/out-adv/game.min.js"
                                   :output-path "resource/public/js/compiled/out-adv"
                                   :optimizations :advanced
                                   :pretty-print false}}]})
