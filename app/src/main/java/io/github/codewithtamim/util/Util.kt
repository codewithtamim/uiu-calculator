package io.github.codewithtamim.util

import java.text.DecimalFormat
import java.text.NumberFormat
import java.util.Locale

fun formatCurrency(value: Double): String {
    val numberFormat = NumberFormat.getNumberInstance(Locale.getDefault()) as DecimalFormat
    numberFormat.applyPattern("#,##0.00")
    return "৳ ${numberFormat.format(value)}"
}


