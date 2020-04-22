# FallingWords
A simple game to learn Spanish words

## Solution and flow

Firstly I used **TDD** methodology to develope game engin and repositoy,then because of  time limit I left out TDD to complete the app through best time.

At the beginning of application start up koin provides dependencies. Next, `MainActivity` asks the `GameViewModel` to init the game. The ViewModel asks the `GameFactory` to load game data and create a new game.
Inside the Factory there is a repository variable which uses to load data from our json file.

## Library and Solutions:

- **TDD** *development method (30%)*
- **Koin** *dependency injection*
- **RxJava** *async data loading / state handling*
- **MVVM** *main app architecture (using Android ViewModel and LiveData)*
- **MockK** *mocking purpose*
- `TileDrawable` and `RxSchedulersOverrideRule` copied from another projects!

## Time Allocation
I have spent **7 hours and 45 minutes** to complete this project (Honestly 4 hours were too short for achieving an acceptable result)
- 1:30 hours to make decision about app architecture/libraries to get the proper result based on time limit and project scale
- 2:30 hours to develop game engine (game/factory/repository classes) using TDD methodology
- 3:15 hours to implement ui classes (activity/ViewModel/dialogs)
- 30 minutes to improve ui

## Important decisions

There were two decisions points, one of which was the architecture, I really adore `Clean Architecture`, but for such a project in a tight time there is no reason to use that!
The second point was choosing the proper Dependency Injection frameworks. As I have experience in either `Dagger` and `Koin`, I went for `Koin` since it helps me to save much time in building project and providing dependencies.

## Next Step?

There are the features which I would implement if I had enough time:
- Animation and UI improvements
- Increase test coverage
- Add levels, offline database and online updates
- Adding some gamifications

