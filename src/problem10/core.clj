(ns problem10.core)

(defn parse-row [row]
  row
  (let [[_ xp yp xv yv] (re-matches #".*?(-?\d+),.*?(-?\d+).*?(-?\d+),.*?(-?\d+)>.*?" row)]
    (map #(Integer/parseInt %) [xp yp xv yv])
    )
  )

(defn get-y-velocity [[_ _ _ yv]]
  yv
  )

(defn find-fastest-moving-up [vs]
  (let [max-up (apply max (map get-y-velocity vs))]
    (first (filter #(= max-up (get-y-velocity %)) vs))
    )
  )

(defn find-fastest-moving-down [vs]
  (let [max-down (apply min (map get-y-velocity vs))]
    (first (filter #(= max-down (get-y-velocity %)) vs))
    )
  )

(defn first-time-y-within-n [n [_ yi1 _ dy1] [_ yi2 _ dy2]]
  (- (/ (- yi2 yi1) (- dy1 dy2)) (/ n (Math/abs (- dy1 dy2))))
  )

(defn last-time-y-within-n [n [xi1 yi1 dx1 dy1] [xi2 yi2 dx2 dy2]]
  (let [first-time (first-time-y-within-n n [xi1 yi1 dx1 dy1] [xi2 yi2 dx2 dy2])]
    (Math/floor (+ first-time (/ (* 2 n) (Math/abs (- dy1 dy2)))))
    )
  )

(defn value-at-time [[xi yi xv yv] t]
  [(+ xi (* xv t)) (+ yi (* yv t))]
  )

(defn show [coords]
  (let [
        min-y (apply min (map second coords))
        max-y (apply max (map second coords))
        min-x (apply min (map first coords))
        max-x (apply max (map first coords))
        ]

    (map (fn [y] (map (fn [x] (if (contains? coords [x y]) "#" ".")) (range (- min-x 3) (+ 3 max-x)))) (range (- min-y 3) (+ 3 max-y)))
    )
  )

(defn run1 [in]
  (let [
        stars (map parse-row in)
        fastest-up (find-fastest-moving-up stars)
        fastest-down (find-fastest-moving-down stars)
        min-time (first-time-y-within-n 8 fastest-up fastest-down)
        max-time (last-time-y-within-n 8 fastest-up fastest-down)
        t (+ 1 min-time)
        ]
    (interpose "\n" (map (fn [t] (interpose "\n" (show (set (map #(value-at-time % t) stars))))) (range min-time (+ max-time 1))))
    )
  )

(defn run2 [in]
  (let [stars (map parse-row in)]
    (+ 1 (first-time-y-within-n 8 (find-fastest-moving-up stars) (find-fastest-moving-down stars)))

    )
  )