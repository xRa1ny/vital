# Known Issues

## vital

- Developers currently still need to implement every submodule as dependencies in their plugin to retrieve all of
  Vital's functionality
- Developers still need to implement transitive dependencies into their project for some modules to work

## vital-core

- Developers currently still need to implement the `plugin.yml`
- Developers currently still need to implement the `requiredAnnotation()` Method in all `AnnotatedVitalComponent`
  Implementations

## vital-holograms

- Developers can currently not display items for every line initialized
- Displayed Items currently will despawn after a while

## vital-commands

- `executeBaseCommand()` will currently still be executed on every `INVALID_ARGS` command return state

## vital-databases

- Developers currently still need to implement hibernate and the mysql-connector-j dependency to their project for Vital
  to work