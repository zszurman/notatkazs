package com.zszurman.notatkazs

import android.content.ContentValues
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Toast
import kotlinx.android.synthetic.main.activity_add_note.*
import java.lang.Exception

class AddNoteActivity : AppCompatActivity() {
    var id = 0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_add_note)

        try {
            val bundle:Bundle = intent.extras
            id = bundle.getInt("ID",0)
            if (id !=0){
                supportActionBar!!.title = "Aktualizacja notatki"
                addBtn.text = "Aktualizuj"
                titleEt.setText(bundle.getString("name"))
                descEt.setText(bundle.getString("des"))

            }


        } catch (ex:Exception){}


    }

    fun klik(view: View) {
        var dbHelper = DbHelper(this)
        var values = ContentValues()
        values.put(TableInfo.COL_TITLE, titleEt.text.toString())
        values.put(TableInfo.COL_DES, descEt.text.toString())

        if (id == 0) {
            val ID = dbHelper.insert(values)
            if (ID > 0) {
                Toast.makeText(this, "Notatkę dodano", Toast.LENGTH_SHORT).show()
                finish()
            }else {
                Toast.makeText(this, "Błąd w dodaniu notatki", Toast.LENGTH_SHORT).show()

            }

        } else {
            var selectionArgs = arrayOf(id.toString())
            val ID = dbHelper.update(values, "ID=?", selectionArgs)
            if (ID>0){
                Toast.makeText(this, "Notatkę zaktualizowano", Toast.LENGTH_SHORT).show()
                finish()

            }
            else {
                Toast.makeText(this, "Błąd w aktualizacji notatki", Toast.LENGTH_SHORT).show()

            }


        }


    }
}
