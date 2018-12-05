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

(defn is-same-unit [c1 c2]
  (or (= c1 c2) (are-opposite c1 c2)))

(defn remove-all [s to-remove]
  (filter #(not (is-same-unit % to-remove)) s)
  )

(defn run2 [[s]]
  (time (let [
         inital-pass (anhilate (seq s))
         chars (map char (range 97 123))
         ]

     (->>
       chars
       (map #(remove-all inital-pass %))
       (map anhilate)
       (map count)
       (apply min)
       )
     ))

  )

