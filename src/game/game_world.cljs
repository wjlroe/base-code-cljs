(ns game.game-world
  (:require [cljs.core.async :refer [chan put! take! >! <! buffer
                                     dropping-buffer sliding-buffer timeout close! alts!]]
            [cljs.core.async :refer-macros [go go-loop alt!]]
            [goog.style :as style]
            [goog.dom :as dom]))

(defonce game-started (atom nil))

(defn find-image
  [id]
  (dom/getElement (name id)))

(def images
  {:crate "crate.png"
   :tree-trunk "tree-trunk.png"})

(defn set-element-style
  [element style]
  (doseq [[key value] style]
    (style/setStyle element (name key) value)))

(defn insert-image-assets!
  [images]
  (when-not (dom/getElement "images")
    (let [body (dom/getElement "body")
          div (dom/createElement "div")]
      (set! (.-id div) "images")
      (set-element-style div {:display "none"})
      (doseq [[id filename] images]
        (let [img (dom/createElement "img")]
          (set! (.-id img) (name id))
          (set! (.-src img) (str "images/" filename))
          (dom/appendChild div img)))
      (dom/appendChild body div))))

(defn game-surface
  []
  (.getElementById js/document "game"))

(defn start-game
  []
  (let [canvas (game-surface)
        surface (.getContext canvas "2d")]
    (insert-image-assets! images)
    (doto canvas
      (-> .-width (set! 1920))
      (-> .-height (set! 1080)))
    (let [width (.-width canvas)
          height (.-height canvas)]
      (set! (.-fillStyle surface) "#00ff00")
      (set! (.-strokeStyle surface) "#00ff00")
      (.fillRect surface 0 0 width height)
      (.strokeRect surface 0 0 width height))
    (.drawImage surface (find-image :crate) 10 10)
    (.drawImage surface (find-image :tree-trunk) 50 50)))

(when-not @game-started
  (start-game)
  ;; (reset! game-started :yes)
  )
