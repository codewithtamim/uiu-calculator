package io.github.codewithtamim.domain.usecase

import io.github.codewithtamim.domain.model.InstallmentResult
import io.github.codewithtamim.domain.model.StudentType
import io.github.codewithtamim.domain.model.TuitionInput
import javax.inject.Inject

class CalculateTuitionUseCase @Inject constructor() {
    operator fun invoke(input: TuitionInput): InstallmentResult {
        val adjustedAmount = when (input.studentType) {
            StudentType.NORMAL -> input.totalAmount
            StudentType.SCHOLARSHIP -> input.totalAmount * (1.0 - (input.scholarshipPercent / 100.0))
        }

        val first = adjustedAmount * 0.40
        val second = adjustedAmount * 0.30
        val third = adjustedAmount * 0.30

        return InstallmentResult(
            firstInstallment = first,
            secondInstallment = second,
            thirdInstallment = third,
            finalAmount = adjustedAmount
        )
    }
}


