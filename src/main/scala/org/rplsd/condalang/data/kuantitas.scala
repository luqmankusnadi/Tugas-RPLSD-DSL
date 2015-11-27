package org.rplsd.condalang.data

/**
  * Created by Luqman on 11/27/2015.
  */
case class Kuantitas(jumlah:Double, satuan:String)

object Kuantitas {
  def gram() = "gr"
  def milliliter() = "ml"
  def buah() = "buah"
  def potong() = "potong"
}
