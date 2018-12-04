(ns problem4.core)


(defn parse-input [row]
  (let
    [[_ date hour minute action] (re-matches #"\[(.*?) (\d\d):(\d\d)\] (.*)" row)]
    (if (= "falls asleep" action)
      {:date date :hour hour :minute minute :action :sleep}
      (if
        (= "wakes up" action)
        {:date date :hour hour :minute minute :action :wake}
        (let [[_ id] (re-matches #"Guard #(\d*) begins shift" action)]
          {:date date :hour hour :minute minute :action :guard :guard-id id}
          )
        )
      )
    )
  )

(defn ordered-events [in]
  (->>
    in
    (map parse-input)
    (sort-by (juxt :date :hour :minute))
    ))

(defn guard-on-duty [events]
  (map :guard-id
       (reductions
         (fn [acc event]
           (if
             (nil? (:guard-id event))
             acc
             event
             )
           ) events)))



(defn with-guard-and-previous [events] (map vector (guard-on-duty events) (rest events) events))

(defn to-sleep [[_ curr prev]] (range (Integer/parseInt (:minute prev)) (Integer/parseInt (:minute curr))))

(defn find-index-for-max [m]
  (first (filter #(= (apply max (vals m)) (get m %)) (keys m)))
  )

(defn run1 [in]
  (let [events (ordered-events in)
        with-guard-and-previous (map vector (guard-on-duty events) (rest events) events)
        guards-sleeping (filter (fn [[_ _ prev]] (= (:action prev) :sleep)) with-guard-and-previous)
        sleep-events (map vector (map first guards-sleeping) (map to-sleep guards-sleeping))
        sleep-mins-by-guard (map (fn [[id es]] [id (mapcat second es)]) (group-by first sleep-events))
        total-sleep-by-guard (map (fn [[id mins]] [id (count mins)]) sleep-mins-by-guard)
        most-sleep (apply max (map second total-sleep-by-guard))
        [most-sleepy-guard _] (first (filter #(= most-sleep (second %)) total-sleep-by-guard))
        most-sleepy-guard-sleep (frequencies (second (first (filter #(= most-sleepy-guard (first %)) sleep-mins-by-guard))))
        most-sleepy-guard-most-sleepy-minute (find-index-for-max most-sleepy-guard-sleep)

        ]
    (* most-sleepy-guard-most-sleepy-minute (Integer/parseInt most-sleepy-guard))
    )
  )
;
;(defn run1 [in]
;  (let [events (ordered-events in)
;        with-guard-and-previous (map vector (guard-on-duty events) (rest events) events)
;        guards-sleeping (filter (fn [[_ _ prev]] (= (:action prev) :sleep)) with-guard-and-previous)
;        sleep-events (map vector (map first guards-sleeping) (map to-sleep guards-sleeping))
;        sleep-mins-by-guard (map (fn [[id es]] [id (mapcat second es)]) (group-by first sleep-events))
;        total-sleep-by-guard (map (fn [[id mins]] [id (count mins)]) sleep-mins-by-guard)
;        most-sleep (apply max (map second total-sleep-by-guard))
;        [most-sleepy-guard _] (first (filter #(= most-sleep (second %)) total-sleep-by-guard))
;        most-sleepy-guard-sleep (frequencies (second (first (filter #(= most-sleepy-guard (first %)) sleep-mins-by-guard))))
;        most-sleepy-guard-most-sleepy-minute
;        (first (filter #(= (apply max (vals most-sleepy-guard-sleep)) (get most-sleepy-guard-sleep %)) (keys most-sleepy-guard-sleep)))
;        ]
;    (* most-sleepy-guard-most-sleepy-minute (Integer/parseInt most-sleepy-guard))
;    )
;  )



