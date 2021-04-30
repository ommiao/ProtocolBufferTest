package com.example.protocolbuffertest

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.example.apilib.AddressBookProtos

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val person = AddressBookProtos.Person.newBuilder()
            .setId(1)
            .setEmail("xxx")
            .setName("xx")
            .build()

        val addressBook = AddressBookProtos.AddressBook.newBuilder()
            .addPeople(person)
            .build()




    }
}