package study.spring.kotlin.firstrestapi

import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication
import org.springframework.data.annotation.Id
import org.springframework.data.jdbc.repository.query.Query
import org.springframework.data.relational.core.mapping.Table
import org.springframework.data.repository.CrudRepository
import org.springframework.stereotype.Service
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RestController

@SpringBootApplication
class FirstrestapiApplication

fun main(args: Array<String>) {
    runApplication<FirstrestapiApplication>(*args)
}

@RestController
class MessageResource(val service: MessageService) {

    @GetMapping
    fun index(): List<Message> {
        return service.findMessage();
    }

    @PostMapping
    fun post(@RequestBody message: Message): String {
        service.post(message);
        return message.text;
    }
}

@Service
class MessageService(val db: MessageRepository) {

    fun findMessage() : List<Message> = db.findMessages();

    fun post(message: Message) {
        db.save(message);
    }
}

interface MessageRepository : CrudRepository<Message, String> {

    @Query("select * from messages")
    fun findMessages() : List<Message>
}

@Table("MESSAGES")
data class Message(@Id val id: String?, val text: String)

