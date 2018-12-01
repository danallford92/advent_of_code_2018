(ns problem1.core
  (:require [clojure.string :as str]
            ))

(defn parse-input [in]
  (->> in
       str/split-lines
       (map (fn [x] (Integer/parseInt x)))))


(defn run1 [in]
  (time (->> in
             parse-input
             (reduce +)))
  )

(defn run2 [in]
  (def cum-sum #(reductions + %))
  (defn find-first-repeat [xs]
    (->> xs
         (#(map (fn [a b] [a b]) (reductions conj #{} %) %))
         (filter #(contains? (first %) (second %)))
         (first)
         (second)))

  (time (->> in
             parse-input
             cycle
             cum-sum
             find-first-repeat
             )))


