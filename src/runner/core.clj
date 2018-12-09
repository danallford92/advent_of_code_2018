(ns runner.core
  (
    :require [clj-http.client :as client]
             [problem9.core :as problem9]
             [clojure.java.io :as io]
             [clojure.string :as str]
             ))


(defn get-problem-input [[problem-number session-id]]
  (defn ensure-problem-input-cached [cache-file]
    (if (not (.exists (io/file cache-file)))
      (spit cache-file
            (:body (client/get (format "https://adventofcode.com/2018/day/%s/input" problem-number)
                               {:headers {:Cookie (format "session=%s" session-id)}})))
      ))

  (defn ensure-dir-exists [dir]
    (if (not (.exists (io/file dir)))
      (.mkdir (io/file dir))
      )
    )

  (def problem-input-dir "problem-input")

  (def cache-file (format "%s/%s.txt" problem-input-dir problem-number))

  (ensure-dir-exists problem-input-dir)
  (ensure-problem-input-cached cache-file)


  (slurp cache-file)
  )


(defn -main
  "Takes session id as first argument to retrieve the problem input"
  [& args]
  (->>
    (get-problem-input args)
    str/split-lines
    ;problem9/run1
    println
    )
  )