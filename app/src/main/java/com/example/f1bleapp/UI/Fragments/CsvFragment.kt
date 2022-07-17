package com.example.f1bleapp.UI.Fragments

import android.app.Activity
import android.content.Intent
import android.net.Uri
import android.os.Build
import android.os.Bundle
import android.preference.PreferenceManager
import android.util.Log
import android.view.View
import android.widget.TextView
import androidx.annotation.RequiresApi
import androidx.appcompat.app.AppCompatActivity
import androidx.core.os.bundleOf
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentTransaction
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.example.f1bleapp.Data.Local.CsvData
import com.example.f1bleapp.Data.Local.DeviceData
import com.example.f1bleapp.R
import com.example.f1bleapp.UI.AppViewModel
import com.example.f1bleapp.databinding.FragmentCsvBinding
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import dagger.hilt.android.AndroidEntryPoint
import java.io.BufferedReader
import java.io.IOException
import java.io.InputStreamReader

@AndroidEntryPoint
@Suppress("DEPRECATION", "UNREACHABLE_CODE", "NAME_SHADOWING")
class CsvFragment : Fragment(R.layout.fragment_csv) {
    val READ_REQUEST_CODE = 123
    private var messageArray: MutableList<CsvData> = arrayListOf<CsvData>()
    private var connectedDevices = arrayOf<DeviceData>()
    private lateinit var binding: FragmentCsvBinding
    private lateinit var textureView: TextView
    private val viewModel: AppViewModel by viewModels()

    @RequiresApi(Build.VERSION_CODES.KITKAT)
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        (activity as AppCompatActivity).supportActionBar?.title = "Import CSV Files"
        binding = FragmentCsvBinding.bind(view)

        textureView = binding.tvCsvFilename
        binding.buttonImportCsv.setOnClickListener {
            val intent = Intent(Intent.ACTION_OPEN_DOCUMENT)
            intent.addCategory(Intent.CATEGORY_OPENABLE)
            intent.type = "text/*"
            startActivityForResult(intent, READ_REQUEST_CODE)
        }
        binding.buttonVideoFragment.setOnClickListener {
            val myParcelizedObjectArrayList = messageArray
            val bundle = bundleOf(
                "SOME_BUNDLE_KEY" to myParcelizedObjectArrayList,
                ("CONNECTED_DEVICE" to connectedDevices ?: null)!!
            )
            setDataInArrayList(messageArray,"SOME_BUNDLE_KEY")
            //findNavController().navigate(R.id.action_csvFragment2_to_videoFragment2, bundle)
            val fragment = VideoFragment()
            fragment.arguments = bundle
            val ft = requireFragmentManager().beginTransaction()
            ft.replace(R.id.framecon, fragment)
            ft.setTransition(FragmentTransaction.TRANSIT_FRAGMENT_FADE)
            ft.addToBackStack(null)
            ft.commit()
        }
    }

    fun setDataInArrayList(list: MutableList<CsvData>, key: String ) {
        val prefs = PreferenceManager.getDefaultSharedPreferences(context)
        val editor = prefs.edit()
        val gson = Gson()
        val json = gson.toJson(list)
        editor.putString(key, json)
        editor.apply()
    }

    @Throws(IOException::class)
    fun readCSV(uri: Uri): List<String> {
        val csvFile = requireActivity().contentResolver.openInputStream(uri)
        val isr = InputStreamReader(csvFile)
        return BufferedReader(isr).readLines()
    }


    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (requestCode == READ_REQUEST_CODE && resultCode == Activity.RESULT_OK) {
            textureView.text = data?.let { intent ->
                intent.data?.let {
                    val csvData = readCSV(it)
                    Log.d("DataLength", (csvData.size - 1).toString())
                    for (i in 1 until csvData.size) {
                        val sortedReadings = csvData.get(i).split(",").toTypedArray()

                        if (sortedReadings.size == 13) {
                            val _csvData = CsvData(
                                timeStamp = sortedReadings[0],
                                message1 = sortedReadings[1],
                                message2 = sortedReadings[2],
                                message3 = sortedReadings[3],
                                message4 = sortedReadings[4],
                                message5 = sortedReadings[5],
                                message6 = sortedReadings[6],
                                message7 = sortedReadings[7],
                                message8 = sortedReadings[8],
                                message9 = sortedReadings[9],
                                message10 = sortedReadings[10],
                                message11 = sortedReadings[11],
                                message12 = sortedReadings[12]
                            )
                            messageArray.addAll(listOf(_csvData))
                        }
                    }
                    readCSV(it).joinToString(separator = "\n")

                }
            }
        }
    }

    fun getDataInArrayList(key: String): Array<DeviceData> {
        val prefs = PreferenceManager.getDefaultSharedPreferences(context)
        val gson = Gson()
        val json = prefs.getString(key, null)
        val type = object : TypeToken<Array<DeviceData>>() {
        }.type
        return gson.fromJson(json, type)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val connectedDevice = arguments?.getParcelableArray("CONNECTED_DEVICE") as Array<DeviceData>
        connectedDevices = getDataInArrayList("CONNECTED_DEVICE")
        //val savedDevice = getDataInArrayList("")
        Log.d("CONNECTED_DEVICE  ", Gson().toJson(connectedDevices))
        Log.d("CONNECTED_DEVICE NEW ", ""+getDataInArrayList("CONNECTED_DEVICE"))

    }
}