(defproject clojure-noob "0.1.0-SNAPSHOT"
  :description "FIXME: write description"
  :url "https://example.com/FIXME"
  :license {:name "EPL-2.0 OR GPL-2.0-or-later WITH Classpath-exception-2.0"
            :url "https://www.eclipse.org/legal/epl-2.0/"}
  :dependencies [[org.clojure/clojure "1.12.2"]
                 [criterium "0.4.6"]
                 [uncomplicate/fluokitten "0.10.0"]
                 [uncomplicate/neanderthal "0.60.0"]
                 [metosin/malli "0.20.0"]
                 [com.github.seancorfield/next.jdbc "1.3.1086"]
                 [org.clojure/core.async "1.8.741"]
                 ]
  :plugins [[lein-midje "3.2.2"]]
  :main ^:skip-aot clojure-noob.core
  :target-path "target/%s"
  :profiles {:dev [[:dev/all ~(leiningen.core.utils/get-os)]
             {:dependencies [[midje "1.10.10"]]}]


             :dev/all {:dependencies [
                                      [org.bytedeco/openblas "0.3.30-1.5.12"]]}
             :windows {:dependencies [[org.bytedeco/mkl "2025.2-1.5.12" :classifier "windows-x86_64-redist"]
                                      ;; optional, if you want GPU computing with CUDA. Beware: the size of these 2 jars is cca 800 MB.
                                      #_[org.bytedeco/cuda-redist "13.0-9.14-1.5.13-20251022.164318-20" :classifier "windows-x86_64"]
                                      #_[org.bytedeco/cuda-redist-cublas "13.0-9.14-1.5.13-20251022.164318-20" :classifier "windows-x86_64"]]}
             }
  :repl-options {:init-ns matrix-operations.core}
  )


