package org.rplsd.condalang.command

import com.mongodb.casbah.commons.MongoDBObject
import org.rplsd.condalang.data.{BahanBaku, Recipe}
import org.rplsd.condalang.util.DBConnection

/**
  * Created by Luqman on 11/27/2015.
  */
object delete {
  def recipe(s:String)(implicit dBConn: DBConnection) = {
    val check1 = MongoDBObject(Recipe.nama_resep -> s)
    if (dBConn.recipeColl.findOne(check1).isEmpty)
      println("Resep tidak ada di database")
    else dBConn.recipeColl.remove(check1)
  }

  def bahan_baku(s:String)(implicit dBConn: DBConnection) = {
    val check1 = MongoDBObject(BahanBaku.nama -> s)
    dBConn.bahanBakuColl.remove(check1)
  }
}
