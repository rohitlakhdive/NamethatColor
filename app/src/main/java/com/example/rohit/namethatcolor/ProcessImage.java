package com.example.rohit.namethatcolor;

import android.graphics.Bitmap;
import android.graphics.Color;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.Iterator;
import java.util.LinkedHashMap;

/**
 * Created by Rohit on 12/18/2017.
 */

class ProcessImage {

    private static Bitmap showPatch(Bitmap bitmap) {

        int widthb, heightb;
        int width, height;
        int xp, yp;
        int xb, yb;

        int border;
        int color = RGBIt(patchIt(bitmap));

        if (MyColor.delta(new MyColor(HexRGB(color)), new MyColor(HexRGB(Color.BLACK))) < MyColor.delta(new MyColor(HexRGB(color)), new MyColor(HexRGB(Color.WHITE))))
            border = Color.WHITE;
        else
            border = Color.BLACK;

        width = bitmap.getWidth();
        height = bitmap.getHeight();

        if (width < height) {

            xp = width;
            width = height;
            height = xp;

            yp = width / 2 - width / 40;
            xp = height / 2 - width / 40;

        } else {

            xp = width / 2 - width / 40;
            yp = height / 2 - width / 40;

        }

        xb = xp - width / 100;
        yb = yp - width / 100;

        widthb = xp + width / 20 + width / 100;
        heightb = yp + width / 20 + width / 100;

        bitmap = bitmap.copy(Bitmap.Config.ARGB_8888, true);

        height = yp + width / 20;
        width = xp + width / 20;


        for (int x = xb; x < widthb; x++) {

            for (int y = yb; y < heightb; y++) {

                bitmap.setPixel(x, y, border);

            }

        }

        for (int x = xp; x < width; x++) {

            for (int y = yp; y < height; y++) {

                bitmap.setPixel(x, y, color);

            }

        }

        return bitmap;

    }

    private static Bitmap patchIt(Bitmap bitmap) {

        Bitmap patch;

        int width, height;
        int xp, yp;

        width = bitmap.getWidth();
        height = bitmap.getHeight();

        if (width < height) {

            xp = width;
            width = height;
            height = xp;

            yp = width / 2 - width / 40;
            xp = height / 2 - width / 40;

        } else {

            xp = width / 2 - width / 40;
            yp = height / 2 - width / 40;

        }

        patch = Bitmap.createBitmap(bitmap, xp, yp, width / 20, width / 20);

        return patch;

    }

    private static int RGBIt(Bitmap patch) {

        int colorPixel;

        Bitmap pixel;

        pixel = Bitmap.createScaledBitmap(patch, 1, 1, true);

        colorPixel = pixel.getPixel(0, 0);

        return colorPixel;

    }

    private static String HexRGB(int colorPixel) {

        int R, G, B;

        R = Color.red(colorPixel);
        G = Color.green(colorPixel);
        B = Color.blue(colorPixel);

        return String.format("%02x%02x%02x", R, G, B);

    }

    static Result targetedColor(Bitmap image) {

        String color[] = MyColor.getName(HexRGB(RGBIt(patchIt(image))));

        if (!color[0].equals(""))
            color[0] = "Shade : " + color[0];

        if (!color[1].equals(""))
            color[1] = "Family : " + color[1];

        return new Result(showPatch(image), color[0], color[1]);

    }

    static Result gridColor(Bitmap image) {

        int count;
        int factor;

        String[] color;
        LinkedHashMap<String, Integer> colors = new LinkedHashMap<>();


        if (image.getWidth() > image.getHeight()) {

            factor = image.getWidth() / 10;

        } else {

            factor = image.getHeight() / 10;

        }

        Bitmap imageDown10 = Bitmap.createScaledBitmap(image, image.getWidth() / factor, image.getHeight() / factor, true);

        for (int x = 0; x < imageDown10.getWidth(); x++) {

            for (int y = 0; y < imageDown10.getHeight(); y++) {

                color = MyColor.getName(HexRGB(imageDown10.getPixel(x, y)));

                if (colors.containsKey(color[1])) {

                    count = colors.get(color[1]) + 1;
                    colors.remove(color[1]);
                    colors.put(color[1], count);

                } else {

                    colors.put(color[1], 1);

                }

            }

        }

        String[] resultColor = colorMap(colors);

        if (!resultColor[0].equals(""))
            if (!resultColor[1].equals(""))
                resultColor[0] = "Color 1 : " + resultColor[0];
            else
                resultColor[0] = "Color : " + resultColor[0];

        if (!resultColor[1].equals(""))
            resultColor[1] = "Color 2 : " + resultColor[1];

        return new Result(imageDown10, resultColor[0], resultColor[1]);

    }

    private static String[] colorMap(LinkedHashMap<String, Integer> colors) {

        String[] resultColors = {"", ""};

        ArrayList<LinkedHashMap.Entry<String, Integer>> entries = new ArrayList<>(colors.entrySet());

        Collections.sort(entries, new Comparator<LinkedHashMap.Entry<String, Integer>>() {
            public int compare(LinkedHashMap.Entry<String, Integer> a, LinkedHashMap.Entry<String, Integer> b) {
                return b.getValue().compareTo(a.getValue());
            }
        });

        LinkedHashMap<String, Integer> sortedMap = new LinkedHashMap<>();

        for (LinkedHashMap.Entry<String, Integer> entry : entries) {
            sortedMap.put(entry.getKey(), entry.getValue());
        }

        Iterator it = sortedMap.entrySet().iterator();
        LinkedHashMap.Entry pair;

        if (it.hasNext()) {
            pair = (LinkedHashMap.Entry) it.next();
            resultColors[0] = pair.getKey().toString();
            it.remove();
        }

        if (it.hasNext()) {
            pair = (LinkedHashMap.Entry) it.next();
            resultColors[1] = pair.getKey().toString();
            it.remove();
        }

        return resultColors;

    }

}