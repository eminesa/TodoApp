package com.note.noteapp.compose.home

import androidx.appcompat.app.AppCompatActivity
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.pager.HorizontalPager
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.material.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.input.nestedscroll.nestedScroll
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.rememberNestedScrollInteropConnection
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.viewinterop.AndroidViewBinding
import com.google.android.material.appbar.MaterialToolbar
import com.note.noteapp.compose.NotePage
import com.note.noteapp.compose.allnotelist.AllNoteScreen
import com.note.noteapp.compose.donelist.DoneScreen
import com.note.noteapp.data.Note
import com.note.noteapp.databinding.HomeScreenBinding
import kotlinx.coroutines.launch

@Composable
fun HomeScreen(
    modifier: Modifier = Modifier,
    onNoteClick: (Note) -> Unit = {},
    onPageChange: (NotePage) -> Unit = {},
    onAttached: (MaterialToolbar) -> Unit = {},
) {
    val activity = (LocalContext.current as AppCompatActivity)

    AndroidViewBinding(factory = HomeScreenBinding::inflate, modifier = modifier) {
        onAttached(toolbar)
        activity.setSupportActionBar(toolbar)
        composeView.setContent {
            HomePagerScreen(onNoteClick = onNoteClick, onPageChange = onPageChange)
        }
    }

}

@OptIn(ExperimentalFoundationApi::class)
@Composable
fun HomePagerScreen(
    onNoteClick: (Note) -> Unit,
    onPageChange: (NotePage) -> Unit,
    modifier: Modifier = Modifier,
    pages: Array<NotePage> = NotePage.values()
) {
    val pagerState = rememberPagerState()

    LaunchedEffect(pagerState.currentPage) {
        onPageChange(pages[pagerState.currentPage])
    }

    // Use Modifier.nestedScroll + rememberNestedScrollInteropConnection() here so that this
    // composable participates in the nested scroll hierarchy so that HomeScreen can be used in
    // use cases like a collapsing toolbar
    Column(modifier.nestedScroll(rememberNestedScrollInteropConnection())) {
        val coroutineScope = rememberCoroutineScope()

        // Tab Row
        TabRow(selectedTabIndex = pagerState.currentPage) {
            pages.forEachIndexed { index, page ->
                val title = stringResource(id = page.titleResId)
                Tab(
                    selected = pagerState.currentPage == index,
                    onClick = { coroutineScope.launch { pagerState.animateScrollToPage(index) } },
                    text = { Text(text = title) },
                    icon = {
                        Icon(
                            painter = painterResource(id = page.drawableResId),
                            contentDescription = title
                        )
                    },
                    unselectedContentColor = MaterialTheme.colors.primaryVariant,
                    selectedContentColor = MaterialTheme.colors.secondary,
                )
            }
        }

        // Pages
        HorizontalPager(
            pageCount = pages.size, state = pagerState, verticalAlignment = Alignment.Top
        ) { index ->
            when (pages[index]) {
                NotePage.ALL_NOTE -> {
                    AllNoteScreen(
                        Modifier.fillMaxSize(),
                        onAddNoteClick = {
                            coroutineScope.launch {
                                pagerState.scrollToPage(NotePage.ALL_NOTE.ordinal)
                            }
                        }
                    )
                }
                NotePage.DONE_NOTE -> {
                    DoneScreen(
                        modifier = Modifier.fillMaxSize(),
                        onAddNoteClick = {
                            coroutineScope.launch {
                                pagerState.scrollToPage(NotePage.DONE_NOTE.ordinal)
                            }
                        },
                      )
                }
            }
        }
    }
}