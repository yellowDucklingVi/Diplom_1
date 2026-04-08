package praktikum;

import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.when;

public class BurgerTest {

    private static final float BUN_PRICE = 100f;
    private static final float INGREDIENT1_PRICE = 50f;
    private static final float INGREDIENT2_PRICE = 30f;

    private Burger burger;

    @Mock
    private Bun bunMock;

    @Mock
    private Ingredient ingredientMock1;

    @Mock
    private Ingredient ingredientMock2;

    @Before
    public void setUp() {
        MockitoAnnotations.openMocks(this);
        burger = new Burger();
    }

    @Test
    public void setBunsShouldSetBun() {
        burger.setBuns(bunMock);
        assertEquals(bunMock, burger.bun);
    }

    @Test
    public void addIngredientShouldAddToList() {
        burger.addIngredient(ingredientMock1);
        assertEquals(ingredientMock1, burger.ingredients.get(0));
    }

    @Test
    public void removeIngredientShouldRemoveByIndex() {
        burger.addIngredient(ingredientMock1);
        burger.addIngredient(ingredientMock2);
        burger.removeIngredient(0);
        assertEquals(1, burger.ingredients.size());
    }

    @Test
    public void moveIngredientShouldChangePosition() {
        burger.addIngredient(ingredientMock1);
        burger.addIngredient(ingredientMock2);
        burger.moveIngredient(0, 1);
        assertEquals(ingredientMock2, burger.ingredients.get(0));
    }

    @Test
    public void moveIngredientShouldPlaceElementAtNewIndex() {
        burger.addIngredient(ingredientMock1);
        burger.addIngredient(ingredientMock2);
        burger.moveIngredient(0, 1);
        assertEquals(ingredientMock1, burger.ingredients.get(1));
    }

    @Test
    public void getPriceShouldReturnSumOfBunsAndIngredients() {
        when(bunMock.getPrice()).thenReturn(BUN_PRICE);
        when(ingredientMock1.getPrice()).thenReturn(INGREDIENT1_PRICE);
        when(ingredientMock2.getPrice()).thenReturn(INGREDIENT2_PRICE);

        burger.setBuns(bunMock);
        burger.addIngredient(ingredientMock1);
        burger.addIngredient(ingredientMock2);

        float expectedPrice = BUN_PRICE * 2 + INGREDIENT1_PRICE + INGREDIENT2_PRICE;
        assertEquals(expectedPrice, burger.getPrice(), 0.001f);
    }

    @Test
    public void getReceiptShouldReturnFormattedString() {
        when(bunMock.getName()).thenReturn("black bun");
        when(bunMock.getPrice()).thenReturn(BUN_PRICE);
        when(ingredientMock1.getType()).thenReturn(IngredientType.SAUCE);
        when(ingredientMock1.getName()).thenReturn("hot sauce");
        when(ingredientMock1.getPrice()).thenReturn(INGREDIENT1_PRICE);
        when(ingredientMock2.getType()).thenReturn(IngredientType.FILLING);
        when(ingredientMock2.getName()).thenReturn("cutlet");
        when(ingredientMock2.getPrice()).thenReturn(INGREDIENT2_PRICE);

        burger.setBuns(bunMock);
        burger.addIngredient(ingredientMock1);
        burger.addIngredient(ingredientMock2);

        float totalPrice = BUN_PRICE * 2 + INGREDIENT1_PRICE + INGREDIENT2_PRICE;
        String expectedReceipt = "(==== black bun ====)\n" +
                "= sauce hot sauce =\n" +
                "= filling cutlet =\n" +
                "(==== black bun ====)\n" +
                "\n" +
                "Price: " + String.format("%f", totalPrice) + "\n";

        assertEquals(expectedReceipt, burger.getReceipt());
    }
}