/*
 * Copyright (c) 2016 Aberger Software GmbH. All Rights Reserved.
 *               http://www.aberger.at
 *
 * Licensed under the Apache License, Version 2.0 (the "License"); you
 * may not use this file except in compliance with the License. You may
 * obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or
 * implied. See the License for the specific language governing
 * permissions and limitations under the License.
 */

package com.example.jerseyguice;

import com.google.inject.Guice;
import com.google.inject.Injector;
import com.google.inject.Scopes;
import com.google.inject.servlet.GuiceServletContextListener;
import com.google.inject.servlet.ServletModule;

public class MyServletContextListener extends GuiceServletContextListener {
	class MyServletModule extends ServletModule {
		@Override
		protected void configureServlets() {
			bind(WelcomeTexter.class);
			bind(HelloWorldServlet.class).in(Scopes.SINGLETON);
			serve("/hello").with(HelloWorldServlet.class);
		}
	}
	@Override
	protected Injector getInjector() {
	    return Guice.createInjector(new MyServletModule());
	}
}
