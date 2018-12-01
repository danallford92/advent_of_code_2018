(ns runner.core
  (:require [clojure.string :as str] [clj-http.client :as client] :require [problem1.core :as problem1]))


(defn -main
  "Takes session id as first argument to retrieve the problem input"
  [& args]
  (->>
    (client/get "https://adventofcode.com/2018/day/1/input"
                {:headers {:Cookie (format "session=%s" (first args))}})
    :body
    problem1/run
    println
    )
  )