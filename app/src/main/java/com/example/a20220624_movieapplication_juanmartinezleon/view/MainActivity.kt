package com.example.a20220624_movieapplication_juanmartinezleon.view

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.example.a20220624_movieapplication_juanmartinezleon.R
import com.example.a20220624_movieapplication_juanmartinezleon.view_model.GenreViewModel
import com.example.a20220624_movieapplication_juanmartinezleon.view_model.MovieViewModel
import org.koin.androidx.viewmodel.ext.android.viewModel

class MainActivity : AppCompatActivity() {
    private val genreViewModel by viewModel<GenreViewModel>()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        genreViewModel.setLoadingState()
        setContentView(R.layout.activity_main)
    }
}