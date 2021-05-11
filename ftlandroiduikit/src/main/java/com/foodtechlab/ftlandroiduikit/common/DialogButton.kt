package com.foodtechlab.ftlandroiduikit.common

import android.os.Parcelable
import androidx.annotation.DrawableRes
import androidx.annotation.IntDef
import com.foodtechlab.ftlandroiduikit.R
import kotlinx.android.parcel.Parcelize

/**
 * Created by Umalt on 20.05.2020
 */

const val CANCEL_BUTTON = 1
const val ADDITIONAL_BUTTON = 2
const val SECONDARY_BUTTON = 3
const val PRIMARY_BUTTON = 4

@IntDef(
    CANCEL_BUTTON,
    ADDITIONAL_BUTTON,
    SECONDARY_BUTTON,
    PRIMARY_BUTTON
)
@Retention(AnnotationRetention.SOURCE)
annotation class ButtonType

@Parcelize
class DialogState(
    val title: String,
    val message: String,
    val type: Type,
    val buttons: List<DialogButton>
) : Parcelable

@Parcelize
class DialogButton(
    val id: Int,
    val title: String,
    @ButtonType val buttonType: Int = ADDITIONAL_BUTTON
) : Parcelable

enum class Type(@DrawableRes val imgRes: Int) {
    GEOLOCATION(R.drawable.ic_geolocation),
    ISSUE(R.drawable.ic_issue),
    SAD(R.drawable.ic_sad_smile),
    WARNING(R.drawable.ic_warning),
    SUCCESS(R.drawable.ic_success),
    CAMERA(R.drawable.ic_camera)
}