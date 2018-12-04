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

(defn find-sleep-mins-by-guard [events]
  (let [with-guard-and-previous (map vector (guard-on-duty events) (rest events) events)
        guards-sleeping (filter (fn [[_ _ prev]] (= (:action prev) :sleep)) with-guard-and-previous)
        sleep-events (map vector (map first guards-sleeping) (map to-sleep guards-sleeping))
        sleep-mins-by-guard (map (fn [[id es]] [id (mapcat second es)]) (group-by first sleep-events))]
    sleep-mins-by-guard)
  )

(defn find-most-sleepy-guard [sleep-mins-by-guard]
  (let [
        total-sleep-by-guard (map (fn [[id mins]] [id (count mins)]) sleep-mins-by-guard)
        most-sleep (apply max (map second total-sleep-by-guard))
        [most-sleepy-guard-id _] (first (filter #(= most-sleep (second %)) total-sleep-by-guard))]
    (first (filter #(= most-sleepy-guard-id (first %)) sleep-mins-by-guard))
    )
  )

(defn find-most-sleepy-minute [sleep-mins-for-guard]
  (let [
        freqs (frequencies sleep-mins-for-guard)
        most-sleepy-minute (find-index-for-max freqs)]
    [most-sleepy-minute (get freqs most-sleepy-minute)]
    )
  )


(defn run1 [in]
  (let [events (ordered-events in)
        sleep-mins-by-guard (find-sleep-mins-by-guard events)
        [most-sleepy-guard-id most-sleepy-guard-sleep] (find-most-sleepy-guard sleep-mins-by-guard)
        [most-sleepy-guard-most-sleepy-minute _] (find-most-sleepy-minute most-sleepy-guard-sleep)]
    (* most-sleepy-guard-most-sleepy-minute (Integer/parseInt most-sleepy-guard-id))
    )
  )

(defn run2 [in]
  (let [events (ordered-events in)
        sleep-mins-by-guard (find-sleep-mins-by-guard events)
        most-sleepy-minutes-by-guard (map (fn [[id sleep]] [id (find-most-sleepy-minute sleep)]) sleep-mins-by-guard)
        most-sleep-in-a-minute (apply max (map second (map second most-sleepy-minutes-by-guard)))
        [guard-id [minute sleep-in-most-sleepy-minute]] (first (filter #(= most-sleep-in-a-minute (second (second %))) most-sleepy-minutes-by-guard))
        ]


    (* minute (Integer/parseInt guard-id))
    )
  )


