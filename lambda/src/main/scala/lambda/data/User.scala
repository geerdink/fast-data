package lambda.data

case class User(userName: String, topProductCategory: String)

object UserHelper {
  def createUser(input: String): User = {
    val parts = input.split(',')
    new User(parts(0).split('=')(1), parts(1).split('=')(1))
  }

  def getCategory(vectorScore: Double): String = {
    if (vectorScore < 0.1) "DVD"
    else if (vectorScore < 0.2) "Sneakers"
    else if (vectorScore < 0.3) "Car"
    else if (vectorScore < 0.4) "Phone"
    else if (vectorScore < 0.5) "Game"
    else if (vectorScore < 0.6) "Book"
    else if (vectorScore < 0.7) "Phone"
    else "Unknown"
  }
}
