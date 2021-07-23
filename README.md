# IntelliJ Monkey C language and Garmin Connect IQ plugin

<!-- Plugin description -->
This plugin adds support for [Monkey C language](https://developer.garmin.com/connect-iq/programmers-guide/monkey-c/) and [Garmin Connect IQ SDK](https://developer.garmin.com/connect-iq/overview/) in IntelliJ IDEA.

Note that it isn't under super-active development, I'm only trying to get the most essential features working again with the latest IntelliJ version.

### Features:

* (WIP) Monkey C grammar validation
* Syntax highlighting
* (WIP) Completion suggestions
* (WIP) Find usages / Go to declaration
* (WIP) Find by class or symbol name
* (WIP) API documentation provider
* (WIP) Method parameters info
* (WIP) Integration with compiler
* (WIP) Integration with simulator runner
* (WIP) Run unit tests
* (WIP) New project generation
* (WIP) App Settings support
* (WIP) Linux support (needs wine installed)


WIP - work in progress, means that there is some support, but it contains 
bugs or is incomplete


### Planned features (in random order):

* Support creating publishable artifact
* Formatting
* Collapse/expand
* Resources Rez. support in code
* Refactoring
* Showing usage error on unresolved (in scope) references
* Support Android Studio
* SDK manager


## How to use the plugin

1. Download latest IDEA Community Edition (2020.2 or later). In Settings>Plugins search for "MonkeyC" and install.
2. Create new project or open existing one
3. Setup SDK and apply it to project
4. Set target device etc

<!-- Plugin description end -->

## Credits
* [Liias](https://github.com/liias) - Most of the [code](https://github.com/liias/monkey)
* [Bertware](https://github.com/bertware) - [added](https://github.com/Bertware/IDEA-MonkeyC-language-plugin) compatibility with IntelliJ 2020.2
* [Flöcsy](https://github.com/flocsy) - [working](https://github.com/flocsy/IntelliJ-MonkeyC-ConnectIQ-plugin) IntelliJ plugin
