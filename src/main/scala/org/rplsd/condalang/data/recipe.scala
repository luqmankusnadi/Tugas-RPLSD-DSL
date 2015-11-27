package org.rplsd.condalang.data

/**
  * Created by Luqman on 11/27/2015.
  */
case class recipe (nama_resep:String, bahan: Map[String,kuantitas], harga:Double) {
  def dengan_bahan (bahan2: Map[String,kuantitas]) = recipe(nama_resep, bahan2, harga)
  def dengan_harga (harga2: Double) = recipe(nama_resep, bahan, harga2)
}
