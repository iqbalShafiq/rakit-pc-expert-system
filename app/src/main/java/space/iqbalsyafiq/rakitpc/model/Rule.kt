package space.iqbalsyafiq.rakitpc.model

data class Rule(
    val rules: List<Rule>,
    val finalProduct: Product
)
