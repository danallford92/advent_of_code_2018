(ns problem5.core)

(defn are-opposite [c1 c2]
  (= 32 (Math/abs (- (int c1) (int c2))))
  )

(defn anhilate [chars]
  (apply str (rest (reverse (reduce (fn [acc c] (if (are-opposite (first acc) c) (rest acc) (cons c acc))) '(\Ā) chars))))
  )

(defn run1 [[s]]
  (time (->>
          s
          seq
          anhilate
          count))
  )


