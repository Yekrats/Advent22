(ns advent.core
  (:gen-class)
  (:require [clojure.string :as str]))

(defn -main
  "I don't do a whole lot yet."
  [& args]
  (println "Hello, World!"))

(def advent1
  (->> (slurp "./resources/advent1.txt")
       str/split-lines
       (map str/trim)
       (map (fn [x] (when-not (str/blank? x) (Integer/parseInt x))))))

(defn calorie-count1 []
  (second (reduce (fn [[my-batch-sum highest-so-far] cur]
            (if (int? cur)
              (vector (+ my-batch-sum cur) highest-so-far)
              (if (> my-batch-sum highest-so-far)
                (vector 0 my-batch-sum)
                (vector 0 highest-so-far))))
          [0 0]
          advent1)))

(defn top-3 [n [top1 top2 top3]]
  (cond
    (>= n top1) [n top1 top2]
    (>= n top2) [top1 n top2]
    (>= n top3) [top1 top2 n]
    :else [top1 top2 top3]))

(defn calorie-count2 []
  (apply + (second
             (reduce (fn [[my-batch-sum [top1 top2 top3]] cur]
                    (if (int? cur)
                      (vector (+ my-batch-sum cur) [top1 top2 top3])
                      (if (> my-batch-sum top3)
                        (vector 0 (top-3 my-batch-sum [top1 top2 top3]))
                        (vector 0 [top1 top2 top3]))))
                  [0 [0 0 0]]
                  advent1))))