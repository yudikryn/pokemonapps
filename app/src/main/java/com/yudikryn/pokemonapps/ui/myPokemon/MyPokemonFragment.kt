package com.yudikryn.pokemonapps.ui.myPokemon

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.EditText
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.core.view.marginTop
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.LinearLayoutManager
import com.google.android.material.textfield.TextInputLayout
import com.yudikryn.pokemonapps.data.local.entity.PokemonEntity
import com.yudikryn.pokemonapps.data.remote.network.Result
import com.yudikryn.pokemonapps.databinding.FragmentMyPokemonBinding
import com.yudikryn.pokemonapps.helper.ViewModelFactory
import com.yudikryn.pokemonapps.ui.detailPokemon.DetailPokemonActivity
import com.yudikryn.pokemonapps.ui.myPokemon.MyPokemonAdapter.Companion.ACTION_DETAIL
import com.yudikryn.pokemonapps.ui.myPokemon.MyPokemonAdapter.Companion.ACTION_RELEASE
import com.yudikryn.pokemonapps.ui.myPokemon.MyPokemonAdapter.Companion.ACTION_RENAME

class MyPokemonFragment : Fragment() {

    private lateinit var binding: FragmentMyPokemonBinding

    private lateinit var viewModel: MyPokemonViewModel

    private val myPokemonAdapter by lazy {
        MyPokemonAdapter { pokemon, action, position ->
            when(action){
                ACTION_DETAIL -> {
                    val intent = Intent(requireContext(), DetailPokemonActivity::class.java)
                    intent.putExtra(DetailPokemonActivity.POKEMON_EXTRAS, pokemon.toPokemon())
                    startActivity(intent)
                }
                ACTION_RENAME -> {
                    showAlertWithTextInputLayout(requireContext(), pokemon, position)
                }
                ACTION_RELEASE -> {
                    deletePokemon(pokemon, position)
                }
            }
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentMyPokemonBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initViewModel()
    }

    private fun initViewModel() {
        val factory = ViewModelFactory.getInstance(requireContext())
        val viewModels: MyPokemonViewModel by viewModels {
            factory
        }
        viewModel = viewModels

        viewModel.getMyPokemon().observe(viewLifecycleOwner) { result ->
            when(result){
                is Result.Success ->{
                    if (result.data.isNotEmpty()){
                        myPokemonAdapter.submitList(result.data.toMutableList())
                        binding.rvPokemon.apply {
                            layoutManager = LinearLayoutManager(context)
                            setHasFixedSize(true)
                            adapter = myPokemonAdapter
                        }
                        binding.tvEmptyData.visibility = View.GONE
                    }else{
                        binding.tvEmptyData.visibility = View.VISIBLE
                    }
                }
                is Result.Loading -> {}
                is Result.Error -> {
                    Toast.makeText(this.requireContext(), result.error, Toast.LENGTH_SHORT).show()
                }
            }
        }
    }

    private fun showAlertWithTextInputLayout(context: Context, pokemon: PokemonEntity, position: Int) {
        val textInputLayout = TextInputLayout(context)
        textInputLayout.setPadding(
            19,
            0,
            19,
            0
        )
        val input = EditText(context)
        input.textSize = 14f
        textInputLayout.addView(input)

        val alert = AlertDialog.Builder(context)
            .setTitle("Rename Pokemon Egg")
            .setView(textInputLayout)
            .setPositiveButton("Save") { dialog, _ ->
                pokemon.name = textInputLayout.editText?.text.toString().trim()
                updatePokemon(pokemon, position)
                dialog.cancel()
            }
            .setNegativeButton("Cancel") { dialog, _ ->
                dialog.cancel()
            }.create()

        alert.show()
    }

    private fun updatePokemon(pokemon: PokemonEntity, position: Int){
        viewModel.updatePokemon(pokemonEntity = pokemon).observe(this){ result ->
            when(result){
                is Result.Success ->{
                    myPokemonAdapter.updateItem(pokemon, position)
                }
                is Result.Loading -> {}
                is Result.Error -> {
                    Toast.makeText(this.requireContext(), result.error, Toast.LENGTH_SHORT).show()
                }
            }
        }
    }

    private fun deletePokemon(pokemon: PokemonEntity, position: Int){
        viewModel.deletePokemon(pokemon.uniqId).observe(viewLifecycleOwner){ result ->
            when(result){
                is Result.Success ->{
                    myPokemonAdapter.deleteItem(position)
                    Toast.makeText(this.requireContext(), "Success release ${pokemon.name}", Toast.LENGTH_SHORT).show()
                }
                is Result.Loading -> {}
                is Result.Error -> {
                    Toast.makeText(this.requireContext(), result.error, Toast.LENGTH_SHORT).show()
                }
            }
        }
    }

}