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
import io.github.codewithtamim.domain.model.StudentType

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun StudentTypeDropdown(
    selected: StudentType,
    onSelected: (StudentType) -> Unit,
    modifier: Modifier = Modifier
) {
    var expanded by remember { mutableStateOf(false) }

    ExposedDropdownMenuBox(expanded = expanded, onExpandedChange = { expanded = !expanded }) {
        TextField(
            readOnly = true,
            value = selected.displayName,
            onValueChange = {},
            label = { Text(text = stringResource(id = R.string.student_type)) },
            trailingIcon = { ExposedDropdownMenuDefaults.TrailingIcon(expanded = expanded) },
            colors = ExposedDropdownMenuDefaults.textFieldColors(),
            modifier = modifier
                .menuAnchor()
                .fillMaxWidth()
        )
        ExposedDropdownMenu(expanded = expanded, onDismissRequest = { expanded = false }) {
            DropdownMenuItem(
                text = { Text(StudentType.NORMAL.displayName) },
                onClick = {
                    onSelected(StudentType.NORMAL)
                    expanded = false
                }
            )
            DropdownMenuItem(
                text = { Text(StudentType.SCHOLARSHIP.displayName) },
                onClick = {
                    onSelected(StudentType.SCHOLARSHIP)
                    expanded = false
                }
            )
        }
    }
}


