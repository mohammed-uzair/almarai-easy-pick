package com.almarai.business

data class CratesAndPieces(val crates: Int, var pieces: Int, var totalUnits: Int, var upc: Int)

object CratesPieces {
    fun calculateTotalCratesAndPieces(
        crates: Int, pieces: Int,
        upc: Int
    ): CratesAndPieces {
        if (upc != 0) {
            val calculatedCrates = pieces / upc + crates
            val calculatedPieces = pieces % upc
            val calculatedTotalUnits =
                calculatedCrates * upc + calculatedPieces
            return CratesAndPieces(calculatedCrates, calculatedPieces, calculatedTotalUnits, upc)
        }
        return CratesAndPieces(0, 0, 0, 0)
    }
}