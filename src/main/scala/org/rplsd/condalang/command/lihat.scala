package org.rplsd.condalang.command

import com.mongodb.casbah.commons.MongoDBObject
import org.rplsd.condalang.data
import org.rplsd.condalang.data._
import com.mongodb.casbah.Imports._
import org.rplsd.condalang.util.DBConnection

/**
  * Created by Luqman on 11/28/2015.
  */
object lihat {
  def bahan_rentang = queryBahanRentang(None)
  def transaksi_resep_rentang = queryResepRentang(None)

  case class queryBahanRentang(waktu1:Option[Waktu]) {
    def dari (w:Waktu) = queryBahanRentang(Some(w))
    def sampai (w:Waktu)(implicit dBConn: DBConnection) = {
      if (waktu1.isEmpty) println("Waktu awal belum di set")
      else {
        val c = Waktu.waktu $gte waktu1.get.toJDKDate $lte w.toJDKDate
        val listBahanAndJumlah = dBConn.logBahanBakuColl.find(c).toIterator.map(obj =>
          (obj.as[String](BahanBaku.nama),obj.as[Double](BahanBaku.kuantitas))).toSeq
        val bahanAndJumlah = listBahanAndJumlah.groupBy(_._1).mapValues(_.map(_._2).sum)
        val penggunaanBahanBaku = bahanAndJumlah.flatMap {case (nama,jumlah) => {
          val result = read bahan_baku nama
          if (result.isDefined) Seq(BahanBaku(nama,Kuantitas(jumlah,result.get.kuantitas.satuan)))
          else Seq(BahanBaku(nama,Kuantitas(jumlah,"unknown")))
        }}
        penggunaanBahanBaku
      }
    }
  }

  case class queryResepRentang(waktu1:Option[Waktu]) {
    def dari (w:Waktu) = queryResepRentang(Some(w))
    def sampai (w:Waktu)(implicit dBConn: DBConnection) = {
      if (waktu1.isEmpty) println("Waktu awal belum di set")
      else {
        val c = Waktu.waktu $gte waktu1.get.toJDKDate $lte w.toJDKDate
        val listResepAndJumlah = dBConn.transactionColl.find(c).toIterator.map(obj =>
          (obj.as[String](RecipeJumlah.nama_resep),obj.as[Int](RecipeJumlah.jumlah))).toSeq
        val resepAndJumlah = listResepAndJumlah.groupBy(_._1).mapValues(_.map(_._2).sum)
        val recipeJumlahs = resepAndJumlah.map {case (nama,jumlah) => {
          RecipeJumlah(nama,jumlah)
        }}
        recipeJumlahs
      }
    }
  }
}