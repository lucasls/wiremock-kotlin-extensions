package helpers

import org.assertj.core.api.ObjectAssert
import java.util.*

infix fun <T> ObjectAssert<T>.hasSameFieldsOf(other: T) =
    this.usingRecursiveComparison()
        .ignoringFieldsOfTypes(UUID::class.java)
        .isEqualTo(other)
