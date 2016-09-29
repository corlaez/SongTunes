package jarmandocordova.restdemo.demo.global.di.component;

import javax.inject.Singleton;

import dagger.Component;
import jarmandocordova.restdemo.demo.global.MyApp;
import jarmandocordova.restdemo.demo.global.di.module.ApplicationModule;
import jarmandocordova.restdemo.demo.main.view.MainActivity;
import jarmandocordova.restdemo.demo.global.gateway.itunes.ITunesApi;

/**
 * Component for {@link MyApp}.
 * <p/>
 * This acts as pairing between the module and injection targets, each of which
 * has to have a corresponding inject method in this component.
 * <p/>
 * Created by mgouline on 23/04/15. Edited by jarma
 */
@Singleton
@Component(modules = ApplicationModule.class)
public interface MyAppComponent {
    //MyApp getMyApp();
    /** Se encarga de inyectar todas las dependencias anotadas en la clase de la instancia parametro**/
    void inject(MyApp application);
    /** Se encarga de inyectar todas las dependencias anotadas en la clase de la instancia parametro**/
    void inject(MainActivity activity);
     /** Se encarga de inyectar todas las dependencias anotadas en la clase de la instancia parametro**/
    void inject(ITunesApi iTunesApiRetrofit);
}