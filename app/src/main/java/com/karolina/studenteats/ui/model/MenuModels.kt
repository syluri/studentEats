package com.karolina.studenteats.ui.model

enum class DishCategory(val title: String) {
    SOUPS("ZUPY"),
    MAIN_COURSES("DANIA GŁÓWNE"),
    DESSERTS("DESERY")
}

data class Dish(
    val id: Int,
    val name: String,
    val category: DishCategory,
    val priceZl: Int,
    val isWege: Boolean,
    val averageRating: Double,
    val reviewCount: Int,
    val kcal: Int,
    val ingredients: List<String>,
    val allergens: List<String>
) {
    val formattedPrice: String
        get() = "$priceZl zł"

    val formattedRating: String
        get() = "${"%.1f".format(java.util.Locale.US, averageRating)}/5 ($reviewCount)"
}

data class UserReviewItem(
    val id: Int,
    val dishId: Int,
    val dishName: String,
    val userStars: Int,
    val dateLabel: String,
    val commentPreview: String
)

object SampleMenuData {
    val dishes: List<Dish> = listOf(
        Dish(
            id = 1,
            name = "Zupa pomidorowa",
            category = DishCategory.SOUPS,
            priceZl = 8,
            isWege = true,
            averageRating = 4.0,
            reviewCount = 21,
            kcal = 240,
            ingredients = listOf("bulion warzywny", "pomidory", "makaron", "śmietana", "bazylia"),
            allergens = listOf("gluten", "mleko")
        ),
        Dish(
            id = 2,
            name = "Kotlet schabowy",
            category = DishCategory.MAIN_COURSES,
            priceZl = 21,
            isWege = false,
            averageRating = 4.5,
            reviewCount = 34,
            kcal = 650,
            ingredients = listOf("schab wieprzowy", "jajko", "bułka tarta", "ziemniaki", "kapusta"),
            allergens = listOf("gluten", "jaja")
        ),
        Dish(
            id = 3,
            name = "Gołąbki w sosie pomidorowym",
            category = DishCategory.MAIN_COURSES,
            priceZl = 15,
            isWege = false,
            averageRating = 3.5,
            reviewCount = 44,
            kcal = 520,
            ingredients = listOf("mięso wieprzowe", "ryż", "kapusta biała", "sos pomidorowy"),
            allergens = listOf("seler")
        ),
        Dish(
            id = 4,
            name = "Szarlotka",
            category = DishCategory.DESSERTS,
            priceZl = 12,
            isWege = true,
            averageRating = 4.0,
            reviewCount = 41,
            kcal = 360,
            ingredients = listOf("jabłka", "mąka pszenna", "masło", "cukier", "cynamon"),
            allergens = listOf("gluten", "mleko", "jaja")
        ),
        Dish(
            id = 5,
            name = "Drożdżówka z serem",
            category = DishCategory.DESSERTS,
            priceZl = 3,
            isWege = true,
            averageRating = 5.0,
            reviewCount = 121,
            kcal = 310,
            ingredients = listOf("ciasto drożdżowe", "twaróg", "jajko", "wanilia"),
            allergens = listOf("gluten", "mleko", "jaja")
        )
    )

    val myReviews: List<UserReviewItem> = listOf(
        UserReviewItem(
            id = 101,
            dishId = 2,
            dishName = "Kotlet schabowy",
            userStars = 5,
            dateLabel = "Dzisiaj, 13:15",
            commentPreview = "Chrupiąca panierka i duża porcja ziemniaków."
        ),
        UserReviewItem(
            id = 102,
            dishId = 1,
            dishName = "Zupa pomidorowa",
            userStars = 4,
            dateLabel = "Wczoraj, 12:40",
            commentPreview = "Dobra i ciepła zupa między wykładami."
        ),
        UserReviewItem(
            id = 103,
            dishId = 5,
            dishName = "Drożdżówka z serem",
            userStars = 5,
            dateLabel = "20.11.2026",
            commentPreview = "Świeża i bardzo tania przekąska."
        )
    )

    fun findDishById(dishId: Int): Dish =
        dishes.firstOrNull { it.id == dishId } ?: dishes.first()
}
