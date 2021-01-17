package ipvc.estg.trabalhofinal.api.models

data class Direction (
    val routes: List<Route>
)

data class Route (
    val legs: List<Leg>,
    val overview_polyline: Line
)

data class Line (
        val points: String
)

data class Leg (
    val start_address: String,
    val end_address: String,
    val distance: Distancia,
    val duration: Duration,
    val steps: List<Step>
)

data class Step (
    val html_instructions: String
)

data class Distancia (
    val value: Int,
    val text: String
)

data class Duration (
    val value: Int,
    val text: String
)