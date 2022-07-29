package fastcampus.issueservice.exception

// sealed 클래스는 추상클래스이면서, 이를 상속받은 클래스가 어떤 클래스인지 알 수 있다
sealed class ServerException(
    val code: Int,
    override val message: String,
) : RuntimeException(message)

data class NotFoundException(
    override val message: String,
) : ServerException(404, message)

data class UnauthorizedException(
    override val message: String = "인증 정보가 잘못되었습니다",
) : ServerException(401, message)