# CLAUDE.md

This file provides guidance to Claude Code (claude.ai/code) when working with code in this repository.

## Quick Start Commands

- **Run all Cucumber tests**: `gradle cucumber`
- **Generate Allure test report**: `gradle allureReport`
- **View Allure report in browser**: `gradle allureServe`
- **Run complete pipeline** (tests + report + serve): `gradle cucumber allureReport allureServe --continue`
- **Build project**: `gradle build`
- **Run with thread parallelization**: Cucumber is configured to run with 4 threads by default (see `build.gradle` task configuration)

## Project Architecture

This is a **test automation framework** for the deck-of-cards API (deckofcardsapi.com).

### Key Components

1. **API Layer** (`ru.rubalkin.api/`)
   - `DeckApi`: Interface defining all API operations
   - `DeckApiImpl`: Implementation using REST-Assured to make HTTP requests to the deck API
   - Base URL: `http://deckofcardsapi.com` (configured in StepDefinitions)

2. **DTOs** (`ru.rubalkin.api.dto/`)
   - Response objects: `ShuffleResponseDto`, `DrawResponseDto`, `BaseResponseDto`, `CardDto`
   - Used to deserialize API responses via Jackson

3. **Test Steps** (`ru.rubalkin.StepDefinitions`)
   - Cucumber step implementations written in Gherkin English
   - Manages test lifecycle with `@Before` and `@After` hooks
   - Steps interact with the deck API and use assertions

4. **State Management** (`ru.rubalkin.stash.Stash`)
   - Thread-safe key-value store for sharing data between test steps
   - Initialized before each scenario, cleared after

5. **Test Scenarios** (`src/test/resources/ru/rubalkin/*.feature`)
   - Gherkin feature files defining test scenarios
   - Currently: `task1.feature`, `task2.feature`, `task3.feature`

6. **Utilities** (`ru.rubalkin.utils.CardUtils`)
   - Helper functions for test data processing (e.g., extracting cards from responses)

### Data Flow

1. Feature files define scenarios in Gherkin syntax
2. Cucumber engine matches steps to `StepDefinitions` methods
3. Step methods call `DeckApi` interface methods
4. `DeckApiImpl` makes REST calls using REST-Assured
5. Responses are deserialized to DTOs via Jackson
6. Assertions are made on response data and stored in Stash for cross-step verification
7. Allure annotations capture attachments and test details
8. Test results are collected for Allure reporting

## Testing Framework Stack

- **Cucumber 6.10.2**: BDD-style test scenarios in Gherkin
- **REST-Assured 4.4.0**: HTTP client for API testing
- **JUnit 5.8.1**: Test engine (Jupiter) and assertions
- **Allure 2.13.6**: Test reporting and test results aggregation
- **Jackson 2.12.4**: JSON deserialization for API responses

## Key Configuration Files

- `build.gradle`: Gradle build configuration, task definitions, dependencies, Allure setup
- `settings.gradle`: Root project name
- `src/test/resources/allure.properties`: Allure configuration
- `src/test/resources/cucumber.properties`: Cucumber configuration
