;;; Very simple generator for various graph types.
;;;
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

;;; Core module.
;;; ATM only pie-chart are supported.

(ns cljgfx.core)

(import 'java.awt.Color)

(require '[cljgfx.config          :as config])
(require '[cljgfx.bitmap          :as bitmap])
(require '[cljgfx.calc            :as calc])
(require '[cljgfx.draw            :as draw])
(require '[cljgfx.rendering-hints :as rendering-hints])

(defn draw-filled-arcs
  "Draw the first part of pie-chart: filled arcs."
  [graphics arc-width arc-height results colors start-angles arc-angles]
  (doseq [i (range (count results))]
    (draw/fill-arc graphics
                   arc-width
                   arc-height
                   (nth colors i)
                   (nth start-angles i)
                   (nth arc-angles i))))

(defn draw-arcs
  "Draw the second part of pie-chart: hollow arcs."
  [graphics width height arc-width arc-height results start-angles arc-angles]
  (.setStroke graphics (new java.awt.BasicStroke 2))
  (doseq [i (range (count results))]
    (draw/draw-arc graphics
                   width
                   height
                   arc-width
                   arc-height
                   (nth start-angles i)
                   (nth arc-angles i))))

(defn draw-labels
  "Draw the third part of pie-chart: labels (texts)."
  [graphics width height arc-width arc-height results start-angles arc-angles]
  (doseq [i (range (count results))]
    (draw/draw-label graphics
                     width
                     height
                     arc-width
                     arc-height
                     (nth start-angles i)
                     (nth arc-angles i)
                     (nth results i))))

(defn draw-pie-chart
  "Draw whole pie-chart onto the selected canvas."
  [graphics width height results colors]
  (let [arc-width  (- width  (* config/arc-border 2))
        arc-height (- height (* config/arc-border 2))
        total      (reduce + results)
        arc-angles (map calc/calc-arc-angle results (repeat (count results) total))
        start-angles (reductions + 0 arc-angles)]
    (draw-filled-arcs graphics
                      arc-width
                      arc-height
                      results
                      colors
                      start-angles
                      arc-angles)
    (draw-arcs graphics
               width
               height
               arc-width
               arc-height
               results
               start-angles
               arc-angles)
    (draw-labels graphics
                 width
                 height
                 arc-width
                 arc-height
                 results
                 start-angles
                 arc-angles)))

(defn generate-pie-chart
  "Generate new bitmap with pie-chart."
  [filename results colors]
  (let [width      config/default-graph-width
        height     config/default-graph-height
        bitmap     (bitmap/create-bitmap width height)
        graphics   (bitmap/get-graphics-2d bitmap)]
    (draw/clear-canvas graphics width height)
    (rendering-hints/set-rendering-hints graphics)
    (draw-pie-chart graphics width height results colors)
    (bitmap/write-bitmap filename bitmap)))

(defn -main
  "Entry function to the cljgfx tool."
  []
  (println "Generating pie chart")
  (generate-pie-chart "test1.png"
                      [10 20 30 40]
                      [Color/green Color/yellow Color/red Color/blue])
  (println "Done"))

;(-main)

