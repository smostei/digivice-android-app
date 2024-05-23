package com.example.digivice_android_app.framework.presentation.home.viewModel.uiMapper

import com.example.digivice_android_app.domain.Digimon
import com.example.digivice_android_app.framework.presentation.transports.DigimonUi

object DigimonDomainToUiMapper {

    fun map(input: Digimon): DigimonUi {
        return DigimonUi(
            name = input.name,
            image = input.image,
            level = input.level?.levelDescription().orEmpty()
        )
    }
}