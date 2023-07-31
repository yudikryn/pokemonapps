package com.yudikryn.pokemonapps.ui.listPokemon

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.yudikryn.pokemonapps.databinding.FragmentListPokemonBinding
import com.yudikryn.pokemonapps.helper.ViewModelFactory
import com.yudikryn.pokemonapps.data.remote.network.Result
import com.yudikryn.pokemonapps.ui.detailPokemon.DetailPokemonActivity
import com.yudikryn.pokemonapps.ui.detailPokemon.DetailPokemonActivity.Companion.POKEMON_EXTRAS

class ListPokemonFragment : Fragment() {

    private lateinit var binding: FragmentListPokemonBinding

    private lateinit var viewModel: ListPokemonViewModel

    private lateinit var linearLayoutManager: LinearLayoutManager

    private val pokemonAdapter by lazy {
        ListPokemonAdapter{ pokemon ->
            val intent = Intent(requireContext(), DetailPokemonActivity::class.java)
            intent.putExtra(POKEMON_EXTRAS, pokemon)
            startActivity(intent)
        }
    }

    private var isLoading = false
    private var isLastPage = false
    private val pageSize = 10
    private var startFrom = 0

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentListPokemonBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initViewModel()
        initScrollListener()
    }

    private fun initViewModel() {
        val factory = ViewModelFactory.getInstance(requireContext())
        val viewModels: ListPokemonViewModel by viewModels {
            factory
        }
        viewModel = viewModels
        getPokemon()
    }

    private fun getPokemon(){
        viewModel.getPokemon(startFrom, pageSize).observe(viewLifecycleOwner) { result ->
            when(result){
                is Result.Success ->{
                    isLoading = false
                    if (result.data.isNotEmpty()){
                        isLastPage = false
                        if (startFrom == 0){
                            pokemonAdapter.submitList(result.data)
                            linearLayoutManager = LinearLayoutManager(context)
                            binding.rvPokemon.apply {
                                layoutManager = linearLayoutManager
                                setHasFixedSize(true)
                                adapter = pokemonAdapter
                            }
                        }else{
                            pokemonAdapter.updateData(result.data)
                        }
                    }else{
                        isLastPage = true
                    }
                }
                is Result.Loading -> {}
                is Result.Error -> {
                    isLoading = false
                    Toast.makeText(this.requireContext(), result.error, Toast.LENGTH_SHORT).show()
                }
            }
        }
    }


    private fun initScrollListener(){
        binding.rvPokemon.addOnScrollListener(object : RecyclerView.OnScrollListener() {
            override fun onScrolled(recyclerView: RecyclerView, dx: Int, dy: Int) {
                super.onScrolled(recyclerView, dx, dy)
                val visibleItemCount = linearLayoutManager.childCount
                val totalItemCount = linearLayoutManager.itemCount
                val firstVisibleItemPosition =
                    linearLayoutManager.findFirstVisibleItemPosition()
                if (!isLoading && !isLastPage) {
                    if (visibleItemCount + firstVisibleItemPosition >= totalItemCount
                        && firstVisibleItemPosition >= 0
                        && totalItemCount >= pageSize
                    ) {
                        isLoading = true
                        startFrom = totalItemCount
                        getPokemon()
                    }
                }
            }
        })

    }
}