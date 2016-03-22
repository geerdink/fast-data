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


}