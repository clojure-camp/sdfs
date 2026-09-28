(ns sdfs.example
  (:require
   [sdfs.solutions :as s]))

(def DEBUG true)

(defn draw!
  [element-id sdf]
  (let [canvas (js/document.getElementById element-id)
        context (.getContext canvas "2d")
        width (.-width canvas)
        height (.-height canvas)]
    (doseq [x (range width)
            y (range height)]
      (let [distance (sdf [x y])]
        (set! (.-fillStyle context)
              (if DEBUG
                (if (pos? distance)
                  (if (< -1 (rem distance 25) 1)
                    "#AAA"
                    "#FFF")
                  (if (< -1 (rem distance 25) 1)
                    "#AAA"
                    "#000"))
                (if (pos? distance)
                  "#FFF" ;; outside
                  "#000" ;; inside
                  )))
        (.fillRect context x y 1 1)))))

(def sdf
  (s/subtract (s/smooth-union 50
                              (->> (s/circle 100)
                                   (s/scale 0.75 2)
                                   (s/translate 245 250))
                              (s/subtract
                               (->> (s/square 100)
                                    (s/translate 150 250))
                               (->> (s/square 50)
                                    (s/translate 150 250))))
              (->>  (s/square 30)
                    (s/translate 250 250)
                    (s/fuzz 5))))

(defn init!
  []
  (draw! "canvas" sdf))
