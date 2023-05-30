package com.bangkit.dessert.home

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.widget.Toast
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.isVisible
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.GridLayoutManager
import com.bangkit.dessert.R
import com.bangkit.dessert.core.data.Resource
import com.bangkit.dessert.core.domain.model.DessertBrief
import com.bangkit.dessert.core.presentation.DessertAdapter
import com.bangkit.dessert.databinding.ActivityHomeBinding
import com.bangkit.dessert.detail.DetailActivity
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch

@AndroidEntryPoint
class HomeActivity : AppCompatActivity() {

    private lateinit var binding: ActivityHomeBinding
    private val viewModel: HomeViewModel by viewModels()
    private val dessertAdapter = DessertAdapter(this::navigateToDetail)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityHomeBinding.inflate(layoutInflater)
        setContentView(binding.root)

        // Setup view
        binding.rvDessert.apply {
            layoutManager = GridLayoutManager(context, 2)
            adapter = dessertAdapter
        }

        // Setup actions
        binding.swipeRefresh.setOnRefreshListener {
            viewModel.refresh()
        }
        binding.topAppBar.setOnMenuItemClickListener { menuItem ->
            when (menuItem.itemId) {
                R.id.favorite_action -> {
                    val uri = Uri.parse("dessert://favorite")
                    startActivity(Intent(Intent.ACTION_VIEW, uri))
                    true
                }

                else -> false
            }
        }

        // Observer
        lifecycleScope.launch {
            viewModel.dessertListFlow.collect { resource ->
                when (resource) {
                    is Resource.Error -> {
                        binding.progressBar.isVisible = false
                        binding.swipeRefresh.isRefreshing = false
                        Toast.makeText(this@HomeActivity, R.string.generic_error, Toast.LENGTH_LONG)
                            .show()
                    }

                    is Resource.Loading -> {
                        binding.progressBar.isVisible = true
                    }

                    is Resource.Success -> {
                        binding.progressBar.isVisible = false
                        binding.swipeRefresh.isRefreshing = false
                    }
                }

                dessertAdapter.submitList(resource.data)
            }
        }
    }

    private fun navigateToDetail(dessert: DessertBrief) {
        val intent = Intent(this, DetailActivity::class.java)
        intent.putExtra(DetailActivity.EXTRA_ID, dessert.id)
        startActivity(intent)
    }
}