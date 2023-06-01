# Ecommerce-Sales-Dashboard-Spring

## Содержание

- [Описание проекта](#описание-проекта)
- [Структура проекта](#структура-проекта)
- [Установка и запуск](#установка-и-запуск)
- [Скриншоты](#скриншоты)
- [Вклад в развитие проекта](#вклад-в-развитие-проекта)
- [Связаться](#связаться)

## Описание проекта
`Ecommerce Operations Monitor` - это система мониторинга и управления операциями продаж товаров для интернет-магазина, написанная на Java Spring Boot. Проект включает в себя проектирование и разработку программной поддержки процедур оперативного мониторинга и управления продажами.

## Структура проекта
Проект основан на архитектуре MVC (Model-View-Controller) и включает в себя следующие компоненты:

- **Контроллеры**: Контроллеры для управления различными операциями, такими как управление категориями, заказами, продуктами и промо-акциями.

- **Модели**: Модели, представляющие ключевые сущности в системе, такие как Категория, Заказ, Продукт, Промоакция и Пользователь.

- **Репозитории**: Репозитории для управления данными моделей. Каждый репозиторий наследуется от JPA.

- **Services**: Сервисы содержащие бизнес-логику для каждой модели, кроме `OrderItem`, так как она включена в сервис для Order.

- **Secure**: Модуль безопасности, содержащий `RoleCheckInterceptor` и `WebMvcConfig`.

- **Ресурсы**: Различные статические ресурсы и шаблоны, используемые проектом.

Подробности можно найти в каждом из подразделов проекта.

## Установка и запуск

### Предварительные требования
- JDK 8 или выше
- Maven 3.0+
- MySQL 5.6+

### Шаги для установки:

1. Клонировать репозиторий
```shell
git clone https://github.com/Heimdall-Program/ecommerce-operations-monitor.git
```

2. Перейти в каталог проекта
```shell
cd ecommerce-operations-monitor
```

3. Собрать проект с помощью Maven
```shell
mvn clean install
```

4. Настройте базу данных MySQL, используя следующие учетные данные (или настройте свои собствен

ные в `application.properties`):
   - Username: root
   - Password: root

5. Запустить проект
```shell
mvn spring-boot:run
```

Теперь вы должны иметь возможность получить доступ к проекту по адресу `http://localhost:8080`.

## Скриншоты
_Скриншоты интерфейса проекта будут добавлены в ближайшем времени._

## Вклад в развитие проекта

Я буду рад увидеть ваш вклад в проект! Если у вас есть идеи или предложения по улучшению, не стесняйтесь предлагать свои изменения. Ваши доработки и улучшения могут сделать проект лучше. Если у вас возникнут вопросы или необходима помощь, я всегда готов помочь. Ваша поддержка и интерес важны для меня. Пожалуйста, не забудьте поставить "звездочку" на наш репозиторий, если вам понравился проект!

[Вверх ↑](#readmemd)
