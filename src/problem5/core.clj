(ns problem5.core)

(defn are-opposite [c1 c2]
  (= 32 (Math/abs (- (int c1) (int c2))))
  )

(defn anhilate [s]
  (rest (reduce (fn [acc c] (if (are-opposite (first acc) c) (subs acc 1) (str c acc))) "Ā" s))
  )

(defn run1 [[s]]
  (time (->>
          s
          anhilate
          count))
  )


