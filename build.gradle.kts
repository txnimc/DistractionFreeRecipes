import toni.blahaj.*
import toni.blahaj.api.*

val templateSettings = object : BlahajSettings {
	// -------------------- Dependencies ---------------------- //
	override val depsHandler: BlahajDependencyHandler get() = object : BlahajDependencyHandler {
		override fun addGlobal(mod : ModData, deps: DependencyHandler) {
			deps.modCompileOnly(modrinth("emi", "1.1.18+${mod.mcVersion}+${mod.loader}"))
			deps.modCompileOnly("mezz.jei:jei-${mod.mcVersion}-${mod.loader}:${when (mod.mcVersion) {
				"1.21.1" -> "19.21.0.247"
				"1.20.1" -> "15.20.0.105"
				else -> ""
			}}")

			deps.modImplementation("me.shedaniel:RoughlyEnoughItems-${mod.loader}:${when (mod.mcVersion) {
				"1.21.1" -> "16.0.788"
				"1.20.1" -> "12.1.785"
				else -> ""
			}}")
		}

		override fun addFabric(mod : ModData, deps: DependencyHandler) {

		}

		override fun addForge(mod : ModData, deps: DependencyHandler) {
			deps.compileOnly(deps.annotationProcessor("io.github.llamalad7:mixinextras-common:0.4.1")!!)
			deps.implementation(deps.include("io.github.llamalad7:mixinextras-forge:0.4.1")!!)
		}

		override fun addNeo(mod : ModData, deps: DependencyHandler) {

		}
	}

	// ---------- Curseforge/Modrinth Configuration ----------- //
	// For configuring the dependecies that will show up on your mod page.
	override val publishHandler: BlahajPublishDependencyHandler get() = object : BlahajPublishDependencyHandler {
		override fun addShared(mod : ModData, deps: DependencyContainer) {
			if (mod.isFabric) {
				deps.requires("fabric-api")
			}
		}

		override fun addCurseForge(mod : ModData, deps: DependencyContainer) {

		}

		override fun addModrinth(mod : ModData, deps: DependencyContainer) {

		}
	}
}

plugins {
	`maven-publish`
	application
	id("toni.blahaj") version "1.0.9"
	kotlin("jvm")
	kotlin("plugin.serialization")
	id("dev.kikugie.j52j") version "1.0"
	id("dev.architectury.loom")
	id("me.modmuss50.mod-publish-plugin")
	id("systems.manifold.manifold-gradle-plugin")
}

blahaj {
	sc = stonecutter
	settings = templateSettings
	init()
}

// Dependencies
repositories {
	maven("https://www.cursemaven.com")
	maven("https://api.modrinth.com/maven")
	maven("https://thedarkcolour.github.io/KotlinForForge/")
	maven("https://maven.kikugie.dev/releases")
	maven("https://maven.txni.dev/releases")
	maven("https://jitpack.io")
	maven("https://maven.neoforged.net/releases/")
	maven("https://maven.terraformersmc.com/releases/")
	maven("https://raw.githubusercontent.com/Fuzss/modresources/main/maven/")
	maven("https://maven.parchmentmc.org")
	maven("https://maven.su5ed.dev/releases")
	maven("https://maven.su5ed.dev/releases")
	maven("https://maven.fabricmc.net")
	maven("https://maven.shedaniel.me/")
	maven("https://maven.blamejared.com/")
	maven("https://modmaven.dev")
}
