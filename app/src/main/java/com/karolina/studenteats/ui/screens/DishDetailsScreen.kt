package com.karolina.studenteats.ui.screens

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.defaultMinSize
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import com.karolina.studenteats.ui.icons.AppIcons
import com.karolina.studenteats.ui.model.Dish
import com.karolina.studenteats.ui.theme.BackgroundWhite
import com.karolina.studenteats.ui.theme.ErrorRed
import com.karolina.studenteats.ui.theme.OnPrimaryWhite
import com.karolina.studenteats.ui.theme.OutlineSubtle
import com.karolina.studenteats.ui.theme.PrimaryGreen
import com.karolina.studenteats.ui.theme.StarAmber
import com.karolina.studenteats.ui.theme.SurfaceHighlightGreen
import com.karolina.studenteats.ui.theme.TextPrimary
import com.karolina.studenteats.ui.theme.TextSecondary

@Composable
fun DishDetailsScreen(
    dish: Dish,
    onAddRatingClick: () -> Unit,
    modifier: Modifier = Modifier
) {
    Column(
        modifier = modifier
            .fillMaxSize()
            .verticalScroll(rememberScrollState())
            .padding(16.dp),
        verticalArrangement = Arrangement.spacedBy(12.dp)
    ) {
        // Placeholder zdjęcia dania (szkielet E2)
        Surface(
            color = SurfaceHighlightGreen,
            shape = RoundedCornerShape(12.dp),
            border = BorderStroke(1.dp, OutlineSubtle),
            modifier = Modifier
                .fillMaxWidth()
                .height(150.dp)
        ) {
            Box(contentAlignment = Alignment.Center) {
                Column(horizontalAlignment = Alignment.CenterHorizontally) {
                    Icon(
                        imageVector = AppIcons.Restaurant,
                        contentDescription = "Zdjęcie dania ${dish.name}",
                        tint = PrimaryGreen,
                        modifier = Modifier.size(40.dp)
                    )
                    Spacer(modifier = Modifier.height(6.dp))
                    Text(
                        text = "Miejsce na zdjęcie dania (szkielet E2)",
                        style = MaterialTheme.typography.bodyMedium,
                        color = TextSecondary
                    )
                }
            }
        }

        // Nazwa, cena, ocena i oznaczenie WEGE
        Row(
            verticalAlignment = Alignment.Top,
            horizontalArrangement = Arrangement.SpaceBetween,
            modifier = Modifier.fillMaxWidth()
        ) {
            Text(
                text = dish.name,
                style = MaterialTheme.typography.headlineSmall,
                color = TextPrimary,
                modifier = Modifier.weight(1f)
            )
            Spacer(modifier = Modifier.width(8.dp))
            Text(
                text = dish.formattedPrice,
                style = MaterialTheme.typography.headlineSmall,
                color = PrimaryGreen
            )
        }

        Row(
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.spacedBy(10.dp)
        ) {
            Row(verticalAlignment = Alignment.CenterVertically) {
                Icon(
                    imageVector = AppIcons.StarFilled,
                    contentDescription = "Średnia ocena użytkowników",
                    tint = StarAmber,
                    modifier = Modifier.size(18.dp)
                )
                Spacer(modifier = Modifier.width(4.dp))
                Text(
                    text = dish.formattedRating,
                    style = MaterialTheme.typography.bodyMedium,
                    color = TextPrimary
                )
            }
            Text(
                text = "• ${dish.kcal} kcal",
                style = MaterialTheme.typography.bodyMedium,
                color = TextSecondary
            )
            if (dish.isWege) {
                WegeBadge()
            }
        }

        // Placeholder składu i alergenów (kodowanie redundantne: ikona + tekst + kolor #B3261E)
        Card(
            shape = RoundedCornerShape(12.dp),
            colors = CardDefaults.cardColors(containerColor = BackgroundWhite),
            border = BorderStroke(1.dp, OutlineSubtle),
            modifier = Modifier.fillMaxWidth()
        ) {
            Column(
                modifier = Modifier.padding(14.dp),
                verticalArrangement = Arrangement.spacedBy(8.dp)
            ) {
                Text(
                    text = "Składniki",
                    style = MaterialTheme.typography.titleMedium,
                    color = TextPrimary
                )
                Text(
                    text = dish.ingredients.joinToString(separator = ", "),
                    style = MaterialTheme.typography.bodyMedium,
                    color = TextSecondary
                )

                if (dish.allergens.isNotEmpty()) {
                    Row(verticalAlignment = Alignment.CenterVertically) {
                        Icon(
                            imageVector = AppIcons.Warning,
                            contentDescription = "Ostrzeżenie o alergenach",
                            tint = ErrorRed,
                            modifier = Modifier.size(18.dp)
                        )
                        Spacer(modifier = Modifier.width(6.dp))
                        Text(
                            text = "Alergeny: ${dish.allergens.joinToString(", ")}",
                            style = MaterialTheme.typography.bodyMedium,
                            fontWeight = FontWeight.SemiBold,
                            color = ErrorRed
                        )
                    }
                }
            }
        }

        // Placeholder ocen użytkowników
        Card(
            shape = RoundedCornerShape(12.dp),
            colors = CardDefaults.cardColors(containerColor = BackgroundWhite),
            border = BorderStroke(1.dp, OutlineSubtle),
            modifier = Modifier.fillMaxWidth()
        ) {
            Column(
                modifier = Modifier.padding(14.dp),
                verticalArrangement = Arrangement.spacedBy(6.dp)
            ) {
                Text(
                    text = "Oceny użytkowników",
                    style = MaterialTheme.typography.titleMedium,
                    color = TextPrimary
                )
                Text(
                    text = "Sekcja opinii i komentarzy studentów (szkielet ekranu E2).",
                    style = MaterialTheme.typography.bodyMedium,
                    color = TextSecondary
                )
            }
        }

        Spacer(modifier = Modifier.height(4.dp))

        // Przycisk przejścia: SZCZEGÓŁY DANIA -> FORMULARZ OCENY
        Button(
            onClick = onAddRatingClick,
            shape = RoundedCornerShape(12.dp),
            colors = ButtonDefaults.buttonColors(
                containerColor = PrimaryGreen,
                contentColor = OnPrimaryWhite
            ),
            modifier = Modifier
                .fillMaxWidth()
                .defaultMinSize(minWidth = 48.dp, minHeight = 52.dp)
        ) {
            Text(
                text = "Dodaj ocenę",
                style = MaterialTheme.typography.labelLarge
            )
        }
    }
}
