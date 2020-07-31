(defproject clj-workshop "0.1.0-SNAPSHOT"
  :plugins [[lein-cljfmt "0.6.6"]
            [lein-nsorg "0.3.0"]]

  :dependencies [[com.stuartsierra/component "1.0.0"]
                 [datascript                 "1.0.0"]
                 [lambdaisland/kaocha        "1.0.641"]
                 [mount                      "0.1.16"]
                 [org.clojure/clojure        "1.10.1"]]

  :aliases {"lint-fix" ["do"
                        ["nsorg" "--replace"]
                        ["cljfmt" "fix"]]
            "kaocha"   ["run" "-m" "kaocha.runner"]}

  :main ^:skip-aot clj-workshop-2020.core
  :target-path "target/%s"
  :profiles {:uberjar {:aot :all}})
