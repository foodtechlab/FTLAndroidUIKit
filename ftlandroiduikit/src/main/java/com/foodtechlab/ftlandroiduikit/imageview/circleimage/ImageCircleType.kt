package com.foodtechlab.ftlandroiduikit.imageview.circleimage

import com.foodtechlab.ftlandroiduikit.R

enum class ImageCircleType(
    val circleRes: Int,
    val paddingImage: Float = 8F,
    val elevation: Float = 0F
) {
    DOUBLE_CHECK(R.drawable.ic_double_check_24),
    MAINTENANCE(R.drawable.ic_maintenance_24),
    STAR(R.drawable.ic_certification_24),
    MEDICAL(R.drawable.ic_medical_book_24),
    QUESTION(R.drawable.ic_question_24)
}
