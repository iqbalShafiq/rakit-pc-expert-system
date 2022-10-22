package space.iqbalsyafiq.rakitpc.model.network

data class DataResponse(
    val majorDimension: String,
    val range: String,
    val values: List<List<String>>
)