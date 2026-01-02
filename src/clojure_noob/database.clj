(ns clojure-noob.database
  (:require [datomic.api :as d ]))

(def db-uri "datomic:dev://localhost:4334/niggadb")
(d/create-database db-uri)
(def conn (d/connect db-uri
                     ))

(def movie-schema [{:db/ident :movie/title
                    :db/valueType :db.type/string
                    :db/cardinality :db.cardinality/one
                    :db/unique :db.unique/identity
                    :db/doc "The title of the movie"}
                   {:db/ident :movie/genre
                    :db/valueType :db.type/string
                    :db/cardinality :db.cardinality/one
                    :db/doc "The genre of the movie"}
                   {:db/ident :movie/release-year
                    :db/valueType :db.type/long
                    :db/cardinality :db.cardinality/one
                    :db/doc "The year the movie was released in theaters"}])

(def first-movies
  [{:movie/title "The Goonies"
    :movie/genre "adventure"
    :movie/release-year 1985}
   {:movie/title "Repo Man"
    :movie/genre "punk dystopia"
    :movie/release-year 1984}
   {:movie/title "Commando"
    :movie/genre "action/adventure"
    :movie/release-year 1985}])

(def db (d/db conn))

(def all-data-from-1985 '[:find ?title ?year ?genre
                          :where [?e :movie/title ?title]
                                  [?e :movie/release-year ?year]
                          [?e :movie/genre ?genre]
                          [?e :movie/release-year 1985]])
(d/q all-data-from-1985 db)
(d/q '[:find ?title ?genre ?year
       :where [
               ?e :movie/title ?title]
       [ ?e :movie/genre ?genre]
       [?e :movie/release-year ?year]
       [?e :movie/release-year 1985]] db)

(d/q '[:find ?e
       :where [?e :movie/title "Commando"]]
     db)
(def commando-id
  (ffirst (d/q '[:find ?e
                 :where [?e :movie/title "Commando"]]
               db)))
(def old-db (d/as-of db 13194139534317))
(def db-history (d/his))