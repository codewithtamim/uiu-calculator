package io.github.codewithtamim.domain.model

enum class StudentType(val displayName: String) {
    NORMAL("Normal Student"),
    SCHOLARSHIP("Scholarship Student");
}

data class TuitionInput(
    val studentType: StudentType,
    val scholarshipPercent: Int, // 0, 25, 50, 100  scam :)
    val totalAmount: Double
)

data class InstallmentResult(
    val firstInstallment: Double,
    val secondInstallment: Double,
    val thirdInstallment: Double,
    val finalAmount: Double
)


