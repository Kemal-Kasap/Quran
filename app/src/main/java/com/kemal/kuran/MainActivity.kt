package com.kemal.kuran

//JUZ_IN_MAJEEDI_MUSHAF diyanetle cüzler tutuyor.

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.mikepenz.fastadapter.FastAdapter
import com.mikepenz.fastadapter.adapters.ItemAdapter
import com.mikepenz.fastadapter.listeners.ClickEventHook
import com.tazkiyatech.quran.sdk.database.QuranDatabase
import com.tazkiyatech.quran.sdk.model.SectionType
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.sample_item.view.*
import java.text.SimpleDateFormat
import java.util.*
import kotlin.collections.ArrayList

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val quranDatabase = QuranDatabase(this)
        quranDatabase.openDatabase()

        val intent = intent
        val butonType = intent.getStringExtra("buton")
        val position = intent.getIntExtra("position",0)
        val sureNo = position + 1
        val hizbNo = position + 1
        val items = ArrayList<SimpleItem>()

        val db = Database(this)
        if (butonType == "Sure"){
            val ayets = db.readSurah(sureNo.toString())
            for(i in 1..ayets.count()){
                val item = SimpleItem()
                item.id = ayets[i-1].id
                item.no = ayets[i-1].ayet_no.toString()
                item.ayah = ayets[i-1].ayet
                item.check = (ayets[i-1].ezber_durumu) == 1
                items.add(item)
            }
        } else {
            val ayets = db.readHizb(hizbNo.toString())
            for(i in 1..ayets.count()){
                val item = SimpleItem()
                item.id = ayets[i-1].id
                item.no = ayets[i-1].sure_name + "  " + ayets[i-1].ayet_no.toString()
                item.ayah = ayets[i-1].ayet
                item.check = (ayets[i-1].ezber_durumu) == 1
                items.add(item)
            }
        }
        db.close()


        val itemAdapter = ItemAdapter<SimpleItem>()
        val fastAdapter = FastAdapter.with(itemAdapter)

        Recylerim.setAdapter(fastAdapter)

        itemAdapter.add(items)

        val layoutManager = LinearLayoutManager(this)
        Recylerim!!.layoutManager = layoutManager

        // just add an `EventHook` to your `FastAdapter` by implementing either a `ClickEventHook`, `LongClickEventHook`, `TouchEventHook`, `CustomEventHook`
        fastAdapter.addEventHook(object : ClickEventHook<SimpleItem>() {
            override fun onBind(viewHolder: RecyclerView.ViewHolder): View? {
                //return the views on which you want to bind this event
                return if (viewHolder is SimpleItem.ViewHolder) {
                    viewHolder.check
                } else {
                    null
                }
            }

            override fun onClick(v: View, position: Int, fastAdapter: FastAdapter<SimpleItem>, item: SimpleItem) {
                //Toast.makeText(applicationContext, v.checkbox.isChecked.toString(), Toast.LENGTH_SHORT).show()
                val chek =  if(v.checkbox.isChecked) 1 else 0
                val sdf = SimpleDateFormat("yyyy/MM/dd hh:mm:ss")
                val currentDate = sdf.format(Date())
                val datam = Database(applicationContext)
                datam.update(item.id.toString(), chek.toString(), currentDate)
                datam.close()
            }
        })
    }


}
