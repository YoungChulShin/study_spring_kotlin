package study.spring.kotlin.bank.datasource.network

import org.springframework.beans.factory.annotation.Autowired
import org.springframework.http.ResponseEntity
import org.springframework.stereotype.Repository
import org.springframework.web.client.RestTemplate
import study.spring.kotlin.bank.datasource.BankDataSource
import study.spring.kotlin.bank.datasource.network.dto.BankList
import study.spring.kotlin.bank.model.Bank
import java.io.IOException

@Repository(value = "network")
class NetworkDataSource(
    @Autowired private val restTemplate: RestTemplate
) : BankDataSource {

    override fun retrieveBanks(): Collection<Bank> {
        val response: ResponseEntity<BankList> =
            restTemplate.getForEntity("http://54.193.31.159/banks", BankList::class.java)

        return response.body?.results
            ?: throw IOException("Could not fetch banks from the network")
    }

    override fun retrieveBank(accountNumber: String): Bank {
        TODO("Not yet implemented")
    }

    override fun createBank(bank: Bank): Bank {
        TODO("Not yet implemented")
    }

    override fun updateBank(bank: Bank): Bank {
        TODO("Not yet implemented")
    }

    override fun deleteBank(accountNumber: String) {
        TODO("Not yet implemented")
    }
}