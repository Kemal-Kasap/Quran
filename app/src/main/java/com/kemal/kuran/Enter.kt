package com.kemal.kuran

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.mikepenz.fastadapter.FastAdapter
import com.mikepenz.fastadapter.IAdapter
import com.mikepenz.fastadapter.adapters.ItemAdapter
import com.mikepenz.fastadapter.listeners.ClickEventHook
import com.tazkiyatech.quran.sdk.database.QuranDatabase
import kotlinx.android.synthetic.main.activity_enter.*

class Enter : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_enter)

        val itemAdapter = ItemAdapter<EnterItem>()
        val fstAdapter = FastAdapter.with(itemAdapter)

        RecylerEnter.adapter = fstAdapter


        val quranDatabase = QuranDatabase(this)
        quranDatabase.openDatabase()

        val items = ArrayList<EnterItem>()

        val intent = intent
        val butonType = intent.getStringExtra("buton")

        val database = Database(this)
        if (butonType == "Cuz"){
            for (i in 1..120){
                val item = EnterItem()
                val hizb = if(i%4 == 0) "4" else (i%4).toString()
                val oran = database.oranHizb(i.toString())
                item.cuzMu = true
                item.name = ((i+3)/4).toString() + ". Cüz   " + hizb + ". Hizb"
                item.ezberlenmis = oran.ezberlenmis
                item.all = oran.all
                //item.oran = oran.ezberlenmis.toString() + "/" + oran.all.toString()
                items.add(item)
            }
        } else if (butonType == "Sure"){
            for (i in 1..114){
                val item = EnterItem()
                val oran = database.oranSurah(i.toString())
                item.cuzMu = false
                item.name = i.toString() + "   " + quranDatabase.getNameOfSurah(i)
                item.ezberlenmis = oran.ezberlenmis
                item.all = oran.all
                //item.oran = oran.ezberlenmis.toString() + "/" + oran.all.toString()
                items.add(item)
            }
        }
        database.close()

        itemAdapter.add(items)

        fstAdapter.onClickListener = { view, adapter, item, position ->
            val into = Intent(this,MainActivity::class.java)
            into.putExtra("buton", butonType)
            into.putExtra("position", position)
            startActivity(into)
            false
        }

        fstAdapter.onLongClickListener = { view, adapter, item, position ->
            Toast.makeText(applicationContext, item.oran.toString(), Toast.LENGTH_SHORT).show()
            true
        }


        val layoutManager = LinearLayoutManager(this)
        RecylerEnter!!.layoutManager = layoutManager



    }
}
