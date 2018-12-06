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

(defn mins-included [[_ curr prev]] (range (Integer/parseInt (:minute prev)) (Integer/parseInt (:minute curr))))

(def find-max
  #(first (filter (fn [[_ v]] (= v (apply max (vals %)))) %))
  )

(defn find-sleep-mins-by-guard [events]
  (let [
        guard-and-previous (with-guard-and-previous events)
        guards-sleeping (filter (fn [[_ _ prev]] (= (:action prev) :sleep)) guard-and-previous)
        sleep-events (map vector (map first guards-sleeping) (map mins-included guards-sleeping))
        sleep-mins-by-guard (map (fn [[id es]] [id (frequencies (mapcat second es))]) (group-by first sleep-events))
        ]
    sleep-mins-by-guard)
  )

(defn find-most-sleepy-guard [sleep-mins-by-guard]
  (let [
        total-sleep-by-guard (map (fn [[id mins]] [id (reduce + (vals mins))]) sleep-mins-by-guard)
        most-sleep (apply max (map second total-sleep-by-guard))
        [most-sleepy-guard-id _] (first (filter #(= most-sleep (second %)) total-sleep-by-guard))]
    (first (filter #(= most-sleepy-guard-id (first %)) sleep-mins-by-guard))
    )
  )

(defn run1 [in]
  (time
    (let [events (ordered-events in)
          mins-by-guard (find-sleep-mins-by-guard events)
          [guard-id sleep] (find-most-sleepy-guard mins-by-guard)
          [minute _] (find-max sleep)]

      (* minute (Integer/parseInt guard-id))
      )
    )
  )

(defn run2 [in]
  (let [events (ordered-events in)
        mins-by-guard (find-sleep-mins-by-guard events)
        most-frequent-minute-by-guard (map (fn [[id sleep]] [id (find-max sleep)]) mins-by-guard)
        most-sleep-in-a-minute (apply max (map second (map second most-frequent-minute-by-guard)))
        [guard-id [minute _]] (first (filter (fn [[_ [_ x]]] (= most-sleep-in-a-minute x)) most-frequent-minute-by-guard))
        ]

    (* minute (Integer/parseInt guard-id))
    )
  )


