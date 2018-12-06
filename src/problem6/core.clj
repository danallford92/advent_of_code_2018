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

(defn equidistant-intersections [[x1 y1] [x2 y2]]
  (if (= x1 x2)
    #{}
    (if (= y1 y2)
      #{}
      (let [
            x-diff (Math/abs (- x1 x2))
            y-diff (Math/abs (- y1 y2))
            ]
        (if (= x-diff y-diff)
          #{[x1 y2] [x2 y1]}
          (let [d (/ (distance [x1 y1] [x2 y2]) 2)]
            (if (> x-diff y-diff)
              ; intersects on the horizontal
              (if (< x1 x2)
                #{[(+ x1 (- d y-diff)) y2] [(- x2 (- d y-diff)) y1]}
                #{[(- x1 (- d y-diff)) y2] [(+ x2 (- d y-diff)) y1]}
                )
              ;intersects on the vertical
              (if (< y1 y2)
                #{[x2 (+ y1 (- d x-diff))] [x1 (- y2 (- d x-diff))]}
                #{[x2 (- y1 (- d x-diff))] [x1 (+ y2 (- d x-diff))]}
                )
              )
            )
          )

        )
      )
    )
  )



;
;(defn bounds [site sites]
;  (let [
;        other-sites (filter #(= site %) sites)
;        nearest (find-nearest sites other-sites)
;
;        ]
;    ()
;    )
;  )

(defn run1 [in]
  ;(time (let [
  ;            sites (map to-coords in)
  ;
  ;            ]
  ;        sites
  ;        ))
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