package UI;

import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import java.util.ArrayList;

public class StringtoPixelConverter {

    public int[][][] convert(String s, Color color) {
        int string_length = s.length();
        String[] out_string = new String[string_length];
        int[][][] count = new int[string_length][7][7];
        int[][] before = new int[70][70];
        int num;
        for (int i = 0; i < string_length; i++) { // string 한글자씩 자름
            if (i == string_length - 1)
                out_string[i] = s.substring(i);
            else
                out_string[i] = s.substring(i, i + 1);
        }

        for (int x = 0; x < string_length; x++) {
            BufferedImage bufferedImage = new BufferedImage(70, 70, BufferedImage.TYPE_INT_ARGB);
            Graphics2D g2d = bufferedImage.createGraphics();
            int charx = out_string[x].charAt(0);
            if (charx > 122) {
                g2d.setColor(Color.WHITE);
                g2d.setFont(g2d.getFont().deriveFont(85f));
                g2d.drawString(out_string[x], -8, 65);
            } else {
                g2d.setColor(Color.WHITE);
                g2d.setFont(g2d.getFont().deriveFont(85f));
                g2d.drawString(out_string[x], 7, 65);
            }
            g2d.dispose();

            for (int i = 0; i < bufferedImage.getHeight(); i++) {// 글자 70x70 픽셀로 변경
                for (int j = 0; j < bufferedImage.getWidth(); j++) {
                    Color c = new Color(bufferedImage.getRGB(j, i));
                    num = Math.min(Math.min(c.getRed(), c.getBlue()), c.getGreen());

                    if (num < 180)
                        before[j][i] = 0;
                    else
                        before[j][i] = 1;
                }
            }
            int i = 0, j = 0;

            for (i = 0; i < before.length; i++) // 한글자씩 자른 string 7x7로 변경
                for (j = 0; j < before[i].length; j++) {
                    if (i >= 0 && i <= 9) {
                        if (j >= 0 && j <= 9)
                            count[x][0][0] += before[j][i];
                        else if (j >= 10 && j <= 19)
                            count[x][0][1] += before[j][i];
                        else if (j >= 20 && j <= 29)
                            count[x][0][2] += before[j][i];
                        else if (j >= 30 && j <= 39)
                            count[x][0][3] += before[j][i];
                        else if (j >= 40 && j <= 49)
                            count[x][0][4] += before[j][i];
                        else if (j >= 50 && j <= 59)
                            count[x][0][5] += before[j][i];
                        else if (j >= 60 && j <= 69)
                            count[x][0][6] += before[j][i];
                    } else if (i >= 10 && i <= 19) {
                        if (j >= 0 && j <= 9)
                            count[x][1][0] += before[j][i];
                        else if (j >= 10 && j <= 19)
                            count[x][1][1] += before[j][i];
                        else if (j >= 20 && j <= 29)
                            count[x][1][2] += before[j][i];
                        else if (j >= 30 && j <= 39)
                            count[x][1][3] += before[j][i];
                        else if (j >= 40 && j <= 49)
                            count[x][1][4] += before[j][i];
                        else if (j >= 50 && j <= 59)
                            count[x][1][5] += before[j][i];
                        else if (j >= 60 && j <= 69)
                            count[x][1][6] += before[j][i];
                    } else if (i >= 20 && i <= 29) {
                        if (j >= 0 && j <= 9)
                            count[x][2][0] += before[j][i];
                        else if (j >= 10 && j <= 19)
                            count[x][2][1] += before[j][i];
                        else if (j >= 20 && j <= 29)
                            count[x][2][2] += before[j][i];
                        else if (j >= 30 && j <= 39)
                            count[x][2][3] += before[j][i];
                        else if (j >= 40 && j <= 49)
                            count[x][2][4] += before[j][i];
                        else if (j >= 50 && j <= 59)
                            count[x][2][5] += before[j][i];
                        else if (j >= 60 && j <= 69)
                            count[x][2][6] += before[j][i];
                    } else if (i >= 30 && i <= 39) {
                        if (j >= 0 && j <= 9)
                            count[x][3][0] += before[j][i];
                        else if (j >= 10 && j <= 19)
                            count[x][3][1] += before[j][i];
                        else if (j >= 20 && j <= 29)
                            count[x][3][2] += before[j][i];
                        else if (j >= 30 && j <= 39)
                            count[x][3][3] += before[j][i];
                        else if (j >= 40 && j <= 49)
                            count[x][3][4] += before[j][i];
                        else if (j >= 50 && j <= 59)
                            count[x][3][5] += before[j][i];
                        else if (j >= 60 && j <= 69)
                            count[x][3][6] += before[j][i];
                    } else if (i >= 40 && i <= 49) {
                        if (j >= 0 && j <= 9)
                            count[x][4][0] += before[j][i];
                        else if (j >= 10 && j <= 19)
                            count[x][4][1] += before[j][i];
                        else if (j >= 20 && j <= 29)
                            count[x][4][2] += before[j][i];
                        else if (j >= 30 && j <= 39)
                            count[x][4][3] += before[j][i];
                        else if (j >= 40 && j <= 49)
                            count[x][4][4] += before[j][i];
                        else if (j >= 50 && j <= 59)
                            count[x][4][5] += before[j][i];
                        else if (j >= 60 && j <= 69)
                            count[x][4][6] += before[j][i];
                    } else if (i >= 50 && i <= 59) {
                        if (j >= 0 && j <= 9)
                            count[x][5][0] += before[j][i];
                        else if (j >= 10 && j <= 19)
                            count[x][5][1] += before[j][i];
                        else if (j >= 20 && j <= 29)
                            count[x][5][2] += before[j][i];
                        else if (j >= 30 && j <= 39)
                            count[x][5][3] += before[j][i];
                        else if (j >= 40 && j <= 49)
                            count[x][5][4] += before[j][i];
                        else if (j >= 50 && j <= 59)
                            count[x][5][5] += before[j][i];
                        else if (j >= 60 && j <= 69)
                            count[x][5][6] += before[j][i];
                    } else if (i >= 60 && i <= 69) {
                        if (j >= 0 && j <= 9)
                            count[x][6][0] += before[j][i];
                        else if (j >= 10 && j <= 19)
                            count[x][6][1] += before[j][i];
                        else if (j >= 20 && j <= 29)
                            count[x][6][2] += before[j][i];
                        else if (j >= 30 && j <= 39)
                            count[x][6][3] += before[j][i];
                        else if (j >= 40 && j <= 49)
                            count[x][6][4] += before[j][i];
                        else if (j >= 50 && j <= 59)
                            count[x][6][5] += before[j][i];
                        else if (j >= 60 && j <= 69)
                            count[x][6][6] += before[j][i];
                    }
                }
            if (charx > 122) {
                for (i = 0; i < 7; i++)
                    for (j = 0; j < 7; j++) {
                        if (count[x][i][j] >= 20)
                            count[x][i][j] = color.getRGB();
                        else
                            count[x][i][j] = Color.BLACK.getRGB();
                    }
            } else {
                for (i = 0; i < 7; i++)
                    for (j = 0; j < 7; j++) {
                        if (count[x][i][j] >= 25)
                            count[x][i][j] = color.getRGB();
                        else
                            count[x][i][j] = Color.BLACK.getRGB();
                    }
            }

        }

        return count;
    }
}
