import java.awt.Color;

/**
 * Standardized color palette for consistent UI theming across the application.
 * Supports both light and dark mode through a dual-tone system where each color
 * provides variants optimized for different background contexts.
 *
 * Each enum constant contains an array of color variants:
 *
 *   Index 0: Color variant optimized for light mode backgrounds</li>
 *   Index 1: Color variant optimized for dark mode backgrounds</li>
 *   Additional indices (if present): Special purpose variants</li>
 *
 *
 * Usage example:
 *
 * // Set global theme mode
 * StandardColor.setIsLightMode(true);
 *
 * // Get appropriate color based on current mode
 * Color primaryButtonColor = StandardColor.Blue.getColorMode();
 *
 * // Get opposite mode variant (for contrast elements)
 * Color textOnPrimaryButton = StandardColor.Blue.getOppositeColorMode();
 *
 * // Get specific variant by index
 * Color specialBorder = StandardColor.Blue.getColor(2);
 *
 */
public enum StandardColor {

    // Color code source from Apple
    // Source URL: https://developer.apple.com/design/human-interface-guidelines/color#Specifications

    /**
     * Red color palette - typically used for critical actions, errors, and destructive operations.
     * Values: [light mode variant, dark mode variant]
     */
    Red(new Color[] {
            new Color(255,56,60), new Color(255,66,69)
    }),

    /**
     * Orange color palette - typically used for warnings and secondary actions.
     * Values: [light mode variant, dark mode variant]
     */
    Orange(new Color[] {
            new Color(255,141,60), new Color(255,146,48)
    }),

    /**
     * Yellow color palette - typically used for highlights, warnings, and attention-grabbing elements.
     * Values: [light mode variant, dark mode variant]
     */
    Yellow(new Color[]{
            new Color(255,204,0),new Color(255,214,0)
    }),

    /**
     * Green color palette - typically used for success states, confirmations, and positive actions.
     * Values: [light mode variant, dark mode variant]
     */
    Green(new Color[]{
            new Color(52,199,89),new Color(48,209,88)
    }),

    /**
     * Mint color palette - typically used for informational elements and subtle accents.
     * Values: [light mode variant, dark mode variant]
     */
    Mint(new Color[]{
            new Color(0,200,179),new Color(0,218,195)
    }),

    /**
     * Teal color palette - typically used for water-themed elements and secondary accents.
     * Values: [light mode variant, dark mode variant]
     */
    Teal(new Color[]{
            new Color(0,195,208),new Color(0,210,224)
    }),

    /**
     * Cyan color palette - typically used for digital-themed interfaces and highlights.
     * Values: [light mode variant, dark mode variant]
     */
    Cyan(new Color[]{
            new Color(0,192,232),new Color(60,211,254)
    }),

    /**
     * Blue color palette - typically used for primary actions, links, and interactive elements.
     * Values: [light mode variant, dark mode variant, special accent variant]
     */
    Blue(new Color[]{
            new Color(0,136,255),new Color(0,145,255),new Color(0,0,139)
    }),

    /**
     * Indigo color palette - typically used for tertiary actions and specialized UI elements.
     * Values: [light mode variant, dark mode variant]
     */
    Indigo(new Color[]{
            new Color(97,85,245),new Color(107,93,255)
    }),

    /**
     * Purple color palette - typically used for creative elements and special categories.
     * Values: [light mode variant, dark mode variant]
     */
    Purple(new Color[]{
            new Color(203,48,224),new Color(219,52,242)
    }),

    /**
     * Pink color palette - typically used for notifications, badges, and gender-specific elements.
     * Values: [light mode variant, dark mode variant]
     */
    Pink(new Color[]{
            new Color(255,45,85),new Color(255,55,95)
    }),

    /**
     * Brown color palette - typically used for natural elements and earthy themes.
     * Values: [light mode variant, dark mode variant]
     */
    Brown(new Color[]{
            new Color(172,127,94),new Color(183,138,102)
    }),

    /**
     * Highest contrast grey palette - used for primary backgrounds and text.
     * Values: [light mode variant (white), dark mode variant (black)]
     */
    GreyHighest(new Color[]{
            new Color(255,255,255),new Color(0,0,0)
    }),

    /**
     * Middle contrast grey palette - used for secondary backgrounds and borders.
     * Values: [light mode variant (light grey), dark mode variant (dark grey)]
     */
    GreyMiddle(new Color[]{
            new Color(242,242,247) ,new Color(28,28,30)
    }),

    /**
     * Lower contrast grey palette - used for disabled states and subtle elements.
     * Values: [light mode variant (medium grey), dark mode variant (medium-dark grey)]
     */
    GreyLower(new Color[]{
            new Color(209,209,214) ,new Color(58,58,60)
    }),

    /**
     * Dark blue color palette - used for specialized UI elements requiring strong contrast.
     * Values: [light mode variant, dark mode variant]
     */
    DarkBlue(new Color[]{
            new Color(0,0,139), new Color(0,0,160)
    }),

    /**
     * Pure white color - consistent across both light and dark modes.
     * Values: [white, white]
     */
    White(new Color[]{
            new Color(255,255,255), new Color(255,255,255)
    }),

    /**
     * Pure black color - consistent across both light and dark modes.
     * Values: [black, black]
     */
    Black(new Color[]{
            new Color(0,0,0), new Color(0,0,0)
    }),

    /**
     * Standard gray color - consistent medium gray for special cases.
     * Values: [gray, gray]
     */
    Gray(new Color[]{
            new Color(128,128,128), new Color(128,128,128)
    });

    /**
     * Global flag determining the current UI theme mode.
     * When true, light mode variants are used; when false, dark mode variants are used.
     * This affects all {@link #getColorMode()} and {@link #getOppositeColorMode()} calls.
     */
    private static boolean isLightMode = true;

    /**
     * Array of color variants for this palette entry.
     * Typically contains at least two colors: [light mode variant, dark mode variant].
     */
    private final Color[] colorElement;

    /**
     * Constructs a new StandardColor enum constant with the specified color variants.
     *
     * @param elements Array of color variants for this palette entry.
     *                 Index 0 should be the light mode variant, index 1 the dark mode variant.
     */
    StandardColor(Color[] elements){
        this.colorElement = elements;
    }

    /**
     * Retrieves a color variant by specific index, regardless of current theme mode.
     *
     * @param index Position in the color variant array (0-based)
     * @return Color at the specified index, or the first variant if index is out of bounds
     */
    public Color getColor(int index){
        if(index < 0 || index >= colorElement.length){
            return colorElement[0];
        }
        return colorElement[index];
    }

    /**
     * Retrieves the color variant appropriate for the current theme mode.
     * Returns light mode variant (index 0) when in light mode, dark mode variant (index 1) when in dark mode.
     *
     * @return Color optimized for current theme mode
     */
    public Color getColorMode(){
        return isLightMode ? colorElement[0] : colorElement[1];
    }

    /**
     * Retrieves the color variant appropriate for elements that need contrast against
     * the current theme mode's primary variant. For example, text on a colored background.
     * Returns dark mode variant (index 1) when in light mode, light mode variant (index 0) when in dark mode.
     *
     * @return Color optimized for contrast with current theme mode
     */
    public Color getOppositeColorMode(){
        return isLightMode ? colorElement[1] : colorElement[0];
    }

    /**
     * Sets the global theme mode for all StandardColor instances.
     *
     * @param status true to enable light mode, false to enable dark mode
     */
    public static void setIsLightMode(boolean status){
        isLightMode = status;
    }

    /**
     * Gets the current global theme mode setting.
     *
     * @return true if light mode is active, false if dark mode is active
     */
    public static boolean getIsLightMode(){
        return isLightMode;
    }
}