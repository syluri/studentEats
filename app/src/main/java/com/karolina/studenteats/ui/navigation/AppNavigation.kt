package com.karolina.studenteats.ui.navigation

import androidx.activity.compose.BackHandler
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.defaultMinSize
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.NavigationBarItemDefaults
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.unit.dp
import com.karolina.studenteats.ui.icons.AppIcons
import com.karolina.studenteats.ui.model.SampleMenuData
import com.karolina.studenteats.ui.screens.DishDetailsScreen
import com.karolina.studenteats.ui.screens.MyReviewsScreen
import com.karolina.studenteats.ui.screens.ProfileScreen
import com.karolina.studenteats.ui.screens.RatingFormScreen
import com.karolina.studenteats.ui.screens.StartScreen
import com.karolina.studenteats.ui.theme.BackgroundWhite
import com.karolina.studenteats.ui.theme.OutlineSubtle
import com.karolina.studenteats.ui.theme.PrimaryGreen
import com.karolina.studenteats.ui.theme.SurfaceHighlightGreen
import com.karolina.studenteats.ui.theme.TextPrimary
import com.karolina.studenteats.ui.theme.TextSecondary

enum class MainTab(
    val label: String,
    val contentDescription: String
) {
    START("Start", "Start – dzisiejsze menu"),
    MY_REVIEWS("Moje oceny", "Moje oceny – historia ocen"),
    PROFILE("Profil", "Profil – preferencje użytkownika");

    val icon: ImageVector
        get() = when (this) {
            START -> AppIcons.Restaurant
            MY_REVIEWS -> AppIcons.RateReview
            PROFILE -> AppIcons.Person
        }
}

private enum class ScreenLevel {
    MAIN_TAB,     // E1 Start, E4 Moje oceny, E5 Profil (z Dolnym Paskiem Nawigacyjnym, bez Wstecz)
    DISH_DETAILS, // E2 Szczegóły dania (bez dolnego paska, z ikoną Wstecz do E1 lub E4)
    RATING_FORM   // E3 Formularz oceny (bez dolnego paska, z ikoną Wstecz/Anuluj do E2 oraz Wyślij do E1)
}

@Composable
fun StudentEatsApp() {
    var activeTabName by rememberSaveable { mutableStateOf(MainTab.START.name) }
    var screenLevelName by rememberSaveable { mutableStateOf(ScreenLevel.MAIN_TAB.name) }
    var selectedDishId by rememberSaveable { mutableIntStateOf(SampleMenuData.dishes.first().id) }
    var detailsOriginTabName by rememberSaveable { mutableStateOf(MainTab.START.name) }

    val activeTab = MainTab.valueOf(activeTabName)
    val screenLevel = ScreenLevel.valueOf(screenLevelName)
    val detailsOriginTab = MainTab.valueOf(detailsOriginTabName)
    val selectedDish = SampleMenuData.findDishById(selectedDishId)

    val navigateBackFromSubScreen = {
        when (screenLevel) {
            ScreenLevel.RATING_FORM -> {
                // FORMULARZ OCENY -> SZCZEGÓŁY DANIA (ikona Wstecz / przycisk Anuluj)
                screenLevelName = ScreenLevel.DISH_DETAILS.name
            }
            ScreenLevel.DISH_DETAILS -> {
                // SZCZEGÓŁY DANIA -> ekran źródłowy (START lub MOJE OCENY)
                activeTabName = detailsOriginTab.name
                screenLevelName = ScreenLevel.MAIN_TAB.name
            }
            ScreenLevel.MAIN_TAB -> Unit
        }
    }

    BackHandler(enabled = screenLevel != ScreenLevel.MAIN_TAB) {
        navigateBackFromSubScreen()
    }

    Scaffold(
        modifier = Modifier.fillMaxSize(),
        containerColor = BackgroundWhite,
        topBar = {
            when (screenLevel) {
                ScreenLevel.MAIN_TAB -> Unit
                ScreenLevel.DISH_DETAILS -> SubScreenTopBar(
                    title = "Szczegóły dania",
                    onBackClick = navigateBackFromSubScreen
                )
                ScreenLevel.RATING_FORM -> SubScreenTopBar(
                    title = "Formularz oceny",
                    onBackClick = navigateBackFromSubScreen
                )
            }
        },
        bottomBar = {
            if (screenLevel == ScreenLevel.MAIN_TAB) {
                StudentEatsBottomBar(
                    selectedTab = activeTab,
                    onTabSelected = { tab ->
                        activeTabName = tab.name
                        screenLevelName = ScreenLevel.MAIN_TAB.name
                    }
                )
            }
        }
    ) { innerPadding ->
        when (screenLevel) {
            ScreenLevel.MAIN_TAB -> {
                when (activeTab) {
                    MainTab.START -> StartScreen(
                        dishes = SampleMenuData.dishes,
                        onDishClick = { dish ->
                            // START -> SZCZEGÓŁY DANIA (zapamiętujemy źródło START)
                            selectedDishId = dish.id
                            detailsOriginTabName = MainTab.START.name
                            screenLevelName = ScreenLevel.DISH_DETAILS.name
                        },
                        modifier = Modifier.padding(innerPadding)
                    )

                    MainTab.MY_REVIEWS -> MyReviewsScreen(
                        reviews = SampleMenuData.myReviews,
                        onReviewClick = { review ->
                            // MOJE OCENY -> SZCZEGÓŁY DANIA (zapamiętujemy źródło MOJE OCENY)
                            selectedDishId = review.dishId
                            detailsOriginTabName = MainTab.MY_REVIEWS.name
                            screenLevelName = ScreenLevel.DISH_DETAILS.name
                        },
                        modifier = Modifier.padding(innerPadding)
                    )

                    MainTab.PROFILE -> ProfileScreen(
                        modifier = Modifier.padding(innerPadding)
                    )
                }
            }

            ScreenLevel.DISH_DETAILS -> DishDetailsScreen(
                dish = selectedDish,
                onAddRatingClick = {
                    // SZCZEGÓŁY DANIA -> FORMULARZ OCENY
                    screenLevelName = ScreenLevel.RATING_FORM.name
                },
                modifier = Modifier.padding(innerPadding)
            )

            ScreenLevel.RATING_FORM -> RatingFormScreen(
                dish = selectedDish,
                onSubmitClick = {
                    // FORMULARZ OCENY -> START (przycisk "Wyślij")
                    activeTabName = MainTab.START.name
                    screenLevelName = ScreenLevel.MAIN_TAB.name
                },
                onCancelClick = {
                    // FORMULARZ OCENY -> SZCZEGÓŁY DANIA (przycisk "Anuluj")
                    screenLevelName = ScreenLevel.DISH_DETAILS.name
                },
                modifier = Modifier.padding(innerPadding)
            )
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun SubScreenTopBar(
    title: String,
    onBackClick: () -> Unit
) {
    Column(modifier = Modifier.fillMaxWidth()) {
        TopAppBar(
            title = {
                Text(
                    text = title,
                    style = MaterialTheme.typography.titleLarge,
                    color = TextPrimary
                )
            },
            navigationIcon = {
                IconButton(
                    onClick = onBackClick,
                    modifier = Modifier.defaultMinSize(minWidth = 48.dp, minHeight = 48.dp)
                ) {
                    Icon(
                        imageVector = AppIcons.ArrowBack,
                        contentDescription = "Wstecz",
                        tint = TextPrimary
                    )
                }
            },
            colors = TopAppBarDefaults.topAppBarColors(
                containerColor = BackgroundWhite,
                titleContentColor = TextPrimary,
                navigationIconContentColor = TextPrimary
            )
        )
        HorizontalDivider(color = OutlineSubtle, thickness = 1.dp)
    }
}

@Composable
fun StudentEatsBottomBar(
    selectedTab: MainTab,
    onTabSelected: (MainTab) -> Unit
) {
    Column(modifier = Modifier.fillMaxWidth()) {
        HorizontalDivider(color = OutlineSubtle, thickness = 1.dp)
        NavigationBar(
            containerColor = BackgroundWhite,
            tonalElevation = 0.dp
        ) {
            MainTab.entries.forEach { tab ->
                val isSelected = tab == selectedTab
                NavigationBarItem(
                    selected = isSelected,
                    onClick = { onTabSelected(tab) },
                    icon = {
                        Icon(
                            imageVector = tab.icon,
                            contentDescription = tab.contentDescription
                        )
                    },
                    label = {
                        Text(
                            text = tab.label,
                            style = MaterialTheme.typography.labelMedium
                        )
                    },
                    colors = NavigationBarItemDefaults.colors(
                        selectedIconColor = PrimaryGreen,
                        selectedTextColor = TextPrimary,
                        indicatorColor = SurfaceHighlightGreen,
                        unselectedIconColor = TextSecondary,
                        unselectedTextColor = TextSecondary
                    )
                )
            }
        }
    }
}
