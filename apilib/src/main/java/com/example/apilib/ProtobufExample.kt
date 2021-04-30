package com.example.apilib

import retrofit2.*
import retrofit2.converter.protobuf.ProtoConverterFactory
import retrofit2.http.GET

fun main(){

    //how to use
//    example1()

    //the difference between proto-data and json
//    example2()

    //how to use with Retrofit
    //see the class named ProtobufWithRetrofitExample


}

fun example1() {

    //0, prepare my addressBook data
    val person = AddressBookProtos.Person.newBuilder()
        .setId(1)
        .setEmail("xxx@xx.xx")
        .setName("Jobs")
        .build()

    val addressBook = AddressBookProtos.AddressBook.newBuilder()
        .addPeople(person)
        .build()


    //1, how to serialize entity to byteArray
    val addressBookByteArray = addressBook.toByteArray()
    printToFile("./data-proto-addressbook-single-person.txt", addressBookByteArray)

    //2, how to deserialize to entity
    val addressBookParsed = AddressBookProtos.AddressBook.parseFrom(addressBookByteArray)
    val personParsed = addressBookParsed.getPeople(0)
    println("addressBook[id:${personParsed.id}, email:${personParsed.email}, name:${personParsed.name}]")

}

fun example2() {

    val addressBookBuilder = AddressBookProtos.AddressBook.newBuilder()

    val addressBookJson = StringBuilder("[")

    for (i in 1..100){
        val person = AddressBookProtos.Person.newBuilder()
            .setId(i)
            .setEmail("xxx$i@xx.xx")
            .setName("jobs$i")
            .build()
        addressBookBuilder.addPeople(person)
        val personJson = "{\"id\":\"${person.id}\",\"email\":\"${person.email}\",\"name\":\"${person.name}\"}"
        addressBookJson.append(personJson).append(",")
    }
    val addressBook = addressBookBuilder.build()
    addressBookJson.append("]")

    val addressBookProtoByteArray = addressBook.toByteArray()

    println("size of addressBookProto:${addressBookProtoByteArray.size}, \n" +
            "the size of addressBookJson:${addressBookJson.toString().toByteArray().size}")

    printToFile("./data-proto-addressbook-most-people.txt", addressBookProtoByteArray)

}

class ProtobufWithRetrofitExample {

    private fun setupAdapter() :IPersonService {
        val retrofit = Retrofit.Builder()
            .baseUrl("base_url")
            //!important: add proto converter factory
            .addConverterFactory(ProtoConverterFactory.create())
            .build()
        return retrofit.create(IPersonService::class.java)
    }

    interface IPersonService {
        @GET("/api/v1/addressbook")
        fun getAddressBook() : Call<AddressBookProtos.AddressBook>
    }

    fun getAddressBook() {
        setupAdapter().getAddressBook().enqueue(object : Callback<AddressBookProtos.AddressBook>{

            override fun onResponse(
                call: Call<AddressBookProtos.AddressBook>,
                response: Response<AddressBookProtos.AddressBook>
            ) {
                val peopleList = response.body()?.peopleList
                //...
            }

            override fun onFailure(call: Call<AddressBookProtos.AddressBook>, t: Throwable) {

            }

        })
    }

}