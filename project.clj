(defproject jenkins-builds-to-csv "0.1.0-SNAPSHOT"
  :description "FIXME: write description"
  :url "http://example.com/FIXME"
  :license {:name "Eclipse Public License"
            :url "http://www.eclipse.org/legal/epl-v10.html"}
  :dependencies [[org.clojure/clojure "1.8.0"]
                 [org.clojure/data.xml "0.0.8"]
                 [org.clojure/data.csv "0.1.3"]
                 [org.clojure/data.zip "0.1.2"]]
  :main ^:skip-aot jenkins-builds-to-csv.core
  :target-path "target/%s"
  :jvm-opts ["-Xmx2g" "-server"]
  :profiles {:uberjar {:aot :all}})
