package org.rplsd.condalang.data

import java.util.Date

/**
  * Created by Luqman on 11/28/2015.
  */
case class Waktu (hari:Int, bulan: Int, tahun: Int){
  def januari(tahun:Int) = Waktu(hari, 1, tahun)
  def febuari(tahun:Int) = Waktu(hari, 2, tahun)
  def maret(tahun:Int) = Waktu(hari, 3, tahun)
  def april(tahun:Int) = Waktu(hari, 4, tahun)
  def mei(tahun:Int) = Waktu(hari, 5, tahun)
  def juni(tahun:Int) = Waktu(hari, 6, tahun)
  def juli(tahun:Int) = Waktu(hari, 7, tahun)
  def agustus(tahun:Int) = Waktu(hari, 8, tahun)
  def september(tahun:Int) = Waktu(hari, 9, tahun)
  def oktober(tahun:Int) = Waktu(hari, 10, tahun)
  def november(tahun:Int) = Waktu(hari, 11, tahun)
  def desember(tahun:Int) = Waktu(hari, 12, tahun)
  def toJDKDate = {
    var date = new Date()
    date.setYear(tahun - 1900)
    date.setDate(hari+1)
    date.setMonth(bulan - 1)
    date
  }
}

object Waktu {
  def waktu = "waktu"
}