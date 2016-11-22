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

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

import javax.inject.Inject;

import com.example.jerseyguice.model.User;

public class InMemoryUserDao extends UserDao {
	@Inject
    InMemoryUserDatabase userDatabase;

    @Override
    public User findById(int id) {
         return userDatabase.getUsers().get(id);
    }
    @Override
    public List<User> listAll() {
        List<User> sortedUsers = new ArrayList<User>(userDatabase.getUsers().values());

        Collections.sort(sortedUsers, new Comparator<User>() {
            public int compare(User lhs, User rhs) {
                return lhs.id - rhs.id;
            }
        });
        return sortedUsers;
    }
    @Override
    public User add(User user) {
        return userDatabase.addUser(user.name, user.email);
    }
    @Override
    public void delete(User user) {
        userDatabase.getUsers().remove(user.id);
    }
    @Override
    public boolean update(User user) {
        boolean ok = false;
        User existingUser = userDatabase.getUsers().get(user.id);
        if (existingUser != null) {
            existingUser.name = user.name;
            existingUser.email = user.email;
            ok = true;
        }
        return ok;
    }
}