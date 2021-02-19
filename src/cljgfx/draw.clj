;;; Copyright (c) 2014, 2015, 2020, 2021  Pavel Tisnovsky, Red Hat
;;; All rights reserved.
;;;
;;; Redistribution and use in source and binary forms, with or without
;;; modification, are permitted provided that the following conditions are met:
;;;     * Redistributions of source code must retain the above copyright
;;;       notice, this list of conditions and the following disclaimer.
;;;     * Redistributions in binary form must reproduce the above copyright
;;;       notice, this list of conditions and the following disclaimer in the
;;;       documentation and/or other materials provided with the distribution.
;;;     * Neither the name of the Red Hat nor the
;;;       names of its contributors may be used to endorse or promote products
;;;       derived from this software without specific prior written permission.
;;;
;;; THIS SOFTWARE IS PROVIDED BY THE COPYRIGHT HOLDERS AND CONTRIBUTORS "AS IS" AND
;;; ANY EXPRESS OR IMPLIED WARRANTIES, INCLUDING, BUT NOT LIMITED TO, THE IMPLIED
;;; WARRANTIES OF MERCHANTABILITY AND FITNESS FOR A PARTICULAR PURPOSE ARE
;;; DISCLAIMED. IN NO EVENT SHALL Pavel Tisnovsky BE LIABLE FOR ANY
;;; DIRECT, INDIRECT, INCIDENTAL, SPECIAL, EXEMPLARY, OR CONSEQUENTIAL DAMAGES
;;; (INCLUDING, BUT NOT LIMITED TO, PROCUREMENT OF SUBSTITUTE GOODS OR SERVICES;
;;; LOSS OF USE, DATA, OR PROFITS; OR BUSINESS INTERRUPTION) HOWEVER CAUSED AND
;;; ON ANY THEORY OF LIABILITY, WHETHER IN CONTRACT, STRICT LIABILITY, OR TORT
;;; (INCLUDING NEGLIGENCE OR OTHERWISE) ARISING IN ANY WAY OUT OF THE USE OF THIS
;;; SOFTWARE, EVEN IF ADVISED OF THE POSSIBILITY OF SUCH DAMAGE.

;;; Module with various drawing functions.

(ns cljgfx.draw)

(import 'java.awt.Color)

(require '[cljgfx.config :as config])
(require '[cljgfx.calc   :as calc])

(defn clear-canvas
  "Clear while canvas with the white color."
  [graphics width height]
  (let [rectangle-width (- width 0)
        rectangle-height (- height 0)]
    (.setColor graphics      Color/black)
    (.setBackground graphics Color/white)
    (.clearRect graphics 0 0 rectangle-width rectangle-height)))

(defn draw-arc
  "Draw arc onto the canvas."
  [graphics width height arc-width arc-height start-angle arc-angle]
  (let [center-x (/ width 2)
        center-y (/ height 2)
        radius   (/ arc-height 2)
        x1 (+ center-x (* radius (Math/cos (calc/deg->rad start-angle))))
        y1 (- center-y (* radius (Math/sin (calc/deg->rad start-angle))))]
    (.setColor graphics Color/black)
    (.drawArc graphics
              config/arc-border
              config/arc-border
              arc-width
              arc-height
              start-angle
              (inc arc-angle))
    (.drawLine graphics center-x center-y x1 y1)))

(defn draw-label
  "Draw label (text) onto the canvas."
  [graphics width height arc-width arc-height start-angle arc-angle percentage]
  (let [center-x (/ width 2)
        center-y (/ height 2)
        radius   (+ 20 (/ arc-height 2))
        angle    (+ start-angle (/ arc-angle 2.0))
        x1 (+ center-x (* radius (Math/cos (calc/deg->rad angle))))
        y1 (- center-y (* radius (Math/sin (calc/deg->rad angle))))]
    (.drawString graphics (str percentage "%") (int (- x1 8)) (int y1))))

(defn fill-arc
  "Draw filled arc onto the canvas."
  [graphics arc-width arc-height arc-color start-angle arc-angle]
  (.setColor graphics arc-color)
  (.fillArc graphics
            config/arc-border
            config/arc-border
            arc-width
            arc-height
            start-angle
            (inc arc-angle)))

