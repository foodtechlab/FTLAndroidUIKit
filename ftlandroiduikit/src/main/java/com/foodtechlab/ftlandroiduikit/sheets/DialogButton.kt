package com.foodtechlab.ftlandroiduikit.sheets

import android.os.Parcelable
import androidx.annotation.DrawableRes
import com.foodtechlab.ftlandroiduikit.R
import kotlinx.android.parcel.Parcelize

/**
 * Created by Umalt on 20.05.2020
 */

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
    val isCancelAction: Boolean = false
) : Parcelable

enum class Type(@DrawableRes val imgRes: Int) {
    GEOLOCATION(R.drawable.ic_geolocation),
    ISSUE(R.drawable.ic_issue),
    SAD(R.drawable.ic_sad_smile),
    WARNING(R.drawable.ic_warning),
    SUCCESS(R.drawable.ic_success)
}