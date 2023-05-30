package com.bangkit.dessert.favorite

import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.isVisible
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.GridLayoutManager
import com.bangkit.dessert.R
import com.bangkit.dessert.core.data.Resource
import com.bangkit.dessert.core.domain.model.DessertBrief
import com.bangkit.dessert.core.presentation.DessertAdapter
import com.bangkit.dessert.detail.DetailActivity
import com.bangkit.dessert.di.FavoriteModuleDependencies
import com.bangkit.dessert.favorite.databinding.ActivityFavoriteBinding
import dagger.hilt.android.EntryPointAccessors
import kotlinx.coroutines.launch
import javax.inject.Inject

class FavoriteActivity : AppCompatActivity() {

    @Inject
    lateinit var viewModel: FavoriteViewModel

    private lateinit var binding: ActivityFavoriteBinding
    private val dessertAdapter = DessertAdapter(this::navigateToDetail)

    override fun onCreate(savedInstanceState: Bundle?) {
        initCoreInjection()
        super.onCreate(savedInstanceState)
        binding = ActivityFavoriteBinding.inflate(layoutInflater)
        setContentView(binding.root)

        // Setup view
        binding.rvDessert.apply {
            layoutManager = GridLayoutManager(context, 2)
            adapter = dessertAdapter
        }

        // Setup actions
        binding.topAppBar.setNavigationOnClickListener {
            finish()
        }

        // Observer
        lifecycleScope.launch {
            viewModel.favoriteDessertListFlow.collect { resource ->
                when (resource) {
                    is Resource.Error -> {
                        binding.progressBar.isVisible = false
                        Toast.makeText(this@FavoriteActivity, R.string.generic_error, Toast.LENGTH_LONG)
                            .show()
                    }

                    is Resource.Loading -> {
                        binding.progressBar.isVisible = true
                    }

                    is Resource.Success -> {
                        binding.progressBar.isVisible = false
                    }
                }

                dessertAdapter.submitList(resource.data)
            }
        }
    }

    private fun initCoreInjection() {
        DaggerFavoriteComponent.builder()
            .context(this)
            .appDependencies(
                EntryPointAccessors.fromApplication(
                    applicationContext,
                    FavoriteModuleDependencies::class.java
                )
            )
            .build()
            .inject(this)
    }

    private fun navigateToDetail(dessert: DessertBrief) {
        val intent = Intent(this, DetailActivity::class.java)
        intent.putExtra(DetailActivity.EXTRA_ID, dessert.id)
        startActivity(intent)
    }
}