package at.aberger.jerseyguice.config;

import java.net.URL;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;

import org.glassfish.hk2.api.ServiceLocator;
import org.glassfish.jersey.server.ResourceConfig;
import org.glassfish.jersey.server.spi.Container;
import org.glassfish.jersey.server.spi.ContainerLifecycleListener;
import org.glassfish.jersey.servlet.ServletContainer;
import org.jvnet.hk2.guice.bridge.api.GuiceBridge;
import org.jvnet.hk2.guice.bridge.api.GuiceIntoHK2Bridge;

import com.google.inject.Injector;
import com.google.inject.Scopes;
import com.google.inject.servlet.ServletModule;

public abstract class RestServletModule extends ServletModule {
	private static final Logger log = Logger.getLogger(RestServletModule.class.getCanonicalName());
	
	@Override
	abstract protected void configureServlets();
	
	public interface RestKeyBindingBuilder {
		void packages(String ... packages);
	}
	protected RestKeyBindingBuilder rest(String... urlPatterns) {
		return new RestKeyBindingBuilderImpl(Arrays.asList(urlPatterns));
	}
	private class RestKeyBindingBuilderImpl implements RestKeyBindingBuilder {
		List<String> paths;
		
		public RestKeyBindingBuilderImpl(List<String> paths) {
			this.paths = paths;
		}
		private boolean checkIfPackageExistsAndLog(String packge) {
			boolean exists = false;
			String resourcePath = packge.replace(".", "/");
			URL resource = getClass().getClassLoader().getResource(resourcePath);
			if (resource != null) {
				exists = true;
				log.log(Level.INFO, "rest(" + paths + ").packages(" + packge + ")");
			} else {
				log.log(Level.INFO, "No Beans in '" + packge + "' found. Requests " + paths + " will fail.");
			}
			return exists;
		}
		@Override
		public void packages(String ... packages) {
			StringBuilder sb = new StringBuilder();

			for (String pkg: packages) {
				if (sb.length() > 0) {
					sb.append(',');
				}
				checkIfPackageExistsAndLog(pkg);
				sb.append(pkg);
			}
			Map<String, String> params = new HashMap<>();
			params.put("javax.ws.rs.Application", GuiceResourceConfig.class.getCanonicalName());
			if (sb.length() > 0) {
				params.put("jersey.config.server.provider.packages", sb.toString());
			}
			bind(ServletContainer.class).in(Scopes.SINGLETON);
			for (String path: paths) {
				serve(path).with(ServletContainer.class, params);
			}
		}
	}
}

class GuiceResourceConfig extends ResourceConfig {
	public GuiceResourceConfig() {
		register(new ContainerLifecycleListener() {
			public void onStartup(Container container) {
				ServletContainer servletContainer = (ServletContainer)container;
	   			ServiceLocator serviceLocator = container.getApplicationHandler().getServiceLocator();
				GuiceBridge.getGuiceBridge().initializeGuiceBridge(serviceLocator);
				GuiceIntoHK2Bridge guiceBridge = serviceLocator.getService(GuiceIntoHK2Bridge.class);
		        Injector injector = (Injector) servletContainer.getServletContext().getAttribute(Injector.class.getName());
		        guiceBridge.bridgeGuiceInjector(injector);
			}
			public void onReload(Container container) {
			}
			public void onShutdown(Container container) {
			}		
		});
	}
}