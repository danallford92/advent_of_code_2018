(ns problem6.core)

(defn to-coords [in]
  (let [[_ x y] (re-matches #"(\d+), (\d+)" in)]
    (map #(Integer/parseInt %) [x y])
    )
  )


(defn middle [[x1 y1] [x2 y2]]
  (vector (/ (+ x1 x2) 2) (/ (+ y1 y2) 2))
  )


(defn distance [[x1 y1] [x2 y2]]
  (+ (Math/abs (- x1 x2)) (Math/abs (- y1 y2)))
  )

(defn finite-length [point1 point2]
  {:type :finite :points #{point1 point2}}
  )

(defn vertical [x]
  {:type :vertical :x x}
  )

(defn horizontal [y]
  {:type :horizontal :y y}
  )


(defn equidistant-intersections [[x1 y1] [x2 y2]]
  (if (or (= x1 x2) (= y1 y2))
    (if (= y1 y2) #{(vertical (/ (+ x1 x2) 2))} #{(horizontal (/ (+ y1 y2) 2))})
    (let [
          x-diff (Math/abs (- x1 x2))
          y-diff (Math/abs (- y1 y2))
          ]
      (if (= x-diff y-diff)
        #{(finite-length [x1 y2] [x2 y1])}
        (let [d (/ (distance [x1 y1] [x2 y2]) 2)]
          (if (> x-diff y-diff)
            ; intersects on the horizontal
            (if (< x1 x2)
              #{(finite-length [(+ x1 (- d y-diff)) y2] [(- x2 (- d y-diff)) y1])}
              #{(finite-length [(- x1 (- d y-diff)) y2] [(+ x2 (- d y-diff)) y1])}
              )
            ;intersects on the vertical
            (if (< y1 y2)
              #{(finite-length [x2 (+ y1 (- d x-diff))] [x1 (- y2 (- d x-diff))])}
              #{(finite-length [x2 (- y1 (- d x-diff))] [x1 (+ y2 (- d x-diff))])}
              )
            )
          )
        )
      )
    )
  )

(defn infinite-equidistant [[x1 y1] [x2 y2]]

  )

(defn find-nearest [coord sites]
  (let [
        distances (map #(distance coord %) sites)
        min-distance (apply min distances)
        nearest-sites (filter #(= (distance coord %) min-distance) sites)
        ]
    nearest-sites
    )
  )

(defn perturb [[x y] n]
  [[(+ n x) y] [(- x n) y] [x (+ n y)] [x (- y n)]]
  )

(defn i [sites site [distance-n n]]
  [(filter
     #(= (find-nearest % sites) [site])
     (set (mapcat (fn [[x y]]
                    (filter #(= (+ n 1) (distance site %)) (perturb [x y] 1))
                    ) distance-n))
     ) (+ 1 n)])


(defn find-all [site sites]
  (if (some #(= (find-nearest % sites) [site]) (perturb site 1000))
    `()
    (mapcat first
            (take-while
              #(not (empty? (first %)))
              (iterate #(i sites site %) [[site] 0])
              )
            )
    )
  )

(defn run1 [in]
  (time (let [
              sites (map to-coords in)

              ]
          (apply max (map count (map #(find-all % sites) sites)))
          ))
  )

(defn distance-to-all [sites site]
  (reduce #(+ %1 (distance site %2)) sites)
  )

(defn y-cost [sites y]
  (let [
        ys (map second sites)
        y-distances (map #(Math/abs (- %1 y)) ys)
        ]
    (reduce + y-distances)
    )
  )

(defn y-costs [sites]
  (let [
        ys (map second sites)
        min-y (apply min ys)
        max-y (apply max ys)
        ]
    (map
      #(y-cost sites %)
      (range min-y (+ 1 max-y)))
    )
  )

(defn x-cost [sites x]
  (let [
        xs (map first sites)
        x-distances (map #(Math/abs (- %1 x)) xs)
        ]
    (reduce + x-distances)
    )
  )

(defn x-costs [sites]
  (let [
        xs (map first sites)
        min-x (apply min xs)
        max-x (apply max xs)
        ]
    (map
      #(x-cost sites %)
      (range min-x (+ 1 max-x)))
    )
  )

(defn run2 [in]
  (time
    (let [
          sites (map to-coords in)
          xcs (x-costs sites)
          ycs (y-costs sites)
          ]
      (reduce + (for [xc xcs yc ycs :when (> 10000 (+ xc yc))] 1))
      )
    )
  )
















;
;
;(defn to-bounding-box [coords]
;  (let [
;        top-row (apply min (map (fn [[x y]] y) coords))
;        bottom-row (apply max (map (fn [[x y]] y) coords))
;        leftest-col (apply min (map (fn [[x y]] x) coords))
;        rightest-col (apply max (map (fn [[x y]] x) coords))
;        ]
;    (for [
;          x (range leftest-col (+ 1 rightest-col))
;          y (range top-row (+ 1 bottom-row))
;          ]
;      [x y]
;      )
;    )
;  )
;
;(defn on-boundary [sites coord]
;  (let [
;        top-row (apply min (map (fn [[x y]] y) sites))
;        bottom-row (apply max (map (fn [[x y]] y) sites))
;        leftest-col (apply min (map (fn [[x y]] x) sites))
;        rightest-col (apply max (map (fn [[x y]] x) sites))
;        ]
;    (and
;      (>= (first coord) leftest-col)
;      (<= (first coord) rightest-col)
;      (>= (second coord) top-row)
;      (>= (second coord) bottom-row)
;      )
;    )
;  )
;
;(defn distance [[x1 y1] [x2 y2]]
;  (+ (Math/abs (- x1 x2)) (Math/abs (- y1 y2)))
;  )
;
;(defn find-nearest [coord sites]
;  (let [
;        distances (map #(distance coord %) sites)
;        min-distance (apply min distances)
;        nearest-sites (filter #(= (distance coord %) min-distance) sites)
;        ]
;    nearest-sites
;    )
;  )
;
;
;(defn find-bound [[x1 y1] [x2 y2]]
;  ; when row is below y1 and y2 and y1 < y2
;  (let [
;        y-diff (- y2 y1)                                    ; y-diff can be
;        ]
;
;    (if (< (abs (- x1 x2)) y-diff)
;      ; coord1 strictly dominates coord2
;      {}
;      ;the bound is the point at which moving along x towards coord2 is traded off against moving extra y
;      (if
;        (< x1 x2)
;        ; [1 0] [2 2] -> [1 0] is disputed
;        {:right-bound (+ x1 (/ (+ y-diff) 2)) :right-disputed (% y-diff 2)}
;        )
;      {:left-bound (+ x2 (/ (+ y-diff) 2)) :left-disputed (% y-diff 2)}
;      )
;    )
;
;  )
;
;(defn distances [y length sites]
;  (let [
;        y-contrib (reverse (sort (map (fn [_ y2] (Math/abs (- y y2))) sites)))
;        ]
;    (reductions
;
;      (fn [prev-distances n]
;        (map + prev-distances (map #(if (<= n %) -1 1) sites))
;        ) (range (count row)))
;
;    )
;  )
;
;
;(defn run1 [in]
;  (time (let [
;              sites (map to-coords in)
;              bounding-box (to-bounding-box sites)
;              nearest (map #(find-nearest % sites) bounding-box)
;              ;undisputed-nearest (map first (filter #(= 1 (count %)) nearest))
;              ;finite-area-sites (filter #(not (on-boundary sites %)) undisputed-nearest)
;              ]
;          nearest
;          ))
;  )