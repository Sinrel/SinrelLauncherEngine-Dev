Sinrel Launcher Engine
======================

SLE ( Sinrel Launcher Engine ) — это движок позволяющий создавать Minecraft-лаунчеры практически любой сложности.

Версия
======

Текущая версия
--------------
0.7.6.14

Модель версии
----------------

x1.x2.x3.x4

* 1) Поколение 
* 2) Версия движка (Java часть)
* 3) Версия серверной части (PHP часть)
* 4) Исправление

Процесс разработки
------------------

При разработке на движке SLE разработчику фактически остаётся писать только графический интерфейс (GUI), что весьма значительно сокращает сроки разработки лаунчера.

Для инициализации SLE используйте такую конструкцию:
```Java
//EngineSettings settings = new EngineSettings( "домен", "путь на сервере", "рабочая папка", "версия", "код версии");
EngineSettings settings = new EngineSettings( "example.com", "launcher", "minecraft", "0.1", 1 );
Engine engine = new Engine(settings);
```

Для работы с SLE используйте объект класса Intent.
Пример запуска minecraft:
```Java
Intent i = engine.getIntent();
i.startMinecraft( "simple", "player" , new Frame() ); 
```
Лицензия
--------
GNU Lesser General Public License v3.0 (LGPL v3)
                       