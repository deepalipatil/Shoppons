# Android App Structure

Base repository for Android apps.

## Introduction

We at BetaCraft, have been continuously working on Android applications, libraries and some
embedded apps. The biggest common issue we had faced was managing a continuously expanding
codebase with quality, when we are delivering the app at faster rate (our usual sprint cycle is
of 6 days) and multiple minds are working on the same codebase. Continuous research and some of
our experience landed us onto this architecture. This is hugely inspired from
AndroidCleanArchitecture, with some of our own beliefs.


## Structure

App is divided into three main modules.

### Data

Everything that is related with data will be done in this layer. No business logic is written in
 this module. Only the caching, server fetching and data mapping will be done. All the
 repositories are implemented in this module.

#### Entities
Entity represents a data entity. UserEntity will have all the fields associated with user to
represent it in the data layer.

#### Repositories
Repositories are associated with entities. UserRepository will do all the functionality like
fetching from the cloud or fetching from the cache. This will have functions like GetUserById,
GetUserByEmail, GetUserFriends. At this stage they don't understand the threading mechanism.
Coder can safely assume that the current thread is the working thread for all the functionality
in this module. Every function returns an Observable.

#### Exceptions
This package has all the data level exceptions like UserNotFoundException,
NetworkConnectionException etc.

#### Net
All the api implementation happens inside this package.

### Domain (This will be written first while starting the project)

Main package will have all the domain models like User, Account etc

#### Interactors
Interactors package contains all the use cases. One use case does one use case at a time. For
example UserLoginUseCase will perform the UserLogin, check and throw all the domain level
exceptions related to that operation. Similarly GetUserDetailsUseCase for getting user details.

#### Executors
Executors define the thread pools for running background jobs as well as there is
PostExecutionThread which (usually return AndroidSchedulers.mainThread()) returns thread pool on
which the onNext, onError and onCompleted will be executed.

#### Repositories
This acts as a blueprint for the repositories that we implement in the data module.

#### Exceptions
This package has all the domain level exceptions. Like InsufficientBalanceException,
UserPasswordMismatchExceptions.


### Presentation

#### Presenter
All the presenter logic. This has the communication with the data layer using the use cases we
have defined in the domain module.
#### Adapters
#### Utils
#### Models
All the presentation level models. UserModel, AccountModel which are parcelable and everything.
Which is very specific to the presentation logic.
#### Views
##### Activities
##### Fragments
##### Components

