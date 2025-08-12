package io.github.codewithtamim.presentation

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import io.github.codewithtamim.data.preferences.TuitionPreferencesRepository
import io.github.codewithtamim.domain.model.InstallmentResult
import io.github.codewithtamim.domain.model.StudentType
import io.github.codewithtamim.domain.model.TuitionInput
import io.github.codewithtamim.domain.usecase.CalculateTuitionUseCase
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

data class TuitionUiState(
    val studentType: StudentType = StudentType.NORMAL,
    val scholarshipPercent: Int = 0,
    val totalAmountInput: String = "",
    val result: InstallmentResult? = null,
    val amountInvalid: Boolean = false
)

@HiltViewModel
class TuitionViewModel @Inject constructor(
    private val calculateTuition: CalculateTuitionUseCase,
    private val preferencesRepository: TuitionPreferencesRepository
) : ViewModel() {

    private val _uiState = MutableStateFlow(TuitionUiState())
    val uiState: StateFlow<TuitionUiState> = _uiState.asStateFlow()

    init {
        viewModelScope.launch {
            preferencesRepository.preferencesFlow.collect { prefs ->
                val type = if (prefs.studentType == StudentType.SCHOLARSHIP.displayName) StudentType.SCHOLARSHIP else StudentType.NORMAL
                _uiState.update {
                    it.copy(
                        studentType = type,
                        scholarshipPercent = prefs.scholarshipPercent,
                        totalAmountInput = if (prefs.totalAmount == 0.0) "" else prefs.totalAmount.toString()
                    )
                }
            }
        }
    }

    fun onStudentTypeChange(type: StudentType) {
        _uiState.update { it.copy(studentType = type, amountInvalid = false) }
    }

    fun onScholarshipPercentChange(percent: Int) {
        _uiState.update { it.copy(scholarshipPercent = percent, amountInvalid = false) }
    }

    fun onTotalAmountChange(input: String) {
        _uiState.update { it.copy(totalAmountInput = input, amountInvalid = false) }
    }

    fun calculateAndSave() {
        val amount = _uiState.value.totalAmountInput.toDoubleOrNull()
        if (amount == null || amount < 0) {
            _uiState.update { it.copy(amountInvalid = true) }
            return
        }

        val input = TuitionInput(
            studentType = _uiState.value.studentType,
            scholarshipPercent = if (_uiState.value.studentType == StudentType.SCHOLARSHIP) _uiState.value.scholarshipPercent else 0,
            totalAmount = amount
        )

        val result = calculateTuition(input)
        _uiState.update { it.copy(result = result, amountInvalid = false) }

        viewModelScope.launch {
            preferencesRepository.savePreferences(
                studentType = _uiState.value.studentType.displayName,
                scholarshipPercent = _uiState.value.scholarshipPercent,
                totalAmount = amount
            )
        }
    }
}


