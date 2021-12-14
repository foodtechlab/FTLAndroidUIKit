package com.foodtechlab.ftlandroiduikit.sheet

import com.foodtechlab.ftlandroiduikit.R
import com.foodtechlab.ftlandroiduikit.util.ViewThemeManager

class FTLAudioRecorderBottomSheetThemeManager(
    override var darkTheme: FTLAudioRecorderBottomSheetTheme? = FTLAudioRecorderBottomSheetTheme(
        R.color.selector_ftl_audio_record_bottom_sheet_text_dark,
        R.color.SurfaceFourthDark
    ),
    override var lightTheme: FTLAudioRecorderBottomSheetTheme = FTLAudioRecorderBottomSheetTheme(
        R.color.selector_ftl_audio_record_bottom_sheet_text_light,
        R.color.SurfaceFourthLight
    )
) : ViewThemeManager<FTLAudioRecorderBottomSheetTheme>()