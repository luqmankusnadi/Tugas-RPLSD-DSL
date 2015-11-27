package com.example

import org.rplsd.condalang.command.{add, add_recipe}
import org.rplsd.condalang.data.recipe

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
    add recipe (
      "ayam goreng" dengan_bahan (
        "ayam" -> 1.buah +
        "gigi" -> 1.buah +
        "hati" -> 1.buah
      ) dengan_harga 10000
      )


  }

}
