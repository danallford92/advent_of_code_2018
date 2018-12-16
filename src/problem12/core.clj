(ns problem12.core)

(defn extract-initial [in]
  (drop 2 (drop-while #(not= % \:) (first in)))
  )


(defn extract-transforms [in]
  (let [transform-lines (rest (rest in))]
    (into {} (map (fn [line] [(take 5 line) (last line)]) transform-lines))
    )
  )


(defn parse [in]
  {:initial (extract-initial in) :transforms (extract-transforms in)}
  )

(defn next-gen-single-pot [n state transforms]
  (get transforms (map #(nth state % \.) (range (- n 2) (+ n 3))) \.)
  )

(defn next-gen [state transforms]
  (map #(next-gen-single-pot % state transforms) (range -2 (+ 2 (count state))))
  )

(defn run1 [in]
  (let [{initial :initial t :transforms} (parse in)
        num-gen 200
        new-state (reduce (fn [acc n] (next-gen acc t)) initial (range 0 num-gen))
        scores (map (fn [k v] [(- k (* 2 num-gen)) v]) (range) new-state)
        alive (map first (filter #(= (second %) \#) scores))
        ]
    (+ (reduce + alive) (* (count alive) (- 50000000000 num-gen)))
    )
  )