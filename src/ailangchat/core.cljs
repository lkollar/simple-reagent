(ns ailangchat.core
  (:require [reagent.core :as r]
            [reagent.dom :as rdom]
            [reagent.dom.client :as client]
            ["antd" :refer (Button Input)]))

(defonce root (client/create-root (js/document.getElementById "app")))

(defonce chat-history (r/atom []))
(defonce input-value (r/atom ""))

(defn chat-message [{:keys [text is-user]}]
  [:div {:style {:text-align (if is-user "right" "left")
                 :margin "8px"}}
   [:> Button {:type (if is-user "primary" "default")
               :style {:max-width "80%"
                       :padding "8px 16px"}}
    text]])

(defn chat-interface []
  [:div
   [:div {:style {:height "400px"
                  :overflow-y "auto"
                  :border "1px solid #ddd"
                  :padding "16px"
                  :margin-bottom "16px"}}
    (for [[idx msg] (map-indexed vector @chat-history)]
      ^{:key idx} [chat-message msg])]
   [:> Input {:placeholder "Type your message"
              :value @input-value
              :onPressEnter #(when-not (empty? @input-value)
                               (swap! chat-history conj {:text @input-value :is-user true})
                               (reset! input-value ""))
              :onChange #(reset! input-value (.. % -target -value))}]])

(defn ^:export run []
  (client/render root
                 [:div {:class "flex justify-center"}
                  [:div {:class "w-1/2"}
                   [chat-interface]]]))

(defn ^:dev/before-load stop []
  (js/console.log "reload - stop"))

(defn ^:dev/after-load start []
  (run)
  (js/console.log "reload - start"))
