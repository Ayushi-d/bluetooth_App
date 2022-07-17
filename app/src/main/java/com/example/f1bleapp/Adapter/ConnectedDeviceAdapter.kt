package com.example.f1bleapp.Adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.f1bleapp.Data.Local.DeviceData
import com.example.f1bleapp.R
import kotlinx.android.synthetic.main.device_list_item.view.*

class ConnectedDeviceAdapter :
    RecyclerView.Adapter<ConnectedDeviceAdapter.ConnectedDeviceViewHolder>() {
    var items: ArrayList<DeviceData> = arrayListOf()

    inner class ConnectedDeviceViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ConnectedDeviceViewHolder {
        return ConnectedDeviceViewHolder(
            LayoutInflater.from(parent.context).inflate(R.layout.device_list_item, parent, false)
        )
    }

    private var onItemConnectedClickListener: ((DeviceData) -> Unit)? = null

    fun setOnConnectedItemClickListener(listener: (DeviceData) -> Unit) {
        onItemConnectedClickListener = listener
    }

    override fun onBindViewHolder(holder: ConnectedDeviceViewHolder, position: Int) {
        val currentItem = items[position]
        holder.itemView.tv_item_device_name.text = currentItem.deviceName
        holder.itemView.button_item_connect.apply {
            text = "Disconnect"
        }
        holder.itemView.setOnClickListener {
            onItemConnectedClickListener?.let { click ->
                click(currentItem)
            }
        }
    }

    override fun getItemCount(): Int {
        return items.size
    }

    fun addDevice(deviceData: DeviceData?) {
        if (!items.contains(deviceData)) {
            deviceData?.let { items.add(it) }
            notifyItemInserted(items.size - 1)
        }
    }

    fun removeDevice(deviceData: DeviceData?) {
        val index: Int = this.items.indexOf(deviceData)
        if (index > -1) {
            items.removeAt(index)
            notifyItemRemoved(index)
            notifyItemRangeChanged(index, items.size)
        }
    }

    fun clear() {
        items.clear()
        notifyDataSetChanged()
    }

    fun update(list: List<DeviceData?>) {
        this.items = list as ArrayList<DeviceData>
        notifyDataSetChanged()
    }
}