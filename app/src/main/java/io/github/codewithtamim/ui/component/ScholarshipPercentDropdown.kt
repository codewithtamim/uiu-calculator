package io.github.codewithtamim.ui.component

import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.ExposedDropdownMenuBox
import androidx.compose.material3.ExposedDropdownMenuDefaults
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.getValue
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import io.github.codewithtamim.R

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ScholarshipPercentDropdown(
    modifier: Modifier = Modifier,
    selectedPercent: Int,
    onSelected: (Int) -> Unit,
    options: List<Int> = listOf(100, 50, 25),

    ) {
    var expanded by remember { mutableStateOf(false) }

    ExposedDropdownMenuBox(expanded = expanded, onExpandedChange = { expanded = !expanded }) {
        TextField(
            readOnly = true,
            value = "$selectedPercent%",
            onValueChange = {},
            label = { Text(text = stringResource(id = R.string.scholarship_percent)) },
            trailingIcon = { ExposedDropdownMenuDefaults.TrailingIcon(expanded = expanded) },
            colors = ExposedDropdownMenuDefaults.textFieldColors(),
            modifier = modifier
                .menuAnchor()
                .fillMaxWidth()
        )
        ExposedDropdownMenu(expanded = expanded, onDismissRequest = { expanded = false }) {
            options.forEach { percent ->
                DropdownMenuItem(text = { Text("$percent%") }, onClick = {
                    onSelected(percent)
                    expanded = false
                })
            }
        }
    }
}


