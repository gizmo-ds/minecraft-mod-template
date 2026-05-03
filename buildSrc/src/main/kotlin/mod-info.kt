val mod = ModInfo()

data class ModInfo(
    val id: String = "examplemod",
    val group: String = "com.example.examplemod",
    val version: String = "0.1.0",
    val name: String = "Example Mod",
    val authors: List<String> = listOf(
        "Gizmo"
    ),
    // https://spdx.org/licenses/
    var license: String = "CC0-1.0",
    val description: String = """
        The description of your mod.
        Accepts multilines.
    """.trimIndent(),

    val contact: Map<String, String> = mapOf(
        "homepage" to "https://github.com/gizmo-ds/minecraft-mod-template",
        "sources" to "https://github.com/gizmo-ds/minecraft-mod-template",
        "issues" to "https://github.com/gizmo-ds/minecraft-mod-template/issues"
    ),

    val javaVersion: Int = 17
)