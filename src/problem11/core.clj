(ns problem11.core)


(defn get-hundreds-digit [n]
  (mod (int (Math/floor (/ n 100))) 10)
  )

(defn compute-square [[x y] serial]
  (let [
        rack-id (+ x 10)
        initial-power (* y rack-id)
        grid-serial serial
        increased-power (+ initial-power grid-serial)
        power-level (* increased-power rack-id)
        hundreds (get-hundreds-digit power-level)
        ]
    (- hundreds 5)
    )
  )

(defn compute-all-squares [serial]
  (reduce conj {}
          (for [x (range 1 301) y (range 1 301)]
            [[x y] (compute-square [x y] serial)]
            ))
  )

(defn compute-n-grid-value [[xi yi] n all-squares]
  (reduce + (for [x (range xi (+ n xi)) y (range yi (+ n yi))]
              (get all-squares [x y])
              ))
  )

;; solution to part 1
(time
  (let [all-squares (compute-all-squares 6878)
        grouped (map (fn [coord] [coord (compute-n-grid-value coord 3 all-squares)])
                     (for [x (range 1 299) y (range 1 299)]
                       [x y]
                       )
                     )
        max-sum (apply max (map second grouped))
        ]
    (filter #(= max-sum (second %)) grouped)
    )
  )


;; solution to part 2

(defn add-horizontal-line [[x y] n square-values]
  (if (zero? n)
    0
    (+ (#'add-horizontal-line [x (+ y 1)] (- n 1) square-values) (get square-values [(+ x n -1) (+ y n)]))
    )
  )

(defn add-vertical-line [[x y] n square-values]
  (if (zero? n)
    0
    (+ (#'add-vertical-line [(+ x 1) y] (- n 1) square-values) (get square-values [(+ x n) (+ y n -1)]))
    )
  )

(def add-horizontal-line (memoize add-horizontal-line))
(def add-vertical-line (memoize add-vertical-line))

(defn expand-to-n [[x y] n square-values]
  (+ (add-vertical-line [x y] n square-values) (add-horizontal-line [x y] n square-values) (get square-values [(+ n x) (+ n y)]))
  )

(defn one-larger [square-values prev-values n]
  (let [
        to-adjust (filter (fn [[[x y] _]] (and (> 301 (+ x n)) (> 301 (+ y n)))) prev-values)
        adjusted (map (fn [[coord v]] [coord (+ v (expand-to-n coord n square-values))]) to-adjust)
        ]
    (into {} adjusted)
    )
  )

(defn get-n-larger [square-values prev-values n]
  (if (= n 0)
    prev-values
    (one-larger square-values (get-n-larger square-values prev-values (- n 1)) n)
    )
  )

;(def s (transient (compute-all-squares 18)))
;
;(time (do (reduce (fn [acc n] (one-larger s acc n)) s (range 1 300)) 0))