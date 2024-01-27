package pl.edu.uwr.pum.lista5

fun findClosestValidValue(value: Float, validValues: List<Float>): Float {
    val closestValidValue = validValues.minByOrNull { kotlin.math.abs(value - it) }
    return closestValidValue ?: value
}
