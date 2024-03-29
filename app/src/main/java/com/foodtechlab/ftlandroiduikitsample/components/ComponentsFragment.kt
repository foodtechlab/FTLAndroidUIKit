package com.foodtechlab.ftlandroiduikitsample.components

import android.os.Bundle
import android.text.Editable
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import androidx.annotation.IntDef
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import com.foodtechlab.ftlandroiduikit.button.FTLButton
import com.foodtechlab.ftlandroiduikit.button.timer.State
import com.foodtechlab.ftlandroiduikit.common.DialogButton
import com.foodtechlab.ftlandroiduikit.common.DialogState
import com.foodtechlab.ftlandroiduikit.common.SECONDARY_BUTTON
import com.foodtechlab.ftlandroiduikit.common.Type
import com.foodtechlab.ftlandroiduikit.sheet.*
import com.foodtechlab.ftlandroiduikit.snackbar.top.FTLSnackbar
import com.foodtechlab.ftlandroiduikit.textfield.helper.ImageType
import com.foodtechlab.ftlandroiduikit.textfield.helper.TextWatcher
import com.foodtechlab.ftlandroiduikit.textfield.table.OnTableHeaderClickListener
import com.foodtechlab.ftlandroiduikit.util.ThemeManager
import com.foodtechlab.ftlandroiduikit.util.changeColor
import com.foodtechlab.ftlandroiduikitsample.R
import com.foodtechlab.ftlandroiduikitsample.utils.Utils.getColorForTheme
import com.foodtechlab.ftlandroiduikitsample.utils.argument
import com.google.android.material.snackbar.Snackbar
import kotlinx.android.synthetic.main.fragment_banner.*
import kotlinx.android.synthetic.main.fragment_bottomsheet.*
import kotlinx.android.synthetic.main.fragment_buttons.*
import kotlinx.android.synthetic.main.fragment_cards.*
import kotlinx.android.synthetic.main.fragment_cards.tvRoute
import kotlinx.android.synthetic.main.fragment_component.*
import kotlinx.android.synthetic.main.fragment_edit_fields.*
import kotlinx.android.synthetic.main.fragment_other.llOtherContainer
import kotlinx.android.synthetic.main.fragment_progress.*
import kotlinx.android.synthetic.main.fragment_table_fields.*
import kotlinx.android.synthetic.main.fragment_text_fields.*
import kotlinx.android.synthetic.main.fragment_times.*
import java.text.SimpleDateFormat
import java.util.*

class ComponentsFragment : Fragment() {
    private var type: Int by argument(TYPE_ID)
    private var bottomSheetDialog: FTLBottomSheet? = null
    private var banneredTextViewState = -1

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        return inflater.inflate(R.layout.fragment_component, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        when (type) {
            BUTTONS -> initButtons()
            TEXT_FIELDS -> initTextViews()
            EDIT_FIELDS -> initEditTexts()
            TABLE_FIELDS -> initTableComponents()
            CARDS -> initCards()
            BOTTOMSHEETS -> initBottomSheets()
            PROGRESS -> initProgress()
            OTHER -> initOther()
            BANNER -> initBanner()
            else -> initTimesComponents()
        }
    }

    private fun initBanner() {
        with(vsComponentContainer) {
            layoutResource = R.layout.fragment_banner
            inflate()
        }
        with(bnrFirst) {
            setOnClickBannerListener { v ->
                if (v is FTLButton) {
                    FTLSnackbar.make(clBannerContainer, v.text.toString(), Snackbar.LENGTH_LONG)
                        ?.show()
                }
            }
            addButtons(
                listOf(
                    DialogButton(
                        3333,
                        "Кнопка 1"
                    ),
                    DialogButton(
                        3334,
                        "Кнопка 2"
                    ),
                    DialogButton(
                        3335,
                        "Кнопка 3"
                    )
                )
            )
        }
        with(bnrSecond) {
            setOnClickBannerListener { v ->
                if (v is FTLButton) {
                    FTLSnackbar.make(clBannerContainer, v.text.toString(), Snackbar.LENGTH_LONG)
                        ?.show()
                }
            }
            addButtons(
                listOf(
                    DialogButton(
                        3336,
                        "Кнопка 4"
                    ),
                    DialogButton(
                        3337,
                        "Кнопка 5"
                    )
                )
            )
        }
    }

    private fun initProgress() {
        with(vsComponentContainer) {
            layoutResource = R.layout.fragment_progress
            inflate()
        }
        llProgressContainer.setBackgroundColor(
            getColorForTheme(
                R.color.SurfaceThirdDark,
                R.color.SurfaceThirdLight
            )
        )
    }

    private fun initOther() {
        with(vsComponentContainer) {
            layoutResource = R.layout.fragment_other
            inflate()
        }
        llOtherContainer.setBackgroundColor(
            getColorForTheme(
                R.color.SurfaceThirdDark,
                R.color.SurfaceThirdLight
            )
        )
    }

    private fun initButtons() {
        with(vsComponentContainer) {
            layoutResource = R.layout.fragment_buttons
            inflate()
        }
        llButtonsContainer.setBackgroundColor(
            getColorForTheme(
                R.color.SurfaceThirdDark,
                R.color.SurfaceThirdLight
            )
        )

        with(btnNew) {
            updateState(State.NEW)
            estimateDuration = 10 * 60L * 1000L
            autoAnimateProgress = true
            val calendar = Calendar.getInstance()
            calendar.add(Calendar.MINUTE, 10)
            estimateSuccessAt = getDateString(calendar.time)
        }
        with(btnReadyForDelivery) {
            updateState(State.READY_FOR_DELIVERY)
            estimateDuration = 30 * 60L * 1000L
            autoAnimateProgress = false
            val calendar = Calendar.getInstance()
            calendar.add(Calendar.MINUTE, 30)
            estimateSuccessAt = getDateString(calendar.time)
        }
        with(btnInDelivery) {
            updateState(State.IN_DELIVERY)
            estimateDuration = 15 * 60L * 1000L
            autoAnimateProgress = false
            val calendar = Calendar.getInstance()
            calendar.add(Calendar.MINUTE, 15)
            estimateSuccessAt = getDateString(calendar.time)
        }
        with(btnTotalCount) {
            updateProgressTrackColor(R.color.TimerNegativeLight)
            imageTypeForProgress = ImageType.CASH
            setOnClickListener { }
        }
    }

    private fun initTextViews() {
        with(vsComponentContainer) {
            layoutResource = R.layout.fragment_text_fields
            inflate()
        }
        llTextFieldsContainer.setBackgroundColor(
            getColorForTheme(
                R.color.SurfaceThirdDark,
                R.color.SurfaceThirdLight
            )
        )
        with(tvMultiple) {
            colorBottomStartSlot = getColorForTheme(
                R.color.TextSuccessEnabledDark,
                R.color.TextSuccessEnabledLight
            )
            imageBackgroundColor = getColorForTheme(
                R.color.IconBackgroundOrangeDark,
                R.color.IconBackgroundOrangeLight
            )
        }
        tvExtendable.imageBackgroundColor = getColorForTheme(
            R.color.IconBackgroundVioletDark,
            R.color.IconBackgroundVioletLight
        )
        tvDoubleText.imageBackgroundColor = getColorForTheme(
            R.color.IconBackgroundCyanDark,
            R.color.IconBackgroundCyanLight
        )

        with(tvMediaContent) {
            imageType = ImageType.CHECK
            val color = ContextCompat.getColor(
                context,
                when (ThemeManager.theme) {
                    ThemeManager.Theme.LIGHT -> R.color.IconBlueLight
                    else -> R.color.IconBlueDark
                }
            )
            val galleryDrawable = ContextCompat.getDrawable(
                context,
                R.drawable.ic_media_content_gallery
            ).apply {
                this?.mutate()?.changeColor(color)
            }
            val messageDrawable = ContextCompat.getDrawable(
                context,
                R.drawable.ic_media_content_message
            ).apply {
                this?.mutate()?.changeColor(color)
            }
            val cameraDrawable = ContextCompat.getDrawable(
                context,
                R.drawable.ic_media_content_camera
            ).apply {
                this?.mutate()?.changeColor(color)
            }
            val videoDrawable = ContextCompat.getDrawable(
                context,
                R.drawable.ic_media_content_video
            ).apply {
                this?.mutate()?.changeColor(color)
            }
            val voiceDrawable = ContextCompat.getDrawable(
                context,
                R.drawable.ic_media_content_voice
            ).apply {
                this?.mutate()?.changeColor(color)
            }
            addIconsForBottomSlot(
                listOf(
                    galleryDrawable,
                    messageDrawable,
                    cameraDrawable,
                    videoDrawable,
                    voiceDrawable
                )
            )
            updateImageColors(
                R.color.IconBackgroundGreenLight,
                R.color.IconBackgroundGreenDark
            )

            tvBannered.setOnClickListener {
                tvBannered.setVisibilityOfCheckbox(
                    shouldVisible = banneredTextViewState < 0,
                    showAnimation = true
                )
                banneredTextViewState *= -1
            }
        }
    }

    private fun initEditTexts() {
        with(vsComponentContainer) {
            layoutResource = R.layout.fragment_edit_fields
            inflate()
        }
        llEditFieldsContainer.setBackgroundColor(
            getColorForTheme(
                R.color.SurfaceThirdDark,
                R.color.SurfaceThirdLight
            )
        )

        scErrorOnCode.setOnCheckedChangeListener { _, isChecked ->
            etCode.isErrorEnabled = isChecked
        }
        scErrorOnDefault.setOnCheckedChangeListener { _, isChecked ->
            etDefault.isErrorEnabled = isChecked
        }
        scErrorOnAutocomplete.setOnCheckedChangeListener { _, isChecked ->
            etAutocomplete.isErrorEnabled = isChecked
        }

        val cats = resources.getStringArray(R.array.cat_names).toCollection(ArrayList())
        val hintsAdapter = ArrayAdapter(
            requireContext(),
            R.layout.layout_ftl_autocomplete_drop_down_item,
            cats
        )
        with(etAutocomplete) {
            setHintsAdapter(hintsAdapter)
            addTextChangedListener(object : TextWatcher() {
                override fun afterTextChanged(s: Editable?) {
                    foldingDropDownDialog(
                        cats,
                        charSequence = s,
                        maxItemCountForFolding = 2
                    )
                    super.afterTextChanged(s)
                }
            })
        }
    }

    private fun initTableComponents() {
        with(vsComponentContainer) {
            layoutResource = R.layout.fragment_table_fields
            inflate()
        }
        llTableFieldsContainer.setBackgroundColor(
            getColorForTheme(
                R.color.SurfaceThirdDark,
                R.color.SurfaceThirdLight
            )
        )

        val positionAdapter = PositionItemRVAdapter()
        rvPositions.adapter = positionAdapter
        positionAdapter.update(
            arrayListOf(
                ItemPosition(getString(R.string.common_positions_name), 10, 100.0),
                ItemPosition(getString(R.string.common_positions_name), 5, 50.0),
                ItemPosition(getString(R.string.common_positions_name), 15, 150.0)
            )
        )
        val paymentAdapter = PaymentItemRVAdapter()
        rvPaymentTypes.adapter = paymentAdapter
        paymentAdapter.update(
            arrayListOf(
                ItemPayment(PaymentType.MONEY, getString(R.string.common_cash_payment), 100.0),
                ItemPayment(PaymentType.ONLINE, getString(R.string.common_payment_online), 50.0)
            )
        )
        with(thPositions) {
            setRippleBackground(forButton = true)
            tableHeaderClickListener = object : OnTableHeaderClickListener {
                override fun onSwitchClick(isUnwrappedHeader: Boolean) {
                    hideTableRows(!thPositions.isUnwrapped, true)
                }
            }
        }
        with(thPaymentTypes) {
            setRippleBackground(forButton = false)
            tableHeaderClickListener = object : OnTableHeaderClickListener {
                override fun onSwitchClick(isUnwrappedHeader: Boolean) {
                    hideTableRows(!thPaymentTypes.isUnwrapped, false)
                }
            }
            imageBackgroundColor = getColorForTheme(R.color.IconPinkDark, R.color.IconPinkLight)
        }
        hideTableRows(!thPositions.isUnwrapped, true)
        hideTableRows(!thPaymentTypes.isUnwrapped, false)
    }

    private fun initCards() {
        with(vsComponentContainer) {
            layoutResource = R.layout.fragment_cards
            inflate()
        }
        with(tvRoute) {
            textAddressTo = getString(R.string.common_first_address)
            textAddressFrom = getString(R.string.common_second_address)
            isBoldStyleAddressTo = false
        }
        tvTimeDelivery.deliveryTime = getHoursAndMinutesString(Calendar.getInstance().time)
    }

    private fun initBottomSheets() {
        with(vsComponentContainer) {
            layoutResource = R.layout.fragment_bottomsheet
            inflate()
        }
        rgBottomSheetType.check(R.id.rbBottomSheetNone)
        rgBottomSheetType.setOnCheckedChangeListener { _, checkedId ->
            when (checkedId) {
                R.id.rbBottomSheetIssue -> showBottomSheetDialog(Type.ISSUE)
                R.id.rbBottomSheetGeolocation -> showBottomSheetDialog(Type.GEOLOCATION)
                R.id.rbBottomSheetSad -> showBottomSheetDialog(Type.SAD)
                R.id.rbBottomSheetWarning -> showBottomSheetDialog(Type.WARNING)
                R.id.rbBottomSheetSuccess -> showBottomSheetDialog(Type.SUCCESS)
                R.id.rbBottomSheetCamera -> showBottomSheetDialog(Type.CAMERA)
                else -> hideBottomSheetDialog()
            }
        }
    }

    private fun showBottomSheetDialog(type: Type) {
        if (bottomSheetDialog?.isAdded == true) bottomSheetDialog?.dismiss()
        bottomSheetDialog =
            FTLBottomSheet.newInstance(
                DialogState(
                    getString(R.string.dialog_sad_title),
                    getString(R.string.dialog_sad_message),
                    type,
                    listOf(
                        DialogButton(
                            1111,
                            getString(R.string.common_ok),
                            SECONDARY_BUTTON
                        )
                    )
                )
            )
        bottomSheetDialog?.show(childFragmentManager, FTLBottomSheet.TAG)
    }

    private fun hideBottomSheetDialog() {
        if (bottomSheetDialog?.isAdded == true) bottomSheetDialog?.dismiss()
    }


    private fun initTimesComponents() {
        with(vsComponentContainer) {
            layoutResource = R.layout.fragment_times
            inflate()
        }

        val time = Calendar.getInstance().time
        dtvLargeUsual.deliveryTime = getHoursAndMinutesString(time)
        dtvLargeUrgent.deliveryTime = getHoursAndMinutesString(time)
        dtvSmallCanceled.deliveryTime = getHoursAndMinutesString(time)
        dtvSmallDelivered.deliveryTime = getHoursAndMinutesString(time)
        dtvSmallDeliveredLate.deliveryTime = getHoursAndMinutesString(time)
        dtvSmallInProgress.deliveryTime = getHoursAndMinutesString(time)
        dtvSmallInProgressLate.deliveryTime = getHoursAndMinutesString(time)

        with(dtvTimer) {
            estimateDuration = 15 * 60L * 1000L
            autoAnimateProgress = false
            val calendar = Calendar.getInstance()
            calendar.add(Calendar.MINUTE, 10)
            estimateSuccessAt = getDateString(calendar.time)
        }
    }

    /**
     * Метод для определения видимости RecyclerView
     * @param hide - флаг видимости, в случае если true - то RecyclerView будет скрыт,
     * в противном случае RecyclerView станет видимым
     */
    private fun hideTableRows(hide: Boolean, isPositions: Boolean) {
        if (isPositions)
            rvPositions.visibility = if (hide) View.GONE else View.VISIBLE
        else
            rvPaymentTypes.visibility = if (hide) View.GONE else View.VISIBLE
    }

    /**
     * Метод для конвертирования даты в текстовый формат (ISO)
     */
    private fun getDateString(date: Date): String {
        val formatDate = SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSS", Locale.getDefault())
        formatDate.timeZone = TimeZone.getDefault()
        return formatDate.format(date)
    }

    /**
     * Метод для конвертирования даты в текстовый формат (Часы и минуты)
     */
    private fun getHoursAndMinutesString(date: Date): String {
        val formatDate = SimpleDateFormat("HH:mm", Locale.getDefault())
        formatDate.timeZone = TimeZone.getDefault()
        return formatDate.format(date)
    }

    companion object {
        const val BUTTONS = 0
        const val TEXT_FIELDS = 1
        const val EDIT_FIELDS = 2
        const val TABLE_FIELDS = 3
        const val CARDS = 4
        const val TIMES = 5
        const val BOTTOMSHEETS = 6
        const val PROGRESS = 7
        const val OTHER = 8
        const val BANNER = 9
        const val TYPE_ID = "TYPE_ID"

        @IntDef(BUTTONS, TEXT_FIELDS, EDIT_FIELDS, TABLE_FIELDS, CARDS, TIMES, BOTTOMSHEETS, OTHER)
        @Retention(AnnotationRetention.SOURCE)
        annotation class ComponentType

        fun newInstance(@ComponentType type: Int): ComponentsFragment {
            return ComponentsFragment().apply {
                arguments = Bundle().apply {
                    putInt(TYPE_ID, type)
                }
            }
        }
    }
}