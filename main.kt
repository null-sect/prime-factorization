import kotlin.math.*

fun gcd(a: Int, b: Int): Int {
  var x = a
  var y = b
  while (y != 0) {
    val t = y
    y = x % y
    x = t
  }
  return x
}

fun main() {
  println("Enter the number to factorize: ")
  val num = readln().toLong()

  // convert to int
  if (num > Int.MAX_VALUE) {
    println("The number is too large to factorize.")
    return
  }

  //trial division
  println(message = "試し割り法: ")
  val startTri = System.currentTimeMillis()
  val factorsTri = factorizeTrialDivision(num.toInt())
  val endTri = System.currentTimeMillis()
  println(message = "Factors: $factorsTri")
  println(message = "Time: ${endTri - startTri} ms")

  // rho
  println(message = "ロー法: ")
  val startRho = System.currentTimeMillis()
  val factorsRho = factorizeRho(num.toInt())
  val endRho = System.currentTimeMillis()
  println(message = "Factors: $factorsRho")
  println(message = "Time: ${endRho - startRho} ms")

}

fun factorizeTrialDivision(n: Int): List<Int> {
  val factors = mutableListOf<Int>()
  var num = n
  var factor = 2
  while (num > 1) {
    if (num % factor == 0) {
      factors.add(factor)
      num /= factor
    } else {
      factor++
    }
  }
  return factors
}

fun factorizeRho(n: Int): List<Int> {
  if (n == 1) return listOf(1)
  if (n % 2 == 0) return listOf(2) + factorizeRho(n / 2)
  if (n % 3 == 0) return listOf(3) + factorizeRho(n / 3)

  val f = { x: Int -> (x.toLong() * x + 1).toInt() % n }
  var x = 2
  var y = 2
  var d = 1
  while (d == 1) {
    x = f(x)
    y = f(f(y))
    d = gcd(abs(x - y), n)
  }
  if (d == n) return listOf(n)
  return factorizeRho(d) + factorizeRho(n / d)
}

