(ns problem1.core)

(defn parse-input [in]
  (->> in
       (map (fn [x] (Integer/parseInt x)))))


(defn run1 [in]
  (time (->> in
             parse-input
             (reduce +)))
  )

(defn find-first-repeat [xs]
  (->> xs
       (#(map (fn [a b] [a b]) (reductions conj #{} %) %))
       (filter #(contains? (first %) (second %)))
       (first)
       (second)))

(defn run2 [in]
  (def cum-sum #(reductions + %))

  (time (->> in
             parse-input
             cycle
             cum-sum
             find-first-repeat
             )))


