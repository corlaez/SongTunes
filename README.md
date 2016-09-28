# SongTunes
Search and listen your favorite songs, powered by iTunes. You can also choose to see the raw json array instead of the formated tracks that can reproduce the song.

Libraries:
RxJava, RxAndroid, RxBidings
Retrofit2, OkHttp
Dagger2

I am aware that the code can be improved. It's the first time I am using Dagger2 and I just recently heard about ReactiveX in general.
But this project is already working and I will be improving it on the future. 

What it needs:
- Avoid another http request upon changing the view format (tracks vs raw)
- Refactor main activity, organize the Observers.
- Use Dagger in a more modular way. (Network module, MainActivityModule?)
- Add Local Cache (I would go with PaperDB) 
- Tests (:s)
- Changing the play icon to a pause icon when playing, revert it back when the song stops playing.
