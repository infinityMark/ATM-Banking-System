import java.awt.Color;

public enum StandardColor {
    Red(new Color[] {
            new Color(255,56,60), new Color(255,66,69)
    }),
    Orange(new Color[] {
            new Color(255,141,60), new Color(255,146,48)
    }),
    Yellow(new Color[]{
            new Color(255,204,0),new Color(255,214,0)
    }),
    Green(new Color[]{
            new Color(52,199,89),new Color(48,209,88)
    }),
    Mint(new Color[]{
            new Color(0,200,179),new Color(0,218,195)
    }),
    Teal(new Color[]{
            new Color(0,195,208),new Color(0,210,224)
    }),
    Cyan(new Color[]{
            new Color(0,192,232),new Color(60,211,254)
    }),
    Blue(new Color[]{
            new Color(0,136,255),new Color(0,145,255),new Color(0,0,139)
    }),
    Indigo(new Color[]{
            new Color(97,85,245),new Color(107,93,255)
    }),
    Purple(new Color[]{
            new Color(203,48,224),new Color(219,52,242)
    }),
    Pink(new Color[]{
            new Color(255,45,85),new Color(255,55,95)
    }),
    Brown(new Color[]{
            new Color(172,127,94),new Color(183,138,102)
    }),
    GreyHighest(new Color[]{
            new Color(255,255,255),new Color(0,0,0)
    }),
    GreyMiddle(new Color[]{
            new Color(242,242,247) ,new Color(28,28,30)
    }),
    GreyLower(new Color[]{
            new Color(209,209,214) ,new Color(58,58,60)
    });

    private static boolean isLightMode = true;
    private final Color[] colorElement;

    StandardColor(Color[] elements){
        this.colorElement = elements;
    }

    //set color to you need, for specially need
    public Color getColor(int index){
        if(index < 0 || index >= colorElement.length){
            return colorElement[0];
        }

        return colorElement[index];
    }

    public Color getColorMode(){
        return isLightMode ? colorElement[0] : colorElement[1];
    }
    public Color getOppositeColorMode(){
        return isLightMode ? colorElement[1] : colorElement[0];
    }
    public static void setIsLightMode(boolean status){
        isLightMode = status;
    }
    public static boolean getIsLightMode(){
        return isLightMode;
    }
}