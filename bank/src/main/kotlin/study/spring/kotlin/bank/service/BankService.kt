package study.spring.kotlin.bank.service

import org.springframework.stereotype.Service
import study.spring.kotlin.bank.datasource.BankDataSource
import study.spring.kotlin.bank.model.Bank

@Service
class BankService(private val dataSource: BankDataSource) {

    fun getBanks(): Collection<Bank> {
        return dataSource.retrieveBanks()
    }
}