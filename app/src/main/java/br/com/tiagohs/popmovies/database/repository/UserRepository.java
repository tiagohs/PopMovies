package br.com.tiagohs.popmovies.database.repository;

import android.content.Context;
import java.util.List;
import br.com.tiagohs.popmovies.model.db.UserDB;
import io.reactivex.Observable;

public interface UserRepository {

    Observable<Long> saveUser(UserDB user, Context context);

    Observable<Integer> deleteUserByID(long id, long profileID);
    Observable<Integer> deleteUserByUsername(String username, long profileID);

    Observable<UserDB> findUserByUsername(String username, long profileID);
    UserDB findUserDatabase(String username, long profileID);

    Observable<List<UserDB>> findAllUsers();
    List<UserDB> findAllUsersDatabase();
}
