package ipvc.estg.trabalhofinal.api.models

data class Paragem (
    val id: Int,
    val longitude: Double,
    val latitude: Double,
    val rua: String,
    val cidade: String,
    val cod_postal: String,
    val carreiras: List<ParagemCarreira>
)

data class ParagemCarreira (
    val id: Int,
    val empresa: String,
    val tempo_medio: Int,
    val distancia: Int,
    val inicio: String,
    val fim: String,
    val horarios: List<String>
)