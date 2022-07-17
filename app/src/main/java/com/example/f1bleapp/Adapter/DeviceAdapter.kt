package com.example.f1bleapp.Adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.f1bleapp.Data.Local.DeviceData
import com.example.f1bleapp.R
import kotlinx.android.synthetic.main.device_list_item.view.*

class DeviceAdapter : RecyclerView.Adapter<DeviceAdapter.DeviceViewHolder>() {
    var items: ArrayList<DeviceData> = arrayListOf()

    inner class DeviceViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): DeviceViewHolder {
        return DeviceViewHolder(
            LayoutInflater.from(parent.context).inflate(R.layout.device_list_item, parent, false)
        )
    }

    private var onItemClickListener: ((DeviceData) -> Unit)? = null

    fun setOnItemClickListener(listener: (DeviceData) -> Unit) {
        onItemClickListener = listener
    }

    override fun onBindViewHolder(holder: DeviceViewHolder, position: Int) {
        val currentItem = items[position]
        holder.itemView.tv_item_device_name.text = currentItem.deviceName
        holder.itemView.button_item_connect.apply {
            text = "Connect"
        }
        holder.itemView.setOnClickListener {
            onItemClickListener?.let { click ->
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