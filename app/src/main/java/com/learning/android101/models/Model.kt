package com.learning.android101.models

data class Hobby(var title: String)

object Supplier {
    val hobbies = listOf(
        Hobby("Swimming"),
        Hobby("Reading"),
        Hobby("Sleeping"),
        Hobby("Baking"),
        Hobby("Dancing"),
        Hobby("Programming"),
        Hobby("Cooking"),
        Hobby("Gaming"),
        Hobby("Hiking"),
        Hobby("Hacking"),
        Hobby("Skating"),
        Hobby("Snowboarding"),
        Hobby("Running"),
        Hobby("Singing"),
        Hobby("Surfing")
    )
}