# Тестирование сервисов и приложений

Проект автоматизированного тестирования веб-сайта и Android-приложения Wikipedia с использованием Selenium, Appium и TestNG.

## Содержание

- [Описание проекта](#описание-проекта)
- [Структура проекта](#структура-проекта)
- [Требования к окружению](#требования-к-окружению)
- [Установка и настройка](#установка-и-настройка)
- [Запуск тестов](#запуск-тестов)
- [Тестовые сценарии](#тестовые-сценарии)
- [Конфигурация](#конфигурация)
- [Отчеты о тестировании](#отчеты-о-тестировании)

---

## Описание проекта

Проект содержит автоматизированные тесты для:

1. **Веб-тестирование** - тестирование сайта Wikipedia (www.wikipedia.org и en.wikipedia.org)
2. **Мобильное тестирование** - тестирование Android-приложения Wikipedia

### Технологический стек

| Компонент | Технология |
|-----------|------------|
| Язык программирования | Java 11+ |
| Система сборки | Maven |
| Веб-тестирование | Selenium WebDriver 4.15.0 |
| Мобильное тестирование | Appium Java Client 9.0.0 |
| Тестовый фреймворк | TestNG 7.8.0 |
| Управление драйверами | WebDriverManager 5.6.2 |

---

## Структура проекта

```
Testing-services-and-applications/
├── pom.xml                                    # Maven конфигурация
├── README.md                                  # Документация
└── src/
    └── test/
        ├── java/
        │   ├── config/
        │   │   └── ConfigReader.java          # Чтение конфигурации
        │   ├── pages/
        │   │   ├── web/                       # Page Objects для веб
        │   │   │   ├── BasePage.java
        │   │   │   ├── WikipediaHomePage.java
        │   │   │   ├── WikipediaEnglishHomePage.java
        │   │   │   ├── WikipediaSearchResultsPage.java
        │   │   │   └── WikipediaArticlePage.java
        │   │   └── mobile/                    # Page Objects для мобильных
        │   │       ├── MobileBasePage.java
        │   │       ├── WikipediaMainScreen.java
        │   │       ├── WikipediaSearchScreen.java
        │   │       └── WikipediaArticleScreen.java
        │   ├── tests/
        │   │   ├── web/                       # Веб-тесты
        │   │   │   ├── BaseWebTest.java
        │   │   │   └── WikipediaWebTest.java
        │   │   └── mobile/                    # Мобильные тесты
        │   │       ├── BaseMobileTest.java
        │   │       └── WikipediaMobileTest.java
        │   └── utils/
        │       └── WaitUtils.java             # Утилиты ожидания
        └── resources/
            ├── config.properties              # Конфигурация
            ├── testng.xml                     # Все тесты
            ├── testng-web.xml                 # Только веб-тесты
            └── testng-mobile.xml              # Только мобильные тесты
```

---

## Требования к окружению

### Общие требования

- **Java Development Kit (JDK)** 11 или выше
- **Apache Maven** 3.6+
- **Git**

### Для веб-тестирования

- **Google Chrome**, **Mozilla Firefox** или **Microsoft Edge** (последние версии)
- Драйверы браузеров загружаются автоматически через WebDriverManager

### Для мобильного тестирования

- **Node.js** 14+ и npm
- **Appium Server** 2.x
- **Android Studio** с Android SDK
- **Android Emulator** или реальное Android-устройство
- **Wikipedia APK** (из Google Play или APKPure)

---

## Установка и настройка

### 1. Клонирование репозитория

```bash
git clone <repository-url>
cd Testing-services-and-applications
```

### 2. Установка зависимостей Maven

```bash
mvn clean install -DskipTests
```

### 3. Настройка веб-тестирования

Веб-тесты готовы к запуску сразу после установки зависимостей. WebDriverManager автоматически загрузит нужный драйвер браузера.

### 4. Настройка мобильного тестирования

#### 4.1. Установка Appium

```bash
npm install -g appium
appium driver install uiautomator2
```

#### 4.2. Запуск Appium Server

```bash
appium
```

По умолчанию сервер запускается на `http://127.0.0.1:4723`

#### 4.3. Настройка Android эмулятора

1. Откройте Android Studio -> AVD Manager
2. Создайте эмулятор с Android 11+ (API 30+)
3. Запустите эмулятор

#### 4.4. Установка Wikipedia APK

```bash
# Через adb
adb install wikipedia.apk

# Или установите из Google Play на эмуляторе
```

#### 4.5. Конфигурация

Отредактируйте `src/test/resources/config.properties`:

```properties
# Appium server
appium.server.url=http://127.0.0.1:4723

# Android device
android.device.name=emulator-5554
android.platform.version=13

# Wikipedia app
wikipedia.app.package=org.wikipedia
wikipedia.app.activity=org.wikipedia.main.MainActivity
```

---

## Запуск тестов

### Запуск всех тестов

```bash
mvn test
```

### Запуск только веб-тестов

```bash
mvn test -Pweb
```

Или напрямую через TestNG:

```bash
mvn test -DsuiteXmlFile=src/test/resources/testng-web.xml
```

### Запуск только мобильных тестов

```bash
mvn test -Pmobile
```

Или напрямую через TestNG:

```bash
mvn test -DsuiteXmlFile=src/test/resources/testng-mobile.xml
```

### Запуск в headless режиме (веб)

Измените в `config.properties`:

```properties
headless=true
```

### Запуск с другим браузером

```bash
mvn test -Pweb -Dbrowser=firefox
```

Поддерживаемые браузеры: `chrome`, `firefox`, `edge`

---

## Тестовые сценарии

### Веб-тесты (15 сценариев)

| # | Сценарий | Описание |
|---|----------|----------|
| 1 | testMainPageLoadsCorrectly | Проверка загрузки главной страницы с ключевыми элементами |
| 2 | testMainPageHasLanguageLinks | Проверка наличия ссылок на языки |
| 3 | testSearchReturnsResults | Проверка возврата результатов поиска |
| 4 | testSearchFromEnglishWikipedia | Поиск с английской Wikipedia |
| 5 | testSearchWithMultipleQueries | Поиск с DataProvider (несколько запросов) |
| 6 | testNavigateToEnglishWikipedia | Навигация на английскую версию |
| 7 | testRandomArticleNavigation | Открытие случайной статьи |
| 8 | testArticlePageTitle | Проверка заголовка статьи |
| 9 | testArticleContainsContent | Проверка содержимого статьи |
| 10 | testArticleHasReferences | Проверка наличия ссылок в статье |
| 11 | testArticleUrlContainsName | Проверка URL статьи |
| 12 | testArticleHasCategories | Проверка категорий статьи |
| 13 | testSearchFromArticlePage | Поиск со страницы статьи |
| 14 | testEnglishWikipediaMainPageSections | Проверка секций главной страницы |
| 15 | testPageTitles | Проверка заголовков страниц |

### Мобильные тесты (14 сценариев)

| # | Сценарий | Описание |
|---|----------|----------|
| 1 | testMainScreenLoadsCorrectly | Проверка загрузки главного экрана |
| 2 | testNavigationTabsDisplayed | Проверка отображения вкладок навигации |
| 3 | testSearchScreenOpens | Открытие экрана поиска |
| 4 | testSearchReturnsResults | Проверка результатов поиска |
| 5 | testSearchWithMultipleQueries | Поиск с DataProvider |
| 6 | testOpenArticleFromSearch | Открытие статьи из результатов поиска |
| 7 | testArticleTitleMatchesSearch | Проверка соответствия заголовка статьи |
| 8 | testArticleToolbarDisplayed | Проверка отображения панели инструментов |
| 9 | testArticleScrolling | Прокрутка статьи |
| 10 | testNavigationBackFromArticle | Навигация назад из статьи |
| 11 | testSearchFromArticleToolbar | Поиск из панели инструментов статьи |
| 12 | testSearchResultsContainKeyword | Проверка результатов поиска на ключевое слово |
| 13 | testMultipleArticleNavigation | Навигация между несколькими статьями |
| 14 | testSaveButtonAvailable | Проверка доступности кнопки сохранения |

---

## Конфигурация

Файл `src/test/resources/config.properties`:

```properties
# ===========================================
# Web Testing Configuration
# ===========================================

# Browser settings
browser=chrome                    # chrome, firefox, edge
headless=false                    # true для headless режима
implicit.wait=10                  # секунды
explicit.wait=15                  # секунды
page.load.timeout=30              # секунды

# Web URLs
web.base.url=https://www.wikipedia.org
web.wikipedia.en.url=https://en.wikipedia.org

# ===========================================
# Mobile Testing Configuration (Appium)
# ===========================================

# Appium server
appium.server.url=http://127.0.0.1:4723

# Android device
android.platform.name=Android
android.platform.version=13
android.device.name=emulator-5554
android.automation.name=UiAutomator2

# Wikipedia app
wikipedia.app.package=org.wikipedia
wikipedia.app.activity=org.wikipedia.main.MainActivity
wikipedia.apk.path=               # путь к APK (опционально)

# Mobile timeouts
mobile.implicit.wait=10
mobile.explicit.wait=20
```

---

## Отчеты о тестировании

После запуска тестов отчеты генерируются в:

- **TestNG отчеты**: `target/surefire-reports/`
- **HTML отчет**: `target/surefire-reports/index.html`
- **XML отчет**: `target/surefire-reports/testng-results.xml`

### Просмотр отчетов

```bash
# Открыть HTML отчет (Linux/Mac)
open target/surefire-reports/index.html

# Открыть HTML отчет (Windows)
start target/surefire-reports/index.html
```

---

## Архитектура

### Page Object Model (POM)

Проект использует паттерн Page Object Model для:
- Разделения логики тестов и элементов страниц
- Повышения читаемости и поддерживаемости кода
- Уменьшения дублирования кода

### Базовые классы

- `BasePage` - базовый класс для веб-страниц с общими методами
- `MobileBasePage` - базовый класс для мобильных экранов
- `BaseWebTest` - настройка WebDriver для веб-тестов
- `BaseMobileTest` - настройка Appium для мобильных тестов

### Явные ожидания

Используются `WebDriverWait` и `ExpectedConditions` для стабильности тестов.

---

## Troubleshooting

### Веб-тесты не запускаются

1. Проверьте версию Java: `java -version` (должна быть 11+)
2. Проверьте Maven: `mvn -version`
3. Очистите кэш: `mvn clean`

### Мобильные тесты не подключаются к эмулятору

1. Проверьте запущен ли Appium: `appium`
2. Проверьте эмулятор: `adb devices`
3. Проверьте установлено ли приложение: `adb shell pm list packages | grep wikipedia`

### Тесты падают из-за таймаутов

Увеличьте значения в `config.properties`:

```properties
explicit.wait=30
mobile.explicit.wait=30
```

