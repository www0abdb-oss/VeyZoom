
# VeyZoom

VeyZoom is a lightweight Minecraft zoom mod for Fabric and NeoForge.

## Features

- Smooth zoom transitions
- Adjustable zoom using the mouse wheel
- Configurable zoom level and speed
- Support for Fabric and NeoForge

## Usage

- Hold `C` to zoom in.
- Scroll the mouse wheel while holding `C` to adjust the zoom level.
- Release `C` to return to the normal field of view.

## Configuration

Configuration file: `config/veyzoom.properties`

| Option | Description | Default |
|---|---|---|
| `defaultZoom` | Default zoom level | `4.0` |
| `maxZoom` | Maximum zoom level | `30.0` |
| `zoomStep` | Mouse wheel zoom multiplier | `1.15` |
| `smoothing` | Zoom transition smoothness | `0.25` |

Settings are loaded when Minecraft starts.



## Building from Source

Run the following command from the project root:

```bash
./gradlew build
