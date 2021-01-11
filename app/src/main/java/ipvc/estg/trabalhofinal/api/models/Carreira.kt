package ipvc.estg.trabalhofinal.api.models

data class Carreira (
    val id: Int,
    val empresa: String,
    val tempo_medio: Int,
    val distancia: Int,
    val inicio: String,
    val fim: String,
    val paragens: List<CarreiraParagem>
)

data class CarreiraParagem (
    val id: Int,
    val longitude: Double,
    val latitude: Double,
    val rua: String,
    val cidade: String,
    val cod_postal: String,
    val horarios: List<String>
)