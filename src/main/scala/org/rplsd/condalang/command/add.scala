package org.rplsd.condalang.command

import org.rplsd.condalang.data.{kuantitas, recipe}

/**
  * Created by gLuqman on 11/27/.
  */
object add {
  def recipe(r:recipe) = add_recipe(r)
}

case class add_recipe(r:recipe)
