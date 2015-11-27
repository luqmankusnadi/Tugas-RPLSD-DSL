package org.rplsd.condalang.command

import com.mongodb.casbah.commons.MongoDBObject
import org.rplsd.condalang.data.{BahanBaku, Kuantitas, Recipe}
import org.rplsd.condalang.util.DBConnection
import com.mongodb.casbah.Imports._

/**
  * Created by Luqman on 11/27/2015.
  */
object read {
  def recipe(s:String)(implicit dBConn: DBConnection): Option[Recipe] = {
    val check1 = MongoDBObject(Recipe.nama_resep -> s)
    val result = dBConn.recipeColl.findOne(check1)
    if (result.isEmpty) {
      println("Resep tidak ada di database")
      None
    }
    else {
      val obj = result.get
      val bahan = obj.as[MongoDBObject](Recipe.bahan)
      val keys = bahan.keys
      val bahans = keys.map (key => key -> (bahan.as[BasicDBList](key))).toMap
        .mapValues(l => (l.as[Double](0),l.as[String](1))).mapValues(t => Kuantitas(t._1,t._2))
      val r = Recipe(obj.as[String](Recipe.nama_resep),
        Some(bahans),
        Some(obj.as[Double](Recipe.harga)))
      Some(r)
    }
  }

  def bahan_baku (b:String)(implicit dBConn: DBConnection): Option[BahanBaku] =   {
    val query = MongoDBObject(BahanBaku.nama -> b)
    val result = dBConn.bahanBakuColl.findOne(query)
    result.map( obj => {
      val basicList = obj.as[BasicDBList](BahanBaku.kuantitas)
      val kuantitas = Kuantitas(basicList.as[Double](0),basicList.as[String](1))
      BahanBaku(b,kuantitas)
    })
  }

  def all_bahan_baku (implicit dBConn: DBConnection): List[BahanBaku] =   {
    dBConn.bahanBakuColl.find().toIterator.map( obj => {
      val basicList = obj.as[BasicDBList](BahanBaku.kuantitas)
      val kuantitas = Kuantitas(basicList.as[Double](0),basicList.as[String](1))
      BahanBaku(obj.as[String](BahanBaku.nama),kuantitas)
    }).toList
  }

  def all_recipe (implicit dBConn: DBConnection): List[Recipe] = {
    dBConn.recipeColl.find().toIterator.map ( obj => {
      val bahan = obj.as[MongoDBObject](Recipe.bahan)
      val keys = bahan.keys
      val bahans = keys.map(key => key -> (bahan.as[BasicDBList](key))).toMap
        .mapValues(l => (l.as[Double](0), l.as[String](1))).mapValues(t => Kuantitas(t._1, t._2))
      val r = Recipe(obj.as[String](Recipe.nama_resep),
        Some(bahans),
        Some(obj.as[Double](Recipe.harga)))
      r
    }).toList
  }
}
