package com.example.adminpannel.model

import android.os.Parcel
import android.os.Parcelable
import java.io.Serializable

class OrderDetails():Serializable{
    var userUid: String? = null
    var userName: String? = null
    var foodNames: ArrayList<String>? = null
    var foodImages: ArrayList<String>? = null
    var foodprices: String?=null
    var foodQuantities: ArrayList<Int>? = null


    var address: String? = null
    var totalPrice: String? = null
    var phoneNumber: String? = null
    var orderAccepted: Boolean = false
    var paymentRecieved: Boolean = false
    var itemPushKey: String? = null
    var currentTime: Long = 0

    constructor(parcel: Parcel) : this() {
        userUid = parcel.readString()
        userName = parcel.readString()
        address = parcel.readString()
        totalPrice = parcel.readString()
        phoneNumber = parcel.readString()
        orderAccepted = parcel.readByte() != 0.toByte()
        paymentRecieved = parcel.readByte() != 0.toByte()
        itemPushKey = parcel.readString()
        currentTime = parcel.readLong()
    }

     fun describeContents(): Int {
        TODO("Not yet implemented")
    }

    fun writeToParcel(p0: Parcel, p1: Int) {
        TODO("Not yet implemented")
    }

    companion object CREATOR : Parcelable.Creator<OrderDetails> {
        override fun createFromParcel(parcel: Parcel): OrderDetails {
            return OrderDetails(parcel)
        }

        override fun newArray(size: Int): Array<OrderDetails?> {
            return arrayOfNulls(size)
        }
    }
}