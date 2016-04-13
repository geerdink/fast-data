package lambda.data

case class ProductScore(userName: String, productCategory: String, productName: String, score: Integer)

object ProductScoreHelper {
  def createProductScore(input: String): ProductScore = {
    //username=Piet,category=phones,productname=iphone,score=2

    val parts = input.split(',')
    new ProductScore(parts(0).split('=')(1), parts(1).split('=')(1), parts(2).split('=')(1), parts(3).split('=')(1).toInt)
  }
}
