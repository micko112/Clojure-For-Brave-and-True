(ns clojure-noob.threads
  (:require [clojure.core.async
             :refer [>! <! >!! <!! go chan buffer map close! thread alts! alts!! timeout]]))

(future (Thread/sleep 4000)
        (println "I'll print after 4 seconds"))
(println "I'll print immediately")

(def task1 (future (do (Thread/sleep 4000)(println "task 1")
            (+ 1 2))))
(future (do (Thread/sleep 4000) (println "task 1")
            (+ 1 2)))
(let [x 6 y 7] [x y])
(def delay2 (delay (do (Thread/sleep 4000)(println "task 1")
            (+ 1 2))))
(delay (do (Thread/sleep 4000) (println "task 1")
            (+ 1 2)))

(* (+ 7 8) @delay2)
(def f (+ 1 2))
(f 2 3)


(def p1 (promise))
(future (Thread/sleep 10000)(deliver p1 30))
(* (+ 7 8) @p1)

(defn slow-inc [x]
  (Thread/sleep 4000)
  (println "Slow inc")
  (inc x))
(def a1 (agent 1))
(send a1 slow-inc)
(println "yes")
@a1

(def echo-ch (chan 1))
(>!! echo-ch :a)
(<!! echo-ch)

(>! echo-ch)
(<! echo-ch)
(future (>!! echo-ch "Hello"))

(go (println "GO1" (<! echo-ch)))
(>!! echo-ch "Hello1")
(buffer (count echo-ch))
(map (comp count name) echo-ch)
(count (name :a))