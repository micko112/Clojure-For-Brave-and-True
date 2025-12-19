(ns clojure-noob.vezbanje
  (:require [uncomplicate.fluokitten.core :as fk]
            [criterium.core :as c]))

(defn my-vec [n] (map float(vec(range n))))
(defn dot [vec1 vec2] (reduce + (map * vec1 vec2)))
(dot (my-vec 5) (my-vec 5))
(eval (dot (my-vec 5) (my-vec 5)))
(def addition-list (list + 1 2))
(eval addition-list)



(use 'criterium.core)
