# KoshelekTestWork.Bitcoin client. 
<h2>Скриншоты:</h2/<br>
https://i.imgur.com/i9ZmjpD.png <br>
https://i.imgur.com/9fMFQwS.png <br>
<br>
  <h2>Стэк технологий:<br></h2>
Kotlin, di фреймворк dagger2, RxJava для асинхронных задач, архитектура - MVVM,<br>
Retrofit, GSon, RxWebsocket - работа с сетью.
<br>
  <h2>Задание:</h2>
Тестовое задание для кандидатов на позицию Android-разработчик
Ориентировочное время выполнения 8-10 часов.
Разработать приложение по заданию ниже.
При реализации задания необходимо продемонстрировать умение работы с
WebSockets, REST API.
Приложение построить на архитектуре MVVM. При разработке приложения,
придерживаться SOLID принципов.
Задание:
Написать приложение основой которого станут два экрана. Их расположение и выбор
контейнера для них на ваше усмотрение. Единственное, переход между
контроллерами должен поддерживать свайп. Переход между экранами реализовать
через BottomNavigationView.
Первый экран должен содержать две таблицы (логика их появления и расположение
на ваше усмотрение). Каждая таблица имеет три столбца: Amount, Price, Total. Одна
таблица отображает ордера типа Bid, другая ордера типа Ask.
Данные обновляются через WebSockets. Price и Amount (Quantity) получаются из API
Binance. Total рассчитывается на клиенте как Price * Amount.
Второй контроллер должен содержать diff-изменения ордеров, т.е. две таблицы (Ask и
Bid) с двумя столбцами (цена и изменение). Должен быть реализован отдельный
выбор(dropdown) базовой и котируемой валюты для ордеров (BTCUSDT, BNBBTC и
ETHBTC). Отображение базовой и котируемой валюты разделить знаком “/”​ ​ .
В таблицах должны отображаться 100 элементов. Добавить лоадеры для загрузки
данных в таблицах и при переходах между экранами приложения.
Обратите внимание: обновление данных в таблицах должно быть реализовано
плавно, без скачков и расширений таблиц.
API для выполнения задания:
https://github.com/binance-exchange/binance-official-api-docs
Пример:
https://nijoelite.github.io/test-binance-api/
