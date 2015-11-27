package org.rplsd.condalang.command

import com.mongodb.casbah.commons.MongoDBObject
import org.rplsd.condalang.data.{BahanBaku, Kuantitas}
import org.rplsd.condalang.util.DBConnection

/**
  * Created by Luqman on 11/28/2015.
  */
object reduce {
  def bahan_baku (b:BahanBaku)(implicit dBConn: DBConnection) = {
    val add_kuantitas = Kuantitas(-b.kuantitas.jumlah,b.kuantitas.satuan)
    add.bahan_baku(BahanBaku(b.nama,add_kuantitas))
  }
}
