package sparking.data

case class Transaction(user: String,amount: Double, balance: Double) extends CustomerEvent
