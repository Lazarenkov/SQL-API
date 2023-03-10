# План автоматизации тестирования API сервиса "Перевод с карты на карту"
## **1. Перечень автоматизируемых сценариев**

### Основная задача:

Проверка корректности операции перевода денежной суммы между двумя картами, путем контроля остатка денежных средств после совершения операции.

### Условия совершения операции:
* Клиент получил токен при авторизации
* Клиент отправляет на север POST-запрос, содержащий в хедере токен, в теле JSON с полями *"from"* (номер карты списания), *"to"* (номер карты зачисления) и *"amount"* (сумма перевода)
### Позитивные сценарии (Happy Path)
1. Перевод с валидными номерами счетов *"from"* и *"to"*. валидной суммы *"amount"*, которая меньше остатка на счете*"from"*, но не равна нулю или отрицательному значению.  
   **Ожидаемый результат:**
Код ответа сервера-200. Происходит уменьшение остатка по счету *"from"* и увеличение остатка по счету *"to"* на размер *"amount"*.

### Негативные сценарии (Sad Path)
1. Перевод с валидными номерами счетов *"from"* и *"to"*. инвалидной суммы *"amount"*, которая равна нулю  
   **Ожидаемый результат:**
   Код ответа сервера-200. Не происходит уменьшение остатка по счету *"from"* и увеличения остатка по счету *"to"* на размер *"amount"*.
1. Перевод с валидным номером счета *"from"* на невалидный, несуществующий номер счета *"to"*. валидной суммы *"amount"*, которая меньше остатка на счете*"from"*, но не равна нулю или отрицательному значению.  
   **Ожидаемый результат:**
   Код ответа сервера-200. Не происходит уменьшение остатка по счету *"from"* и увеличения остатка по любому другому счету,  существующему в системе.
1. Перевод с валидным номером счета *"from"* на невалидный, некорректный номер счета *"to"*. валидной суммы *"amount"*, которая меньше остатка на счете*"from"*, но не равна нулю или отрицательному значению.  
      **Ожидаемый результат:**
      Код ответа сервера-200. Не происходит уменьшение остатка по счету *"from"* и увеличения остатка по любому другому счету,  существующему в системе.
1. Перевод с валидным номером счета *"from"* на невалидный, пустой номер счета *"to"*. валидной суммы *"amount"*, которая меньше остатка на счете*"from"*, но не равна нулю или отрицательному значению.  
      **Ожидаемый результат:**
      Код ответа сервера-200. Не происходит уменьшение остатка по счету *"from"* и увеличения остатка по любому другому счету,  существующему в системе.
### Критические сценарии (Critical Path)
1. Перевод с валидными номерами счетов *"from"* и *"to"*. валидной суммы *"amount"*, которая больше остатка на счете*"from"*  
   **Ожидаемый результат:**
   Код ответа сервера-200. Не происходит уменьшение остатка по счету *"from"* и увеличения остатка по счету *"to"* на размер *"amount"*.
1. Перевод с инвалидным, несуществующим номером счета *"from"* на валидный номер счета *"to"*. валидной суммы *"amount"*, не равна нулю или отрицательному значению.  
   **Ожидаемый результат:**
   Код ответа сервера-200. Не происходит увеличение остатка по счету *"to* и уменьшения остатка по любому другому счету,  существующему в системе.
1. Перевод с инвалидным, некорректным номером счета *"from"* на валидный номер счета *"to"*. валидной суммы *"amount"*, не равна нулю или отрицательному значению.  
   **Ожидаемый результат:**
   Код ответа сервера-200. Не происходит увеличение остатка по счету *"to* и уменьшения остатка по любому другому счету,  существующему в системе.
1. Перевод с инвалидным, пустым номером счета *"from"* на валидный номер счета *"to"*. валидной суммы *"amount"*, не равна нулю или отрицательному значению.  
      **Ожидаемый результат:**
      Код ответа сервера-200. Не происходит увеличение остатка по счету *"to* и уменьшения остатка по любому другому счету,  существующему в системе.
1. Перевод с валидными номерами счетов *"from"* и *"to"*. инвалидной суммы *"amount"*, которая равна отрицательному значению.  
   **Ожидаемый результат:**
   Код ответа сервера-200. Не происходит увеличение остатков по счетам *"from"* и *"to*.

----------

### **2. Перечень используемых инструментов**
1. Java 11 - язык программирования.
2. IntelliJ IDEa - среда разработки.
3. SQL - язык программирования для создания запросов к базе данных
4. Gradle - система автоматической сборки проекта и исполнения кода.
5. JUnit5 -  "оберточный" фреймворк для автотестов.
6. Rest-asuured  - фреймворк для отправки HTTP-запросов и экстрации данных полученых в ответе сервера.
7. Java-Faker - генерация рандомизированных данных для уменьшения ээфекта пестицида.
8. Docker - для развертывания и запуска базы данных в контейнере.
9. Git и GitHub - для хранения кода и документации проекта.
10. DBUtils — фреймворк для создания запросов к базе данных на языке SQL.
11. JavaDBConnector - коннектор для отправки запросов к БД.
12. Lombok - фреймворк-кодогенератор для уменьшения бойлерплейт кода.
13. Jackson-databind - библиотека для автоматической сериализации JSON-структур.
14. Appveyor - CI/CD система для запуска автотестов при каждом коммите в репозитории.

### **3. Перечень и описание возможных рисков при автоматизации**

Выполнение задачи может затянуться из-за следующих моментов:

* Неожиданное и незадокументированное поведение системы, потребующее затрат времени на выяснение причин такого поведения.
* Настроить работу симулятора банковских сервисов.

### **4. Интервальная оценка с учётом рисков (в часах)**

* Подготовка к проведению тестирования: 3 часа
* Написание автотестов: 5 часов
* Подготовка отчетов о проведении тестирования: 3 часа

