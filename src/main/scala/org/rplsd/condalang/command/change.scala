package org.rplsd.condalang.command

import com.mongodb.casbah.commons.MongoDBObject
import org.rplsd.condalang.data.{Kuantitas, BahanBaku, Recipe}
import org.rplsd.condalang.util.DBConnection
import com.mongodb.casbah.Imports._

/**
  * Created by Luqman on 11/27/2015.
  */
object change {
  def recipe(r:Recipe)(implicit dBConn: DBConnection)  = {
    val check1 = MongoDBObject(Recipe.nama_resep -> r.nama_resep)
    if (dBConn.recipeColl.findOne(check1).isEmpty)
      println("Resep tidak ada di database")
    else {
      val allBahanExistOrBahanNotUpdated = r.bahan.map(_.forall {
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
      }).getOrElse(true)

      if (allBahanExistOrBahanNotUpdated) {
        val query = MongoDBObject(Recipe.nama_resep -> r.nama_resep)
        var update = $set()
        r.bahan.foreach( map => update.put(Recipe.bahan,map))
        r.harga.foreach( harga => update.put(Recipe.harga,harga))
        dBConn.recipeColl.update(query,update)
      }
    }
  }
  def bahan_baku (b:BahanBaku)(implicit dBConn: DBConnection) =   {
    val query = MongoDBObject(BahanBaku.nama -> b.nama)
    val result = read bahan_baku b.nama

    if (result.isDefined) {
      val update = $set(BahanBaku.kuantitas -> b.kuantitas)
      dBConn.bahanBakuColl.update(query,update)
    }
    else {
      println(s"Bahan baku ${b.nama} tidak ada di database")
    }
  }
}
