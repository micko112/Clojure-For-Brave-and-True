(ns clojure-noob.uredno
  (:require [uncomplicate.fluokitten.core :as fk]
            [uncomplicate.neanderthal
             [core :refer :all]
             [native :refer :all]]
            )
  )

(println (fk/fmap inc (fk/just 1)))

(defn desc [a b] ; nasao sam na guglu kako se sortira od najveceg ka najmanjem.
  (compare b a))

(def raw-users [{:name "Uros" :xp 130 :level 0}
            {:name "Milan" :xp 40 :level 0}
            {:name "Micko" :xp 70 :level 0}
            {:name "Trener" :xp 2400 :level 0}])

(defn formula-for-level [level]
  (* 25 level (+ level 1))
  )

(def all-levels (vec (range 1 10)))

(defn xp-needed-to-level-up [vector-levels]
  (vec(map formula-for-level vector-levels)))

(def xp-level-up
  (xp-needed-to-level-up all-levels))

(defn define-users-levels
  [list-users xp-for-levels]
  (map (fn [user]
         (assoc user :level
                     (reduce (fn [level-count level]
                               (if (>= (:xp user) level)
                                 (inc level-count) level-count ))
                             0
                             xp-for-levels)))
       list-users))


(def users (vec (define-users-levels raw-users xp-level-up)))
(defn
  leaderboard
  "users Leaderboard by earned XP"
  [participant-list]
  (sort-by :xp desc participant-list))


(defn filter-by-level [users level] (reduce (fn [acc user]
                                        (if (= (:level user) level) (conj acc user) acc)) [] users))


(defn vec-set-levels [users] (vec (sort (set ( map (fn [user] (:level user) )users)))))

(defn users-by-level [levels users]
  (map (fn [level-sorted]
         (reduce (fn [acc user]
                   (if (= (:level user) level-sorted)(conj acc user) acc) )
                 [] users)) levels))

;(users-by-level vec-set-levels users)

(defn make-user [name]
  {:name name
   :xp 0
   :level 0})
(defn add-xp [user xp]
  (update user :xp + xp))
(defn add-xp-atom [user xp]
  (swap! user :xp + xp))
(def micko (atom {
                  {:name "Micko" :xp 0 :level 0}
                  }))
(comment
  (vec-set-levels users)
  (users-by-level (vec-set-levels users) users)
  (leaderboard users)
  (def micko (make-user "Micko"))
  (def micko (add-xp micko 40)))
(println all-levels)

(defn -main [& args]
  (println "Radi"))