package sparking.data

case class Transaction(user: String,amount: Double, balance: Double) extends CustomerEvent{
  override def text = s"user=${user},amount=${amount},balance=${balance}"
}
