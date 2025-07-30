package com.hadeer.photogalleryapplication.ui

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.appcompat.app.AppCompatDelegate
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.GridLayoutManager
import com.hadeer.data.CheckInternetConnectivity
import com.hadeer.data.ThemePreferance
import com.hadeer.data.remote.AppDataBase
import com.hadeer.data.remote.getInstance
import com.hadeer.domain.entity.Photo
import com.hadeer.domain.entity.toMap
import com.hadeer.photogalleryapplication.R
import com.hadeer.photogalleryapplication.databinding.FragmentHomeBinding
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch
import java.lang.Exception

@AndroidEntryPoint
class HomeFragment : Fragment() {
    private lateinit var binding : FragmentHomeBinding
    private val viewModel : PhotosViewModel by viewModels()
    private val coroutineScope = CoroutineScope(Dispatchers.Main)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentHomeBinding.inflate(inflater, container, false)
        // Inflate the layout for this fragment
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.imageResponseContainer.layoutManager = GridLayoutManager(context, 2)
        val photosDataBase = getInstance(requireContext())
        photosDataBase.photoDao().getAllPhotos().observe(viewLifecycleOwner) { data ->
            if(data.isEmpty() && CheckInternetConnectivity.isInternetEnable(requireContext())){
                binding.errorNetworkMessageTxt.visibility = View.GONE
                viewModel.getPhotosData()
                eventListener()
            }
            if(data.isEmpty() && !CheckInternetConnectivity.isInternetEnable(requireContext())){
                binding.errorNetworkMessageTxt.visibility = View.VISIBLE
                Toast.makeText(requireContext(), getString(R.string.error_message_in_bo_data), Toast.LENGTH_LONG).show()
            }
            if(data.isNotEmpty() &&  !CheckInternetConnectivity.isInternetEnable(requireContext()) || CheckInternetConnectivity.isInternetEnable(requireContext())){
                binding.errorNetworkMessageTxt.visibility = View.GONE
                initializeRoom()
            }
        }
        handleToggleMode()
    }
    private fun eventListener(){
        coroutineScope.launch {
            viewModel.photosIntent.collect{
                when(it){
                    is PhotosIntent.Idle ->{
                        handleIsLoading(it.state.isLoading)
                    }
                    is PhotosIntent.Failed ->{
                        handleIsLoading(it.state.isLoading)
                        if(!it.state.isSuccess){
                            Toast.makeText(requireContext(), it.state.apiError, Toast.LENGTH_LONG).show()
                        }
                    }
                    is PhotosIntent.NetworkFailed ->{
                        handleIsLoading(it.state.isLoading)
                        Toast.makeText(requireContext(), it.state.apiError, Toast.LENGTH_LONG).show()
                    }
                    is PhotosIntent.Success->{
                        handleIsLoading(it.state.isLoading)
                        binding.imageResponseContainer.adapter = ImagesAdaptor(requireContext(), it.state.photosData)
                    }
                }
            }
        }
    }
    private fun handleIsLoading(load : Boolean){
        println("is loading stat $load")
        if (load){
            binding.loader.visibility = View.VISIBLE
        }
        else{
            binding.loader.visibility = View.GONE
        }
    }

    private fun initializeRoom(){
        val photosDataBase = getInstance(requireContext())
        photosDataBase.photoDao().getAllPhotos().observe(viewLifecycleOwner){
            data ->
                val customizedList = data.map { it.toMap() }
                binding.imageResponseContainer.adapter = ImagesAdaptor(requireContext(), customizedList)

        }

    }

    private fun handleToggleMode(){
        val switch = binding.topSection.themeSwitch
        val isDark = ThemePreferance.getCurrentTheme(requireContext()) == "dark"
        switch.isChecked = isDark

        switch.setOnCheckedChangeListener { _, isChecked ->
            if (isChecked) {
                ThemePreferance.setCurrentThemeState(requireContext(), "dark")
                AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_YES)
            } else {
                ThemePreferance.setCurrentThemeState(requireContext(), "light")
                AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO)
            }
//            requireActivity().recreate()
        }
    }
}