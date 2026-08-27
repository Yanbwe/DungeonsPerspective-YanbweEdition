# Dungeons Perspective - Yanbwe Edition (NeoForge)

A **NeoForge-only** modified edition of [Dungeons Perspective](https://github.com/cleannrooster/dungeons-perspective) by cleannrooster/Forg, based on [Minecraft XIV](https://github.com/ashkitten/minecraft-xiv) by ashkitten.

Targets **Minecraft 1.21.1 + NeoForge 21.1.219**.

## Features

- Minecraft Dungeons-style isometric / top-down perspective, toggle with **F4**.
- Dynamic camera, scroll-wheel zoom, orthographic mode, FOV / zoom controls.
- Room / roof culling: detects the enclosed space you are in and removes its ceiling, walls, and pillars.
- Shape culling: sightline-based whole-shape removal with silhouette openings.
- Ghost rendering: removed blocks are drawn back translucently, avoiding black voids.
- Controller Mode, native gamepad left-stick movement, contextual combat targeting, movement-based targeting, contextual block interaction, lock-on, and click-to-move.
- YACL-powered settings screen with a Gson fallback when YACL is absent.
- Optional compatibility: Spell Engine, Combat Roll, owo-lib.

## Requirements

- Minecraft **1.21.1**
- NeoForge **21.1.219**
- Java **21**

## Installation

Put the built jar into your `mods` folder and launch the NeoForge client.

## License

This project is distributed under the **GNU Lesser General Public License v3.0** (LGPL-3.0).

See `COPYING`, `COPYING.LESSER`, and `NOTICE.md` for details.