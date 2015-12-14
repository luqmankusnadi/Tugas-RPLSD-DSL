package org.rplsd.condalang.util

import com.mongodb.casbah.{MongoClient, MongoCollection}
import org.rplsd.condalang.data._
import scala.reflect.ClassTag

/**
  * Created by Luqman on 11/27/2015.
  */
object CondaImplicit {

  implicit lazy val dBConnection = new DBConnection()

  implicit class DoubleImplicits (l: Double) {
    def gram = Kuantitas(l,Kuantitas.gram)
    def kilogram = Kuantitas(l*1000,Kuantitas.gram)
    def mililiter = Kuantitas(l,Kuantitas.milliliter)
    def liter = Kuantitas(l*1000,Kuantitas.milliliter)
    def buah = Kuantitas(l,Kuantitas.buah)
    def potong = Kuantitas(l,Kuantitas.potong)
  }
  implicit def int2Double (l:Int) = DoubleImplicits(l.toDouble)
  implicit def int2Waktu (l:Int) = Waktu(l,0,0)

  implicit class StringImplicits (s: String) {
    def dengan_bahan (bahan2: Map[String,Kuantitas]) = Recipe(s, Some(bahan2), None)
    def dengan_harga (harga2: Double) = Recipe(s, None, Some(harga2))
    def sebanyak(jumlah: Int) = RecipeJumlah(s,jumlah)
    def sebanyak(kuantitas: Kuantitas) = BahanBaku(s,kuantitas)
  }
}

class DBConnection {
  var host = "localhost:27017"
  implicit lazy val mongoClient = MongoClient(host)
  implicit lazy val db = mongoClient("conda")
  implicit lazy val recipeColl = db("resep")
  implicit lazy val bahanBakuColl = db("bahan_baku")
  implicit lazy val transactionColl = db("transaction")
  implicit lazy val logBahanBakuColl = db("log_bahan_baku")
}