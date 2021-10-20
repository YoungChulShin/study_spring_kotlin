package study.spring.kotlin.bank.datasource.network.dto

import study.spring.kotlin.bank.model.Bank

data class BankList (
    val results: Collection<Bank>
)