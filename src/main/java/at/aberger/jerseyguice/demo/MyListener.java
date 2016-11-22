package at.aberger.jerseyguice.demo;

import com.google.inject.Guice;
import com.google.inject.Injector;
import com.google.inject.servlet.GuiceServletContextListener;

import at.aberger.jerseyguice.config.RestServletModule;
import at.aberger.jerseyguice.demo.dao.MyInMemoryUserDemoDatabase;
import at.aberger.jerseyguice.demo.dao.UserDao;
import at.aberger.jerseyguice.demo.model.DebugUser;
import at.aberger.jerseyguice.demo.model.User;

public class MyListener extends GuiceServletContextListener {	

	@Override
	protected Injector getInjector() {
		return Guice.createInjector(new RestServletModule() {
			protected void configureServlets() {
				
				rest("/app/*").packages("at.aberger.jerseyguice.demo.app");
				
				bind(User.class).to(DebugUser.class);
				bind(UserDao.class).to(MyInMemoryUserDemoDatabase.class);
			}
		});
	}
}
