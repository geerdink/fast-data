/**
  * Ger van Rossum (2016).
  */
package sparking.rules

import sparking.data.Transaction
import sparking.results.Category

object Rules {

  def passThrough(input: String): String = {
    input
  }

  def defineCategory(transaction: Transaction): Category = {
    val balance = transaction.balance - transaction.amount
    val score = transaction.balance.abs - balance.abs
    if (balance <= 0.0) Category(transaction.user, "Loan", - score)
    else Category(transaction.user, "Saving", score)
  }

  def parse(transaction: String): Transaction ={
    val parsedString = transaction.split(",")
    Transaction(user= parsedString(0).split("=")(1),
      amount= parsedString(1).split("=")(1).toDouble,
      balance= parsedString(2).split("=")(1).toDouble)
  }

  def convertDefineCategory(transaction: String): String = {
    defineCategory(parse(transaction)).text
  }

  def median(values: List[Int]): Int = {
    val index = values.size / 2
    values.sorted.apply(index)
  }

  def product(method: String) {
    method match {
      case "PAY" =>
      case "INV" =>
    }
  }

  // Offer ING mobile App for users whom browse MING by their mobile
  def offerINGApp(message: String): String = {
    if (message != "Diverse PC&Laptop") {
      if (message.startsWith("ip")) "Apple" else "Android"
    }
    else "None"
  }



}
