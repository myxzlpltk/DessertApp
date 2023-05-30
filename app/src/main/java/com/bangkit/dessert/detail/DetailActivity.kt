package com.bangkit.dessert.detail

import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.isVisible
import androidx.lifecycle.lifecycleScope
import com.bangkit.dessert.R
import com.bangkit.dessert.core.data.Resource
import com.bangkit.dessert.core.domain.model.Dessert
import com.bangkit.dessert.core.utils.load
import com.bangkit.dessert.core.utils.toBulletedList
import com.bangkit.dessert.databinding.ActivityDetailBinding
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch

@AndroidEntryPoint
class DetailActivity : AppCompatActivity() {

    private lateinit var binding: ActivityDetailBinding
    private val viewModel: DetailViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityDetailBinding.inflate(layoutInflater)
        setContentView(binding.root)

        // Setup actions
        binding.topAppBar.setNavigationOnClickListener {
            finish()
        }
        binding.swipeRefresh.setOnRefreshListener {
            viewModel.refresh()
        }
        binding.favoriteToggle.setOnCheckedChangeListener { _, isFavorite ->
            viewModel.setFavorite(isFavorite)
        }

        // Observers
        lifecycleScope.launch {
            viewModel.dessertFlow.collect { resource ->
                when (resource) {
                    is Resource.Error -> {
                        binding.progressBar.isVisible = false
                        binding.swipeRefresh.isRefreshing = false
                        binding.swipeRefresh.visibility = View.VISIBLE
                        Toast.makeText(
                            this@DetailActivity,
                            R.string.generic_error,
                            Toast.LENGTH_LONG
                        )
                            .show()
                    }

                    is Resource.Loading -> {
                        binding.progressBar.isVisible = true
                        binding.swipeRefresh.visibility = View.GONE
                    }

                    is Resource.Success -> {
                        binding.progressBar.isVisible = false
                        binding.swipeRefresh.isRefreshing = false
                        binding.swipeRefresh.visibility = View.VISIBLE
                    }
                }

                resource.data?.let { bind(it) }
            }
        }
    }

    private fun bind(dessert: Dessert) = with(binding) {
        // Load image
        dessertImage.load(dessert.image, 500)

        // Load data
        dessertName.text = dessert.name
        dessertArea.text = getString(R.string.dessert_area_template, dessert.area)
        dessertIngredients.text =
            dessert.ingredients.map { "${it.measure} ${it.name}" }.toBulletedList()
        dessertInstructions.text = dessert.instructions

        // Load state
        favoriteToggle.isChecked = dessert.isFavorite
    }

    companion object {
        const val EXTRA_ID: String = "id_extra"
    }
}