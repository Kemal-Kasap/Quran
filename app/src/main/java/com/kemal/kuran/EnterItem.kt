package com.kemal.kuran

import android.content.Intent
import android.view.View
import android.widget.Button
import android.widget.TextView
import com.mikepenz.fastadapter.FastAdapter
import com.mikepenz.fastadapter.items.AbstractItem

open class EnterItem : AbstractItem<EnterItem.ViewHolder>() {
    var name: String? = null
    var oran: String? = null
    var cuzMu: Boolean = false
    var ezberlenmis: Int = 0
    var all: Int = 0

    /** defines the type defining this item. must be unique. preferably an id */
    override val type: Int
        get() = R.id.RecylerEnter

    /** defines the layout which will be used for this item in the list */
    override val layoutRes: Int
        get() = R.layout.enter_item

    override fun getViewHolder(v: View): ViewHolder {
        return ViewHolder(v)
    }

    class ViewHolder(view: View) : FastAdapter.ViewHolder<EnterItem>(view) {
        var buton: TextView = view.findViewById(R.id.buttonEnter)
        var oran: TextView = view.findViewById(R.id.oranım)
        var oranCuz: TextView = view.findViewById(R.id.oranCuz)

        override fun bindView(item: EnterItem, payloads: List<Any>) {
            buton.text = item.name
            item.oran = item.ezberlenmis.toString() + "/" + item.all.toString()
            val yuzde = item.ezberlenmis*100/item.all
            if(item.cuzMu){
                oranCuz.visibility = View.VISIBLE
                oranCuz.text = "%$yuzde"
            } else {
                oran.visibility = View.VISIBLE
                oran.text = "%$yuzde"
            }
        }

        override fun unbindView(item: EnterItem) {
            buton.text = null
            oran.text = null
            oranCuz.text = null
            oran.visibility = View.GONE
            oranCuz.visibility = View.GONE

        }
    }
}