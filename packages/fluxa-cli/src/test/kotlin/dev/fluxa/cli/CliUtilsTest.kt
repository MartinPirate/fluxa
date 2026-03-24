package dev.fluxa.cli

import kotlin.test.Test
import kotlin.test.assertEquals

class CliUtilsTest {

    @Test
    fun `toPascalCase converts kebab case`() {
        assertEquals("MyApp", "my-app".toPascalCase())
    }

    @Test
    fun `toPascalCase converts snake case`() {
        assertEquals("MyApp", "my_app".toPascalCase())
    }

    @Test
    fun `toPascalCase converts space separated`() {
        assertEquals("MyApp", "my app".toPascalCase())
    }

    @Test
    fun `toPascalCase handles single word`() {
        assertEquals("Hello", "hello".toPascalCase())
    }

    @Test
    fun `toCamelCase converts kebab case`() {
        assertEquals("myApp", "my-app".toCamelCase())
    }

    @Test
    fun `toPackageName lowercases and strips invalid chars`() {
        assertEquals("com.example.app", "com.example.App!".toPackageName())
    }

    @Test
    fun `toPackageName preserves dots`() {
        assertEquals("com.example.app", "com.example.app".toPackageName())
    }
}
