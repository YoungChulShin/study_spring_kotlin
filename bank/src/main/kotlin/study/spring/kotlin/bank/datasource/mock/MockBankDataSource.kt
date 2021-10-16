package study.spring.kotlin.bank.datasource.mock

import org.springframework.stereotype.Repository
import study.spring.kotlin.bank.datasource.BankDataSource
import study.spring.kotlin.bank.model.Bank

@Repository
class MockBankDataSource : BankDataSource {

    val banks = listOf(
        Bank("1234", 3.14, 17),
        Bank("5678", 17.0, 1),
        Bank("9999", 1.0, 100)
    )

    override fun retrieveBanks(): Collection<Bank> = banks
}