(ns clojure-noob.deep-learning
  (:require [uncomplicate.fluokitten.core :as fk]
            [uncomplicate.commons.core :refer [with-release let-release Releaseable release]]
            [uncomplicate.neanderthal
             [core :refer [mv! axpy! scal! transfer!]]
             [native :refer [dv dge]]
             [math :refer [signum exp]]
             [vect-math :refer [fmax! tanh! linear-frac!]]
             ]
            [criterium.core :refer [quick-bench]]

            )
  )

(def a (dge 2 3 [1 2 3 4 5 6]))
(def b (dge 3 2 [1 3 5 7 9 11]))
(mm a b)

(def v1 (dv -1 2 5.2 0))
(def v2 (dv (range 22)))
(def v3 (dv -2 -3 1 0))

(with-release [x (dv 0.3 0.9)
               w1 (dge 4 2 [0.3 0.6
                            0.1 2.0
                            0.9 3.7
                            0.0 1.0]
                       {:layout :row})
               h1 (dv 4)]
              (println (mv! w1 x h1)))

(with-release [x (dv 0.3 0.9)
               w1 (dge 4 2 [0.3 0.6
                            0.1 2.0
                            0.9 3.7
                            0.0 1.0]
                       {:layout :row})
               h1 (dv 4)
               w2 (dge 1 4 [0.75 0.15 0.22 0.33])
               y (dv 1)]
              (println (mv! w2 (mv! w1 x h1) y)))


(defn step! [threshold x]
  (fk/fmap! signum (axpy! -1.0 threshold (fmax! threshold x x))))
(let [threshold (dv [1 2 3])
      x (dv [0 2 7])]
  (step! threshold x))

(def x (dv 0.3 0.9))
(def w1 (dge 4 2 [0.3 0.6
                  0.1 2.0
                  0.9 3.7
                  0.0 1.0]
             {:layout :row}))
(def threshold (dv 0.7 0.2 1.1 2))

(step! threshold (mv w1 x
                     ))
(def bias (dv 0.7 0.2 1.1 2))
(def zero (dv 4))
(step! bias (mv w1 x))

(defn relu! [threshold x]
  (axpy! -1.0 threshold (fmax! threshold x x)))

(relu! bias (mv w1 x))

(tanh! (axpy! -1.0 bias (mv w1 x)))

(defn sigmoid! [x]
  (linear-frac! 0.5 (tanh! (scal! 0.5 x)) 0.5))

(sigmoid! (axpy! -1.0 bias (mv w1 x)))
(import 'clojure.lang.IFn)

(defprotocol Parameters
  (weight [this])
  (bias [this]))

(deftype FullyConnectedInference [w b h activ-fn]
  Releaseable (release [_]
                (release w)
                (release b)
                (release h))
  Parameters
  (weight [this] w)
  (bias [this] b)
  IFn
  (invoke [_ x]
    (activ-fn b (mv! w x h)))
  )
(defn fully-connected [activ-fn in-dim out-dim]
  (let-release [w (dge out-dim in-dim)
                bias (dv out-dim)
                h (dv out-dim)]
               (->FullyConnectedInference w bias h activ-fn)))