package com.example.f1bleapp.UI.Fragments

import android.Manifest
import android.annotation.SuppressLint
import android.bluetooth.BluetoothDevice
import android.content.Context
import android.content.SharedPreferences
import android.content.pm.PackageManager
import android.os.Build
import android.os.Bundle
import android.preference.PreferenceManager
import android.util.Log
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.PermissionChecker.checkSelfPermission
import androidx.core.os.bundleOf
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentTransaction
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout.OnRefreshListener
import com.example.f1bleapp.Adapter.ConnectedDeviceAdapter
import com.example.f1bleapp.Adapter.DeviceAdapter
import com.example.f1bleapp.Data.Local.DeviceData
import com.example.f1bleapp.HomeFragment
import com.example.f1bleapp.R
import com.example.f1bleapp.UI.AppViewModel
import com.example.f1bleapp.Util.ByteConverter
import com.example.f1bleapp.Util.TinyDB
import com.example.f1bleapp.databinding.FragmentBleDeviceBinding
import com.google.gson.Gson
import com.vithamastech.blecentralmodule.BleCentralManager
import com.vithamastech.blecentralmodule.interfaces.BleCentralManagerCallbacks
import com.vithamastech.blescannermodule.BLEScanner
import com.vithamastech.blescannermodule.BLEScanner.BLEScannerCallback
import dagger.hilt.android.AndroidEntryPoint
import java.util.*
import javax.inject.Inject


@Suppress("DEPRECATION")
@AndroidEntryPoint
class BleDeviceFragment @Inject constructor(
) : Fragment(R.layout.fragment_ble_device) {
    private val viewModel: AppViewModel by viewModels()
    private lateinit var binding: FragmentBleDeviceBinding
    private lateinit var bleScanner: BLEScanner
    private lateinit var bleCentralManager: BleCentralManager
    private lateinit var scanDeviceAdapter: DeviceAdapter
    private lateinit var connectedDeviceAdapter: ConnectedDeviceAdapter
    private var selectedDevice: DeviceData? = null
//    val sharedPreferences: SharedPreferences = requireActivity().getSharedPreferences("MySharedPref",
//        Context.MODE_PRIVATE)

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        (activity as AppCompatActivity).supportActionBar?.title = "Device List"
        bleCentralManager = BleCentralManager.getInstance(context?.applicationContext)
        bleScanner = BLEScanner.getInstance(context?.applicationContext)
        scanDeviceAdapter = DeviceAdapter()
        connectedDeviceAdapter = ConnectedDeviceAdapter()
        // viewModel = ViewModelProvider(requireActivity()).get(AppViewModel::class.java)
        binding = FragmentBleDeviceBinding.bind(view)
        foundDevice()
        connectedDevice()
        binding.swipeRefresh.apply {
            setColorSchemeResources(R.color.teal_700)
            setOnRefreshListener(swipeRefreshCallback)
        }
        binding.fabNextFragment.setOnClickListener {
            val mySelectedDevice = arrayOf(selectedDevice)
                val bundle = bundleOf("CONNECTED_DEVICE" to mySelectedDevice)
           // setDataByKeyValue("CONNECTED_DEVICE",mySelectedDevice)
             setDataInArrayList(mySelectedDevice,"CONNECTED_DEVICE")
            //findNavController().navigate(R.id.action_bleDeviceFragment2_to_csvFragment2, bundle)
            val fragment = CsvFragment()
            fragment.arguments = bundle
            val ft = requireFragmentManager().beginTransaction()
            ft.replace(R.id.framecon, fragment)
            ft.setTransition(FragmentTransaction.TRANSIT_FRAGMENT_FADE)
            ft.addToBackStack(null)
            ft.commit()
        }

        scanDeviceAdapter.setOnItemClickListener {
            selectedDevice = it
            bleScanner.stopScan()
            bleCentralManager.connect(it.deviceMacId)
            binding.progressBar.visibility = View.VISIBLE
//            Toast.makeText(
//                activity?.applicationContext,
//                "Connecting to ${it.deviceName}",
//                Toast.LENGTH_SHORT
//            ).show()


        }
        connectedDeviceAdapter.setOnConnectedItemClickListener {
            selectedDevice = it
            bleCentralManager.disconnect(it.deviceMacId)
            Toast.makeText(
                activity?.applicationContext,
                "Disconnected  ${it.deviceName}",
                Toast.LENGTH_SHORT
            ).show()

        }
    }

//    fun setDataByKeyValue(key: String, DataArrayList: Array<DeviceData?>) {
//        val arrayString = Gson().toJson(DataArrayList)
//        sharedPreferences.edit().putString(key, arrayString).apply()
//    }

    fun setDataInArrayList(list: Array<DeviceData?>, key: String ) {
        val prefs = PreferenceManager.getDefaultSharedPreferences(context)
        val editor = prefs.edit()
        val gson = Gson()
        val json = gson.toJson(list)
        editor.putString(key, json)
        editor.apply()
    }

    private fun foundDevice() {
        binding.recyclerFoundViewBle.apply {
            adapter = scanDeviceAdapter
            setHasFixedSize(false)
            layoutManager = LinearLayoutManager(requireContext())
        }
    }

    private fun connectedDevice() {
        binding.recyclerConnectedViewBle.apply {
            adapter = connectedDeviceAdapter
            setHasFixedSize(false)
            layoutManager = LinearLayoutManager(requireContext())
        }
    }

    var bleScannerCallback: BLEScannerCallback = object : BLEScannerCallback {
        override fun onStartScan() {}
        override fun onStopScan() {}
        override fun onScanFailed(errorCode: Int, errorMessage: String) {
            Toast.makeText(
                activity?.applicationContext,
                errorMessage,
                Toast.LENGTH_SHORT
            ).show()
        }

        override fun onScanResult(bluetoothDevice: BluetoothDevice, scanRecord: ByteArray) {
            // Add found devices into Found devices RecyclerView
            binding.swipeRefresh.setRefreshing(false)
            val device = DeviceData(id, bluetoothDevice.name, bluetoothDevice.address)
            scanDeviceAdapter.addDevice(device)
        }

        override fun onScanTimeout() {
            bleScanner.startDeviceScan()
        }
    }
    var bluetoothCentralManagerCallbacks: BleCentralManagerCallbacks =
        object : BleCentralManagerCallbacks() {
            override fun onDeviceDisconnected(macId: String?) {
                connectedDeviceAdapter.removeDevice(selectedDevice)
                selectedDevice = DeviceData(null, null, null)
                bleScanner.startDeviceScan()
            }

            override fun onDeviceConnectionFailed(macId: String?) {
                binding.progressBar.visibility = View.GONE
//                Toast.makeText(
//                    activity?.applicationContext,
//                    "Connection Failed",
//                    Toast.LENGTH_SHORT
//                ).show()
            }

            override fun onDeviceConnectedAndReadyToCommunicate(
                macId: String?,
                map: MutableMap<String, ArrayList<String>>?
            ) {
                Log.d("Connected Device  ", macId.toString())
                fragmentManager!!.popBackStack()
                Toast.makeText(
                    activity?.applicationContext,
                    "Connected ${selectedDevice?.deviceName}",
                    Toast.LENGTH_SHORT
                ).show()

                val mySelectedDevice = arrayOf(selectedDevice)
                setDataInArrayList(mySelectedDevice,"CONNECTED_DEVICE")
                scanDeviceAdapter.removeDevice(selectedDevice)
                connectedDeviceAdapter.addDevice(selectedDevice)
                binding.progressBar.visibility = View.GONE
                selectedDevice?.let { data ->
                    viewModel.insertDeviceDetail(data)
                }

//                findNavController().navigate(
//                    R.id.action_bleDeviceFragment2_to_successDialog
//                )
                Toast.makeText(activity,"Device Connected",Toast.LENGTH_SHORT)
            }

            override fun onCharacteristicWrite(
                macId: String?,
                serviceUUID: UUID?,
                characteristicUUID: UUID?,
                rawData: ByteArray?
            ) {
                super.onCharacteristicWrite(macId, serviceUUID, characteristicUUID, rawData)
                Log.d(
                    "Connected Device",
                    "serviceUUID : $serviceUUID -- characteristicUUID: $characteristicUUID -- rawData : ${
                        ByteConverter.getHexStringFromByteArray(
                            rawData,
                            true
                        )
                    } "
                )
            }

            override fun onCharacteristicWriteFailed(
                macId: String?,
                serviceUUID: UUID?,
                characteristicUUID: UUID?,
                rawData: ByteArray?
            ) {
                super.onCharacteristicWriteFailed(macId, serviceUUID, characteristicUUID, rawData)
            }

            override fun onCharacteristicUpdate(
                macId: String?,
                serviceUUID: UUID?,
                characteristicUUID: UUID?,
                rawData: ByteArray?
            ) {
                super.onCharacteristicUpdate(macId, serviceUUID, characteristicUUID, rawData)
                Log.d(
                    "Connected Device",
                    "serviceUUID : $serviceUUID -- characteristicUUID: $characteristicUUID -- rawData : ${
                        ByteConverter.getHexStringFromByteArray(
                            rawData,
                            true
                        )
                    } "
                )
            }

            override fun onCharacteristicReadFailed(
                macId: String?,
                serviceUUID: UUID?,
                characteristicUUID: UUID?
            ) {
                super.onCharacteristicReadFailed(macId, serviceUUID, characteristicUUID)
            }

        }

    var swipeRefreshCallback = OnRefreshListener {
        scanDeviceAdapter.clear() // Clear the items in RecyclerView (Refresh)
    }

    @SuppressLint("LongLogTag")
    override fun onStart() {
        super.onStart()
        requestPermission()
//        Log.d(
//            "IsDeviceconnected onStart ",
//            bleCentralManager.isDeviceConnected(selectedDevice?.deviceMacId).toString()
//        )
        bleCentralManager.setAllowOperationsOnUIThread(true) // Allows BLE communication callbacks in UI Thread
        bleScanner.setOnBLEScanListener(bleScannerCallback) // Sets BLE Scanner event callback
        bleCentralManager.setOnBluetoothCentralListener(bluetoothCentralManagerCallbacks)
        isDeviceConnected()
        Toast.makeText(activity?.applicationContext, "Scanning For device", Toast.LENGTH_LONG)
            .show()
    }

    override fun onStop() {
        super.onStop()
        bleScanner.stopScan()
        bleScanner.setOnBLEScanListener(null)
    }

    @SuppressLint("LongLogTag")
    override fun onPause() {
        super.onPause()
        Log.d(
            "IsDeviceconnected onPause ",
            bleCentralManager.isDeviceConnected(selectedDevice?.deviceMacId).toString()
        )
    }

    @SuppressLint("LongLogTag")
    override fun onResume() {
        super.onResume()
        bleScanner.startDeviceScan()
        Log.d(
            "IsDeviceconnected onResume",
            bleCentralManager.isDeviceConnected(selectedDevice?.deviceMacId).toString()
        )
    }

    private fun isDeviceConnected() {
        viewModel.getDevice.observe(viewLifecycleOwner, androidx.lifecycle.Observer {
            val addDeviceList = arrayListOf<DeviceData>()
            Log.d("alldevices  ", Gson().toJson(it))
            for (device in it) {
                if (bleCentralManager.isDeviceConnected(device.deviceMacId)) {
                    addDeviceList.add(device)
                }
            }
            if (!addDeviceList.isNullOrEmpty()) {
                connectedDeviceAdapter.addDevice(addDeviceList.get(0))
            }

        })
    }
    @SuppressLint("WrongConstant")
    private fun hasExternalStoragePermission() =
        checkSelfPermission(
            requireContext().applicationContext,
            Manifest.permission.READ_EXTERNAL_STORAGE
        ) == PackageManager.PERMISSION_GRANTED

    @SuppressLint("WrongConstant")
    private fun hasLocationPermission() =
        when {
            Build.VERSION.SDK_INT >= Build.VERSION_CODES.S -> {
                checkSelfPermission(
                    requireActivity(),
                    Manifest.permission.BLUETOOTH_SCAN
                ) == PackageManager.PERMISSION_GRANTED
                checkSelfPermission(
                    requireActivity(),
                    Manifest.permission.BLUETOOTH_CONNECT
                ) == PackageManager.PERMISSION_GRANTED
            }
            Build.VERSION.SDK_INT >= Build.VERSION_CODES.Q -> {
                checkSelfPermission(
                    requireActivity(),
                    Manifest.permission.ACCESS_FINE_LOCATION
                ) == PackageManager.PERMISSION_GRANTED

            }
            else -> checkSelfPermission(
                requireActivity(),
                Manifest.permission.ACCESS_COARSE_LOCATION
            ) == PackageManager.PERMISSION_GRANTED
        }

    private fun requestPermission() {
        val permissionToRequest = mutableListOf<String>()
        if (!hasExternalStoragePermission()) {
            permissionToRequest.add(Manifest.permission.READ_EXTERNAL_STORAGE)
        }
        if (!hasLocationPermission()) {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.S) {
                permissionToRequest.add(Manifest.permission.BLUETOOTH_SCAN)
                permissionToRequest.add(Manifest.permission.BLUETOOTH_CONNECT)
            }
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.Q) {
                permissionToRequest.add(Manifest.permission.ACCESS_FINE_LOCATION)
            }
            if (Build.VERSION.SDK_INT < Build.VERSION_CODES.Q) {
                permissionToRequest.add(Manifest.permission.ACCESS_COARSE_LOCATION)

            }
        }
        if (permissionToRequest.isNotEmpty()){
            requestPermissions(permissionToRequest.toTypedArray(),0)
        }

    }

    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<out String>,
        grantResults: IntArray
    ) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        if (requestCode ==0 && grantResults.isNotEmpty()){
            for (i in grantResults.indices){
                if (grantResults[i]==PackageManager.PERMISSION_GRANTED){
                    Toast.makeText(requireActivity(),"${permissions[i]} is granted.",Toast.LENGTH_SHORT).show()
                }

            }
        }
    }
}