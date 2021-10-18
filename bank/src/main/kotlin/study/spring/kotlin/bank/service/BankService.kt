package study.spring.kotlin.bank.service

import org.springframework.stereotype.Service
import study.spring.kotlin.bank.datasource.BankDataSource
import study.spring.kotlin.bank.model.Bank

@Service
class BankService(private val dataSource: BankDataSource) {

    fun getBanks(): Collection<Bank> = dataSource.retrieveBanks()

    fun getBank(accountNumber: String): Bank = dataSource.retrieveBank(accountNumber)

    fun addBank(bank: Bank): Bank = dataSource.createBank(bank)

}