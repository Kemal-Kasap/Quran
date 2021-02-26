package com.kemal.kuran

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import com.tazkiyatech.quran.sdk.database.QuranDatabase
import com.tazkiyatech.quran.sdk.model.SectionType
import kotlinx.android.synthetic.main.activity_start.*

class Start : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_start)

        val db = Database(this)
        if(db.readAll().count() == 0){
            writeDatabase()
            //Toast.makeText(this,"Yükleniyor", Toast.LENGTH_SHORT).show()
        } else {
            val oran = db.oranAll()
            val yuzde = oran.ezberlenmis*100/oran.all
            allYuzde.text = "% $yuzde"
            allYuzde.setOnClickListener {
                Toast.makeText(this,oran.ezberlenmis.toString() + "/" + oran.all.toString(),Toast.LENGTH_SHORT).show()
            }

            //Toast.makeText(this,"Zaten Yüklendi", Toast.LENGTH_SHORT).show()
        }
        db.close()

        butonCuz.setOnClickListener {
            val intent = Intent(this, Enter::class.java)
            intent.putExtra("buton", "Cuz")
            startActivity(intent)
        }

        butonSure.setOnClickListener {
            val intent = Intent(this, Enter::class.java)
            intent.putExtra("buton", "Sure")
            startActivity(intent)
        }
    }

    fun writeDatabase(){
        val quranDatabase = QuranDatabase(this)
        quranDatabase.openDatabase()
        val db = Database(this)

        for(a in 1..7){
            val sureName = quranDatabase.getNameOfSurah(1)
            val arabic = quranDatabase.getAyah(1,a)
            val ezberDurumu = 0
            val ezberTarihi = "Bugün"
            val ayetYaz = Ayet(0,1,1,1,sureName,a,arabic,ezberDurumu,ezberTarihi)
            db.insert(ayetYaz)
        }

        for (i in 1..120){
            val type = SectionType.JUZ_QUARTER_IN_MAJEEDI_MUSHAF
            val cuzNo:Int = (i+3)/4
            val hizbNo:Int = i
            val firstSureNo = quranDatabase.getMetadataForSection(type,i).surahNumber
            val firstAyetNo = quranDatabase.getMetadataForSection(type,i).ayahNumber

            val lastSureNo = if(i == 120) 114 else quranDatabase.getMetadataForSection(type, i + 1).surahNumber
            val lastAyetNo = if(i == 120) 6 else quranDatabase.getMetadataForSection(type, i + 1).ayahNumber - 1

            for (s in firstSureNo..lastSureNo){
                if (firstSureNo == lastSureNo){
                    for (a in firstAyetNo..lastAyetNo){
                        val sureNo = s
                        val sureName = quranDatabase.getNameOfSurah(s)
                        val ayetNo = a
                        val arabic = quranDatabase.getAyah(s,a)
                        val ezberDurumu = 0
                        val ezberTarihi = "Bugün"
                        val ayetYaz = Ayet(0,cuzNo,hizbNo,sureNo,sureName,ayetNo,arabic,ezberDurumu,ezberTarihi)
                        db.insert(ayetYaz)
                    }
                }else {
                    if(s == firstSureNo) {
                        val totalAyetNo = quranDatabase.getAyahsInSurah(s).count()
                        for (a in firstAyetNo..totalAyetNo) {
                            val sureNo = s
                            val sureName = quranDatabase.getNameOfSurah(s)
                            val ayetNo = a
                            val arabic = quranDatabase.getAyah(s,a)
                            val ezberDurumu = 0
                            val ezberTarihi = "Bugün"
                            val ayetYaz = Ayet(0,cuzNo,hizbNo,sureNo,sureName,ayetNo,arabic,ezberDurumu,ezberTarihi)
                            db.insert(ayetYaz)
                        }
                    } else{
                        val son = if(s != lastSureNo) quranDatabase.getAyahsInSurah(s).count() else lastAyetNo
                        for (a in 1..son) {
                            val sureNo = s
                            val sureName = quranDatabase.getNameOfSurah(s)
                            val ayetNo = a
                            val arabic = quranDatabase.getAyah(s,a)
                            val ezberDurumu = 0
                            val ezberTarihi = "Bugün"
                            val ayetYaz = Ayet(0,cuzNo,hizbNo,sureNo,sureName,ayetNo,arabic,ezberDurumu,ezberTarihi)
                            db.insert(ayetYaz)
                        }
                    }
                }
            }
        }
        db.close()
    }
}
