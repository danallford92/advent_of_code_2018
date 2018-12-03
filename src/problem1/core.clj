(ns problem1.core)

(defn parse-input [in]
  (map #(Integer/parseInt %) in)
  )

(defn run1 [in]
  (time (->> in
             parse-input
             (reduce +)))
  )

(map f [1 2 3] [4 5 6]) -> [(f 1 4) (f 2 5) (f 3 6)]

(reductions conj #{} [1 2 3 2]) -> [#{} #{1} #{1 2} #{1 2 3}] [1 2 3]

[#{} 1] [#{1} 2] [#{1 2} 3] [#{1 2 3} 2]

(defn find-duplicates [xs]
  (->> xs
       (#(map (fn [a b] [a b]) (reductions conj #{} %) %))
       (filter #(contains? (first %) (second %)))
       (map second)
       (into #{})  ;TODO: should not be set (infinite seq)
       ))

(defn run2 [in]
  (def cum-sum #(reductions + %))

  (time (->> in
             parse-input
             cycle
             cum-sum
             find-duplicates
             )))


