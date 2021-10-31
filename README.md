# deck-of-cards
Project with tecnologies: gradle, java, cucumber, rest-assured, allure

easy way to: 
 * run cucumber tests `gradle cucumber`
 * aggregate tests results to allure report `gradle allureReport`
 * run web server with generated allure report `gradle allureServe`
 * run all steps with complete build ignoring errors\failures `gradle cucumber allureReport allureServe --continue`
