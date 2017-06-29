package com.example.adeolu.bakingapp.utils;

import java.util.List;

/**
 * Created by ADEOLU on 6/4/2017.
 */

public class JSonResponse {
    public class Recipe{
        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public String getServings() {
            return servings;
        }


        public String getImage() {
            return image;
        }


        public int getId() {
            return id;
        }

        public void setId(int id) {
            this.id = id;
        }

        public List<Ingredients> getIngredients() {
            return ingredients;
        }

        public void setIngredients(List<Ingredients> ingredients) {
            this.ingredients = ingredients;
        }

        public List<Steps> getSteps() {
            return steps;
        }


        private String name,servings,image;
        private int id;
        private List<Ingredients> ingredients;
        private List<Steps> steps;
    }
    public class Ingredients{
        public String getQuantity() {
            return quantity;
        }


        public String getMeasure() {
            return measure;
        }


        public String getIngredient() {
            return ingredient;
        }


        private String quantity,measure,ingredient;
    }
    public  class Steps{


        public String getDescription() {
            return description;
        }


        public String getVideoURL() {
            return videoURL;
        }


        public String getThumbnailURL() {
            return thumbnailURL;
        }


        public int getId() {
            return id;
        }

        public void setId(int id) {
            this.id = id;
        }

        private String shortDescription,description,videoURL,thumbnailURL;
        private int id;
    }
}
