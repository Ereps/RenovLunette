# RenovLunette
Besoin d'avoir JavaFX 21 et Gradle 8.7
Pour lancer l'application il faut écrire "gradle run" dans le terminal dans le dossier où le projet est présent
Si vous rencontrez des problèmes pour compiler le projet, supprimez:

java {
    toolchain {
        languageVersion = JavaLanguageVersion.of(21)
    }
}

tasks.withType(JavaCompile) {
    options.encoding = 'UTF-8'
    options.release = 21
}

du build.gradle