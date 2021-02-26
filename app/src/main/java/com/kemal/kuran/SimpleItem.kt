package com.kemal.kuran

import android.view.View
import android.widget.CheckBox
import android.widget.TextView
import com.mikepenz.fastadapter.FastAdapter
import com.mikepenz.fastadapter.items.AbstractItem

open class SimpleItem : AbstractItem<SimpleItem.ViewHolder>() {
    var id: Int? = null
    var no: String? = null
    var ayah: String? = null
    var check: Boolean = false

    /** defines the type defining this item. must be unique. preferably an id */
    override val type: Int
        get() = R.id.Recylerim

    /** defines the layout which will be used for this item in the list */
    override val layoutRes: Int
        get() = R.layout.sample_item

    override fun getViewHolder(v: View): ViewHolder {
        return ViewHolder(v)
    }

    class ViewHolder(view: View) : FastAdapter.ViewHolder<SimpleItem>(view) {
        var no: TextView = view.findViewById(R.id.no)
        var ayah: TextView = view.findViewById(R.id.ayah)
        var check: CheckBox = view.findViewById(R.id.checkbox)

        override fun bindView(item: SimpleItem, payloads: List<Any>) {
            no.text = item.no
            ayah.text = item.ayah
            check.isChecked = item.check
        }

        override fun unbindView(item: SimpleItem) {
            no.text = null
            ayah.text = null
            check.isChecked = false
        }
    }
}