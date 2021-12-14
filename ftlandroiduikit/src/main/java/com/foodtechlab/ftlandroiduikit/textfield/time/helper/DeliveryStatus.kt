import androidx.annotation.ColorRes
import androidx.annotation.DrawableRes
import com.foodtechlab.ftlandroiduikit.R
import com.foodtechlab.ftlandroiduikit.util.ThemeManager

enum class DeliveryStatus(
    @ColorRes var textColor: Int? = null,
    @ColorRes var bgColor: Int? = null,
    @DrawableRes var iconDrawable: Int? = null,
    @ColorRes var iconColor: Int? = null
) {
    USUAL(
        iconDrawable = R.drawable.ic_clock_24,
    ),
    URGENT(
        iconDrawable = R.drawable.ic_flash_24
    ),
    DELIVERED(
        iconDrawable = R.drawable.ic_finish_flag_green_24
    ),
    DELIVERED_LATE(
        iconDrawable = R.drawable.ic_finish_flag_pink_24
    ),
    CANCELED(
        iconDrawable = R.drawable.ic_clock_gray_24
    ),
    IN_PROGRESS(
        iconDrawable = R.drawable.ic_timer_24
    ),
    IN_PROGRESS_LATE(
        iconDrawable = R.drawable.ic_timer_pink_24
    )
}