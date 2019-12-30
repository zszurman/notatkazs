package com.zszurman.notatkazs


import android.app.AlertDialog
import android.app.SearchManager
import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.ClipboardManager
import android.view.Menu
import android.view.MenuItem
import android.view.View
import android.view.ViewGroup
import android.widget.BaseAdapter


import android.widget.Toast
import androidx.appcompat.widget.SearchView

import kotlinx.android.synthetic.main.activity_main.*


import kotlinx.android.synthetic.main.row.view.*

class MainActivity : AppCompatActivity() {

    var listNotes = ArrayList<Note>()
    var mSharedPreferences: SharedPreferences? = null


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        mSharedPreferences =
            this.getSharedPreferences("My_Data", android.content.Context.MODE_PRIVATE)


        val mSorting = mSharedPreferences!!.getString("Sort", "najnowsza")
        when (mSorting) {
            "najnowsza" -> loadQeryNewest("%")
            "najstarsza" -> loadQeryOldest("%")
            "rosnaco" -> loadQeryAscending("%")
            "malejaco" -> loadQeryDescending("%")

        }

    }

    override fun onResume() {
        super.onResume()
        val mSorting = mSharedPreferences!!.getString("Sort", "najnowsza")
        when (mSorting) {
            "najnowsza" -> loadQeryNewest("%")
            "najstarsza" -> loadQeryOldest("%")
            "rosnaco" -> loadQeryAscending("%")
            "malejaco" -> loadQeryDescending("%")
        }

    }


    private fun loadQeryAscending(title: String) {
        var dbHelper = DbHelper(this)
        val projections = arrayOf(TableInfo.COL_ID, TableInfo.COL_TITLE, TableInfo.COL_DES)
        val selectionArgs = arrayOf(title)

        val cursor = dbHelper.qery(
            projections,
            TableInfo.COL_TITLE + " like ?",
            selectionArgs,
            TableInfo.COL_TITLE
        )
        listNotes.clear()

        if (cursor.moveToFirst()) {
            do {
                val ID = cursor.getInt(cursor.getColumnIndex(TableInfo.COL_ID))
                val Title = cursor.getString(cursor.getColumnIndex(TableInfo.COL_TITLE))
                val Description = cursor.getString(cursor.getColumnIndex(TableInfo.COL_DES))

                listNotes.add(Note(ID, Title, Description))
            } while (cursor.moveToNext())
        }

        var myNotesAdapter = MyNotesAdapter(this, listNotes)
        notesZs.adapter = myNotesAdapter

        val total = notesZs.count
        val mActionBar = supportActionBar
        if (mActionBar != null) {
            if (total == 0) {
                mActionBar.subtitle = "Nie masz notatek"

            } else if (total == 1) {
                mActionBar.subtitle = "Masz 1 notatkę"
            } else {
                mActionBar.subtitle = "Ilość notatek: $total"
            }

        }
    }

    private fun loadQeryDescending(title: String) {
        var dbHelper = DbHelper(this)
        val projections = arrayOf(TableInfo.COL_ID, TableInfo.COL_TITLE, TableInfo.COL_DES)
        val selectionArgs = arrayOf(title)

        val cursor = dbHelper.qery(
            projections,
            TableInfo.COL_TITLE + " like ?",
            selectionArgs,
            TableInfo.COL_TITLE
        )
        listNotes.clear()

        if (cursor.moveToLast()) {
            do {
                val ID = cursor.getInt(cursor.getColumnIndex(TableInfo.COL_ID))
                val Title = cursor.getString(cursor.getColumnIndex(TableInfo.COL_TITLE))
                val Description = cursor.getString(cursor.getColumnIndex(TableInfo.COL_DES))

                listNotes.add(Note(ID, Title, Description))
            } while (cursor.moveToPrevious())
        }

        var myNotesAdapter = MyNotesAdapter(this, listNotes)
        notesZs.adapter = myNotesAdapter

        val total = notesZs.count
        val mActionBar = supportActionBar
        if (mActionBar != null) {
            if (total == 0) {
                mActionBar.subtitle = "Nie masz notatki"

            } else if (total == 1) {
                mActionBar.subtitle = "Masz 1 notatkę"
            } else {
                mActionBar.subtitle = "Ilość notatek: $total "
            }

        }
    }

    private fun loadQeryNewest(title: String) {
        var dbHelper = DbHelper(this)
        val projections = arrayOf(TableInfo.COL_ID, TableInfo.COL_TITLE, TableInfo.COL_DES)
        val selectionArgs = arrayOf(title)

        val cursor = dbHelper.qery(
            projections,
            TableInfo.COL_ID + " like ?",
            selectionArgs,
            TableInfo.COL_ID
        )
        listNotes.clear()

        if (cursor.moveToLast()) {
            do {
                val ID = cursor.getInt(cursor.getColumnIndex(TableInfo.COL_ID))
                val Title = cursor.getString(cursor.getColumnIndex(TableInfo.COL_TITLE))
                val Description = cursor.getString(cursor.getColumnIndex(TableInfo.COL_DES))

                listNotes.add(Note(ID, Title, Description))
            } while (cursor.moveToPrevious())
        }

        var myNotesAdapter = MyNotesAdapter(this, listNotes)
        notesZs.adapter = myNotesAdapter

        val total = notesZs.count
        val mActionBar = supportActionBar
        if (mActionBar != null) {
            if (total == 0) {
                mActionBar.subtitle = "Nie masz notatki"

            } else if (total == 1) {
                mActionBar.subtitle = "Masz 1 notatkę"
            } else {
                mActionBar.subtitle = "Ilość notatek: $total "
            }

        }
    }

    private fun loadQeryOldest(title: String) {
        var dbHelper = DbHelper(this)
        val projections = arrayOf(TableInfo.COL_ID, TableInfo.COL_TITLE, TableInfo.COL_DES)
        val selectionArgs = arrayOf(title)

        val cursor = dbHelper.qery(
            projections,
            TableInfo.COL_ID + " like ?",
            selectionArgs,
            TableInfo.COL_ID
        )
        listNotes.clear()

        if (cursor.moveToFirst()) {
            do {
                val ID = cursor.getInt(cursor.getColumnIndex(TableInfo.COL_ID))
                val Title = cursor.getString(cursor.getColumnIndex(TableInfo.COL_TITLE))
                val Description = cursor.getString(cursor.getColumnIndex(TableInfo.COL_DES))

                listNotes.add(Note(ID, Title, Description))
            } while (cursor.moveToNext())
        }

        var myNotesAdapter = MyNotesAdapter(this, listNotes)
        notesZs.adapter = myNotesAdapter

        val total = notesZs.count
        val mActionBar = supportActionBar
        if (mActionBar != null) {
            if (total == 0) {
                mActionBar.subtitle = "Nie masz notatki"

            } else if (total == 1) {
                mActionBar.subtitle = "Masz 1 notatkę"
            } else {
                mActionBar.subtitle = "Ilość notatek: $total "
            }

        }
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {

        menuInflater.inflate(R.menu.main_menu, menu)
        val sv: SearchView = menu!!.findItem(R.id.app_bar_search).actionView as SearchView

        val sm = getSystemService(Context.SEARCH_SERVICE) as SearchManager
        sv.setSearchableInfo(sm.getSearchableInfo(componentName))
        sv.setOnQueryTextListener(object : SearchView.OnQueryTextListener {

            override fun onQueryTextSubmit(query: String?): Boolean {
                loadQeryAscending("%" + query + "%")
                return false
            }

            override fun onQueryTextChange(newText: String?): Boolean {
                loadQeryAscending("%" + newText + "%")
                return false
            }
        })

        return super.onCreateOptionsMenu(menu)

    }

    override fun onOptionsItemSelected(item: MenuItem?): Boolean {
        if (item != null) {
            when (item.itemId) {
                R.id.addNote -> {
                    startActivity(Intent(this, AddNoteActivity::class.java))
                }
                R.id.action_sort -> {
                    showSortDialog()
                }
            }
        }

        return super.onOptionsItemSelected(item)
    }

    private fun showSortDialog() {
        val sortOptions = arrayOf("najnowsza", "najstarsza", "alfabetycznie(+)", "alfabetycznie(-)")
        val mBilder = AlertDialog.Builder(this)
        mBilder.setTitle("Sortuj wg")
        mBilder.setIcon(R.drawable.ic_action_sort)
        mBilder.setSingleChoiceItems(sortOptions, -1) { dialogInterface, i ->
            if (i == 0) {
                Toast.makeText(this, "najnowsza", Toast.LENGTH_SHORT).show()
                val editor = mSharedPreferences!!.edit()
                editor.putString("Sort", "najnowsza")
                editor.apply()
                loadQeryNewest("%")
            }
            if (i == 1) {
                Toast.makeText(this, "najstarsza", Toast.LENGTH_SHORT).show()
                val editor = mSharedPreferences!!.edit()
                editor.putString("Sort", "najstarsza")
                editor.apply()
                loadQeryOldest("%")

            }
            if (i == 2) {
                Toast.makeText(this, "alfabetycznie(+)", Toast.LENGTH_SHORT).show()
                val editor = mSharedPreferences!!.edit()
                editor.putString("Sort", "rosnaco")
                editor.apply()
                loadQeryAscending("%")

            }
            if (i == 3) {
                Toast.makeText(this, "alfabetycznie(-)", Toast.LENGTH_SHORT).show()
                val editor = mSharedPreferences!!.edit()
                editor.putString("Sort", "malejaco")
                editor.apply()
                loadQeryDescending("%")

            }
            dialogInterface.dismiss()
        }

        val mDialog = mBilder.create()
        mDialog.show()

    }

    inner class MyNotesAdapter : BaseAdapter {

        var listNotesAdapter = ArrayList<Note>()
        var context: Context? = null

        constructor(context: Context, listNotesArray: ArrayList<Note>) : super() {
            this.listNotesAdapter = listNotesArray
            this.context = context
        }


        override fun getView(position: Int, convertView: View?, parent: ViewGroup?): View {
            var myView = layoutInflater.inflate(R.layout.row, null)
            var myNote = listNotesAdapter[position]
            myView.titleTv.text = myNote.nodeName
            myView.descTv.text = myNote.nodeDes

            myView.deleteBtn.setOnClickListener {

                val builder = AlertDialog.Builder(this@MainActivity)
                builder.setTitle("Uwaga")
                builder.setMessage("Chcesz usunąć notatkę")

                builder.setPositiveButton("Tak") { dialog, which ->
                    var dbHelper = DbHelper(this.context!!)
                    val selectionArgs = arrayOf(myNote.nodeId.toString())
                    dbHelper.delete("ID=?", selectionArgs)
                    loadQeryAscending("%")
                    Toast.makeText(this@MainActivity, "Notatkę usunięto", Toast.LENGTH_SHORT).show()
                }

                builder.setNegativeButton("Nie") { dialog, which ->
                    Toast.makeText(applicationContext, "Nie usunięto notatki", Toast.LENGTH_SHORT)
                        .show()
                }

                val dialog: AlertDialog = builder.create()
                dialog.show()

            }

            myView.editBtn.setOnClickListener {
                GoToUpdateFun(myNote)
            }

            myView.copyBtn.setOnClickListener {

                val title = myView.titleTv.text.toString()
                val desc = myView.descTv.text.toString()
                val s = title + "\n" + desc
                val cb = getSystemService(Context.CLIPBOARD_SERVICE) as ClipboardManager
                cb.text = s
                Toast.makeText(this@MainActivity, "Skopiowano", Toast.LENGTH_SHORT).show()

            }

            myView.shareBtn.setOnClickListener {
                val title = myView.titleTv.text.toString()
                val desc = myView.descTv.text.toString()
                val s = title + "\n" + desc
                val shareIntent = Intent()
                shareIntent.action = Intent.ACTION_SEND
                shareIntent.type = "text/plain"
                shareIntent.putExtra(Intent.EXTRA_TEXT, s)
                startActivity(Intent.createChooser(shareIntent, s))
            }
            return myView
        }

        override fun getItem(position: Int): Any {
            return listNotesAdapter[position]


        }

        override fun getItemId(position: Int): Long {
            return position.toLong()
        }

        override fun getCount(): Int {
            return listNotesAdapter.size
        }

    }

    private fun GoToUpdateFun(myNote: Note) {
        var intent = Intent(this, AddNoteActivity::class.java)
        intent.putExtra("ID", myNote.nodeId)

        intent.putExtra("name", myNote.nodeName)
        intent.putExtra("des", myNote.nodeDes)
        startActivity(intent)

    }


}