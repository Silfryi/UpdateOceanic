package silfryi.updateoceanic.util;

public class ColorShiftHelper {

    public static int getWhiteAppliedWaterColor(int inputColor) {
        //Get the color and turn it into RBG int values
        int f = (inputColor >> 16 & 255);
        int f1 = (inputColor >> 8 & 255);
        int f2 = (inputColor & 255);

        //Change coloring by the default water color, being about 50 : 74 : 244 RGB
        int of = (int)(f * (50/255f)) << 16;
        int of1 = (int)(f1 * (74/255f)) << 8;
        int of2 = (int)(f2 * (244/255f));

        //Return the composited number
        return of | of1 | of2;
    }

    public static int getFogShiftFromColor(int inputColor) {
        //Get the color and turn it into RBG int values
        int f = (inputColor >> 16 & 255);
        int f1 = (inputColor >> 8 & 255);
        int f2 = (inputColor & 255);

        //Process color according to the standard water color : fog color ratios, being 5/63 : 5/118 : 51/228
        int of = (int)(f * (5/63f)) << 16;
        int of1 = (int)(f1 * (5/118f)) << 8;
        int of2 = (int)(f2 * (51/228f));

        //Return the composited number
        return of | of1 | of2;
    }
}
