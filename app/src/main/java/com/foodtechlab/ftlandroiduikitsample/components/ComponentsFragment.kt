package com.foodtechlab.ftlandroiduikitsample.components

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.annotation.IntDef
import androidx.fragment.app.Fragment
import com.foodtechlab.ftlandroiduikit.button.timer.State
import com.foodtechlab.ftlandroiduikit.textfield.table.OnTableHeaderClickListener
import com.foodtechlab.ftlandroiduikitsample.R
import com.foodtechlab.ftlandroiduikitsample.utils.argument
import kotlinx.android.synthetic.main.fragment_buttons.*
import kotlinx.android.synthetic.main.fragment_cards.*
import kotlinx.android.synthetic.main.fragment_component.*
import kotlinx.android.synthetic.main.fragment_edit_fields.*
import kotlinx.android.synthetic.main.fragment_table_fields.*
import kotlinx.android.synthetic.main.fragment_times.*
import java.text.SimpleDateFormat
import java.util.*

class ComponentsFragment : Fragment() {

    private var type: Int by argument(TYPE_ID)

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
            else -> initTimesComponents()
        }
    }

    private fun initButtons() {
        with(vsComponentContainer) {
            layoutResource = R.layout.fragment_buttons
            inflate()
        }

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
    }

    private fun initTextViews() {
        with(vsComponentContainer) {
            layoutResource = R.layout.fragment_text_fields
            inflate()
        }
    }

    private fun initEditTexts() {
        with(vsComponentContainer) {
            layoutResource = R.layout.fragment_edit_fields
            inflate()
        }

        scErrorOnCode.setOnCheckedChangeListener { _, isChecked ->
            etCode.isErrorEnabled = isChecked
        }

        scErrorOnDefault.setOnCheckedChangeListener { _, isChecked ->
            etDefault.isErrorEnabled = isChecked
        }
    }

    private fun initTableComponents() {
        with(vsComponentContainer) {
            layoutResource = R.layout.fragment_table_fields
            inflate()
        }
        val positionAdapter = PositionItemRVAdapter()
        rvPositions.adapter = positionAdapter
        positionAdapter.update(
            arrayListOf(
                ItemPosition("Товар 1", 10, 100.0),
                ItemPosition("Товар 2", 5, 50.0),
                ItemPosition("Товар 3", 15, 150.0)
            )
        )

        val paymentAdapter = PaymentItemRVAdapter()
        rvPaymentTypes.adapter = paymentAdapter
        paymentAdapter.update(
            arrayListOf(
                ItemPayment(PaymentType.MONEY, "Оплата наличными", 100.0),
                ItemPayment(PaymentType.ONLINE, "Оплата картой", 50.0)
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
            textAddressTo ="Волгоград, ул. им Рокоссовского д.62 кв./оф. 102 под.1 эт 24"
            textAddressFrom = "Волгоград, ул. им Маршала Чуйкова д.37"
            isBoldStyleAddressTo = false
        }
        tvTimeDelivery.deliveryTime = getHoursAndMinutesString(Calendar.getInstance().time)
    }

    private fun initTimesComponents() {
        with(vsComponentContainer) {
            layoutResource = R.layout.fragment_times
            inflate()
        }
        val time = Calendar.getInstance().time
        dtvLargeUsual.deliveryTime = getHoursAndMinutesString(time)
        dtvLargeUrgent.deliveryTime = getHoursAndMinutesString(time)
        dtvSmallActualTime.deliveryTime = getHoursAndMinutesString(time)
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
     * Метод для определения видимости RecyclerView с позициями заказа
     * @param hide - флаг видимости, в случае если true - то RecyclerView будет скрыт,
     * в противном случае RecyclerView станет видимым
     */
    private fun hideTableRows(hide: Boolean, isPositions: Boolean) {
        if (isPositions)
            rvPositions.visibility = if (hide) View.GONE else View.VISIBLE
        else
            rvPaymentTypes.visibility = if (hide) View.GONE else View.VISIBLE
    }


    private fun getDateString(date: Date): String {
        val formatDate = SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSS", Locale.getDefault())
        formatDate.timeZone = TimeZone.getDefault()
        return formatDate.format(date)
    }

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
        const val TYPE_ID = "TYPE_ID"

        @IntDef(BUTTONS, TEXT_FIELDS, EDIT_FIELDS, TABLE_FIELDS, CARDS, TIMES)
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