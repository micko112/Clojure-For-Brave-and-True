(ns clojure-noob.bank
  (:require [malli.core :as m]
            [malli.error :as me]))


;; Credit adds money to user's balance
(defn creditTo
  [to-atom amount]
  (swap! to-atom update :balance + amount))

;; Debit subtracts money from user's balance
(defn debitFrom
  [from-atom amount]
  (swap! from-atom update :balance - amount))

;; Transfer money from one user to another
(defn transfer
  "Transfers amount from from-user to to-user.
   Returns true if successful, nil if transfer fails."
  [from-user-atom to-user-atom amount]
  (if (and (>= (:balance @from-user-atom) amount)
           (> amount 0))
    (do
      (dosync(debitFrom from-user-atom amount)
             (creditTo to-user-atom amount))
      true)
    nil))
(defn transfer2 [from-user-atom to-user-atom amount]
  (dosync(alter from-user-atom debitFrom amount)
  (alter to-user-atom creditTo amount)))

;(defn validator-balance (comp pos? :balance))
(def user-schema
  [:map
   [:name :string]
   [:balance [:int {:min 0}]]])

(defn validate-user [new-state]
  (if (m/validate user-schema new-state)
    true
    (do
      ;; Opciono: Ispisujemo zašto je validacija pala (npr. balans < 0)
      (println "Validation error:" (me/humanize (m/explain user-schema new-state)))
      false)))

;; Main function demonstrating the banking system
(defn -main []
  (println)

  ;; Hardcoded users Pera and Mika stored in atoms
  (let [pera (atom {:name "Pera" :balance 1000} :validator validator-balance)
        mika (atom {:name "Mika" :balance 500})]

    (println "Initial balances:")
    (println (str "User: " (:name @pera) ", Balance: " (:balance @pera)))
    (println (str "User: " (:name @mika) ", Balance: " (:balance @mika)))
    (println)

    ;; Transfer 200 from Pera to Mika
    (println "Transferring 200 from Pera to Mika...")
    (let [result (transfer2 pera mika 200)]
      (if result
        (do
          (println "Transfer successful!")
          (println (str "User: " (:name @pera) ", Balance: " (:balance @pera)))
          (println (str "User: " (:name @mika) ", Balance: " (:balance @mika))))
        (println "Transfer failed! Insufficient balance or invalid amount."))))

  )
