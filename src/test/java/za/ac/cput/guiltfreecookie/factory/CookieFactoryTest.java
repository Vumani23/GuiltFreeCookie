package za.ac.cput.guiltfreecookie.factory;

import org.junit.jupiter.api.MethodOrderer;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;
import za.ac.cput.guiltfreecookie.domain.Cookie;
import za.ac.cput.guiltfreecookie.domain.CookieCategory;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNull;

@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
class CookieFactoryTest {

    @Test
    @Order(1)
    void testCreateCookieSuccess() {
        Cookie cookie = CookieFactory.createCookie(
                "CK001",
                CookieCategory.LOW_SUGAR,
                "Chocolate Stevia Cookie",
                "Almond flour, Cocoa powder, Stevia, Coconut oil",
                "Tree Nuts",
                25.00
        );

        assertNotNull(cookie);
        assertEquals("CK001", cookie.getCookieId());
        assertEquals(CookieCategory.LOW_SUGAR, cookie.getCategory());
        assertEquals("Chocolate Stevia Cookie", cookie.getDescription());
        assertEquals("Almond flour, Cocoa powder, Stevia, Coconut oil", cookie.getIngredients());
        assertEquals("Tree Nuts", cookie.getAllergies());
        assertEquals(25.00, cookie.getPrice());
        assertFalse(cookie.isArchived(), "A newly-factoried cookie should not be archived");
        assertNull(cookie.getImage(), "The factory does not assign an image; that happens on upload");
    }

    @Test
    @Order(2)
    void testCreateCookieWithNullDescription() {
        Cookie cookie = CookieFactory.createCookie(
                "CK002",
                CookieCategory.CLASSIC,
                null,
                "Flour, Sugar",
                "Gluten",
                15.00
        );

        assertNull(cookie);
    }

    @Test
    @Order(3)
    void testCreateCookieWithEmptyDescription() {
        Cookie cookie = CookieFactory.createCookie(
                "CK003",
                CookieCategory.CLASSIC,
                "",
                "Flour, Sugar",
                "Gluten",
                15.00
        );

        assertNull(cookie);
    }

    @Test
    @Order(4)
    void testCreateCookieWithEmptyIngredients() {
        Cookie cookie = CookieFactory.createCookie(
                "CK004",
                CookieCategory.CLASSIC,
                "Classic Butter Cookie",
                "",
                "Gluten",
                15.00
        );

        assertNull(cookie);
    }

    @Test
    @Order(5)
    void testCreateCookieWithEmptyAllergies() {
        Cookie cookie = CookieFactory.createCookie(
                "CK005",
                CookieCategory.CLASSIC,
                "Classic Butter Cookie",
                "Flour, Sugar",
                "",
                15.00
        );

        assertNull(cookie);
    }

    @Test
    @Order(6)
    void testCreateCookieWithNegativePrice() {
        Cookie cookie = CookieFactory.createCookie(
                "CK006",
                CookieCategory.VEGAN,
                "Vegan Oatmeal Cookie",
                "Oats, Coconut oil",
                "None",
                -5.00
        );

        assertNull(cookie);
    }

    @Test
    @Order(7)
    void testCreateCookieWithZeroPrice() {
        Cookie cookie = CookieFactory.createCookie(
                "CK007",
                CookieCategory.VEGAN,
                "Vegan Oatmeal Cookie",
                "Oats, Coconut oil",
                "None",
                0.00
        );

        assertNull(cookie);
    }
}
