package com.karolina.studenteats.ui.screens

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.defaultMinSize
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.OutlinedTextFieldDefaults
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.karolina.studenteats.ui.icons.AppIcons
import com.karolina.studenteats.ui.model.Dish
import com.karolina.studenteats.ui.theme.BackgroundWhite
import com.karolina.studenteats.ui.theme.OnPrimaryWhite
import com.karolina.studenteats.ui.theme.OutlineSubtle
import com.karolina.studenteats.ui.theme.PrimaryGreen
import com.karolina.studenteats.ui.theme.StarAmber
import com.karolina.studenteats.ui.theme.SurfaceHighlightGreen
import com.karolina.studenteats.ui.theme.TextPrimary
import com.karolina.studenteats.ui.theme.TextSecondary

@Composable
fun RatingFormScreen(
    dish: Dish,
    onSubmitClick: () -> Unit,
    onCancelClick: () -> Unit,
    modifier: Modifier = Modifier
) {
    var selectedStars by rememberSaveable { mutableIntStateOf(0) }
    var commentText by rememberSaveable { mutableStateOf("") }

    Column(
        modifier = modifier
            .fillMaxSize()
            .verticalScroll(rememberScrollState())
            .padding(16.dp),
        verticalArrangement = Arrangement.spacedBy(12.dp)
    ) {
        Text(
            text = "Oceniane danie",
            style = MaterialTheme.typography.titleSmall,
            color = TextSecondary
        )
        Surface(
            color = SurfaceHighlightGreen,
            shape = RoundedCornerShape(10.dp),
            modifier = Modifier.fillMaxWidth()
        ) {
            Text(
                text = "${dish.name} (${dish.formattedPrice})",
                style = MaterialTheme.typography.titleMedium,
                color = TextPrimary,
                modifier = Modifier.padding(horizontal = 14.dp, vertical = 12.dp)
            )
        }

        Spacer(modifier = Modifier.height(4.dp))

        Text(
            text = "Twoja ocena (1–5)",
            style = MaterialTheme.typography.titleMedium,
            color = TextPrimary
        )

        // Cele dotykowe gwiazdek min. 48x48 dp
        Row(
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.spacedBy(4.dp)
        ) {
            for (star in 1..5) {
                val isSelected = star <= selectedStars
                IconButton(
                    onClick = { selectedStars = star },
                    modifier = Modifier.defaultMinSize(minWidth = 48.dp, minHeight = 48.dp)
                ) {
                    Icon(
                        imageVector = if (isSelected) AppIcons.StarFilled else AppIcons.StarOutline,
                        contentDescription = "Ocena $star z 5 gwiazdek",
                        tint = if (isSelected) StarAmber else TextSecondary,
                        modifier = Modifier.size(30.dp)
                    )
                }
            }
        }

        Text(
            text = "Komentarz (opcjonalnie)",
            style = MaterialTheme.typography.titleMedium,
            color = TextPrimary
        )
        OutlinedTextField(
            value = commentText,
            onValueChange = { commentText = it },
            placeholder = {
                Text(
                    text = "Podziel się krótką opinią o posiłku...",
                    color = TextSecondary
                )
            },
            minLines = 3,
            shape = RoundedCornerShape(12.dp),
            colors = OutlinedTextFieldDefaults.colors(
                focusedBorderColor = PrimaryGreen,
                unfocusedBorderColor = OutlineSubtle,
                focusedContainerColor = BackgroundWhite,
                unfocusedContainerColor = BackgroundWhite
            ),
            modifier = Modifier.fillMaxWidth()
        )

        Spacer(modifier = Modifier.height(8.dp))

        // Wyszczególniony Przycisk "Wyślij": FORMULARZ OCENY -> START
        Button(
            onClick = onSubmitClick,
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
                text = "Wyślij",
                style = MaterialTheme.typography.labelLarge
            )
        }

        // Przycisk drugoplanowy "Anuluj": FORMULARZ OCENY -> SZCZEGÓŁY DANIA
        OutlinedButton(
            onClick = onCancelClick,
            shape = RoundedCornerShape(12.dp),
            border = BorderStroke(1.dp, OutlineSubtle),
            colors = ButtonDefaults.outlinedButtonColors(
                containerColor = BackgroundWhite,
                contentColor = TextPrimary
            ),
            modifier = Modifier
                .fillMaxWidth()
                .defaultMinSize(minWidth = 48.dp, minHeight = 52.dp)
        ) {
            Text(
                text = "Anuluj",
                style = MaterialTheme.typography.labelLarge
            )
        }
    }
}
