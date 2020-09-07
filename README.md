# FTLAndroidUIKit
[ ![Download](https://api.bintray.com/packages/foodtechlab/FTLAndroidUIKit/com.foodtechlab.ftlandroiduikit/images/download.svg) ](https://bintray.com/foodtechlab/FTLAndroidUIKit/com.foodtechlab.ftlandroiduikit/_latestVersion) [![License](https://img.shields.io/badge/License-Apache%202.0-blue.svg)](https://opensource.org/licenses/Apache-2.0)

Данная библиотека является готовым набором графических элементов используемых в продуктах FoodTech Lab.

## Установка
Прежде всего, добавьте зависимость в ваше приложение через build.gradle:

`implementation 'com.foodtechlab.ftlandroiduikit:ftlandroiduikit:$latest_version'`

или Maven:

```
<dependency>
    <groupId>com.foodtechlab.ftlandroiduikit</groupId>
    <artifactId>ftlandroiduikit</artifactId>
    <version>0.8.0</version>
</dependency>
```

## AndroidX
Чтобы использовать FTLAndroidUIKit с AndroidX, вы должны установить targetSdkVersion для своего проекта => 28 и добавить следующие 2 строки в `gradle.properties`:

```
android.useAndroidX=true
android.enableJetifier=true
```

## Java project
Если ваш проект написан исключительно на Java, без настроек Kotlin, добавьте в свой проект следующее:

`implementation 'org.jetbrains.kotlin:kotlin-stdlib-jdk7:$latest_kotlin_version'`

*Узнать больше о последней версиии для Kotlin можно __[здесь](https://kotlinlang.org/docs/reference/using-gradle.html)__* 

## Пример
Пример можно найти в модуле `app`
