package org.rplsd.condalang.command

import org.rplsd.condalang.util.DBConnection
import com.mongodb.casbah.MongoCollection
import com.mongodb.casbah.commons.MongoDBObject
import org.rplsd.condalang.data.{Kuantitas, BahanBaku, Recipe}
import com.mongodb.casbah.Imports._

/**
  * Created by gLuqman on 11/27/.
  */
object add {
  def recipe (r:Recipe)(implicit dBConn: DBConnection)  = {
    if (r.bahan.isEmpty)
      println("Bahan resep harus ada")
    else if (r.harga.isEmpty)
      println("Harga resep harus ada")
    else {
      val check1 = MongoDBObject(Recipe.nama_resep -> r.nama_resep)
      if (dBConn.recipeColl.findOne(check1).isDefined)
        println("Resep sudah ada di database")
      else {
        val allBahanExist = r.bahan.map(_.forall {
          case (bahan,kuantitas) => {
            val result = read bahan_baku bahan
            val isExist = result.isDefined
            if (!isExist) println(s"Bahan baku $bahan tidak ada")
            if (isExist) {
              val bahan_baku = result.get
              if (bahan_baku.kuantitas.satuan equals kuantitas.satuan) true
              else {
                println(s"Satuan kuantitas yang ditambah (${kuantitas.satuan}) berbeda dengan kuantitas di database (${bahan_baku.kuantitas.satuan}")
                false
              }
            }
            else false
          }
        }).get

        if (allBahanExist) {
          val query = MongoDBObject(
            Recipe.nama_resep -> r.nama_resep,
            Recipe.bahan -> r.bahan,
            Recipe.harga -> r.harga
          )
          dBConn.recipeColl.insert(query)
        }
      }
    }
  }

  def bahan_baku (b:BahanBaku)(implicit dBConn: DBConnection):Unit =   {
    val query = MongoDBObject(BahanBaku.nama -> b.nama)
    val result = read bahan_baku b.nama
    val prev_kuantitas = result.map( _.kuantitas)

    if (prev_kuantitas.isDefined)  {
      if (prev_kuantitas.get.satuan.contentEquals(b.kuantitas.satuan)) {
        val cur_kuantitas = Kuantitas(b.kuantitas.jumlah + prev_kuantitas.get.jumlah, b.kuantitas.satuan)
        val update = $set(BahanBaku.kuantitas -> cur_kuantitas)
        dBConn.bahanBakuColl.update(query, update)
      }
      else {
        println(s"Satuan kuantitas yang ditambah (${b.kuantitas.satuan}) berbeda dengan kuantitas di database (${prev_kuantitas.get.satuan}")
      }
    }
    else {
      val update = MongoDBObject(BahanBaku.nama -> b.nama, BahanBaku.kuantitas -> b.kuantitas)
      dBConn.bahanBakuColl.insert(update)
    }
  }

  def bahan_baku (s:String) (kuantitas: Kuantitas)(implicit dBConn: DBConnection):Unit = {
    bahan_baku(BahanBaku(s,kuantitas))
  }
}
