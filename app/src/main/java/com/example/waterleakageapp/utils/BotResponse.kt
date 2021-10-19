

package com.example.waterleakageapp.utils
import java.util.*

object BotResponse {

    @Suppress("NAME_SHADOWING")
    fun basicResponses(message: String): String {

        val random = (0..2).random()
        val message  = message.toLowerCase(Locale.ROOT)

        return when {
            message.contains("hello") -> {
                val answer =
                    " Hello! I am Ari. Try asking me by typing one of the keywords below:-\\n\\nAbout us\\nComplaint\\nService Type\\nEnquiry\\nOffice Location"
                answer
            }
            message.contains("enquiry") ->{
                val answer = "Do you have an enquiry regarding your water bill or billing account? Call our Careline at 082-111111, \n" +
                        "or email us at forfyp60976@gmail.com for further assistance. "
                answer
            }
            message.contains("about us") -> {
                    val answer = "Hello, what would you like to know about JBALB Sarawak? \n" +
                            "Please visit our website at https://jbalb.sarawak.gov.my/"
                    answer
            }

            message.contains("office location") -> {
                    val answer = "Our Headquarters is located at the following address: \nJabatan Bekalan Air Luar Bandar Sarawak, No.55, Tingkat Mezzanine, Bangunan ST3, Jalan Simpang Tiga, 93350 Kuching, Sarawak. This Google map will lead you straight to us here:https://goo.gl/maps/DCa254bBwSF7KhEDA"
                    answer
            }
            message.contains("service type") -> {
                val answer = "We offers a complete range of services including:\n" +
                        "a) Complaints and Damages\n" +
                        "b) Enquiries of New Meter Application, Outstanding Bill, Disconnection and Reconnection\n" +
                        "c) Request of Water and Billing Delivery.\n" +
                        "For more information, please visit our website."
                answer
            }
            message.contains("complaint")->{
                val answer = "Are you facing a problem with your water supply? \nDo fill in the Report Form provided in this application for further assistance"
                answer
            }

                else -> {
                    when (random) {
                        0 -> "I don't understand. Please try again."
                        1 -> "Sorry, please try another keywords provided"
                        2 -> "try asking me something different"
                        else -> "error" }

                }
            }
        }

    }
