package praktikum;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;

import java.util.Arrays;
import java.util.Collection;
import java.util.List;

import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

@RunWith(Parameterized.class)
public class BurgerPriceParameterizedTest {

    private static final float BUN_PRICE = 100f;
    private static final float INGREDIENT_PRICE_1 = 50f;
    private static final float INGREDIENT_PRICE_2 = 30f;

    private Burger burger;
    private Bun bunMock;
    private final List<Ingredient> ingredients;
    private final float expectedPrice;

    public BurgerPriceParameterizedTest(List<Ingredient> ingredients, float expectedPrice) {
        this.ingredients = ingredients;
        this.expectedPrice = expectedPrice;
    }

    @Parameterized.Parameters(name = "Ингредиенты: {0}, ожидаемая цена: {1}")
    public static Collection<Object[]> data() {
        Bun bunMock = mock(Bun.class);
        when(bunMock.getPrice()).thenReturn(BUN_PRICE);

        Ingredient ingredient50 = mock(Ingredient.class);
        when(ingredient50.getPrice()).thenReturn(INGREDIENT_PRICE_1);
        Ingredient ingredient30 = mock(Ingredient.class);
        when(ingredient30.getPrice()).thenReturn(INGREDIENT_PRICE_2);

        return Arrays.asList(
                new Object[][]{
                        {Arrays.asList(ingredient50), BUN_PRICE * 2 + INGREDIENT_PRICE_1},
                        {Arrays.asList(ingredient50, ingredient30), BUN_PRICE * 2 + INGREDIENT_PRICE_1 + INGREDIENT_PRICE_2},
                        {Arrays.asList(), BUN_PRICE * 2}
                }
        );
    }

    @Before
    public void setUp() {
        burger = new Burger();
        bunMock = mock(Bun.class);
        when(bunMock.getPrice()).thenReturn(BUN_PRICE);
        burger.setBuns(bunMock);
        for (Ingredient ing : ingredients) {
            burger.addIngredient(ing);
        }
    }

    @Test
    public void testGetPrice() {
        assertEquals(expectedPrice, burger.getPrice(), 0.001f);
    }
}