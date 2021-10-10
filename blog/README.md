# 참고 사이트
https://spring.io/guides/tutorials/spring-boot-kotlin/

# 메모
Extension 메서드 생성 시 class 없이 생성
- java에서는 어떻게 처리되는거지?

JPA entity를 만들 때는 var로 만들어야한다
```
Here we don’t use data classes with val properties because JPA is not designed to work with immutable classes or the methods generated automatically by data classes. If you are using other Spring Data flavor, most of them are designed to support such constructs so you should use classes like data class User(val login: String, …​) when using Spring Data MongoDB, Spring Data JDBC, etc.
```

