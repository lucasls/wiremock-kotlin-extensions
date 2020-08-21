package helpers

import org.junit.jupiter.api.extension.ExtensionContext
import org.junit.jupiter.params.provider.Arguments
import org.junit.jupiter.params.provider.ArgumentsProvider
import java.util.stream.Stream

abstract class ArgsList(private vararg val arguments: Arguments) : ArgumentsProvider {
    constructor(block: () -> List<Arguments>): this(*block().toTypedArray())

    override fun provideArguments(context: ExtensionContext): Stream<out Arguments> =
        arguments.toList().stream()
}

fun args(vararg values: Any?): Arguments = Arguments.arguments(*values)
