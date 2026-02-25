# WAL (Work Activity Log)

## 2026-02-25: Обновление зависимостей до актуальных версий

### Обновлённые версии

| Зависимость | Было | Стало |
|---|---|---|
| java.sourceCompatibility | 1.8 | 21 |
| Allure Gradle plugin | 2.8.0 | 2.11.2 |
| Allure Java libs | 2.13.6 | 2.32.0 |
| Cucumber | 6.10.2 | 7.20.1 |
| REST-Assured | 4.4.0 | 5.5.0 |
| Jackson Databind | 2.12.4 | 2.18.2 |
| JUnit Jupiter | 5.8.1 | 5.11.4 |
| JUnit Platform Suite | 1.8.1 | 1.11.4 |

### Удалено и исправлено

- `cucumber-java8` — удалён (deprecated в Cucumber 7, в коде используются аннотации)
- `allure-gradle:2.5` — удалён из testImplementation (это плагин, не библиотека)
- `allure-cucumber6-jvm` → `allure-cucumber7-jvm` (включая `cucumber.properties`)
- `jcenter()` — удалён (deprecated, артефакты доступны через Maven Central)
- `javaexec {}` → `task(type: JavaExec)` (совместимость с Gradle 9)
- `main =` → `mainClass.set()` (deprecated API в Gradle 9)
- Упрощена конфигурация Allure (удалены неподдерживаемые свойства `clean`, `resultsDir`)
