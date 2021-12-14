package com.foodtechlab.ftlandroiduikit.button.image

import androidx.annotation.DrawableRes

enum class ImageButtonType(@DrawableRes var bgRes: Int? = null, val size: Int) {
    NAVIGATOR_SMALL(size = 32),
    NAVIGATOR_MEDIUM(size = 40),
    NAVIGATOR_LARGE(size = 48),

    LOCATION_SMALL(size = 32),
    LOCATION_MEDIUM(size = 40),
    LOCATION_LARGE(size = 48),

    INFO_SMALL(size = 32),
    INFO_MEDIUM(size = 40),
    INFO_LARGE(size = 48),

    REPLAY_SMALL(size = 32),
    REPLAY_MEDIUM(size = 40),
    REPLAY_LARGE(size = 48),

    TRASH_LARGE(size = 48),

    RECORD_LARGE(size = 48),

    STOP_LARGE(size = 48),

    PLAY_MEDIUM(size = 40),
    PLAY_LARGE(size = 48),

    PAUSE_MEDIUM(size = 40),
    PAUSE_LARGE(size = 48)
}