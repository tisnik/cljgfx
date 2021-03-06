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

;;; Module with functions for bitmap handling.

(ns cljgfx.bitmap)

(import 'java.io.File)
(import 'java.awt.image.BufferedImage)
(import 'javax.imageio.ImageIO)

(defn create-bitmap
  "Create new bitmap with given dimension."
  [width height]
  (new BufferedImage width height BufferedImage/TYPE_4BYTE_ABGR))

(defn write-bitmap
  "Write bitmap into the PNG file."
  [filename bitmap]
  (ImageIO/write bitmap "png" (new File filename)))

(defn get-graphics-2d
  "Return Graphics or Graphics2d object for the given bitmap."
  [bitmap]
  (.getGraphics bitmap))

