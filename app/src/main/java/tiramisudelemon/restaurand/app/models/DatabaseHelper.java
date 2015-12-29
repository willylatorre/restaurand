package tiramisudelemon.restaurand.app.models;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

import com.j256.ormlite.android.apptools.OrmLiteSqliteOpenHelper;
import com.j256.ormlite.dao.Dao;
import com.j256.ormlite.dao.RuntimeExceptionDao;
import com.j256.ormlite.stmt.QueryBuilder;
import com.j256.ormlite.stmt.UpdateBuilder;
import com.j256.ormlite.support.ConnectionSource;
import com.j256.ormlite.table.TableUtils;

import java.sql.SQLException;
import java.util.Date;
import java.util.List;

import tiramisudelemon.restaurand.app.restaurants.Restaurant;

/**
 * Created by Past on 16/07/2014.
 */
public class DatabaseHelper extends OrmLiteSqliteOpenHelper implements DatabaseModule {

    private static final String DATABASE_NAME = "restauRand.db";
    private static final int DATABASE_VERSION = 1;

    //DAOs
    private Dao<Restaurant, Integer> restauDao = null;
    private RuntimeExceptionDao<Restaurant, Integer> restauREDao = null;

    public DatabaseHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
        restauREDao = getRERestauDao();
    }

    @Override
    public void onCreate(SQLiteDatabase db, ConnectionSource connectionSource) {
        try {
            Log.i(DatabaseHelper.class.getName(), "onCreate");
            TableUtils.createTable(connectionSource, Restaurant.class);
        } catch (SQLException e) {
            Log.e(DatabaseHelper.class.getName(), "Can't create database", e);
            throw new RuntimeException();
        }
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, ConnectionSource connectionSource, int oldVersion, int newVersion) {
        try {
            Log.i(DatabaseHelper.class.getName(), "onUpgrade");
            TableUtils.dropTable(connectionSource, Restaurant.class, true);
            // after we drop the old databases, we create the new ones
            onCreate(db, connectionSource);
        } catch (SQLException e) {
            Log.e(DatabaseHelper.class.getName(), "Can't drop databases", e);
            throw new RuntimeException(e);
        }
    }

    public void clearDatabase(ConnectionSource connectionSource) {
        try {
            TableUtils.clearTable(connectionSource, Restaurant.class);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    /**
     * Returns the Database Access Object (DAO) for our SimpleData class. It will create it or just give the cached
     * value.
     */
    public Dao<Restaurant, Integer> getRestauDao() throws SQLException {
        if (restauDao == null) {
            restauDao = getDao(Restaurant.class);
        }
        return restauDao;
    }

    /**
     * Returns the RuntimeExceptionDao (Database Access Object) version of a Dao for our SimpleData class. It will
     * create it or just give the cached value. RuntimeExceptionDao only through RuntimeExceptions.
     */
    public RuntimeExceptionDao<Restaurant, Integer> getRERestauDao() {
        if (restauREDao == null) {
            restauREDao = getRuntimeExceptionDao(Restaurant.class);
        }
        return restauREDao;
    }


    /**
     * Close the database connections and clear any cached DAOs.
     */
    @Override
    public void close() {
        super.close();
        restauDao = null;
        restauREDao = null;
    }


    @Override
    public void createRest(Restaurant restaurant) {
        restauREDao.create(restaurant);
    }

    @Override
    public void updateRestaurant(Restaurant restaurant) {
        restauREDao.update(restaurant);
    }

    @Override
    public long countRestaurants() {
        return restauREDao.countOf();
    }

    @Override
    public List<Restaurant> getAllRestaurants() {
        return restauREDao.queryForAll();
    }

    @Override
    public Restaurant searchRestaurantById(int id) {

        final Restaurant rest;
        QueryBuilder<Restaurant, Integer> qb = restauREDao.queryBuilder();
        try {
            qb.where().eq("id", id);
            rest = qb.queryForFirst();
            return rest;
        } catch (SQLException e) {
            e.printStackTrace();
            return null;
        }
    }

    @Override
    public void increaseRestaurantRndFactor(Restaurant restaurant) {

        UpdateBuilder<Restaurant, Integer> ub = restauREDao.updateBuilder();
        try {
            ub.where().eq("id", restaurant.getId());
            //Aumentamos 1 el valor rnd_factor
            double rf = restaurant.getRandom_factor();
            rf++;
            ub.updateColumnValue("RND_FACTOR", rf);
            ub.update();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void updateRestaurantRndFactor(Restaurant restaurant) {
        UpdateBuilder<Restaurant, Integer> ub = restauREDao.updateBuilder();
        try {
            ub.where().eq("id", restaurant.getId());
            //Recuperamos el mayor valor de random_factor
            double max = restauREDao.queryRawValue("select max(RND_FACTOR) from restaurant");
            max = max + 0.1;
            ub.updateColumnValue("RND_FACTOR", max);
            ub.updateColumnValue("LAST_SELECTED", new Date());

            ub.update();
        } catch (SQLException e) {
            e.printStackTrace();
        }

    }

    @Override
    public void deleteRR(Restaurant rest) {
        restauREDao.delete(rest);
    }
}
