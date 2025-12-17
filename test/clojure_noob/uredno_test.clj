
(ns clojure-noob.uredno-test
  (:require [midje.sweet :refer :all]
            [clojure-noob.uredno :as u]))


(def sample-users [{:name "Uros" :xp 130 :level 1}
                   {:name "Micko" :xp 70 :level 1}
                   {:name "Milan" :xp 40 :level 0}
                   {:name "Trener" :xp 2400 :level 9}])

(facts "filter-by-level"
       (fact "returns only users with the given level"
             (map :name (u/filter-by-level sample-users 1))
             => ["Uros" "Micko"])

       (fact "returns empty vector when no users match"
             (u/filter-by-level sample-users 5)
             => []))

(fact "add-xp adds xp to user"
      (u/add-xp{:name "Micko" :xp 0 :level 0} 40)
      => {:name "Micko" :xp 40 :level 0})

(fact "leaderboard"
      (map :name (u/leaderboard [{:name "A" :xp 10}
                                 {:name "B" :xp 50}
                                 {:name "C" :xp 20}]))
      => ["B" "C" "A"])
;(fact "filter by level"
;      (u/filter-by-level))

(facts "vec set levels"
       (fact "return solid  vector of set of levels"
             (u/vec-set-levels sample-users)
             => [0 1 9]))