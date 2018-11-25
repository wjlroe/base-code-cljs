(ns base-code.game
  (:require [goog.dom :as dom]
            [goog.Timer :as timer]
            [goog.events :as events]
            [goog.events.EventType :as event-type]))

(def sq-width 30)
(def sq-height 30)

(def shapes
  {:o
   [[0 0]
    [sq-height sq-width]
    [0 sq-height]
    [sq-width 0]]
   :j
   [[0 (* 2 sq-height)]
    [sq-width 0]
    [sq-width sq-height]
    [sq-width (* 2 sq-height)]]
   :i
   [[0 0]
    [0 sq-height]
    [0 (* 2 sq-height)]
    [0 (* 3 sq-height)]]
   :l
   [[0 0]
    [0 sq-height]
    [0 (* 2 sq-height)]
    [sq-width (* 2 sq-height)]]
   :s
   [[0 sq-height]
    [sq-width 0]
    [sq-width sq-height]
    [(* 2 sq-width) 0]]
   :z
   [[0 0]
    [sq-width 0]
    [sq-width sq-height]
    [(* 2 sq-width) sq-height]]
   :t
   [[0 0]
    [sq-width 0]
    [sq-width sq-height]
    [(* 2 sq-width) 0]]})

(defn get-shape
  "Get the co-ordinates of a shape. Either returns the required shape or a random shape."
  ([]
     (get-shape (rand-nth (keys shapes))))
  ([shape-name]
     (get shapes shape-name)))

(defn make-shape
  "Make a shape. Either return the shape required or a random one."
  [origin & shape-name]
  {:origin origin
   :block-width sq-width
   :block-height sq-height
   :blocks
   (apply get-shape shape-name)})

(defn shift-down
  [shape]
  (assoc shape :origin (assoc (:origin shape) :y
                              (+ sq-height
                                 (:y (:origin shape))))))

(defn surface
  []
  (let [surface (dom/getElement "surface")]
    [(.getContext surface "2d")
     (. surface -width)
     (. surface -height)]))

(defn fill-rect [[surface] [x y width height] [r g b]]
  (set! (. surface -fillStyle) (str "rgb(" r "," g "," b ")"))
  (.fillRect surface x y width height))

(defn stroke-rect [[surface] [x y width height] line-width [r g b]]
  (set! (. surface -strokeStyle) (str "rgb(" r "," g "," b ")"))
  (set! (. surface -lineWidth) line-width)
  (.strokeRect surface x y width height))

(defn fill-circle [[surface] coords [r g b]]
  (let [[x y d] coords]
    (set! (. surface -fillStyle) (str "rgb(" r "," g "," b ")"))
    (. surface (beginPath))
    (.arc surface x y d 0 (* 2 Math/PI) true)
    (. surface (closePath))
    (. surface (fill))))

(defn update-canvas
  [world surface]
  (let [falling-shapes (:falling world)
        [_ width height] surface]
    (fill-rect surface [0 0 width height] [10 10 10])
    (stroke-rect surface [0 0 width height] 2 [0 0 0])
    (doseq [{:keys [origin blocks]} falling-shapes]
      (doseq [[incx incy] blocks]
        (let [x (+ incx (:x origin))
              y (+ incy (:y origin))]
          (fill-rect surface [x y sq-width sq-height] [60 80 160]))))))

(defn move-pieces-down
  [state [_ width height]]
  (let [{:keys [falling]} state]
    (assoc state :falling (map shift-down falling))))

(defn game
  [timer state surface]
  (let [[_ width height] surface]
    (swap! state (fn [curr]
                   (update-canvas curr surface)
                   (-> curr
                       (move-pieces-down surface)
                       ;; More shit
                       )))))

(defn click [timer state surface event]
  (when (not (.-enabled timer))
    (. timer (start))))

(defn ^:export main
  []
  (let [surface (surface)
        timer (goog.Timer. 500)
        state (atom {:falling [(make-shape {:x 30 :y 0})]})]
    (update-canvas @state surface)
    (events/listen timer goog.Timer/TICK #(game timer state surface))
    (events/listen js/window event-type/CLICK #(click timer state surface %))))
