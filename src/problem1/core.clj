(ns problem1.core
  (:require [clojure.string :as str]
            ))

(defn parse-input [in]
  (->> in
       str/split-lines
       (map (fn [x] (Integer/parseInt x)))))

(defn run1 [in]
  (->> in
       parse-input
       (reduce +))
  )

(defn run2 [in]
  (->> in
       parse-input
       cycle
       (reductions +)
       ((fn [y] (map (fn [a b] [a b]) y (reductions conj #{} y))))
       (filter (fn [[n s]] (contains? s n)))
       (first)
       (first)
  )
)


