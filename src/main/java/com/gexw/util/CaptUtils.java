package com.gexw.util;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.Base64;
import java.util.HashMap;
import java.util.Map;
import java.util.Random;

/**
 * @author zhaoran
 * @title: CaptUtils
 * @projectName 
 * @description: TODO
 * @date 
 * @sign:
 */
public class CaptUtils {
    public static Map<String, String> createCaptString() throws IOException {
        Map<String,String> map=new HashMap<>();
        int length = 4;
        String str = "ABCDEFGHJKMNOPQRSTUVWXYZabcdefghjkmnopqrstuvwxyz1234567890";
        StringBuilder sb = new StringBuilder();
        int len = str.length() - 1;
        double r;
        for (int i = 0; i < length; i++) {
            r = (Math.random()) * len;
            sb.append(str.charAt((int) r));
        }
        map.put("captCodeValue",sb.toString());
        int width = 80;

        int height = 25;

        BufferedImage img = new BufferedImage(width, height,
                BufferedImage.TYPE_INT_RGB);


        Graphics g = img.getGraphics();


        g.setColor(Color.WHITE);

        g.fillRect(0, 0, width, height);

        Random rd=new Random();
        for (int i = 0; i < 50; i++) {
            g.setColor(new Color(rd.nextInt(100) + 155, rd.nextInt(100) + 155,
                    rd.nextInt(100) + 155));
            g.drawLine(rd.nextInt(width), rd.nextInt(height),
                    rd.nextInt(width), rd.nextInt(height));
        }


        g.setColor(Color.GRAY);

        g.drawRect(0, 0, width - 1, height - 1);


        Font[] fonts = { new Font("隶书", Font.BOLD, 18),
                new Font("楷体", Font.BOLD, 18), new Font("宋体", Font.BOLD, 18),
                new Font("幼圆", Font.BOLD, 18) };

        for (int i = 0; i < length; i++) {

            g.setColor(new Color(rd.nextInt(150), rd.nextInt(150), rd
                    .nextInt(150)));

            g.setFont(fonts[rd.nextInt(fonts.length)]);

            g.drawString(sb.charAt(i) + "", width / sb.length() * i
                    + 2, 18);

        }


        g.dispose();
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        ImageIO.write(img,"png",baos);

        String s = Base64.getEncoder().encodeToString(baos.toByteArray());
        s = s.replaceAll("\n","")
                .replaceAll("\r","");
        String base64Value="data:image/jpg;base64," + s;
        map.put("base64Value",base64Value);
        return map;
    }
}
