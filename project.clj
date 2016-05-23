(defproject dummy "0.1.0-SNAPSHOT"
  :description "FIXME: write description"
  :url "http://example.com/FIXME"
  :license {:name "Eclipse Public License"
            :url "http://www.eclipse.org/legal/epl-v10.html"}
  :dependencies [ [org.clojure/clojure      "1.8.0"]
                  [org.clojure/core.async   "0.2.374"]
                  [ring/ring-codec          "1.0.0"]
                  [tupelo                   "0.1.72"]
                  [criterium                "0.4.3"]
                ]
  :main ^:skip-aot clj.core
  :target-path "target/%s"
  :jvm-opts [ 
              ; "-XX:+PrintFlagsFinal" 
            ]
  :profiles {:uberjar {:aot :all}}
  :repl-options { :init-ns user }
)
