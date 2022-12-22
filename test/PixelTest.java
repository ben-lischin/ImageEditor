import org.junit.Before;
import org.junit.Test;

import model.pixel.ColorPixel;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;

/**
 * Tests the Pixel class.
 */
public class PixelTest {
  ColorPixel p1;
  ColorPixel p2;
  ColorPixel p3;
  ColorPixel invalid;

  @Before
  public void init() {
    this.p1 = new ColorPixel(10, 209, 39, 1);
    this.p2 = new ColorPixel(187, 20, 255, 1);
    this.p3 = new ColorPixel(187, 20, 255, 1);
  }

  @Test
  public void testValidConstruction() {
    try {
      this.p1 = new ColorPixel(10, 209, 39, 1);
      assertEquals(10, this.p1.getRed());
    } catch (IllegalArgumentException e) {
      fail("should not have thrown exception; this was a valid pixel construction");
    }
    try {
      this.p1 = new ColorPixel(0, 0, 0, 1);
      assertEquals(0, this.p1.getRed());
    } catch (IllegalArgumentException e) {
      fail("should not have thrown exception; this was a valid pixel construction");
    }
    try {
      this.p1 = new ColorPixel(255, 255, 255, 1);
      assertEquals(255, this.p1.getRed());
    } catch (IllegalArgumentException e) {
      fail("should not have thrown exception; this was a valid pixel construction");
    }
  }

  @Test
  public void testInvalidConstruction() {
    this.invalid = new ColorPixel(-10, 10, 10, 1);
    assertEquals(0, this.invalid.getRed());

    this.invalid = new ColorPixel(10, -10, 10, 1);
    assertEquals(0, this.invalid.getGreen());

    this.invalid = new ColorPixel(10, 10, -10, 1);
    assertEquals(0, this.invalid.getBlue());

    this.invalid = new ColorPixel(10, 10, 10, -10);
    assertEquals(0, this.invalid.getAlpha());

    this.invalid = new ColorPixel(300, 100, 100, 1);
    assertEquals(255, this.invalid.getRed());

    this.invalid = new ColorPixel(100, 300, 100, 1);
    assertEquals(255, this.invalid.getGreen());

    this.invalid = new ColorPixel(100, 100, 300, 1);
    assertEquals(255, this.invalid.getBlue());

    this.invalid = new ColorPixel(10, 10, 10, 2);
    assertEquals(1, this.invalid.getAlpha());

  }

  @Test
  public void testGetRed() {
    assertEquals(10, this.p1.getRed());
    assertEquals(187, this.p2.getRed());
    assertEquals(187, this.p3.getRed());
  }

  @Test
  public void testGetGreen() {
    assertEquals(209, this.p1.getGreen());
    assertEquals(20, this.p2.getGreen());
    assertEquals(20, this.p3.getGreen());
  }

  @Test
  public void testGetBlue() {
    assertEquals(39, this.p1.getBlue());
    assertEquals(255, this.p2.getBlue());
    assertEquals(255, this.p3.getBlue());
  }

  @Test
  public void testEquals() {
    assertFalse(this.p1.equals(this.p2));
    assertFalse(this.p2.equals(this.p1));
    assertFalse(this.p1.equals(this.p3));

    assertTrue(this.p2.equals(this.p2));
    assertTrue(this.p2.equals(this.p3));
    assertTrue(this.p3.equals(this.p2));
  }

  @Test
  public void testHashCode() {
    assertEquals(17486150, this.p1.hashCode());
    assertEquals(29037854, this.p2.hashCode());
    assertEquals(29037854, this.p3.hashCode());
  }
}