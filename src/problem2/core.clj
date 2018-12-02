(ns problem2.core
  (:require
    [problem1.core :as problem1]))

(defn letter-count-frequencies [s]
  (->>
    s
    seq
    frequencies
    vals
    frequencies
    )
  )

(defn run1 [in]
  (def freqs
    (map letter-count-frequencies in))
  (def count2 (count (filter #(contains? % 2) freqs)))
  (def count3 (count (filter #(contains? % 3) freqs)))
  (* count2 count3)
  )

(defn remove-nth [s n]
  (str (subs s 0 n) (subs s (+ n 1))))

(defn stubs [s]
  (->>
    s
    count
    range
    (map #(remove-nth s %))
    set
    )
  )

(defn run2 [in]
  (->>
    in
    (map stubs)
    (mapcat seq)
    problem1/find-first-repeat
    )
  )