package com.bitesync.web.repositories;

import com.bitesync.web.models.Recipe;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface RecipeRepository extends MongoRepository<Recipe, String> {
    Recipe findByName(String name);
}
