package study.spring.kotlin.bank.datasource

import study.spring.kotlin.bank.model.Bank

interface BankDataSource {

    fun retrieveBanks(): Collection<Bank>
    fun retrieveBank(accountNumber: String): Bank
}