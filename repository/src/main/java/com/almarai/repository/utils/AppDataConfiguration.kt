package com.almarai.repository.utils

sealed class AppDataConfiguration {
    object NetworkConfiguration : AppDataConfiguration()
    object DataConfiguration : AppDataConfiguration()
    object Home : AppDataConfiguration()
}