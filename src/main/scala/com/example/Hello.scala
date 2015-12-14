package com.example

import org.rplsd.condalang.command.{add}
import org.rplsd.condalang.data.{RecipeJumlah, Recipe}

/**
 * This is actually just a small wrapper around the generic launcher
 * class akka.Main, which expects only one argument: the class name of
 * the application?s main actor. This main method will then create the
 * infrastructure needed for running the actors, start the given main
 * actor and arrange for the whole application to shut down once the main
 * actor terminates.
 *
 * Thus you could also run the application with a
 * command similar to the following:
 * java -classpath  akka.Main com.example.actors.HelloWorldActor
 *
 * @author alias
 */
object HelloSimpleMain {

  def main(args: Array[String]): Unit = {
    import org.rplsd.condalang.util.CondaImplicit._
    import org.rplsd.condalang.command._

    dBConnection host = "localhost:12345"

    add bahan_baku ("tirai" sebanyak 10.buah)
    add bahan_baku ("sultan" sebanyak 10.potong)
    add bahan_baku ("kambing" sebanyak 10.potong)

    reduce bahan_baku ("kambing" sebanyak 1.potong)

    change bahan_baku("kambing" sebanyak 100.potong)

    println(read all_bahan_baku)
    println(read all_recipe)

    add recipe (
      "surai kambing" dengan_bahan Map(
        "tirai" -> 1.buah,
        "sultan" -> 1.potong,
        "kambing" -> 1.potong
      ) dengan_harga 10000
      )

    save transaksi(27 november 1994) dengan_transaksi Seq("surai kambing" sebanyak 9)

    println(lihat.transaksi_resep_rentang dari(20 november 1994) sampai(30 november 1994))
    println(lihat.bahan_rentang dari(20 november 1994) sampai(30 november 1994))
  }

}
