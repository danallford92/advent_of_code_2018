(ns problem1.core)

(defn parse-input [in]
  (map #(Integer/parseInt %) in)
  )


(defn run1 [in]
  (time (->> in
             parse-input
             (reduce +)))
  )

(defn find-duplicates [xs]
  (->> xs
       (#(map (fn [a b] [a b]) (reductions conj #{} %) %))
       (filter #(contains? (first %) (second %)))
       (map second)
       (into #{})
       ))

(defn run2 [in]
  (def cum-sum #(reductions + %))

  (time (->> in
             parse-input
             cycle
             cum-sum
             find-duplicates
             )))


