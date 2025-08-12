package io.github.codewithtamim.ui.screen

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.expandVertically
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.animation.shrinkVertically
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.imePadding
import androidx.compose.foundation.layout.navigationBarsPadding
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.widthIn
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalUriHandler
import androidx.compose.ui.res.stringResource
import androidx.compose.foundation.text.ClickableText
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.withStyle
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import io.github.codewithtamim.R
import io.github.codewithtamim.domain.model.StudentType
import io.github.codewithtamim.presentation.TuitionViewModel
import io.github.codewithtamim.ui.component.AmountTextField
import io.github.codewithtamim.ui.component.ScholarshipPercentDropdown
import io.github.codewithtamim.ui.component.StudentTypeDropdown
import io.github.codewithtamim.util.formatCurrency

@Composable
fun TuitionScreen(
    modifier: Modifier = Modifier, viewModel: TuitionViewModel = hiltViewModel()
) {
    val state by viewModel.uiState.collectAsStateWithLifecycle()

    Column(
        modifier = modifier
            .fillMaxSize()
            .padding(16.dp)
            .imePadding()
            .navigationBarsPadding(),
        verticalArrangement = Arrangement.SpaceBetween,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .weight(1f)
                .widthIn(max = 480.dp),
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            StudentTypeDropdown(
                selected = state.studentType,
                onSelected = viewModel::onStudentTypeChange,
                modifier = Modifier.fillMaxWidth()
            )

            Spacer(modifier = Modifier.height(12.dp))

            if (state.studentType == StudentType.SCHOLARSHIP) {
                ScholarshipPercentDropdown(
                    selectedPercent = state.scholarshipPercent,
                    onSelected = viewModel::onScholarshipPercentChange,
                    modifier = Modifier.fillMaxWidth()
                )

                Spacer(modifier = Modifier.height(12.dp))
            }

            AmountTextField(
                value = state.totalAmountInput,
                onValueChange = viewModel::onTotalAmountChange,
                modifier = Modifier.fillMaxWidth()
            )

            Spacer(modifier = Modifier.height(16.dp))

            val isCalculateEnabled =
                state.totalAmountInput.isNotBlank() && state.totalAmountInput.toDoubleOrNull() != null

            Button(
                onClick = viewModel::calculateAndSave,
                modifier = Modifier.fillMaxWidth(),
                contentPadding = PaddingValues(16.dp),
                enabled = isCalculateEnabled
            ) {
                Text(text = stringResource(id = R.string.calculate_installments))
            }

            if (state.amountInvalid) {
                Spacer(modifier = Modifier.height(12.dp))
                Text(
                    text = stringResource(id = R.string.invalid_amount),
                    color = MaterialTheme.colorScheme.error
                )
            }

            AnimatedVisibility(
                visible = state.result != null,
                enter = fadeIn() + expandVertically(),
                exit = fadeOut() + shrinkVertically()
            ) {
                state.result?.let { result ->
                    Card(
                        colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surfaceContainer),
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(top = 24.dp)
                    ) {
                        Column(modifier = Modifier.padding(16.dp)) {
                            Text(
                                text = stringResource(id = R.string.results_title),
                                style = MaterialTheme.typography.titleMedium
                            )
                            Spacer(modifier = Modifier.height(8.dp))
                            Text(
                                text = stringResource(
                                    id = R.string.final_amount, formatCurrency(result.finalAmount)
                                )
                            )
                            Spacer(modifier = Modifier.height(8.dp))
                            Text(
                                text = stringResource(
                                    id = R.string.first_installment,
                                    formatCurrency(result.firstInstallment)
                                )
                            )
                            Text(
                                text = stringResource(
                                    id = R.string.second_installment,
                                    formatCurrency(result.secondInstallment)
                                )
                            )
                            Text(
                                text = stringResource(
                                    id = R.string.third_installment,
                                    formatCurrency(result.thirdInstallment)
                                )
                            )
                        }
                    }
                }
            }
            val uriHandler = LocalUriHandler.current
            val authorText = buildAnnotatedString {
                append("Made with ❤️ by ")
                pushStringAnnotation(
                    tag = "AUTHOR", annotation = "https://github.com/codewithtamim"
                )
                withStyle(
                    SpanStyle(
                        color = MaterialTheme.colorScheme.primary, fontWeight = FontWeight.SemiBold
                    )
                ) {
                    append("codewithtamim")
                }
                pop()
            }
            val sourceText = buildAnnotatedString {
                pushStringAnnotation(
                    tag = "SOURCE", annotation = "https://github.com/codewithtamim/uiu-calculator"
                )
                withStyle(
                    SpanStyle(
                        color = MaterialTheme.colorScheme.primary, fontWeight = FontWeight.SemiBold
                    )
                ) {
                    append(" </> Source code")
                }
                pop()
            }
            val footerStyle = MaterialTheme.typography.bodySmall.copy(
                color = MaterialTheme.colorScheme.onSurfaceVariant
            )

            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(12.dp),
                horizontalArrangement = Arrangement.Center,
                verticalAlignment = Alignment.CenterVertically
            ) {
                ClickableText(
                    text = authorText, style = footerStyle, onClick = { offset ->
                        authorText.getStringAnnotations("AUTHOR", offset, offset).firstOrNull()
                            ?.let {
                                uriHandler.openUri(it.item)
                            }
                    })
                Spacer(modifier = Modifier.width(16.dp))
                ClickableText(
                    text = sourceText, style = footerStyle, onClick = { offset ->
                        sourceText.getStringAnnotations("SOURCE", offset, offset).firstOrNull()
                            ?.let {
                                uriHandler.openUri(it.item)
                            }
                    })
            }
        }
    }
}

