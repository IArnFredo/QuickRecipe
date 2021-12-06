package quick_recipe.didi_x_djongers;

public class Recipes {
    String name;
    String ingredients;
    String steps;
    String firstCategory;
    String secondCategory;

    public Recipes(String name, String ingredients, String steps, String firstCategory, String secondCategory) {
        this.name = name;
        this.ingredients = ingredients;
        this.steps = steps;
        this.firstCategory = firstCategory;
        this.secondCategory = secondCategory;
    }

    public String getName() {
        return name;
    }

    public String getIngredients() {
        return ingredients;
    }

    public String getSteps() {
        return steps;
    }

    public String getFirstCategory() {
        return firstCategory;
    }

    public String getSecondCategory() {
        return secondCategory;
    }
}
