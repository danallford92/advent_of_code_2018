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

(defn add-vertical-line [[x y] n square-values]
  (loop [x x y y n n acc 0]
    (if (zero? n)
      acc
      (recur (+ x 1) (+ y 1) (- n 1) (+ acc (get square-values [(+ x n) y])))
      )
    )
  )

(defn add-horizontal-line [[x y] n square-values]
  (loop [x x y y n n acc 0]
    (if (zero? n)
      acc
      (recur (+ x 1) (+ y 1) (- n 1) (+ acc (get square-values [x (+ y n)])))
      )
    )
  )


(defn expand-to-n [[x y] n square-values previous]
  (+ previous (add-vertical-line [x y] n square-values) (add-horizontal-line [x y] n square-values) (get square-values [(+ n x) (+ n y)]))
  )

(defn find-max-for-coord [coord square-values]
  (reduce (fn [acc n] (expand-to-n coord n square-values acc)) (range 1 300))
  )

(reduce (fn [acc n] (expand-to-n [1 1] n s acc)) [0 (get s [1 1])] (range 1 2))
(reduce (fn [acc n] (expand-to-n [33 45] n serial18 acc)) (get serial18 [33 45]) (range 1 2))

(expand-to-n [90 269] 1 serial18 0)

(time (reduce (fn [[max-sum prev-sum] n]
                (let
                  [curr-sum (expand-to-n [1 1] n s prev-sum)
                   new-max (Math/max max-sum curr-sum)
                   new-max-n (if (= new-max curr-sum) n n)
                   ]
                  [new-max-n new-max curr-sum]
                  )
                ) [0 (get s [1 1])] (range 1 300)))


(def serial18 (compute-all-squares 18))
(def serial42 (compute-all-squares 42))
(def serial6878 (compute-all-squares 6878))

(defn find-maximising-n [[x y] serial-grid]
  (let [
        largest-n (- 300 (Math/max x y))
        ]
    (reduce (fn [[max-n max-sum prev-sum] n]
              (let
                [curr-sum (expand-to-n [x y] n serial-grid prev-sum)
                 new-max (Math/max max-sum curr-sum)
                 new-max-n (if (= new-max curr-sum) n max-n)
                 ]
                [new-max-n new-max curr-sum]
                )
              ) [0 0 (get serial-grid [x y])] (range 1 largest-n))
    )
  )


(time (reduce (fn [[max-n max-sum prev-sum] n]
                (println max-sum)
                (let
                  [curr-sum (expand-to-n [90 269] n serial18 prev-sum)
                   new-max (Math/max max-sum curr-sum)
                   new-max-n (if (= new-max curr-sum) n max-n)
                   ]
                  [new-max-n new-max curr-sum]
                  )
                ) [0 0 (get serial18 [90 269])] (range 1 31)))


(time (reduce (fn [[max-n max-sum prev-sum] n]
                (println max-sum)
                (let
                  [curr-sum (expand-to-n [232 251] n serial42 prev-sum)
                   new-max (Math/max max-sum curr-sum)
                   new-max-n (if (= new-max curr-sum) n max-n)
                   ]
                  [new-max-n new-max curr-sum]
                  )
                ) [0 0 (get serial42 [232 251])] (range 1 49)))


(time (reduce (fn [[prev-maximising-n prev-max-sum] n]
                (let [
                      new-sum (find-maximising-n n serial18)
                      ]
                  (if (> new-sum prev-max-sum) [n new-sum] [prev-maximising-n prev-max-sum])
                  )) (for [x (range 1 301) y (range 1 301)] [x y])))


(time
  (reduce
    (fn [[prev-maximising-n prev-maximising-coord prev-max-sum] coord]
      (let [
            [current-maximising-n current-max-sum _] (find-maximising-n coord serial6878)
            ]
        (if (> current-max-sum prev-max-sum) [current-maximising-n coord current-max-sum] [prev-maximising-n prev-maximising-coord prev-max-sum])
        )
      ) [0 [0 0] 0] (for [x (range 1 301) y (range 1 301)] [x y])
    )
  )
;(defn one-larger [square-values prev-values n]
;  (let [
;        to-adjust (filter (fn [[[x y] _]] (and (> 301 (+ x n)) (> 301 (+ y n)))) prev-values)
;        adjusted (map (fn [[coord v]] [coord (+ v (expand-to-n coord n square-values))]) to-adjust)
;        ]
;    (into {} adjusted)
;    )
;  )
;
;(defn get-n-larger [square-values prev-values n]
;  (if (= n 0)
;    prev-values
;    (one-larger square-values (get-n-larger square-values prev-values (- n 1)) n)
;    )
;  )

;(def s (transient (compute-all-squares 18)))
;
;(time (do (reduce (fn [acc n] (one-larger s acc n)) s (range 1 300)) 0))
(def next10 (+ 10 (- smaller (mod smaller 10))))
(def last10  (- bigger (mod bigger 10)))
(range next10 last10 10)


(def tens (range next10 (+ last10 1) 10))
(def singles (concat (range smaller next10) (range (+ 1 last10) (+ 1 bigger))))

(defn sum-row-squares [[x y] n squares squares10]
  (let [
        last (+ x n)
        first10 (+ 10 (- x (mod x 10)))
        last10  (- last (mod last 10))
        tens (range first10 (+ last10 1) 10)
        singles (concat (range x first10) (range (+ 1 last10) (+ 1 last)))
        ]
    (+ (reduce + (concat (map #(get squares10 [% y]) tens) (map #(get squares [% y]) singles))))
    )
  )