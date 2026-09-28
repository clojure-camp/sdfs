(ns sdfs.solutions)

(defn circle [r]
  (fn [[x y]]
    (- (Math/sqrt (+ (Math/pow x 2) (Math/pow y 2)))
       r)))

(defn rectangle [height, width]
  (fn [[x y]]
    (max (- (Math/abs x) width)
         (- (Math/abs y) height))))

(defn square [size]
  (rectangle size size))

;; higher level fns

(defn translate [dx dy f]
  (fn [[x y]]
    (f [(- x dx) (- y dy)])))

(defn union [& fs]
  (fn [xy]
    (apply min (map #(% xy) fs))))

(defn smooth-union [k a b]
  (fn [xy]
    (let [h (/ (Math/max (- k (Math/abs (- (a xy) (b xy)))) 0)
               k)]
      (- (apply min (map #(% xy) [a b]))
         (* (/ k 4) (Math/pow h 2))))))

(defn intersection [& fs]
  (fn [xy]
    (apply max (map #(% xy) fs))))

(defn subtract [f1 f2]
  (fn [xy]
    (max (f1 xy) (* -1 (f2 xy)))))

(defn scale
  [x-scale y-scale f]
  (fn [[x y]]
    (f [(/ x x-scale) (/ y y-scale)])))

(defn fuzz [k f]
  (fn [[x y]]
    (f [(+ x (* k (- (rand 2) 1)))
        (+ y (* k (- (rand 2) 1)))])))


