package com.example.f1bleapp.UI

import android.annotation.SuppressLint
import android.bluetooth.BluetoothDevice
import android.content.Context
import android.content.SharedPreferences
import android.content.pm.ActivityInfo
import android.net.Uri
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import android.preference.PreferenceManager
import android.util.Log
import android.view.View
import android.widget.Toast
import com.example.f1bleapp.Data.Local.CsvData
import com.example.f1bleapp.Data.Local.DeviceData
import com.example.f1bleapp.R
import com.example.f1bleapp.Util.ByteConverter
import com.example.f1bleapp.Util.Constants
import com.example.f1bleapp.databinding.ActivityVideoBinding
import com.example.f1bleapp.databinding.FragmentVideoBinding
import com.google.android.exoplayer2.*
import com.google.android.exoplayer2.extractor.DefaultExtractorsFactory
import com.google.android.exoplayer2.source.ExtractorMediaSource
import com.google.android.exoplayer2.source.MediaSource
import com.google.android.exoplayer2.source.TrackGroupArray
import com.google.android.exoplayer2.trackselection.AdaptiveTrackSelection
import com.google.android.exoplayer2.trackselection.DefaultTrackSelector
import com.google.android.exoplayer2.trackselection.TrackSelectionArray
import com.google.android.exoplayer2.trackselection.TrackSelector
import com.google.android.exoplayer2.ui.SimpleExoPlayerView
import com.google.android.exoplayer2.upstream.BandwidthMeter
import com.google.android.exoplayer2.upstream.DataSource
import com.google.android.exoplayer2.upstream.DefaultBandwidthMeter
import com.google.android.exoplayer2.upstream.FileDataSourceFactory
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import com.vithamastech.blecentralmodule.BleCentralManager
import com.vithamastech.blecentralmodule.interfaces.BleCentralManagerCallbacks
import com.vithamastech.blescannermodule.BLEScanner
import kotlinx.android.synthetic.main.activity_video.*
import java.util.*

class VideoActivity : AppCompatActivity() {


    private lateinit var binding: ActivityVideoBinding
    val SELECT_VIDEO = 100
    private lateinit var bleScanner: BLEScanner
    private lateinit var bleCentralManager: BleCentralManager
    private lateinit var mPlayer: SimpleExoPlayer
    private lateinit var mPlayerView: SimpleExoPlayerView
    private var durationSet: Boolean = false
    private val handler = Handler()
    private var connectedDevice: Array<DeviceData> = arrayOf()
    private var csvArrayList = arrayListOf<CsvData>()
    var connectedDeviceName: String = ""
    var videoURL =
        "https://www.learningcontainer.com/wp-content/uploads/2020/05/sample-mp4-file.mp4"
    private lateinit var playerView: SimpleExoPlayerView


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_video)
        // permissionRequest()
        binding = ActivityVideoBinding.inflate(layoutInflater)
        bleCentralManager = BleCentralManager.getInstance(this?.applicationContext)
        bleScanner = BLEScanner.getInstance(this?.applicationContext)


        csvArrayList =  ArrayList(getDataInMutableList("SOME_BUNDLE_KEY"))
        connectedDevice = getDataInArrayList("CONNECTED_DEVICE")
        Log.d("CONNECTED_DEVICE  ", Gson().toJson(getDataInArrayList("CONNECTED_DEVICE")))
        Log.d("SOME_BUNDLE_KEY  ", Gson().toJson(getDataInMutableList("SOME_BUNDLE_KEY")))

        //this.requestedOrientation = ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE
//        if (connectedDevice[0] != null && bleCentralManager.isDeviceConnected(connectedDevice[0].deviceMacId)) {
//            connectedDeviceName = connectedDevice[0].deviceName.toString()
//            Log.d("connectedDevice  ", connectedDevice[0].toString())
//            //binding.tvConnectedDevice.text = "Connected to: ${connectedDevice[0].deviceName.toString()}"
//        }
//        binding.btnSelectVideo.setOnClickListener {
//            val intent = Intent()
//            intent.type = "video/*"
//            intent.action = Intent.ACTION_PICK
//            startActivityForResult(Intent.createChooser(intent, "Choose Video"), SELECT_VIDEO)
//        }
        playerView = findViewById(R.id.video_player)
        mPlayerView = playerView

        val bandwidthMeter: BandwidthMeter = DefaultBandwidthMeter()
        val trackSelector: TrackSelector =
            DefaultTrackSelector(AdaptiveTrackSelection.Factory(bandwidthMeter))
        mPlayer = ExoPlayerFactory.newSimpleInstance(applicationContext, trackSelector)

        val sharedPreferences: SharedPreferences = this.getSharedPreferences("MySharedPref",
            Context.MODE_PRIVATE)
        val videouri = sharedPreferences.getString("uri","");
        val uri = Uri.parse(videouri)
        Log.d("connectedDevice  ", videouri+"----"+uri)

        playVideo(uri)

        mPlayer.addListener(object : ExoPlayer.EventListener {
            override fun onTimelineChanged(timeline: Timeline?, manifest: Any?) {
            }

            override fun onTracksChanged(
                trackGroups: TrackGroupArray?,
                trackSelections: TrackSelectionArray?
            ) {
            }

            override fun onLoadingChanged(isLoading: Boolean) {

            }

            override fun onPlayerStateChanged(playWhenReady: Boolean, playbackState: Int) {
                if (playbackState == ExoPlayer.STATE_READY) {
                    var longWatchTime: Long = 0
                    var index: Int = 0
                    handler.postDelayed(object : Runnable {
                        @SuppressLint("LongLogTag")
                        override fun run() {
                            if (playWhenReady) {
                                longWatchTime = mPlayer.currentPosition /10
                                for (i in 0 until csvArrayList.size) {
                                    val sortData =
                                        csvArrayList.get(i).timeStamp.equals(longWatchTime.toString())
                                    if (sortData) {
                                        Log.d(
                                            "onPlayerduration  ",
                                            Gson().toJson(csvArrayList[i].timeStamp)
                                        )
                                        Log.d("longWatchTime  ",longWatchTime.toString())
                                        Log.d("longWatchTime mPlayer.currentPosition  ",mPlayer.currentPosition.toString())
                                        val message1 = csvArrayList[i].message1.toByte()
                                        val message2 = csvArrayList[i].message2.toByte()
                                        val message3 = csvArrayList[i].message3.toByte()
                                        val message4 = csvArrayList[i].message4.toByte()
                                        val message5 = csvArrayList[i].message5.toByte()
                                        val message6 = csvArrayList[i].message6.toByte()
                                        val message7 = csvArrayList[i].message7.toByte()
                                        val message8 = csvArrayList[i].message8.toByte()
                                        val message9 = csvArrayList[i].message9.toByte()
                                        val message10 = csvArrayList[i].message10.toByte()
                                        val message11 = csvArrayList[i].message11.toByte()
                                        val message12 = csvArrayList[i].message12.toByte()
                                        val byteArrayData: ByteArray = byteArrayOf(
                                            message1,
                                            message2,
                                            message3,
                                            message4,
                                            message5,
                                            message6,
                                            message7,
                                            message8,
                                            message9,
                                            message10,
                                            message11,
                                            message12
                                        )

                                        if (connectedDevice[0]!=null && bleCentralManager.isDeviceConnected(connectedDevice[0].deviceMacId) && playWhenReady) {
                                            //binding.tvConnectedDevice.text = "Connected to: ${connectedDevice[0].deviceName.toString()}"
                                            bleCentralManager.sendData(
                                                connectedDevice[0].deviceMacId, Constants.Device.SERVICE_UUID,
                                                Constants.Device.CHARACTERISTIC_UUID, byteArrayData
                                            )
                                            Log.d("SendingData  ", ByteConverter.getHexStringFromByteArray(byteArrayData,true))
                                            Toast.makeText(applicationContext,"Device connected Data : ${ByteConverter.getHexStringFromByteArray(byteArrayData,true)}",
                                                Toast.LENGTH_SHORT).show()
                                        }else if (!bleCentralManager.isDeviceConnected(connectedDevice[0].deviceMacId) && playWhenReady){
                                            Toast.makeText(applicationContext,"Device Disconnected : ${connectedDevice[0].deviceName}",
                                                Toast.LENGTH_SHORT).show()
                                            mPlayerView.hideController()
                                            //binding.tvConnectedDevice.text = "No Device Connected"
//                                            Toast.makeText(requireActivity(),"No device connected Data : ${ByteConverter.getHexStringFromByteArray(byteArrayData,true)}",Toast.LENGTH_SHORT).show()
                                        }else if(!playWhenReady && !bleCentralManager.isDeviceConnected(connectedDevice[0].deviceMacId)){
                                            Toast.makeText(applicationContext,"No device is connected",
                                                Toast.LENGTH_SHORT).show()
                                        }
                                        index++
                                        Log.d("dummyCsvData size  ", index.toString())
                                    }
                                    if (index == csvArrayList.size) {
                                        handler.removeCallbacksAndMessages(null)
                                    }
                                    if (playbackState == ExoPlayer.STATE_ENDED) {
                                        handler.removeCallbacksAndMessages(null)
                                    }
                                }
                                //  Log.d("longWatchTime  ", longWatchTime.toString())

                                handler.postDelayed(this, 0)
                            } else if (!playWhenReady) {
                                handler.removeCallbacksAndMessages(null)
                            }
                        }
                    }, 10)

                }
                if (playbackState == ExoPlayer.STATE_ENDED) {
                    handler.removeCallbacksAndMessages(null)
                }

            }

            override fun onPlayerError(error: ExoPlaybackException?) {
            }

            override fun onPositionDiscontinuity() {
            }

            override fun onPlaybackParametersChanged(playbackParameters: PlaybackParameters?) {
            }

        })

    }

    fun getDataInArrayList(key: String): Array<DeviceData> {
        val prefs = PreferenceManager.getDefaultSharedPreferences(this)
        val gson = Gson()
        val json = prefs.getString(key, null)
        val type = object : TypeToken<Array<DeviceData>>() {
        }.type
        return gson.fromJson(json, type)
    }

    fun getDataInMutableList(key: String): MutableList<CsvData> {
        val prefs = PreferenceManager.getDefaultSharedPreferences(this)
        val gson = Gson()
        val json = prefs.getString(key, null)
        val type = object : TypeToken<MutableList<CsvData>>() {
        }.type
        return gson.fromJson(json, type)
    }


    private fun pausePlayer() {
        mPlayer.setPlayWhenReady(false)
        mPlayer.getPlaybackState()
    }

    private fun startPlayer() {
        mPlayer.setPlayWhenReady(true)
        mPlayer.getPlaybackState()
    }

    var bleScannerCallbacks: BLEScanner.BLEScannerCallback = object : BLEScanner.BLEScannerCallback {
        override fun onStartScan() {
        }

        override fun onStopScan() {
        }

        override fun onScanFailed(p0: Int, p1: String?) {
        }

        override fun onScanResult(devices: BluetoothDevice?, p1: ByteArray?) {
            if (connectedDevice[0].deviceMacId.equals(devices?.address) && !bleCentralManager.isDeviceConnected(connectedDevice[0].deviceMacId)){
                bleScanner.stopScan()
                bleCentralManager.connect(devices?.address)
                //binding.tvConnectedDevice.text = "Connected to: ${devices?.name.toString()}"
                startPlayer()
            }
        }

        override fun onScanTimeout() {

        }

    }

    private var bluetoothCentralManagerCallbacks: BleCentralManagerCallbacks =
        object : BleCentralManagerCallbacks() {
            @SuppressLint("LongLogTag")
            override fun onDeviceDisconnected(macId: String?) {
                pausePlayer()
                Log.d("Connected Device onDeviceDisconnected  ", macId.toString())
                // binding.tvConnectedDevice.text = "No Device Connected"
                bleScanner.startDeviceScan()
                Toast.makeText(applicationContext,"Started Scan",Toast.LENGTH_SHORT).show()
            }

            @SuppressLint("LongLogTag")
            override fun onDeviceConnectionFailed(macId: String?) {
                Log.d("Connected Device onDeviceConnectionFailed  ", macId.toString())

            }

            @SuppressLint("LongLogTag")
            override fun onDeviceConnectedAndReadyToCommunicate(
                macId: String?,
                map: MutableMap<String, ArrayList<String>>?
            ) {
                Log.d("Connected Device onDeviceConnectedAndReadyToCommunicate  ", macId.toString())
            }

            @SuppressLint("LongLogTag")
            override fun onCharacteristicWrite(
                macId: String?,
                serviceUUID: UUID?,
                characteristicUUID: UUID?,
                rawData: ByteArray?
            ) {
                super.onCharacteristicWrite(macId, serviceUUID, characteristicUUID, rawData)
                Log.d(
                    "Connected Device  onCharacteristicWrite",
                    "serviceUUID : $serviceUUID -- characteristicUUID: $characteristicUUID -- rawData : ${
                        ByteConverter.getHexStringFromByteArray(
                            rawData,
                            true
                        )
                    } "
                )
            }

            @SuppressLint("LongLogTag")
            override fun onCharacteristicWriteFailed(
                macId: String?,
                serviceUUID: UUID?,
                characteristicUUID: UUID?,
                rawData: ByteArray?
            ) {
                super.onCharacteristicWriteFailed(macId, serviceUUID, characteristicUUID, rawData)
                Log.d("Connected Device onCharacteristicWriteFailed ", ByteConverter.getHexStringFromByteArray(rawData,true))

            }

            @SuppressLint("LongLogTag")
            override fun onCharacteristicUpdate(
                macId: String?,
                serviceUUID: UUID?,
                characteristicUUID: UUID?,
                rawData: ByteArray?
            ) {
                super.onCharacteristicUpdate(macId, serviceUUID, characteristicUUID, rawData)
                Log.d(
                    "Connected Device onCharacteristicUpdate",
                    "serviceUUID : $serviceUUID -- characteristicUUID: $characteristicUUID -- rawData : ${
                        ByteConverter.getHexStringFromByteArray(
                            rawData,
                            true
                        )
                    } "
                )
            }

            @SuppressLint("LongLogTag")
            override fun onCharacteristicReadFailed(
                macId: String?,
                serviceUUID: UUID?,
                characteristicUUID: UUID?
            ) {
                super.onCharacteristicReadFailed(macId, serviceUUID, characteristicUUID)
                Log.d("Connected Device onCharacteristicReadFailed ", macId.toString())

            }


        }

    private fun playVideo(uri: Uri) {

        mPlayerView.player = mPlayer
        mPlayer.prepare(buildMediaSource(uri))
        mPlayer.playWhenReady = true
        durationSet = false

    }

    private fun buildMediaSource(uri: Uri): MediaSource? {
        val dataSourceFactory: DataSource.Factory = FileDataSourceFactory()
        return ExtractorMediaSource(
            uri, dataSourceFactory,
            DefaultExtractorsFactory(), null, null
        )
    }


    private fun isPlaying(): Boolean {

        return mPlayer != null && mPlayer.playbackState !== ExoPlayer.STATE_ENDED
                && mPlayer.playbackState !== ExoPlayer.STATE_IDLE && mPlayer.playWhenReady

    }


    override fun onStart() {
        super.onStart()
        bleCentralManager.setAllowOperationsOnUIThread(true)
        bleScanner.setOnBLEScanListener(bleScannerCallbacks) // Sets BLE Scanner event callback
        bleCentralManager.setOnBluetoothCentralListener(bluetoothCentralManagerCallbacks)
    }

    override fun onDestroy() {
        super.onDestroy()
        mPlayer.release()
    }

    override fun onPause() {
        super.onPause()
        mPlayer.playWhenReady = false
        mPlayer.playbackState
    }

    @SuppressLint("LongLogTag")
    override fun onResume() {
        super.onResume()
        if(bleCentralManager.isDeviceConnected(connectedDeviceName)){
            bleScanner.startDeviceScan()
        }
    }

    override fun onStop() {
        super.onStop()
        bleScanner.stopScan()
        bleScanner.setOnBLEScanListener(null)
    }

}