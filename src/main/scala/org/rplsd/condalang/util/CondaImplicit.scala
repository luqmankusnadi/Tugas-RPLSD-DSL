package org.rplsd.condalang.util

import org.rplsd.condalang.data.{recipe, kuantitas}
import scala.reflect.ClassTag

/**
  * Created by Luqman on 11/27/2015.
  */
object CondaImplicit {
  implicit class DoubleImplicits (l: Double) {
    def gram = kuantitas(l,kuantitas.gram)
    def kilogram = kuantitas(l*1000,kuantitas.gram)
    def mililiter = kuantitas(l,kuantitas.milliliter)
    def liter = kuantitas(l*1000,kuantitas.milliliter)
    def buah = kuantitas(l,kuantitas.buah)
  }
  implicit def int2Double (l:Int) = DoubleImplicits(l.toDouble)
  implicit def string2Recipe (s:String) = recipe(s,Map(),0)
  implicit def bahanAndKuantitas2Map (t:Tuple2[String,kuantitas]) = Map(t)

  implicit class MapImplicits (l:Map[String,kuantitas]) {
    def +(t:Tuple2[String,kuantitas]) = l.updated(t._1,t._2)
  }
}
