package org.rplsd.condalang.command

import com.mongodb.casbah.commons.MongoDBObject
import org.rplsd.condalang.data
import com.mongodb.casbah.commons.conversions.scala._
import org.rplsd.condalang.data._
import org.rplsd.condalang.util.DBConnection

/**
  * Created by Luqman on 11/28/2015.
  */
object save {
  def transaksi (waktu:Waktu) = saveTransaction(waktu)
}

case class saveTransaction (waktu:Waktu) {
  def dengan_transaksi(list_resep_jumlah : Seq[RecipeJumlah])(implicit dBConn: DBConnection) = {
    list_resep_jumlah.foreach( recipeJumlah => {
      val result = read recipe recipeJumlah.nama_resep
      if (result.isDefined) {
        val recipe = result.get
        recipe.bahan.map(_.map( t => {
          val b = BahanBaku(t._1, Kuantitas(t._2.jumlah * recipeJumlah.jumlah,t._2.satuan))
          reduce bahan_baku (b)
          val obj = MongoDBObject(
            BahanBaku.nama -> b.nama,
            BahanBaku.kuantitas -> b.kuantitas.jumlah,
            Waktu.waktu -> waktu.toJDKDate)
          dBConn.logBahanBakuColl.insert(obj)
        }))
        val obj = MongoDBObject (
          Waktu.waktu -> waktu.toJDKDate,
          RecipeJumlah.nama_resep -> recipe.nama_resep,
          RecipeJumlah.jumlah -> recipeJumlah.jumlah
        )
        dBConn.transactionColl.insert(obj)
      }
      else println(s"Resep ${recipeJumlah.nama_resep} tidak ada di database")
    })
  }
}
