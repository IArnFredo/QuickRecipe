package quick_recipe.didi_x_djongers;

public class UserHelperClass {
    String name, ingredients, steps, firstCategory, secondCategory;

    public UserHelperClass(String name, String ingredients, String steps, String firstCategory, String secondCategory){
        this.name = name;
        this.ingredients = ingredients;
        this.steps = steps;
        this.firstCategory = firstCategory;
        this.secondCategory = secondCategory;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getIngredients() {
        return ingredients;
    }

    public void setIngredients(String ingredients) {
        this.ingredients = ingredients;
    }

    public String getSteps() {
        return steps;
    }

    public void setSteps(String steps) {
        this.steps = steps;
    }

    public String getFirstCategory() {
        return firstCategory;
    }

    public void setFirstCategory(String firstCategory) {
        this.firstCategory = firstCategory;
    }

    public String getSecondCategory() {
        return secondCategory;
    }

    public void setSecondCategory(String secondCategory) {
        this.secondCategory = secondCategory;
    }
}
