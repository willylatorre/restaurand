package tiramisudelemon.restaurand.app.models;

import java.util.List;

import tiramisudelemon.restaurand.app.restaurants.Restaurand;

/**
 * Created by work on 17/12/15.
 */
public interface  DatabaseModule {

    void createRest(Restaurand restaurand);
    void updateRestaurant(Restaurand restaurand);

    long countRestaurants();

    List<Restaurand> getAllRestaurants();

    Restaurand searchRestaurantById(int id);

    void updateRestaurantRndFactor (Restaurand restaurand);
    void increaseRestaurantRndFactor (Restaurand restaurand);


    void deleteRR(Restaurand rest);
}
