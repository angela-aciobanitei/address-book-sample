# address-book-sample

## App Architecture
The app uses clean architecture. For simplicity, it has 3 layers:
* the _domain layer_ contains the business logic of the app. It holds the business models, the abstract definitions of the repositories, some mappers, and the use cases.
* the _data layer_ provides definitions of the data sources. It holds the concrete implementations of the repositories and other data sources like the database in this case. 
* the _presentation layer_ contains the UI-related code and makes use of the MVVM pattern in this particular case.

## Testing
* local unit tests to test the usecases; since every use case depends on the repository, in order to test in isolation, I  have created a fake repository that implements the same interface as the real repository, and thus can be swapped during testing via dependency injection (which is managed by Hilt)
* local intrumented tests for DAO and the local data source
* integration tests for view models
* to handle coroutines testing I have used the TestCoroutineDispatcher provided by the "kotlinx-coroutines-test" library

## Demo

https://user-images.githubusercontent.com/37955938/124450479-5d8ae900-dd7c-11eb-9f84-60af4279511c.mp4

