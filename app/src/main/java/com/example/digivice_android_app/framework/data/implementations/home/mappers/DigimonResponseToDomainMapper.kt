package com.example.digivice_android_app.framework.data.implementations.home.mappers

import com.example.digivice_android_app.domain.Digimon
import com.example.digivice_android_app.framework.data.config.retrofit.response.DigimonResponse

object DigimonResponseToDomainMapper {

    fun map(input: List<DigimonResponse>): List<Digimon> {
        return input.map {
            Digimon(
                name = it.name,
                image = it.image,
                level = getLevelByDescription(it.level)
            )
        }
    }

    private fun getLevelByDescription(levelDescription: String): Digimon.Level? =
        when(levelDescription) {
            Digimon.Level.CHAMPION.levelDescription() -> Digimon.Level.CHAMPION
            Digimon.Level.FRESH.levelDescription() -> Digimon.Level.FRESH
            Digimon.Level.TRAINING.levelDescription() -> Digimon.Level.TRAINING
            Digimon.Level.IN_TRAINING.levelDescription() -> Digimon.Level.IN_TRAINING
            Digimon.Level.MEGA.levelDescription() -> Digimon.Level.MEGA
            Digimon.Level.ROOKIE.levelDescription() -> Digimon.Level.ROOKIE
            Digimon.Level.ULTIMATE.levelDescription() -> Digimon.Level.ULTIMATE
            Digimon.Level.ARMOR.levelDescription() -> Digimon.Level.ARMOR
            else -> null
        }
}