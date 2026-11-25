package com.karolina.studenteats.ui.screens

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.defaultMinSize
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.OutlinedTextFieldDefaults
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.karolina.studenteats.ui.icons.AppIcons
import com.karolina.studenteats.ui.model.Dish
import com.karolina.studenteats.ui.model.DishCategory
import com.karolina.studenteats.ui.model.SampleMenuData
import com.karolina.studenteats.ui.navigation.MainTab
import com.karolina.studenteats.ui.navigation.StudentEatsBottomBar
import com.karolina.studenteats.ui.theme.BackgroundWhite
import com.karolina.studenteats.ui.theme.OutlineSubtle
import com.karolina.studenteats.ui.theme.PrimaryGreen
import com.karolina.studenteats.ui.theme.StarAmber
import com.karolina.studenteats.ui.theme.StudentEatsTheme
import com.karolina.studenteats.ui.theme.SurfaceHighlightGreen
import com.karolina.studenteats.ui.theme.TextPrimary
import com.karolina.studenteats.ui.theme.TextSecondary

@Composable
fun StartScreen(
    dishes: List<Dish>,
    onDishClick: (Dish) -> Unit,
    modifier: Modifier = Modifier
) {
    var searchQuery by rememberSaveable { mutableStateOf("") }
    var filtersSelected by rememberSaveable { mutableStateOf(false) }

    val groupedDishes = DishCategory.entries.associateWith { category ->
        dishes.filter { it.category == category }
    }

    Column(
        modifier = modifier
            .fillMaxSize()
    ) {
        // Górny nagłówek ekranu głównego (bez ikony Wstecz)
        StartHeader(
            searchQuery = searchQuery,
            onSearchQueryChange = { searchQuery = it },
            filtersSelected = filtersSelected,
            onFiltersClick = { filtersSelected = !filtersSelected }
        )

        HorizontalDivider(color = OutlineSubtle, thickness = 1.dp)

        // Pionowy Stos (jedna kolumna, brak przewijania w poziomie) z Zatytułowanymi Sekcjami
        LazyColumn(
            modifier = Modifier.fillMaxSize(),
            contentPadding = PaddingValues(horizontal = 16.dp, vertical = 12.dp),
            verticalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            DishCategory.entries.forEach { category ->
                val categoryDishes = groupedDishes[category].orEmpty()
                if (categoryDishes.isNotEmpty()) {
                    item(key = "header_${category.name}") {
                        CategorySectionHeader(title = category.title)
                    }
                    items(
                        items = categoryDishes,
                        key = { dish -> dish.id }
                    ) { dish ->
                        DishMenuCard(
                            dish = dish,
                            onClick = { onDishClick(dish) }
                        )
                    }
                }
            }
        }
    }
}

@Composable
private fun StartHeader(
    searchQuery: String,
    onSearchQueryChange: (String) -> Unit,
    filtersSelected: Boolean,
    onFiltersClick: () -> Unit
) {
    Surface(
        color = BackgroundWhite,
        modifier = Modifier.fillMaxWidth()
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 16.dp, vertical = 10.dp)
        ) {
            Row(
                verticalAlignment = Alignment.CenterVertically,
                modifier = Modifier.fillMaxWidth()
            ) {
                Surface(
                    shape = RoundedCornerShape(10.dp),
                    color = SurfaceHighlightGreen,
                    modifier = Modifier.size(36.dp)
                ) {
                    Icon(
                        imageVector = AppIcons.Restaurant,
                        contentDescription = "Logo Stołówka Studencka",
                        tint = PrimaryGreen,
                        modifier = Modifier.padding(7.dp)
                    )
                }
                Spacer(modifier = Modifier.width(10.dp))
                Column {
                    Text(
                        text = "Stołówka Studencka",
                        style = MaterialTheme.typography.titleLarge,
                        color = TextPrimary
                    )
                    Text(
                        text = "Dzisiejsze menu",
                        style = MaterialTheme.typography.bodyMedium,
                        color = TextSecondary
                    )
                }
            }

            Spacer(modifier = Modifier.height(10.dp))

            Row(
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.spacedBy(8.dp),
                modifier = Modifier.fillMaxWidth()
            ) {
                OutlinedTextField(
                    value = searchQuery,
                    onValueChange = onSearchQueryChange,
                    singleLine = true,
                    placeholder = {
                        Text(
                            text = "Wyszukaj",
                            style = MaterialTheme.typography.bodyMedium,
                            color = TextSecondary
                        )
                    },
                    leadingIcon = {
                        Icon(
                            imageVector = AppIcons.Search,
                            contentDescription = "Wyszukaj danie",
                            tint = TextSecondary
                        )
                    },
                    shape = RoundedCornerShape(12.dp),
                    colors = OutlinedTextFieldDefaults.colors(
                        focusedBorderColor = PrimaryGreen,
                        unfocusedBorderColor = OutlineSubtle,
                        focusedContainerColor = BackgroundWhite,
                        unfocusedContainerColor = BackgroundWhite
                    ),
                    modifier = Modifier
                        .weight(1f)
                        .defaultMinSize(minHeight = 52.dp)
                )

                OutlinedButton(
                    onClick = onFiltersClick,
                    shape = RoundedCornerShape(12.dp),
                    border = BorderStroke(
                        width = 1.dp,
                        color = if (filtersSelected) PrimaryGreen else OutlineSubtle
                    ),
                    colors = ButtonDefaults.outlinedButtonColors(
                        containerColor = if (filtersSelected) SurfaceHighlightGreen else BackgroundWhite,
                        contentColor = if (filtersSelected) PrimaryGreen else TextPrimary
                    ),
                    contentPadding = PaddingValues(horizontal = 12.dp, vertical = 12.dp),
                    modifier = Modifier.defaultMinSize(minWidth = 48.dp, minHeight = 52.dp)
                ) {
                    Icon(
                        imageVector = AppIcons.FilterList,
                        contentDescription = "Filtry menu",
                        modifier = Modifier.size(20.dp)
                    )
                    Spacer(modifier = Modifier.width(6.dp))
                    Text(
                        text = "Filtry",
                        style = MaterialTheme.typography.labelLarge
                    )
                }
            }
        }
    }
}

@Composable
private fun CategorySectionHeader(title: String) {
    Surface(
        color = SurfaceHighlightGreen,
        shape = RoundedCornerShape(8.dp),
        modifier = Modifier
            .fillMaxWidth()
            .padding(top = 4.dp)
    ) {
        Text(
            text = title,
            style = MaterialTheme.typography.titleSmall,
            color = PrimaryGreen,
            modifier = Modifier.padding(horizontal = 12.dp, vertical = 6.dp)
        )
    }
}

@Composable
private fun DishMenuCard(
    dish: Dish,
    onClick: () -> Unit
) {
    Card(
        onClick = onClick,
        shape = RoundedCornerShape(12.dp),
        colors = CardDefaults.cardColors(containerColor = BackgroundWhite),
        border = BorderStroke(1.dp, OutlineSubtle),
        modifier = Modifier
            .fillMaxWidth()
            .defaultMinSize(minHeight = 64.dp)
    ) {
        Row(
            verticalAlignment = Alignment.CenterVertically,
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 14.dp, vertical = 10.dp)
        ) {
            Column(modifier = Modifier.weight(1f)) {
                Row(
                    verticalAlignment = Alignment.Top,
                    horizontalArrangement = Arrangement.SpaceBetween,
                    modifier = Modifier.fillMaxWidth()
                ) {
                    Text(
                        text = dish.name,
                        style = MaterialTheme.typography.titleMedium,
                        color = TextPrimary,
                        modifier = Modifier.weight(1f)
                    )
                    Spacer(modifier = Modifier.width(8.dp))
                    Text(
                        text = dish.formattedPrice,
                        style = MaterialTheme.typography.titleMedium,
                        fontWeight = FontWeight.Bold,
                        color = PrimaryGreen
                    )
                }

                Spacer(modifier = Modifier.height(6.dp))

                Row(
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.spacedBy(8.dp)
                ) {
                    Row(verticalAlignment = Alignment.CenterVertically) {
                        Icon(
                            imageVector = AppIcons.StarFilled,
                            contentDescription = "Średnia ocena",
                            tint = StarAmber,
                            modifier = Modifier.size(16.dp)
                        )
                        Spacer(modifier = Modifier.width(4.dp))
                        Text(
                            text = dish.formattedRating,
                            style = MaterialTheme.typography.bodyMedium,
                            color = TextSecondary
                        )
                    }

                    if (dish.isWege) {
                        WegeBadge()
                    }
                }
            }

            Spacer(modifier = Modifier.width(6.dp))
            Icon(
                imageVector = AppIcons.ChevronRight,
                contentDescription = "Zobacz szczegóły dania ${dish.name}",
                tint = TextSecondary,
                modifier = Modifier.size(22.dp)
            )
        }
    }
}

@Composable
fun WegeBadge(modifier: Modifier = Modifier) {
    Surface(
        color = SurfaceHighlightGreen,
        border = BorderStroke(1.dp, PrimaryGreen),
        shape = RoundedCornerShape(6.dp),
        modifier = modifier
    ) {
        Text(
            text = "WEGE",
            style = MaterialTheme.typography.labelMedium,
            color = PrimaryGreen,
            modifier = Modifier.padding(horizontal = 8.dp, vertical = 2.dp)
        )
    }
}

@Preview(showBackground = true, widthDp = 360, heightDp = 800)
@Composable
fun StartScreenPreview() {
    StudentEatsTheme {
        androidx.compose.material3.Scaffold(
            containerColor = BackgroundWhite,
            bottomBar = {
                StudentEatsBottomBar(
                    selectedTab = MainTab.START,
                    onTabSelected = {}
                )
            }
        ) { innerPadding ->
            StartScreen(
                dishes = SampleMenuData.dishes,
                onDishClick = {},
                modifier = Modifier.padding(innerPadding)
            )
        }
    }
}
