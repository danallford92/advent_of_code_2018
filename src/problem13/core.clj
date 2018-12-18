(ns problem13.core)


(def lookup {\- :horizontal \< :horizontal \> :horizontal \+ :junction \| :vertical \^ :vertical \V :vertical})

(def rightward-moving #{:horizontal :junction :corner-right-up :corner-right-down})

(defn parse-slash [prev c]
  (if (contains? rightward-moving prev)
    (if (= c \\)
      :corner-left-down
      :corner-left-up
      )
    (if (= c \\)
      :corner-right-up
      :corner-right-down
      )
    )
  )

(defn parse-char [prev c]
  (if (or (= c \\) (= c \/))
    (parse-slash prev c)
    (get lookup c)
    )
  )

(defn to-track [chars]
  (rest (reverse (reduce (fn [acc c] (cons (parse-char (first acc) c) acc)) [#{}] chars)))
  )

(defn parse-track [line]
  (into {}
        (filter #(not (nil? (second %)))
                (map (fn [n c] [n c]) (range) (to-track line)))
        )
  )

(defn parse-all [lines]
  (apply merge (map (fn [y-coord tracks] (map (fn [[x-coord type]] [[x-coord y-coord] type]) tracks)) (range) (map parse-track lines)))
  )

(defn to-start [c]
  (case c
    \< {:direction :left}
    \> {:direction :right}
    \^ {:direction :up}
    \V {:direction :down}
    :default nil
    )
  )

(defn parse-starts [line]
  (filter #(not (nil? (second %)))
          (map (fn [n c] [n c]) (range) (to-start line)))
  )



([[0 2] :corner-right-up] [[1 2] :horizontal] [[2 2] :horizontal] [[3 2] :horizontal] [[4 2] :horizontal] [[5 2] :corner-left-up]) ([[0 1] :vertical] [[5 1] :vertical]) [[0 0] :corner-right-down] [[1 0] :horizontal] [[2 0] :horizontal] [[3 0] :horizontal] [[4 0] :horizontal] [[5 0] :corner-left-down])

{[2 2] :horizontal, [0 0] :corner-right-down, [1 0] :horizontal, [4 2] :horizontal, [3 0] :horizontal, [5 2] :corner-left-up, [5 1] :vertical, [0 2] :corner-right-up, [2 0] :horizontal, [5 0] :corner-left-down, [1 2] :horizontal, [3 2] :horizontal, [0 1] :vertical, [4 0] :horizontal}))
