package BusinessLogicLayer;

public class adultFood extends food
{
    int chickenMeals;
    int steakMeals;
    int gammonRoastMeals;
    int pastaMeals;
    int soupMeals;
    int lobsterBisqueMeals;

    public adultFood(int ChickenMeals, int SteakMeals, int GammonRoastMeals, int PastaMeals, int SoupMeals, int LobsterBisqueMeals)
    {
        this.chickenMeals = ChickenMeals;
        this.steakMeals = SteakMeals;
        this.gammonRoastMeals = GammonRoastMeals;
        this.pastaMeals = PastaMeals;
        this.soupMeals = SoupMeals;
        this.lobsterBisqueMeals = LobsterBisqueMeals;
    }

    @Override
    public int sumTotal()
    {
        int total = 0;

        total = chickenMeals + steakMeals + gammonRoastMeals + pastaMeals + soupMeals + lobsterBisqueMeals;

        return total;
    }
}
