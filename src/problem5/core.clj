(ns problem5.core)

(defn are-opposite [c1 c2]
  (and (not (= c1 c2)) (= (. c1 String/toLowerCase) (. c2 String/toLowerCase)))
  )

(defn anhilate [s]
  (apply str (rest (reduce (fn [acc c] (if (are-opposite (str (last acc)) (str c)) (subs acc 0 (- (count acc) 1)) (str acc c))) "~" s))))

(defn run1 [[s]]
  (time (->>
          s
          anhilate
          count))
  )


