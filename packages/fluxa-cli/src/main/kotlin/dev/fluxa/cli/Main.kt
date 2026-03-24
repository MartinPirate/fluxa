package dev.fluxa.cli

import dev.fluxa.runtime.FluxaRuntime

fun main(args: Array<String>) {
    if (args.isEmpty()) {
        printUsage()
        return
    }

    when (args[0]) {
        "new" -> NewCommand.run(args.drop(1))
        "generate", "g" -> GenerateCommand.run(args.drop(1))
        "version", "--version", "-v" -> println("fluxa ${FluxaRuntime.VERSION}")
        "help", "--help", "-h" -> printUsage()
        else -> {
            System.err.println("Unknown command: ${args[0]}")
            printUsage()
        }
    }
}

private fun printUsage() {
    println("""
        |fluxa ${FluxaRuntime.VERSION}
        |
        |Usage:
        |  fluxa new <app-name>                 Create a new Fluxa project
        |  fluxa generate screen <name>         Generate a screen
        |  fluxa generate component <name>      Generate a component
        |  fluxa generate feature <name>         Generate a feature module
        |  fluxa version                        Print version
        |  fluxa help                           Show this help
        |
        |Aliases:
        |  fluxa g screen <name>                Short for generate
    """.trimMargin())
}
