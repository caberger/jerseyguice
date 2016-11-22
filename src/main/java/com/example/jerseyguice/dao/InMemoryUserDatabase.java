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
package com.example.jerseyguice.dao;

import java.util.HashMap;
import java.util.Map;

import com.example.jerseyguice.model.User;

public class InMemoryUserDatabase {
    private Map<Integer, User> users;
    private int sequence = 0;

    public InMemoryUserDatabase() {
        users = new HashMap<>();
        addUser("Joe Sixpack", "joe.sixpack@example.com");
        addUser("John Doe", "john.dow@example.com");
        addUser("Jane Roe", "jane.roe@example.com");
    }
    public User addUser(String name, String email) {
        User user = new User();
        user.id = ++sequence;
        user.name = name;
        user.email = email;
        users.put(user.id, user);
        return user;
    }
    public Map<Integer, User> getUsers() {
    	return users;
    }
}