/**
 * 코틀린의 객체
 *
 * 코틀린은 인스턴스가 오직 하나만 존재하게 보장하는 싱글턴 패턴을 내장하고있다.
 * 클래스와 비슷한 방법으로 싱글턴을 선언한다.
 *
 * - 코틀린의 객체 정의(object)는 thread safe 하다.
 *   싱글턴에 접근하더라도 오직 한 인스턴스만 공유되고 초기화 코드도 단 한 번만 실행되도록 보장한다.
 * - 초기화는 싱글턴 클래스가 실제 로딩되는 시점 까지 지연되었다가 객체 인스턴스에 접근할 때 초기화가 이루어진다.
 * - 객체에는 주 생성자와 부 생성자가 없다. 객체 인스턴스는 선언 시점에 암시적으로 만들어지기 때문에 생성자 호출이 의미가 없다.
 * - 객체 내부에는 내부 클래스를 선언할 수 없다. (내부 클래스의 인스턴스는 항상 바깥 쪽 클래스의 인스턴스와 상호작용하는데 객체는 싱글턴이므로 inner 변경자가 불필요 해진다.)
 * - 객체는 다른 객체 혹은 클래스에 내포될 수 있다. 하지만 클래스의 내부 함수, 내부 클래스, 지역 클래스 등에는 내포될 수 없다. (이런 정의 들은 외부 문맥에 의존하므로 싱글턴이 될 수 없기 때문이다.)
 */

// 이와 같은 객체 선언은 클래스를 선언함과 동시에 인스턴스를 선언하는 것과 마찬가지이다.
object Application {
    val name = "My Application"
    override fun toString(): String = name

    fun exit() {}
}

/**
 * object로 선언한 객체를 타입으로 사용해도 무관하다. 인스턴스를 통해 타입 추론이 가능하기 때문이다.
 */
fun describe(app: Application) = app.name

/**
 * 동반 객체 (companion)
 *
 * 내포된 클래스와 마찬가지로 내포 객체도 인스턴스가 생기면 자신을 둘러싼 클래스의 비공개 멤버에 접근할 수 있다.
 * 이런 특성은 팩토리 디자인 패턴을 쉽게 구현할 수 있도록 도와준다.
 * 클래스의 생성자를 사용하면 검사 결과에 따라 null을 반환하거나 다른 타입의 객체를 반환할 수 없다. (생성자는 항상 자신이 정의된 클래스의 객체를 반환하거나 예외만 던질 수 있기 때문이다.)
 *
 * 이를 해결하는 방법은
 * 생성자를 비공개로 지정한 후 내포된 객체(object Factory)에 팩토리 메서드 역할을 하는 함수를 정의하고 필요에 따라 객체의 생성자를 호출하는 것이다.
 */
class ApplicationFactory private constructor(val name: String) {
    /**
     * - 동반 객체 companion 키워드를 사용하면 정의에서 이름을 아예 생략할 수 있다. (생략 시 동반 객체의 default 이름을 Companion으로 가정)
     * - 한 객체에 동반 객체가 2개 이상 올 수 없다.
     */
//    object Factory {
    companion object {
        fun create(args: Array<String>): ApplicationFactory? {
            val name = args.firstOrNull() ?: return null
            return ApplicationFactory(name)
        }
    }
}

fun main(args: Array<String>) {
    println(Application.name)

    // 직접 생성자를 호출하도록 허용하지 않음
//    val a = ApplicationFactory("서민규 앱")
    val app = ApplicationFactory.create(arrayOf("aa")) ?: return // companion 이라는 키워드를 통해 ApplicationFactory.factory.create 가 아닌 ApplicationFactory.create로 참조 할 수 있다.

    println(app.name)
}