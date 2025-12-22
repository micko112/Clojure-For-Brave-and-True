(ns clojure-noob.deep-learning
  (:require [uncomplicate.fluokitten.core :as fk]
            [uncomplicate.commons.core :refer [with-release]]
            [uncomplicate.neanderthal
             [core :refer :all]
             [native :refer :all]
             [math :refer [signum exp]]
             [vect-math :refer [fmax! tanh! linear-frac!]]]

            ))

(def a (dge 2 3 [1 2 3 4 5 6]))
(def b (dge 3 2 [1 3 5 7 9 11]))
(mm a b)

(def v1 (dv -1 2 5.2 0))
(def v2 (dv (range 22)))
(def v3 (dv -2 -3 1 0))

(with-release )








