(ns sdfs.core)

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
              (if (pos? distance)
                "#FFF" ;; outside
                "#000" ;; inside
                ))
        (.fillRect context x y 1 1)))))

(defn sdf
  [[x y]]
  (- x y))

(defn init!
  []
  (draw! "canvas" sdf))

(defn reload!
  []
  (draw! "canvas" sdf))
