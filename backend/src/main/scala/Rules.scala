object Rules{

  def passThrough(input: String)={
      input
  }

  def median(values: List[Int]): Int = {
    val index = values.size / 2
    values.sorted.apply(index)
  }

  def product(method: String){
    method match {
      case "PAY" =>
      case "INV" =>
    }
  }

  // Offer ING mobile App for users whom browse MING by their mobile
  def offerINGApp(message: String): String ={
    if (message != "Diverse PC&Laptop"){
      if (message.startsWith("ip")) "Apple" else "Android"
      }
    else "None"
    }
}