package com.foodtechlab.ftlandroiduikitsample.showroom.components

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.annotation.IntDef
import androidx.fragment.app.Fragment
import com.foodtechlab.ftlandroiduikit.button.timer.State
import com.foodtechlab.ftlandroiduikitsample.R
import com.foodtechlab.ftlandroiduikitsample.utils.argument
import kotlinx.android.synthetic.main.fragment_buttons.*
import kotlinx.android.synthetic.main.fragment_component.*
import kotlinx.android.synthetic.main.fragment_edit_fields.*
import java.text.SimpleDateFormat
import java.util.*

class ItemFragment : Fragment() {

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
            estimateSuccessAt = convertTimeFromDateToString(calendar.time)
        }
        with(btnReadyForDelivery) {
            updateState(State.READY_FOR_DELIVERY)
            estimateDuration = 30 * 60L * 1000L
            autoAnimateProgress = false
            val calendar = Calendar.getInstance()
            calendar.add(Calendar.MINUTE, 30)
            estimateSuccessAt = convertTimeFromDateToString(calendar.time)
        }
        with(btnInDelivery) {
            updateState(State.IN_DELIVERY)
            estimateDuration = 15 * 60L * 1000L
            autoAnimateProgress = false
            val calendar = Calendar.getInstance()
            calendar.add(Calendar.MINUTE, 15)
            estimateSuccessAt = convertTimeFromDateToString(calendar.time)
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

    private fun initTableComponents() {}
    private fun initCards() {}
    private fun initTimesComponents() {}

    private fun convertTimeFromDateToString(date: Date): String {
        val formatDate = SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSS", Locale.getDefault())
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

        fun newInstance(@ComponentType type: Int): ItemFragment {
            return ItemFragment().apply {
                arguments = Bundle().apply {
                    putInt(TYPE_ID, type)
                }
            }
        }
    }
}