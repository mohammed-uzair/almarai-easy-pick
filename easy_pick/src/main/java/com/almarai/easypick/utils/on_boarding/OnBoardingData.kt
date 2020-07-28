package com.almarai.easypick.utils.on_boarding

import com.almarai.easypick.R

val OnBoardingScreens = listOf(
    OnBoarding(
        illustrationResourceId = R.drawable.ic_on_boarding_filter,
        title = R.string.on_boarding_title_custom_filter,
        description = R.string.on_boarding_message_custom_filter
    ),
    OnBoarding(
        animationResourceId = R.raw.anim_barcode_scan,
        title = R.string.on_boarding_title_barcode_scanning,
        description = R.string.on_boarding_message_barcode_scanning
    ),
    OnBoarding(
        illustrationResourceId = R.drawable.ic_on_boarding_stats,
        title = R.string.on_boarding_title_statistics,
        description = R.string.on_boarding_message_statistics
    ),
    OnBoarding(
        illustrationResourceId = R.drawable.ic_on_boarding_green_earth,
        title = R.string.on_boarding_title_save_papers,
        description = R.string.on_boarding_message_save_papers
    ),
    OnBoarding(
        illustrationResourceId = R.drawable.ic_on_boarding_voice,
        title = R.string.on_boarding_title_voice_search,
        description = R.string.on_boarding_message_voice_search,
        isLastScreen = true
    )
)