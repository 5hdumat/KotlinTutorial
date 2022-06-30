/**
 * private로 선언하면 한 파일안으로 가시성이 제한되고,
 * internal로 선언하면 한 모듈안에 있는 모든 파일에서 사용 가능하다. (빌드 시스템에 따라 다르지만 intellij 기준 한 모듈이다.)
 * public의 경우 최상위 함수는 디폴트로 공개 가시성을 갖기 때문에 이 변경자는 불필요한 중복이므로 사용하지 않아도 된다.
 */
/**
 * 패키지를 지정하지 않으면 해당 파일을 디폴트 최상위 패키지에 속한다고 가정한다.
 * 디폴트 최상위 패키지에는 이름이 없다.
 * 보통 패키지 이름은 루프 패키지로부터 지정한 패키지까지의 경로를 사용한다.
 *
 * 소스 파일 트리와 패키지 경로가 꼭 같지 않아도 되지만 맞추는걸 권장한다.(ide가 맞추라고 경고함)
 */
package util

private fun readIntUtil(): Int {
    return readLine()!!.toInt()
}

fun readIntPair() = intArrayOf(readIntUtil(), readIntUtil())