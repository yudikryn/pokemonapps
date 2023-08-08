package com.yudikryn.pokemonapps.ui.detailPokemon

import android.os.Build
import android.os.Bundle
import android.widget.Toast
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import by.kirich1409.viewbindingdelegate.viewBinding
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions
import com.yudikryn.pokemonapps.R
import com.yudikryn.pokemonapps.data.local.entity.PokemonEntity
import com.yudikryn.pokemonapps.data.remote.network.Result
import com.yudikryn.pokemonapps.data.remote.model.Pokemon
import com.yudikryn.pokemonapps.databinding.ActivityDetailPokemonBinding
import com.yudikryn.pokemonapps.helper.ViewModelFactory
import java.util.Locale
import kotlin.random.Random

class DetailPokemonActivity : AppCompatActivity(R.layout.activity_detail_pokemon) {

    private val binding by viewBinding(ActivityDetailPokemonBinding::bind)

    private lateinit var viewModel: DetailPokemonViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        initViewModel()
        getDetailPokemon()
    }

    private fun initViewModel() {
        val factory = ViewModelFactory.getInstance(this)
        val viewModels: DetailPokemonViewModel by viewModels {
            factory
        }
        viewModel = viewModels
    }

    private fun getDetailPokemon() {
        val dataPokemon = if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
            intent.getParcelableExtra(POKEMON_EXTRAS, Pokemon::class.java)
        } else {
            intent.getParcelableExtra(POKEMON_EXTRAS)
        }

        val id: Int = if (dataPokemon?.url?.contains("pokemon") == true){
            val splitData = dataPokemon.url.split("pokemon")
            val maxSub = when(splitData.last().length){
                3 -> 2
                4 -> 3
                5 -> 4
                else -> 5
            }
            splitData.last().substring(1, maxSub).toInt()
        }else{
            dataPokemon?.url?.toInt() as Int
        }

        id.let {
            viewModel.getDetailPokemon(it).observe(this) { result ->
                when (result) {
                    is Result.Success -> {
                        with(result.data) {

                            Glide.with(this@DetailPokemonActivity)
                                .load(sprites.frontDefault)
                                .apply(
                                    RequestOptions.placeholderOf(R.drawable.ic_pokeball)
                                        .error(R.drawable.ic_pokeball)
                                )
                                .into(binding.ivPhotoFront)

                            Glide.with(this@DetailPokemonActivity)
                                .load(sprites.backDefault)
                                .apply(
                                    RequestOptions.placeholderOf(R.drawable.ic_pokeball)
                                        .error(R.drawable.ic_pokeball)
                                )
                                .into(binding.ivPhotoBack)


                            binding.tvName.text = name.capitalize(Locale.getDefault())
                            binding.tvHeight.setValue(height)
                            binding.tvWeight.setValue(weight)
                            binding.tvAbility.setValue(abilities.joinToString { abilities -> abilities.ability.name.capitalize(
                                Locale.getDefault()) })
                            binding.tvStats.setValue(stats.joinToString { stats -> stats.stat.name.capitalize(
                                Locale.getDefault()) })
                            binding.tvType.setValue(types.joinToString { types -> types.type.name.capitalize(
                                Locale.getDefault()) })

                            binding.btnCollect.setOnClickListener {
                                insertPokemon(PokemonEntity(id = id, name = name))
                            }
                        }
                    }

                    is Result.Loading -> {}
                    is Result.Error -> {
                        Toast.makeText(this, result.error, Toast.LENGTH_SHORT).show()
                    }
                }
            }
        }
    }

    private fun insertPokemon(pokemonEntity: PokemonEntity){
        if (catchPokemon()){
            viewModel.insertPokemon(pokemonEntity).observe(this){ result ->
                when(result){
                    is Result.Success -> {
                        Toast.makeText(this, "Success to catch ${pokemonEntity.name}", Toast.LENGTH_SHORT).show()
                        finish()
                    }
                    is Result.Loading -> {}
                    is Result.Error -> {
                        Toast.makeText(this, result.error, Toast.LENGTH_SHORT).show()
                    }
                }
            }
        }else{
            Toast.makeText(this, "Failed to catch ${pokemonEntity.name}", Toast.LENGTH_SHORT).show()
        }
    }

    private fun catchPokemon(): Boolean {
        val randomValue = Random.nextDouble()
        return randomValue < 0.5
    }

    companion object {
        const val POKEMON_EXTRAS = "pokemon-extras"
    }
}