/**
 * 객체 식
 *
 * 코틀린은 명시적인 선언 없이 객체를 바로 생성할 수 있는 특별한 식을 제공한다.
 */
fun main() {
    var sa = 1

    val o = object { // 익명 타입의 객체로 추론 (anonymous object)
//        val x = readLine()!!.toInt()
//        val y = readLine()!!.toInt()

        /**
         * 지역 함수나 클래스와 마찬가지로 객체 식도 자신을 둘러싼 코드 영역의 변수를 포획할 수 있다.
         */
        fun change() {
            sa = 2
        }

    }

    /**
     * 지연 초기화 되는 객체 선언과 달리 객체 식이 만들어내는 객체는 객체 인스턴스가 생성된 직후 초기화된다.
     */
    var x = 1

    val obj = object {
        var a = x++
    }

    /**
     * 위 obj 객체 식은 인스턴스가 생성된 시점에 초기화 되므로 obj.a를 호출하기 전 x를 호출했음에도
     * x가 2가 되어있다.
     */
    println(x)
    println(obj.a)


    /**
     * 익명 객체 타입은 지역 선언이나 비공개 선언에만 전달될 수 있다.
     * 아래의 함수를 최상위 함수로 정의하면 객체 멤버에 접근 오류가 발생한다.
     * 이 함수가 최상위 프로퍼티가 되면 익명 객체 타입이 아닌, 객체 식에 지정된 상위 타입이 된다.(Any)
     */
    fun midPoint(xRange: IntRange, yRange: IntRange) = object {
        val x = (xRange.first + xRange.last) /2
        val y = (yRange.first + yRange.last) /2
    }

    val midPoint = midPoint(1..5, 2..6)
    val midPoint2 = midPoint(1..5, 2..6)

    println(midPoint == midPoint2)
    println("${midPoint.x}, ${midPoint.y}")
}