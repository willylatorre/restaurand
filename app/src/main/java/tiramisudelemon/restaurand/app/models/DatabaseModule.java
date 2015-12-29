package tiramisudelemon.restaurand.app.models;

import java.util.List;

import tiramisudelemon.restaurand.app.restaurants.Restaurant;

/**
 * Created by work on 17/12/15.
 */
public interface  DatabaseModule {

    void createRest(Restaurant restaurant);
    void updateRestaurant(Restaurant restaurant);

    long countRestaurants();

    List<Restaurant> getAllRestaurants();

    Restaurant searchRestaurantById(int id);

    void updateRestaurantRndFactor (Restaurant  restaurant);
    void increaseRestaurantRndFactor (Restaurant restaurant);


    void deleteRR(Restaurant rest);
}
