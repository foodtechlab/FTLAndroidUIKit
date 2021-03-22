package com.foodtechlab.ftlandroiduikitsample.components

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.RecyclerView
import com.foodtechlab.ftlandroiduikit.textfield.helper.ImageType
import com.foodtechlab.ftlandroiduikitsample.R
import com.foodtechlab.ftlandroiduikitsample.base.BaseListAdapter
import com.foodtechlab.ftlandroiduikitsample.base.ItemBase
import com.foodtechlab.ftlandroiduikitsample.utils.Utils.getString
import kotlinx.android.synthetic.main.rv_payment_item.view.*

class PaymentItemRVAdapter :
    BaseListAdapter<ItemBase, PaymentItemRVAdapter.PaymentViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PaymentViewHolder {
        return PaymentViewHolder(
            LayoutInflater.from(parent.context).inflate(viewType, parent, false)
        )
    }

    override fun onBindViewHolder(holder: PaymentViewHolder, position: Int) {
        holder.bind()
    }

    inner class PaymentViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

        fun bind() {
            val item = getItem(adapterPosition) as ItemPayment
            with(itemView.tvPayment) {
                textTopStartSlot = item.name
                textBottomStartSlot = getString(R.string.common_price_format, item.amount)
                colorBottomStartSlot = if (item.type == PaymentType.MONEY) ContextCompat.getColor(context, R.color.AdditionalPink) else ContextCompat.getColor(context, R.color.AdditionalGreen)
                imageType =
                    if (item.type == PaymentType.MONEY) ImageType.CASH else ImageType.CARD_ONLINE
            }
        }
    }
}