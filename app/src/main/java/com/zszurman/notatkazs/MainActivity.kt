package com.zszurman.notatkazs


import android.app.SearchManager
import android.content.Context
import android.content.Intent
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

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        LoadQery("%")
    }

    override fun onResume() {
        super.onResume()
        LoadQery("%")
    }

    private fun LoadQery(title: String) {
        var dbHelper = DbHelper(this)
        val projections = arrayOf(TableInfo.COL_ID, TableInfo.COL_TITLE, TableInfo.COL_DES)
        val selectionArgs = arrayOf(title)
        val cursor = dbHelper.qery(projections, "Title like ?", selectionArgs, "Title")
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
                LoadQery("%" + query + "%")
                return false
            }

            override fun onQueryTextChange(newText: String?): Boolean {
                LoadQery("%" + newText + "%")
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
                R.id.action_settings -> {
                    Toast.makeText(this, "Settings", Toast.LENGTH_SHORT).show()
                }
            }
        }

        return super.onOptionsItemSelected(item)
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
                var dbHelper = DbHelper(this.context!!)
                val selectionArgs = arrayOf(myNote.nodeId.toString())
                dbHelper.delete("ID=?", selectionArgs)
                LoadQery("%")
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
                Toast.makeText(this@MainActivity, "Copied", Toast.LENGTH_SHORT).show()

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