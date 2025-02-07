(ns simpleexample.core
  (:require [reagent.core :as r]
            [reagent.dom :as rdom]
            [reagent.dom.client :as client]))

(defonce root (client/create-root (js/document.getElementById "app")))

(defn example-component []
  [:div {:class "card"}
   [:div {:class "card-body"}
    [:h5 {:class "card-title"} "Hello World"]]])

(defn simple-example []
  [:div {:class "container-sm custom-container"}
   [:div {:class "row justify-content-center"}
    [:div {:class "col-12 text-center"}
     [example-component]]]])
     

(defn ^:export run []
  (client/render root [simple-example]))


(defn ^:dev/before-load stop []
    (js/console.log "reload - stop"))

(defn ^:dev/after-load start []
  (run)
  (js/console.log "reload - start"))
