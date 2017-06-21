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

        public void setServings(String servings) {
            this.servings = servings;
        }

        public String getImage() {
            return image;
        }

        public void setImage(String image) {
            this.image = image;
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

        public void setSteps(List<Steps> steps) {
            this.steps = steps;
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

        public void setQuantity(String quantity) {
            this.quantity = quantity;
        }

        public String getMeasure() {
            return measure;
        }

        public void setMeasure(String measure) {
            this.measure = measure;
        }

        public String getIngredient() {
            return ingredient;
        }

        public void setIngredient(String ingredient) {
            this.ingredient = ingredient;
        }

        private String quantity,measure,ingredient;
    }
    public  class Steps{
        public String getShortDescription() {
            return shortDescription;
        }

        public void setShortDescription(String shortDescription) {
            this.shortDescription = shortDescription;
        }

        public String getDescription() {
            return description;
        }

        public void setDescription(String description) {
            this.description = description;
        }

        public String getVideoURL() {
            return videoURL;
        }

        public void setVideoURL(String videoURL) {
            this.videoURL = videoURL;
        }

        public String getThumbnailURL() {
            return thumbnailURL;
        }

        public void setThumbnailURL(String thumbnailURL) {
            this.thumbnailURL = thumbnailURL;
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
