package org.rplsd.condalang.data

/**
  * Created by Luqman on 11/27/2015.
  */
case class kuantitas(jumlah:Double, satuan:String)

object kuantitas {
  def gram() = "gr"
  def milliliter() = "ml"
  def buah() = "buah"
}
