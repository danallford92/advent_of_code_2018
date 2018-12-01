(ns runner.core
  (
    :require [clj-http.client :as client]
             [problem1.core :as problem1]
             [clojure.java.io :as io]))


(defn get-problem-input [[problem-number session-id]]

  (def problem-input-dir "problem-input")

  (if (not (.exists (io/file problem-input-dir)))
    (.mkdir (io/file problem-input-dir))
    )

  (def cache-file (format "%s/%s.txt" problem-input-dir problem-number))

  (if (not (.exists (io/file cache-file)))
    (spit cache-file
          (:body (client/get (format "https://adventofcode.com/2018/day/%s/input" problem-number)
                             {:headers {:Cookie (format "session=%s" session-id)}})))
    )

  (slurp cache-file)
  )


(defn -main
  "Takes session id as first argument to retrieve the problem input"
  [& args]
  (->>
    (get-problem-input args)
    problem1/run2
    println
    )
  )