package com.neugelb.ui.fragment

import android.content.Intent
import android.view.View
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.neugelb.R
import com.neugelb.adapter.AutoCompleteAdapter
import com.neugelb.adapter.MovieListAdapter
import com.neugelb.base.BaseFragment
import com.neugelb.binding.setBottomListener
import com.neugelb.config.INTENT_ID
import com.neugelb.config.TYPE_NAVIGATE_NO_CONNECTION
import com.neugelb.data.Status
import com.neugelb.data.model.Movie
import com.neugelb.databinding.FragmentHomeBinding
import com.neugelb.interfaces.Connect
import com.neugelb.room.database.RoomResponse
import com.neugelb.ui.activity.DetailActivity
import com.neugelb.utils.SnackBar.Companion.showError
import com.neugelb.viewmodel.fragment.HomeViewModel
import kotlinx.coroutines.flow.collectLatest
import javax.inject.Inject

class HomeFragment : BaseFragment<HomeViewModel, FragmentHomeBinding>() {
    @Inject
    lateinit var mAdapter: MovieListAdapter

    @Inject
    lateinit var autoCompleteAdapter: AutoCompleteAdapter

    override fun getLayoutId(): Int = R.layout.fragment_home
    override fun getViewModel(): Class<HomeViewModel> = HomeViewModel::class.java
    override fun setViewModel() {
        binding.viewModel = viewModel
    }


    override fun setupView() {
        with(binding) {
            recyclerview.adapter = mAdapter.apply {
                itemClickListener = { onMovieItemClick(it) }
            }
            autoCompleteTv.setAdapter(autoCompleteAdapter)
            GridLayoutManager(
                requireContext(), 2, RecyclerView.VERTICAL, false
            ).apply {
                recyclerview.layoutManager = this
            }
            recyclerview.setBottomListener {
                viewModel?.loadMore()
            }
            autoCompleteTv.setOnItemClickListener { _, _, position, _ ->
                onMovieItemClick(autoCompleteAdapter.getItem(position)!!)
            }
            lifecycleScope.launchWhenCreated {
                setObservables()
                collectData()
            }
        }
    }

    private fun setObservables() {
        viewModel.offlineLiveData.observe(this) {
            when (it) {
                is RoomResponse.Success -> {
                    mAdapter.updateData(it.data)
                    viewModel.updatePage()
                    updateView(isLoading = false)
                }
                is RoomResponse.Error -> showError(
                    requireActivity(),
                    getString(R.string.no_data_found)
                )

            }
        }
    }


    private suspend fun collectData() {
        viewModel.apiState.collectLatest {
            when (it.status) {
                is Status.LOADING -> updateView(mAdapter.currentList.size == 0)
                is Status.ERROR -> {
                    showError(requireActivity(), it.message.toString())
                    updateView(isLoading = false)
                }
                is Status.SUCCESS -> {
                    mAdapter.updateData(it.data?.results)
                    updateAutoComplete(mAdapter.currentList)
                    updateView(isLoading = false)
                }
            }
        }
    }


    private fun updateAutoComplete(list: List<Movie>) = autoCompleteAdapter.update(list)

    private fun updateView(isLoading: Boolean) {
        with(binding) {
            recyclerview.visibility = if (isLoading) View.INVISIBLE else View.VISIBLE
            loading.visibility = if (isLoading) View.VISIBLE else View.GONE
        }
    }


    private fun onMovieItemClick(movie: Movie) {
        isConnectedToTheInternet(TYPE_NAVIGATE_NO_CONNECTION, object : Connect() {
            override fun retry() = onMovieItemClick(movie = movie)
            override fun isConnected() {
                val intent = Intent(requireContext(), DetailActivity::class.java)
                intent.putExtra(INTENT_ID, movie.id.toLong())
                startActivity(intent)
            }
        })
    }
}
