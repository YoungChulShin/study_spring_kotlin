package study.spring.kotlin.bank.model

import com.fasterxml.jackson.annotation.JsonProperty

data class Bank(
    @JsonProperty(value = "account_number")
    val accountNumber: String,

    @JsonProperty(value = "trust")
    val trust: Double,

    @JsonProperty(value = "transaction_fee")
    val transactionFee: Int
)