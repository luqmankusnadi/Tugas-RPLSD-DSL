package org.rplsd.condalang.data

import com.mongodb.casbah.Implicits._
import com.mongodb.casbah.commons.MongoDBObject

/**
  * Created by Luqman on 11/27/2015.
  */
case class Recipe(nama_resep:String, bahan: Option[Map[String,Kuantitas]], harga:Option[Double]) {
  def dengan_bahan (bahan2: Map[String,Kuantitas]) = Recipe(nama_resep, Some(bahan2), harga)
  def dengan_harga (harga2: Double) = Recipe(nama_resep, bahan, Some(harga2))
}

object Recipe {
  def nama_resep = "nama_resep"
  def bahan = "bahan"
  def harga = "harga"
}