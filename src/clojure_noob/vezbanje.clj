(ns clojure-noob.vezbanje
  (:require [uncomplicate.fluokitten.core :as fk]
            [criterium.core :as c]
            [clojure.string :as str]))

(defn my-vec [n] (map float(vec(range n))))
(defn dot [vec1 vec2] (reduce + (map * vec1 vec2)))
(dot (my-vec 5) (my-vec 5))
(eval (dot (my-vec 5) (my-vec 5)))
(def addition-list (list + 1 2))
(eval addition-list)

(def mat1 [[1 2 3]
           [4 5 6]] )

(def mat2 [[10 40]
           [20 50]
           [30 60]] )
(defn transpose [mat2] (apply mapv vector mat2))

(defn take-column [mat n]
  (let [result (mapv #(nth % n) mat)]
    (println "get-column called with arr:" mat "col-index:" n "result:" result)
    result))

(def mat2t (transpose mat2))
(defn cross-prod ()

             )
(mapv (fn [row] (reduce (fn [acc elm](+ acc (dot row elm)))mat1)) mat2)

(use 'criterium.core)
(defn -main []
  (take-column mat2 2))

(str/split "racecar")

