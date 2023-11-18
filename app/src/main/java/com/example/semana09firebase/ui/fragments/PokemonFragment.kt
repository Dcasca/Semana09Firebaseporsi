package com.example.semana09firebase.ui.fragments

import android.app.ProgressDialog
import android.os.Bundle
import android.os.Handler
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.EditText
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.semana09firebase.R
import com.example.semana09firebase.adapter.PokemonAdapter
import com.example.semana09firebase.model.PokemonModel
import com.example.semana09firebase.model.PokemonModelDetails
import com.example.semana09firebase.model.PokemonModelResponse
import com.example.semana09firebase.service.PokeApiService
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class PokemonFragment : Fragment() {
    private lateinit var pokemonAdapter: PokemonAdapter
    private var PokemonList: MutableList<PokemonModel> = mutableListOf()
    private var OFFSET: Int = 0
    private var TOTAL_ITEMS: Int = 0
    private var LIMIT_ROWS: Int = 10
    private var loadingDialog: ProgressDialog? = null
    private val handler = Handler()
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_pokemon, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val etSearchPokemon: EditText = view.findViewById(R.id.etSearchPokemon)
        val btnSearchPokemon: Button = view.findViewById(R.id.btnSearchPokemon)
        val rvPokemon: RecyclerView = view.findViewById(R.id.rvPokemon)
        rvPokemon.layoutManager = LinearLayoutManager(requireContext())
        pokemonAdapter = PokemonAdapter(PokemonList)
        rvPokemon.adapter = pokemonAdapter

        val retrofit = Retrofit.Builder()
            .baseUrl("https://pokeapi.co/api/v2/")
            .addConverterFactory(GsonConverterFactory.create())
            .build()
        val pokeApiService = retrofit.create(PokeApiService::class.java)

        fetchPokemonList(pokeApiService)
        //pokemonAdapter.setOnItemClickListener(this)

    }
    fun showLoadingDialog(){
        if(loadingDialog == null){
            loadingDialog = ProgressDialog(requireContext())
            loadingDialog?.setMessage("Cargando Pokemones");
            loadingDialog?.setCancelable(false)
        }
        handler.postDelayed({
            loadingDialog!!.dismiss()
        },5000)
        loadingDialog!!.show()
    }

    fun fetchPokemonList(pokeApiService: PokeApiService){
        showLoadingDialog()

        pokeApiService.getPokemonList(OFFSET,LIMIT_ROWS)
            .enqueue(object : Callback<PokemonModelResponse> {
                override fun onResponse(
                    call: Call<PokemonModelResponse>,
                    response: Response<PokemonModelResponse>
                ) {

                    //TODO: Ocultar el dialog

                    if(response.isSuccessful){
                        val pokemonResponse = response.body()
                        val newPokemonList = pokemonResponse?.results ?: emptyList()
                        TOTAL_ITEMS = pokemonResponse?.count ?: 0

                        newPokemonList.forEach {pokemon ->
                            pokeApiService.getPokemonDetails(pokemon.name)
                                .enqueue(object : Callback<PokemonModelDetails>{
                                    override fun onResponse(
                                        call: Call<PokemonModelDetails>,
                                        response: Response<PokemonModelDetails>
                                    ) {
                                        if(response.isSuccessful){
                                            val pokemonDetails = response.body()
                                            val imageUrl = pokemonDetails?.sprites?.frontDefault ?: ""
                                            val abilities = pokemonDetails?.abilities?.map { it.ability.name}?.toList() ?: emptyList()

                                            val pokemonModel = PokemonModel(pokemonDetails?.name ?: "", imageUrl, abilities)

                                            PokemonList.add(pokemonModel)
                                            pokemonAdapter.notifyDataSetChanged()
                                        }
                                    }
                                    override fun onFailure(
                                        call: Call<PokemonModelDetails>,
                                        t: Throwable
                                    ) {
                                        TODO("Not yet implemented")
                                    }
                                })
                        }
                        pokemonAdapter.setData(PokemonList)
                    }
                }

                override fun onFailure(call: Call<PokemonModelResponse>, t: Throwable) {
                    TODO("Not yet implemented")
                }


            })

    }


}