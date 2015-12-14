/**
  * Created by Luqman on 12/14/2015.
  */
import org.rplsd.condalang.command.{add}
import org.rplsd.condalang.data.{RecipeJumlah, Recipe}
import org.rplsd.condalang.util.CondaImplicit._
import org.rplsd.condalang.command._

object test {
  def main(args: Array[String]): Unit = {
    dBConnection host = "localhost:12345"
    test_add()
    test_change()
    test_reduce()
    test_save()
    test_lihat()
    test_read()
    test_delete()
  }

  def test_add(): Unit ={
    add bahan_baku ("ayam" sebanyak 10.potong)
    println("Uji add bahan_baku berhasil")
    add recipe (
      "ayam goreng" dengan_bahan Map(
        "ayam" -> 1.potong
      ) dengan_harga 10000
      )
    println("Uji add recipe berhasil")
  }

  def test_change(): Unit ={
    change bahan_baku ("ayam" sebanyak 5.potong)
    println("Uji change bahan_baku berhasil")
    change recipe (
      "ayam goreng" dengan_bahan Map(
        "ayam" -> 2.potong
      ) dengan_harga 12000
      )
    println("Uji change recipe berhasil")
  }

  def test_delete(): Unit ={
    delete bahan_baku ("kambing")
    println("Uji delete bahan_baku berhasil")
    delete recipe ("ayam goreng")
    println("Uji delete recipe berhasil")
  }

  def test_lihat(): Unit ={
    println(lihat.transaksi_resep_rentang dari(20 november 1994) sampai(15 desember  2015))
    println("Uji lihat transaksi_resep_rentang berhasil")
    println(lihat.bahan_rentang dari(20 november 1994) sampai(15 desember  2015))
    println("Uji lihat bahan_rentang berhasil")
  }

  def test_read(): Unit ={
    println(read all_bahan_baku)
    println("Uji read all_bahan_baku berhasil")
    println(read all_recipe)
    println("Uji read all_recipe berhasil")
    println(read bahan_baku("ayam"))
    println("Uji read bahan_baku berhasil")
    println(read recipe("ayam goreng"))
    println("Uji read recipe berhasil")
  }

  def test_reduce(): Unit ={
    reduce bahan_baku("ayam" sebanyak 1.potong)
    println("Uji reduce bahan_baku berhasil")
  }

  def test_save(): Unit ={
    save transaksi(14 desember 2015) dengan_transaksi Seq("ayam goreng" sebanyak 9)
    println("Uji save_transaksi berhasil")
  }
}
