package com.template.state

import com.template.contract.TemplateContract
import com.template.contract.TradeMatchingContract
import net.corda.core.contracts.Contract
import net.corda.core.contracts.ContractState
import net.corda.core.identity.AbstractParty

/**
 * Define your state object here.
 */
class TemplateState(override val contract: TemplateContract) : ContractState {
    /** The public keys of the involved parties. */
    override val participants: List<AbstractParty>
        get() = listOf()
}


data class TradeState(val tradeData: Map<String, String>) : ContractState {
    override val contract: Contract = TradeMatchingContract()
    override val participants: List<AbstractParty>
        get() = listOf()


    open fun isMatchedBy(other: TradeState): Boolean {
        if (
        other.tradeData.filter { listOf("Currency", "Amount").contains(it.key) } ==
                this.tradeData.filter { listOf("Currency", "Amount").contains(it.key) }
                ) return true
        // other matching logic here...
        return false
    }

}


fun main(args: Array<String>) {
    val x1 = mapOf("Currency" to "GBP", "Amount" to "100.00", "MyTradeRef" to "ABC123", "TheirTradeRef" to "789DEF")
    val x2 = mapOf("Currency" to "GBP", "Amount" to "100.00", "MyTradeRef" to "789DEF", "TheirTradeRef" to "ABC123")
    val x3 = mapOf("Currency" to "GBP", "Amount" to "150.00", "MyTradeRef" to "789DEF", "TheirTradeRef" to "ABC123")
    val ts1 = TradeState(x1)
    val ts2 = TradeState(x2)
    val ts3 = TradeState(x3)
    println(listOf(ts1, ts2, ts1.isMatchedBy(ts2), ts1.isMatchedBy(ts3)))
}