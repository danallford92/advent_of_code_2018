(ns problem5.core)

(defn are-opposite [c1 c2]
  (and (not (= c1 c2)) (= (. c1 String/toLowerCase) (. c2 String/toLowerCase)))
  )

(defn anhilate [s]
  (rest (reduce (fn [acc c] (if (are-opposite (str (first acc)) (str c)) (subs acc 1) (str c acc))) "~" s))
  )

(defn run1 [[s]]
  (time (->>
          s
          anhilate
          count))
  )


