# CLAUDE.md

This file provides guidance to Claude Code (claude.ai/code) when working with code in this repository.

## Build Commands

```bash
./gradlew build                  # Compile and package (produces JARs in build/libs/)
./gradlew test                   # Run JUnit 5 tests
./gradlew catherder_client       # Launch Minecraft client for testing
./gradlew catherder_server       # Launch Minecraft server for testing
./gradlew catherder_data         # Run data generators (blockstates, recipes, loot, lang)
./gradlew clean                  # Clean build artifacts
```

Gradle requires `-Xmx4G` (set in `gradle.properties`). Build produces three JARs: main, sources, and API.

## Architecture Overview

**Cat Herder** is a Minecraft Forge 1.19.4 mod that enhances vanilla cats. It borrows concepts heavily from Doggy Talents / Doggy Talents Next.

### Source Sets

The project has three source sets beyond `main`:
- `src/api/` — Public API (`com.sweetrpg.catherder.api`) — interfaces, registry objects, and base classes intended for addon authors
- `src/test/` — JUnit 5 unit tests
- `src/generated/` — Auto-generated resources from `catherder_data` task; do not edit manually

### Key Architectural Patterns

**Registry-based extension:** Talents and Accessories are registered objects (like Forge blocks/items). New talents extend a base class and register to `ModTalents`. New accessories register to `ModAccessories`. Both systems use the Forge registry pipeline initialized in `CatHerder.java`.

**API / impl split:** `src/api/` exports stable interfaces (`ICat`, `AbstractCatEntity`, `ICatAlteration`, etc.) that can be depended upon without the full implementation. The `api` Gradle source set compiles separately into `CatHerder-*-api.jar`.

**Client / server split:** Client-only code (renderers, models, screens) lives under `com.sweetrpg.catherder.client`. Server-safe code lives under `com.sweetrpg.catherder.common`. `CatHerder.java` wires them together via `DistExecutor`.

**Network:** A single `SimpleChannel` (`CatHerder.HANDLER`, protocol version 3) handles all client-server sync packets in `common/network/`.

**Persistence:** `CatLocationStorage` and `CatRespawnStorage` use Forge's `SavedData` to persist cat tracking data across sessions.

### Core Files

| File | Purpose |
|------|---------|
| `CatHerder.java` | `@Mod` entry point — registers everything, wires lifecycle events |
| `common/entity/CatEntity.java` | Main cat entity (~2700 lines) — all cat behavior, AI, stats, talents, accessories |
| `api/CatHerderAPI.java` | Constants and API entry point (`MOD_ID = "catherder"`) |
| `common/lib/Constants.java` | All translation keys and string constants |
| `common/registry/Mod*.java` | `DeferredRegister` holders for each registry type |
| `common/CommonSetup.java` | `FMLCommonSetupEvent` — packets, food handlers, talent configs |
| `client/ClientSetup.java` | `FMLClientSetupEvent` — screen factories, collar renderers |

### Talent System

Each talent is a class in `common/talent/` that extends a base talent class and is registered in `ModTalents`. Talents are configured via `ConfigHandler` and applied to `CatEntity` instances. The talent registry is initialized during `FMLCommonSetupEvent`.

### Accessory System

Accessories (collars, capes, sunglasses) are registered objects in `ModAccessories`. They support dyeing and are rendered via layer classes in `client/entity/render/layer/`.

### Optional Mod Integrations

Integration with JEI, Patchouli, Farmer's Delight, Configured, and various wood-type mods (Autumnity, Biomes O' Plenty, Botania) is handled through the addon system in `common/addon/`. Integrations are optional and guarded by mod presence checks.

### Data Generation

Run `./gradlew catherder_data` to regenerate assets. Providers live in `src/main/java/com/sweetrpg/catherder/data/`. Output goes to `src/generated/resources/` (committed to source control). Language files support 8 locales (en_us, en_gb, de_de, ko_kr, ru_ru, vi_vn, zh_cn, zh_tw).

### IntelliJ Run Configurations

For Apple Silicon Macs, run configurations may need the JRE path overridden (see `Notes.md`):
```
$USER_HOME$/Library/Application Support/minecraft/runtime/java-runtime-beta/mac-os/java-runtime-beta/jre.bundle/Contents/Home
```
