package com.example.digivice_android_app.domain

data class Digimon(
    val name: String,
    val image: String,
    val level: Level?
) {

    companion object {
        private const val TRAINING_DESCRIPTION = "Training"
        private const val IN_TRAINING_DESCRIPTION = "In Training"
        private const val ROOKIE_DESCRIPTION = "Rookie"
        private const val CHAMPION_DESCRIPTION = "Champion"
        private const val ULTIMATE_DESCRIPTION = "Ultimate"
        private const val FRESH_DESCRIPTION = "Fresh"
        private const val MEGA_DESCRIPTION = "Mega"
        private const val ARMOR_DESCRIPTION = "Armor"
    }

    enum class Level {
        TRAINING {
            override fun levelDescription(): String {
                return TRAINING_DESCRIPTION
            }
        },
        IN_TRAINING {
            override fun levelDescription(): String {
                return IN_TRAINING_DESCRIPTION
            }
        },
        ROOKIE {
            override fun levelDescription(): String {
                return ROOKIE_DESCRIPTION
            }
        },
        CHAMPION {
            override fun levelDescription(): String {
                return CHAMPION_DESCRIPTION
            }
        },
        ULTIMATE {
            override fun levelDescription(): String {
                return ULTIMATE_DESCRIPTION
            }
        },
        FRESH {
            override fun levelDescription(): String {
                return FRESH_DESCRIPTION
            }
        },
        MEGA {
            override fun levelDescription(): String {
                return MEGA_DESCRIPTION
            }
        },
        ARMOR {
            override fun levelDescription(): String {
                return ARMOR_DESCRIPTION
            }
        };

        abstract fun levelDescription(): String
    }
}