
inline fun <reified ValueT> VariantDimension.buildConfigField(name: String, value: ValueT) {
    val resolvedValue = when (value) {
        is String -> "\"$value\""
        is Boolean -> "\"Boolean.parseBoolean(\"$value\")\""
        else -> value.toString()
    }

    buildConfigField(ValueT::class.java.simpleName, name, resolvedValue)
}
