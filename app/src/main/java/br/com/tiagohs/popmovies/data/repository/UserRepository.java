package br.com.tiagohs.popmovies.data.repository;

import android.content.Context;
import java.util.List;
import br.com.tiagohs.popmovies.model.db.UserDB;

public interface UserRepository {

    long saveUser(UserDB user, Context context);

    void deleteUserByID(long id, long profileID);
    void deleteUserByUsername(String username, long profileID);

    UserDB findUserByUsername(String username, long profileID);

    List<UserDB> findAllUsers();
}
