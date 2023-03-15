package com.note.noteapp.activity

import android.os.Bundle
import android.view.Menu
import android.view.MenuInflater
import android.view.MenuItem
import androidx.activity.compose.setContent
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.MenuProvider
import androidx.core.view.WindowCompat
import com.google.android.material.composethemeadapter.MdcTheme
import com.note.noteapp.R
import com.note.noteapp.compose.NoteApp
import com.note.noteapp.compose.NotePage
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : AppCompatActivity() {

    private val menuProvider = object : MenuProvider {
        override fun onCreateMenu(menu: Menu, menuInflater: MenuInflater) {
            menuInflater.inflate(R.menu.menu_note_list, menu)
        }

        override fun onMenuItemSelected(menuItem: MenuItem): Boolean {
            return when (menuItem.itemId) {
                R.id.filter_zone -> {
                    //viewModel.updateData()
                    true
                }
                else -> false
            }
        }
    }


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        // Displaying edge-to-edge
        WindowCompat.setDecorFitsSystemWindows(window, false)

        setContent {
            MdcTheme {
                NoteApp(
                    onAttached = { toolbar ->
                        // setSupportActionBar(toolbar)
                    },
                    onPageChange = { page ->
                        when (page) {
                            NotePage.ALL_NOTE -> removeMenuProvider(menuProvider)
                            NotePage.DONE_NOTE -> addMenuProvider(
                                menuProvider,
                                this
                            )
                        }
                    }
                )
            }
        }

    }
}

